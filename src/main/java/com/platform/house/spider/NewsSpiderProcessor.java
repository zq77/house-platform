package com.platform.house.spider;

import com.platform.house.form.NewsForm;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Office on 2019/1/21.
 */
public class NewsSpiderProcessor implements PageProcessor {

    private static final Logger logger = Logger.getLogger(NewsSpiderProcessor.class);

    private Site site = Site.me().setCharset("UTF-8").setRetryTimes(3).setSleepTime(1000).setTimeOut(10000).setUserAgent(
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");


    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        String[] gzhDomainList = {"mp.weixin.qq.com"};
        String[] jrttDomainList = {"toutiao.com", "m.toutiaocdn.com", "m.toutiaocdn.cn"};
        NewsForm newsForm = null;
        if (this.isWhiteDomain(page.getUrl().toString(), gzhDomainList)) {
            newsForm = grabGZHArticle(html);
        } else if (this.isWhiteDomain(page.getUrl().toString(), jrttDomainList)) {
            newsForm = grabJRTTArticle(html);
        } else {
            page.setSkip(true);
        }
        page.putField("newsForm", newsForm);
    }

    private Boolean isWhiteDomain(String grabUrl, String[] whiteDomainList) {
        Boolean isWhiteDomain = false;
        for(String domain : whiteDomainList) {
            if (grabUrl.indexOf(domain) > 0) {
                isWhiteDomain = true;
                break;
            }
        }
        return isWhiteDomain;
    }

    private NewsForm grabGZHArticle(Html html) {
        NewsForm newsForm = new NewsForm();
        Selectable richMediaSelector = html.$(".rich_media_area_primary_inner");
        String newsTitle = richMediaSelector.$(".rich_media_title", "text").get();
        newsForm.setTitle(trimStr(newsTitle));
        String original = richMediaSelector.$("#meta_content #copyright_logo", "text").get();
        newsForm.setOriginal(trimStr(original));
        String author = richMediaSelector.$("#meta_content #profileBt a", "text").get();
        newsForm.setAuthor(trimStr(author));
        String authorNick = richMediaSelector.css("#meta_content .rich_media_meta.rich_media_meta_text:nth-child(2)", "text").get();
        newsForm.setAuthorNick(trimStr(authorNick));
        Selectable selectable = richMediaSelector.$("em#publish_time");
        String publishDate = richMediaSelector.$("#publish_time", "text").get();
        newsForm.setPublishDate(trimStr(publishDate));
        String mediaContent = richMediaSelector.$("#js_content").get();
        newsForm.setContent(mediaContent.replaceAll("data-src", "src"));
        List<String> imgUrlList = richMediaSelector.$("#js_content img", "data-src").all();
        if (imgUrlList !=  null && !imgUrlList.isEmpty()) {
            newsForm.setCoverImage(imgUrlList.get(0));
        }
        return newsForm;
    }

    private Pattern BASE_DATA_PATTERN = Pattern.compile("BASE_DATA.*=(.+);</script>");

    private NewsForm grabJRTTArticle(Html html) {
        NewsForm newsForm = new NewsForm();
        String baseData = "";
        List<String> scriptList = html.$("script").all();
        for (String script : scriptList) {
            if (script.indexOf("var BASE_DATA =") > 0) {
                baseData = script.replaceAll("((<script>var BASE_DATA =)|(;</script>)|(\n\\s+))", "");
                baseData = getContet(baseData, "articleInfo:", ",mediaInfo:");
                logger.info(baseData);
                break;
            }
        }
        if(StringUtils.isNotBlank(baseData)) {
            String title = getContet(baseData, "title:", ",content:");
            String content = getContet(baseData, "content:", ",groupId:");
            String isOriginal = getContet(baseData, "isOriginal:", ",source:");
            String source = getContet(baseData, "source:", ",time:");
            String time = getContet(baseData, "time:", "},tagInfo:");
            String coverImage = getContet(baseData, "coverImg:", "},commentInfo");
            newsForm.setTitle(trimStr(title));
            newsForm.setContent(StringEscapeUtils.unescapeHtml(content));
            newsForm.setAuthor(trimStr(source));
            newsForm.setPublishDate(trimStr(time));
            newsForm.setCoverImage(coverImage);
            if ("true".equals(isOriginal)) {
                newsForm.setOriginal("原创：");
            }
        }

//        String newsTitle = richMediaSelector.$(".article-title", "text").get();
//        newsForm.setTitle(trimStr(newsTitle));
//        String original = richMediaSelector.$(".article-sub .original", "text").get();
//        newsForm.setOriginal(trimStr(original));
//        String author = "";
//        String publishDate = "";
//        if (StringUtils.isNotBlank(original)) {
//            author = richMediaSelector.css(".article-sub span:n-child(2)", "text").get();
//            publishDate = richMediaSelector.css(".article-sub span:n-child(3)", "text").get();
//        } else {
//            author = richMediaSelector.css(".article-sub span:n-child(1)", "text").get();
//            publishDate = richMediaSelector.css(".article-sub span:n-child(2)", "text").get();
//        }
//        newsForm.setAuthor(trimStr(author));
//        newsForm.setPublishDate(trimStr(publishDate));
//        String mediaContent = richMediaSelector.$(".article-content").get();
//        newsForm.setContent(mediaContent);
        return newsForm;
    }

    private String getContet(String originStr, String startStr, String endStr) {
        Integer start = originStr.indexOf(startStr);
        Integer end = originStr.indexOf(endStr);
        String returnStr = null;
        try {
            returnStr = originStr.substring(start + startStr.length(), end).replaceAll("\'", "");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return returnStr;
    }

    private String trimStr(String inputStr) {
        String returnStr = "";
        if (StringUtils.isNotBlank(inputStr)) {
            returnStr = inputStr.trim();
        }
        return returnStr;
    }

    public static void main(String[] args) {
        NewsSpiderProcessor newsSpiderProcessor = new NewsSpiderProcessor();
        NewsSpiderPipeline newsSpiderPipeline = new NewsSpiderPipeline();
        // String grabUrl = "https://mp.weixin.qq.com/s/YUEAVeleVkQ9YS6LwukLuw";
        String grabUrl = "https://m.toutiaocdn.cn/group/6664121237922382339/?app=news_article&timestamp=1553049557&group_id=6664121237922382339";
        Spider.create(newsSpiderProcessor).addUrl(grabUrl)
                .addPipeline(newsSpiderPipeline).thread(1).run();
        NewsForm newsForm = newsSpiderPipeline.getNewsForm();
        logger.info(newsForm);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
