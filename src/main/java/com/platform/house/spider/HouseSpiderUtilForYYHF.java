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
public class HouseSpiderUtilForYYHF {
    private static final Logger logger = Logger.getLogger(HouseSpiderUtilForYYHF.class);

    public static void grabListPage(Page page, HouseSpiderForm houseSpiderForm) {
        Selectable s = page.getHtml().$(".content ");
        // logger.info(page.getHtml().$(".content .list-content li .info .newHouse-tit").links());
        List<String> allDetailLinks = page.getHtml().$(".content .list-content li .info .newHouse-tit").links().regex(houseSpiderForm.getDetailUrlReg()).all();
        page.addTargetRequests(allDetailLinks);
        Integer pages = 1;
        if (allDetailLinks.isEmpty()) {
            page.setSkip(true);
        } else {
            Integer totalNum = Integer.valueOf(page.getHtml().$(".result-des .total .yellow", "text").get());
            Integer PAGE_SIZE = allDetailLinks.size();
            pages = totalNum / PAGE_SIZE;
            pages = totalNum % PAGE_SIZE > 0 ? pages + 1 : pages;
        }
        Integer startPageNum = houseSpiderForm.getStartPageNum();
        Integer endPageNum = houseSpiderForm.getEndPageNum();
        if (endPageNum != null && endPageNum < pages) {
            pages = endPageNum;
        }

        List<String> pageList = new ArrayList<String>();
        for (int i = startPageNum; i <= pages; i++) {
            // 列表页路径
            pageList.add("/xinfang/p" + i + ".html");
        }
        page.addTargetRequests(pageList);
    }

    public static HouseInfoForm grabHouseInfoFromHtml(Html html) {
        HouseInfoForm houseForm = new HouseInfoForm();
        houseForm.setName(html.$("div.newhouse-detail .newhouse-detail-name", "text").get());
        String priceStr = html.$("div.newhouse-detail .selling-price .num", "text").get();
        if (StringUtils.isNotBlank(priceStr)) {
            houseForm.setPrice(Double.valueOf(priceStr.trim()));
        }

        List<Selectable> labelSelectors = html.$(".basic-information .box-div-margin div").nodes();
        labelSelectors.forEach(labelSelector -> {
            String label = labelSelector.$(".gray-text", "text").get();
            String labelValue = labelSelector.$(".black-text ", "text").get();
            if (StringUtils.isNotBlank(label)) {
                HouseSpiderUtil.setNewHouseInfo(houseForm, label, labelValue);
            }
        });
        return houseForm;
    }

    public static void grabListPageForResold(Page page, HouseSpiderForm houseSpiderForm) {
        List<String> allDetailLinks = page.getHtml().$(".content .list-content li .info .title").links().regex(houseSpiderForm.getDetailUrlReg()).all();
        page.addTargetRequests(allDetailLinks);
        Integer pages = 1;
        if (allDetailLinks.isEmpty()) {
            page.setSkip(true);
        } else {
            Integer totalNum = Integer.valueOf(page.getHtml().$(".result-des .total .yellow", "text").get());
            Integer PAGE_SIZE = allDetailLinks.size();
            pages = totalNum / PAGE_SIZE;
            pages = totalNum % PAGE_SIZE > 0 ? pages + 1 : pages;
        }
        Integer startPageNum = houseSpiderForm.getStartPageNum();
        Integer endPageNum = houseSpiderForm.getEndPageNum();
        if (endPageNum != null && endPageNum < pages) {
            pages = endPageNum;
        }
        List<String> pageList = new ArrayList<String>();
        for (int i = startPageNum; i <= pages; i++) {
            // 列表页路径
            pageList.add("/ershoufang/p" + i + ".html");
        }
        page.addTargetRequests(pageList);
    }

    public static ResoldHouseInfoForm grabHouseInfoFromHtmlForResold(Html html) {
        ResoldHouseInfoForm houseForm = new ResoldHouseInfoForm();
        houseForm.setTitle(html.$(".info h1", "text").get());

        String totalPriceStr = html.$(".detail .c-orange-price", "text").get();
        if (StringUtils.isNotBlank(totalPriceStr)) {
            houseForm.setTotalPrice(Double.valueOf(totalPriceStr.trim()));
        }
        String priceStr = html.$(".detail .c-orange-unit", "text").get();
        if (StringUtils.isNotBlank(priceStr)) {
            houseForm.setPrice(Double.valueOf(priceStr.replace("元/平米", "")));
        }

        List<String> tagList = html.$(".m-post .con .label_tag a", "text").all();
        StringBuffer tagBuffer = new StringBuffer();
        for (String tag : tagList) {
            if (StringUtils.isNotBlank(tag)) {
                tagBuffer.append(tag).append("&;");
            }
        }
        houseForm.setLabels(tagBuffer.toString());
        houseForm.setIntroduction(html.$(".m-post .hexin-maidain", "text").get());

        List<Selectable> labelSelectors = html.$(".jichu-info dl dd").nodes();
        labelSelectors.forEach(labelSelector -> {
            String label = labelSelector.$("span.c-gray", "text").get();
            StringBuffer labelValueBuffer = new StringBuffer(labelSelector.$("dd ", "text").get());
            if (label.contains("建筑面积")) {
                labelValueBuffer = new StringBuffer(labelSelector.$("dd em", "text").get());
            } else if (StringUtils.isBlank(labelValueBuffer.toString())) {
                String aText = labelSelector.css(".houseInfo-content span:last-child", "text").get();
                if (StringUtils.isNotBlank(aText)) {
                    labelValueBuffer = new StringBuffer(aText);
                }
            }
            HouseSpiderUtilForLJ.setResoldDetailInfo(houseForm, label.replaceAll("(　| )", ""), labelValueBuffer);

        });
        return houseForm;
    }
}
