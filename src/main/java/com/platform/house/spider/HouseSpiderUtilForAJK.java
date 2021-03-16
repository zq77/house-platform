package com.platform.house.spider;

import com.platform.house.form.HouseInfoForm;
import com.platform.house.form.HouseSpiderForm;
import com.platform.house.form.ResoldHouseInfoForm;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Office on 2018/12/31.
 */
public class HouseSpiderUtilForAJK {
    private static final Logger logger = Logger.getLogger(HouseSpiderUtilForAJK.class);

    public static void grabListPage(Page page, HouseSpiderForm houseSpiderForm) {
        Selectable s = page.getHtml().$("body");
        logger.info(s);
        List<String> allDetailLinks = page.getHtml().$("body .list-results .key-list .item-mod .lp-name").links().regex(houseSpiderForm.getDetailUrlReg()).all();
        List<String> allDetailGrabLinks = new ArrayList<>();
        for(String link : allDetailLinks) {
            allDetailGrabLinks.add(link.replaceAll("/loupan/(.+)\\.html", "/loupan/canshu-$1.html" ));
        }
        page.addTargetRequests(allDetailGrabLinks);
        Integer pages = 1;
        if (allDetailGrabLinks.isEmpty()) {
            page.setSkip(true);
        } else {
            Integer totalNum = Integer.valueOf(page.getHtml().$("body .list-results .result em", "text").get());
            Integer PAGE_SIZE = allDetailLinks.size();
            pages = totalNum / PAGE_SIZE;
            pages = totalNum % PAGE_SIZE > 0 ? pages + 1 : pages;
        }
        Integer startPageNum = houseSpiderForm.getStartPageNum();
        Integer endPageNum = houseSpiderForm.getEndPageNum();
        if (endPageNum != null && endPageNum < pages) {
            pages = endPageNum;
        }
        // 获取总页数

        List<String> pageList = new ArrayList<String>();
        for (int i = startPageNum; i <= pages; i++) {
            // 列表页路径
            pageList.add("/loupan/all/pg" + i + "/");
        }
        page.addTargetRequests(pageList);
    }

    public static HouseInfoForm grabHouseInfoFromHtml(Html html) {
        HouseInfoForm houseForm = new HouseInfoForm();
        Selectable container = html.$("div.can-container ul.list");

        List<String> tagList = container.$("li .desc .can-tag", "text").all();
        StringBuffer tagBuffer = new StringBuffer();
        for(String tag : tagList) {
            if (StringUtils.isNotBlank(tag)) {
                tagBuffer.append(tag).append("&;");
            }
        }
        houseForm.setLabels(tagBuffer.toString());


        List<Selectable> labelSelectors = container.$("li").nodes();
        labelSelectors.forEach(labelSelector -> {
            String label = labelSelector.$(".name", "text").get();
            String labelValue = labelSelector.$(".des", "text").get();
            if (StringUtils.isNotBlank(label)) {
                label = label.trim();
                if ("楼盘名称".equals(label)) {
                    houseForm.setName(labelSelector.$(".des a", "text").get());
                } else {
                    if ("参考单价".equals(label)) {
                        labelValue = labelSelector.$(".des span.can-big", "text").get();
                    } else if("开发商".equals(label)) {
                        labelValue = labelSelector.$(".des a", "text").get();
                    }
                    HouseSpiderUtil.setNewHouseInfo(houseForm, label, labelValue);
                }
            }
        });
        return houseForm;
    }

    public static void grabListPageForResold(Page page, HouseSpiderForm houseSpiderForm) {
        List<String> allDetailLinks = page.getHtml().$("ul.houselist-mod-new li a.houseListTitle").links().regex(houseSpiderForm.getDetailUrlReg()).all();
        page.addTargetRequests(allDetailLinks);
        Integer pages = 1;
        if (allDetailLinks.isEmpty()) {
            page.setSkip(true);
        }
        Integer startPageNum = houseSpiderForm.getStartPageNum();
        Integer endPageNum = houseSpiderForm.getEndPageNum();
        if (endPageNum != null && endPageNum < pages) {
            pages = endPageNum;
        }

        List<String> pageList = new ArrayList<String>();
        for (int i = startPageNum; i <= pages; i++) {
            // 列表页路径
            pageList.add("/sale/p" + i + "/");
        }
        if (endPageNum != null) {
            page.addTargetRequests(pageList);
        } else {
            page.addTargetRequests(page.getHtml().$("div.multi-page a:last-child").links().all());
        }
    }

    public static ResoldHouseInfoForm grabHouseInfoFromHtmlForResold(Html html) {
        ResoldHouseInfoForm houseForm = new ResoldHouseInfoForm();
        houseForm.setTitle(html.$("#content .long-title", "text").get());

        String totalPriceStr = html.$(".basic-info .light.info-tag em", "text").get();
        if (StringUtils.isNotBlank(totalPriceStr)) {
            houseForm.setTotalPrice(Double.valueOf(totalPriceStr.trim()));
        }

        List<Selectable> labelSelectors = html.$("ul.houseInfo-detail-list li.houseInfo-detail-item").nodes();
        labelSelectors.forEach(labelSelector -> {
            String label = labelSelector.$(".houseInfo-label", "text").get();
            StringBuffer labelValueBuffer = new StringBuffer(labelSelector.$(".houseInfo-content", "text").get());
            if (StringUtils.isBlank(labelValueBuffer.toString())) {
                String aText = labelSelector.$(".houseInfo-content a", "text").get();
                if (StringUtils.isNotBlank(aText)) {
                    labelValueBuffer = new StringBuffer(aText);
                }
            }
            HouseSpiderUtilForLJ.setResoldDetailInfo(houseForm, label, labelValueBuffer);

        });
        return houseForm;
    }
}
