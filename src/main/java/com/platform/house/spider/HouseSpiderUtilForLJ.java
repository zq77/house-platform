package com.platform.house.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.platform.house.constant.HouseWebsite;
import com.platform.house.constant.ImageType;
import com.platform.house.constant.SellingStatus;
import com.platform.house.form.*;
import com.platform.house.services.AreaDivisionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Created by Office on 2018/12/29.
 */
public class HouseSpiderUtilForLJ {

    private static AreaDivisionService areaDivisionService = new AreaDivisionService();

    private static final Logger logger = Logger.getLogger(HouseSpiderUtilForLJ.class);

    public static void grabListPage(Page page, HouseSpiderForm houseSpiderForm) {
        String detailUrlReg = houseSpiderForm.getDetailUrlReg();
        HouseWebsite website = houseSpiderForm.getHouseWebsite();
        List<String> allDetailLinks = page.getHtml().$("ul.resblock-list-wrapper .resblock-name").links().regex(detailUrlReg).all();
        List<String> allDetailGrabLinks = new ArrayList<>();
        for (String link : allDetailLinks) {
            allDetailGrabLinks.add(link + website.getNewHouseDetailPartUrl());
            allDetailGrabLinks.add(link + website.getNewHouseTypePartUrl());
            allDetailGrabLinks.add(link + website.getNewHouseImagePartUrl());
        }
        page.addTargetRequests(allDetailGrabLinks);

        Integer pages = 1;
        if (allDetailGrabLinks.isEmpty()) {
            page.setSkip(true);
        } else {
            Integer totalNum = Integer.valueOf(page.getHtml().$("div.page-box", "data-total-count").get());
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
            pageList.add("/loupan/pg" + i + "/");
        }
        page.addTargetRequests(pageList);
    }

    public static HouseInfoForm grabHouseInfoFromHtml(Html html, HouseSpiderForm houseSpiderForm) {
        HouseInfoForm houseForm = new HouseInfoForm();
        JSONObject selectedCity = houseSpiderForm.getSelectedCity();
        String nameInfo = html.$(".intro .container a:last-child", "text").get();
        houseForm.setName(nameInfo.replace("详情", ""));
        String labelStr = html.$("ul.x-box li .label-val.tese-val", "text").get();
        if (StringUtils.isNotBlank(labelStr)) {
            houseForm.setLabels(labelStr.trim().replace(" ", "&;"));
        }
        List<Selectable> labelSelectors = html.$("ul.x-box li").nodes();
        labelSelectors.forEach(labelSelector -> {
            String label = labelSelector.$(".label", "text").get();
            String labelValue = labelSelector.$(".label-val", "text").get();
            if (StringUtils.isBlank(labelValue)) {
                labelValue = labelSelector.$(".label-val span", "text").get();
            }
            if (StringUtils.isNotBlank(label) && label.contains("区域位置")) {
                String areaName = labelSelector.$(".label-val a", "text").get();
                JSONObject getArea  = areaDivisionService.getAreaByCityCodeAndName(selectedCity.getString("code"), areaName);
                houseForm.setArea(getArea.getString("code"));
            }
            if (StringUtils.isNotBlank(label)) {
                HouseSpiderUtil.setNewHouseInfo(houseForm, label, labelValue);
            }
        });
        return houseForm;

    }

    public static List<HouseTypeForm> grabHouseTypeFromHtml(Html html) {
        List<Selectable> houseTypeSelectors = html.$(".huxingtu .item-list .huxing-item").nodes();
        List<HouseTypeForm> houseTypeFormList = new ArrayList<>();
        HouseTypeForm houseTypeForm = null;
        for (Selectable houseTypeSelector : houseTypeSelectors) {
            String imgageUrl = houseTypeSelector.$(".hxt-img img", "src").get();
            String totalPrice = houseTypeSelector.$(".price i", "text").get();
            houseTypeForm = new HouseTypeForm();
            houseTypeForm.setImageUrl(imgageUrl.replaceAll("(\\.618x\\.jpg)", "!m_fill,w_600"));
            if (StringUtils.isNotBlank(totalPrice)) {
                houseTypeForm.setTotalPrice(Double.parseDouble(totalPrice));
            }
            String types = houseTypeSelector.css(".info ul li:first-child", "text").get().replace("居室：", "");
            Map<String, String> typeMap = HouseSpiderUtil.getNumberForHouseType(types);
            String roomCountStr = typeMap.get("室");
            if (StringUtils.isNotBlank(roomCountStr)) {
                houseTypeForm.setRoomCount(Integer.valueOf(roomCountStr));
            }
            String hallCountStr = typeMap.get("厅");
            if (StringUtils.isNotBlank(hallCountStr)) {
                houseTypeForm.setHallCount(Integer.valueOf(hallCountStr));
            }
            String kitchenCountStr = typeMap.get("厨");
            if (StringUtils.isNotBlank(kitchenCountStr)) {
                houseTypeForm.setKitchenCount(Integer.valueOf(kitchenCountStr));
            }
            String bathroomCountStr = typeMap.get("卫");
            if (StringUtils.isNotBlank(bathroomCountStr)) {
                houseTypeForm.setBathroomCount(Integer.valueOf(bathroomCountStr));
            }
            String floorageStr = houseTypeSelector.css(".info ul li:last-child", "text").get().replace("建面：", "");
            Matcher matcher = HouseSpiderUtil.DOUBLE_PATTERN.matcher(floorageStr);
            if (matcher.find()) {
                String floorage = matcher.group();
                houseTypeForm.setFloorage(Double.parseDouble(floorage));
            }
            String status = houseTypeSelector.$(".status", "text").get();
            SellingStatus sellingStatus = HouseSpiderUtil.sellingStatusMap.get(status);
            if (sellingStatus != null) {
                houseTypeForm.setSellingStatus(sellingStatus.toString());
            }
            houseTypeFormList.add(houseTypeForm);
        }
        return houseTypeFormList;
    }

    public static List<NormalImageFrom> grabHouseImageFromHtml(Html html) {
        List<Selectable> tabGroupSelectors = html.$(".xiangce .tab-group").nodes();
        List<NormalImageFrom> hoseuImageList = new ArrayList<>();
        NormalImageFrom normalImageFrom = null;
        for (Selectable selectable : tabGroupSelectors) {
            String houseImageText = selectable.$("h4 a", "text").get().replaceAll("（\\d*）", "");
            ImageType imageType = HouseSpiderUtil.imageTypeMap.get(houseImageText);
            if (imageType != null) {
                for (String imgUrl : selectable.$(".tab-list li img", "src").all()) {
                    normalImageFrom = new NormalImageFrom();
                    normalImageFrom.setImageUrl(imgUrl.replaceAll("(w_235,h_178,.*)", "w_600"));
                    normalImageFrom.setImageType(imageType.toString());
                    hoseuImageList.add(normalImageFrom);
                }
            }
        }
        return hoseuImageList;
    }

    public static void grabListPageForResold(Page page, HouseSpiderForm houseSpiderForm) {
        List<String> allDetailLinks = page.getHtml().$("ul.sellListContent li a.img").links().regex(houseSpiderForm.getDetailUrlReg()).all();
        page.addTargetRequests(allDetailLinks);
        Integer pages = 1;
        if (allDetailLinks.isEmpty()) {
            page.setSkip(true);
        } else {
            String pageInfo = page.getHtml().$("div.page-box.house-lst-page-box", "page-data").get();
            JSONObject pageJson = JSON.parseObject(pageInfo);
            pages = Integer.valueOf(pageJson.getString("totalPage"));
        }
        Integer startPageNum = houseSpiderForm.getStartPageNum();
        Integer endPageNum = houseSpiderForm.getEndPageNum();
        if (endPageNum != null && endPageNum < pages) {
            pages = endPageNum;
        }

        List<String> pageList = new ArrayList<String>();
        for (int i = startPageNum; i <= pages; i++) {
            // 列表页路径
            pageList.add("/ershoufang/pg" + i + "/");
        }
        page.addTargetRequests(pageList);
    }

    public static ResoldHouseInfoForm grabHouseInfoFromHtmlForResold(Html html, HouseSpiderForm houseSpiderForm) {
        ResoldHouseInfoForm houseForm = new ResoldHouseInfoForm();
        JSONObject selectedCity = houseSpiderForm.getSelectedCity();
        houseForm.setTitle(html.$(".title-wrapper h1.main", "text").get());
        houseForm.setSubTitle(html.$(".title-wrapper div.sub", "text").get());
        houseForm.setName(html.$("div.aroundInfo .communityName .info", "text").get());
        String totalPriceStr = html.$("div.overview .price .total", "text").get();
        if (StringUtils.isNotBlank(totalPriceStr)) {
            houseForm.setTotalPrice(Double.valueOf(totalPriceStr.trim()));
        }
        String priceStr = html.$("div.overview .price .unitPrice .unitPriceValue", "text").get();
        if (StringUtils.isNotBlank(priceStr)) {
            houseForm.setPrice(Double.valueOf(priceStr.trim()));
        }
        String areaName = html.$("div.aroundInfo  .areaName .info").css("a:first-child", "text").get();
        if (StringUtils.isNotBlank(areaName)) {
            JSONObject getArea  = areaDivisionService.getAreaByCityCodeAndName(selectedCity.getString("code"), areaName);
            houseForm.setArea(getArea.getString("code"));
        }

        List<String> thumbnailUrlList = html.$(".thumbnail ul li", "data-src").all();
        houseForm.setHouseImageList(thumbnailUrlList);

        List<Selectable> labelSelectors = html.$("#introduction div.content ul li").nodes();
        labelSelectors.forEach(labelSelector -> {
            String label = labelSelector.$(".label", "text").get();
            StringBuffer labelValueBuffer = new StringBuffer(labelSelector.$("li", "text").get());
            if (StringUtils.isBlank(labelValueBuffer.toString())) {
                labelValueBuffer = new StringBuffer(labelSelector.css("span:not(.label)", "text").get());
            }
            setResoldDetailInfo(houseForm, label, labelValueBuffer);
        });


        return houseForm;
    }

    public static void setResoldDetailInfo(ResoldHouseInfoForm houseForm, String label, StringBuffer labelValueBuffer) {
        if (StringUtils.isNotBlank(label)) {
            if ("房屋户型".equals(label.trim())) {
                Map<String, String> houseTypeMap = HouseSpiderUtil.getNumberForHouseType(labelValueBuffer.toString());
                for (String key : houseTypeMap.keySet()) {
                    HouseSpiderUtil.setResoldHouseInfo(houseForm, key, houseTypeMap.get(key));
                }
            }
            if (StringUtils.isNotBlank(labelValueBuffer.toString())) {
                HouseSpiderUtil.setResoldHouseInfo(houseForm, label.trim(), labelValueBuffer.toString().trim());
            }
        }
    }


    public static void main(String[] args) {
        Map resultMap = HouseSpiderUtil.getNumberForHouseType("3室2厅2厨4卫");
    }
}
