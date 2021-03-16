package com.platform.house.spider;

import com.alibaba.fastjson.JSONObject;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.platform.house.constant.HouseWebsite;
import com.platform.house.form.HouseSpiderForm;
import com.platform.house.services.AreaDivisionService;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by Office on 2018/12/27.
 */
public class HouseSpiderProcessor implements PageProcessor {

    private static final Logger logger = Logger.getLogger(HouseSpiderUtil.class);

    private HouseSpiderForm houseSpiderForm;

    private Site site = Site.me().setCharset("UTF-8").setRetryTimes(3).setSleepTime(1000).setTimeOut(10000).setUserAgent(
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");


    public HouseSpiderProcessor(HouseSpiderForm houseSpiderForm) {
        this.houseSpiderForm = houseSpiderForm;
    }

    @Override
    public void process(Page page) {
        if (this.houseSpiderForm.getIfNewHouse()) {
            HouseSpiderUtil.process(this, page);
        } else {
            HouseSpiderUtil.processResoldHouse(this, page);
        }
    }

    public static void main(String[] args) {
        AreaDivisionService areaDivisionService = new AreaDivisionService();
        String cityCode = "1101";
        String webSiteName = "LJ";
        JSONObject selectedCity = null;
        try {
            selectedCity = areaDivisionService.getCityByCode(cityCode);
        } catch (PinyinException e) {
            logger.error(e.getMessage());
        }
        String cityShortPY = selectedCity.getString("shortPY");
        HouseWebsite website = HouseWebsite.getWebsiteByName(webSiteName);
        HouseSpiderForm houseSpiderForm = new HouseSpiderForm();
        houseSpiderForm.setCityCode(cityCode);
        houseSpiderForm.setWebSiteName(webSiteName);
        houseSpiderForm.setStartPageNum(1);
        houseSpiderForm.setEndPageNum(2);
        houseSpiderForm.setSelectedCity(selectedCity);
        houseSpiderForm.setHouseWebsite(website);


//        houseSpiderForm.setIfNewHouse(true);
//        String firstListUrl = website.getNewHouseListUrl().replace("$1", cityShortPY).replace("$2", "1");
//        String listUrlReg = website.getNewHouseListUrl().replace("$1", cityShortPY).replace("$2", "\\d+").replace(".", "\\.");
//        String detailUrlReg = website.getNewHouseDetailUrl().replace("$1", cityShortPY).replace(".", "\\.").replace("$2", ".+");
//        houseSpiderForm.setFirstListUrl(firstListUrl);
//        houseSpiderForm.setListUrlReg(listUrlReg);
//        houseSpiderForm.setDetailUrlReg(detailUrlReg);
//        HouseSpiderProcessor houseSpiderProcessor = new HouseSpiderProcessor(houseSpiderForm);
//        HouseSpiderPipeline pipeline = new HouseSpiderPipeline();
//        Spider.create(houseSpiderProcessor).addUrl(firstListUrl)
//                .addPipeline(pipeline).thread(5).run();
//          System.out.println(pipeline.houseList.size());
//          System.out.println(pipeline.houseTypeList.size());


        houseSpiderForm.setIfNewHouse(false);
        String firstListUrl = website.getResoldHouseListUrl().replace("$1", cityShortPY).replace("$2", "1");
        String listUrlReg =  website.getResoldHouseListUrl().replace("$1", "\\w+").replace("$2", "\\d+").replace(".", "\\.");
        String detailUrlReg = website.getResoldHouseDetailUrl().replace("$1", "\\w+").replace(".", "\\.").replace("$2", ".+");
        houseSpiderForm.setFirstListUrl(firstListUrl);
        houseSpiderForm.setListUrlReg(listUrlReg);
        houseSpiderForm.setDetailUrlReg(detailUrlReg);
        HouseSpiderProcessor houseSpiderProcessor = new HouseSpiderProcessor(houseSpiderForm);
        ResoldHouseSpiderPipeline pipeline = new ResoldHouseSpiderPipeline();
        Spider.create(houseSpiderProcessor).addUrl(firstListUrl)
                .addPipeline(pipeline).thread(5).run();

        System.out.println(pipeline.houseList.size());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public HouseSpiderForm getHouseSpiderForm() {
        return houseSpiderForm;
    }

    public void setHouseSpiderForm(HouseSpiderForm houseSpiderForm) {
        this.houseSpiderForm = houseSpiderForm;
    }
}
