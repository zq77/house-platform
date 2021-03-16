package com.platform.house.spider;

import com.platform.house.form.*;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * Created by Office on 2018/12/29.
 */
public class HouseSpiderUtilForBK {

    private static final Logger logger = Logger.getLogger(HouseSpiderUtilForBK.class);

    public static void grabListPage(Page page, HouseSpiderForm houseSpiderForm) {
        HouseSpiderUtilForLJ.grabListPage(page, houseSpiderForm);
    }

    public static HouseInfoForm grabHouseInfoFromHtml(Html html, HouseSpiderForm houseSpiderForm) {
        return HouseSpiderUtilForLJ.grabHouseInfoFromHtml(html, houseSpiderForm);
    }

    public static List<HouseTypeForm> grabHouseTypeFromHtml(Html html) {
        return HouseSpiderUtilForLJ.grabHouseTypeFromHtml(html);
    }

    public static List<NormalImageFrom> grabHouseImageFromHtml(Html html) {
        return HouseSpiderUtilForLJ.grabHouseImageFromHtml(html);
    }

    public static void grabListPageForResold(Page page, HouseSpiderForm houseSpiderForm) {
        HouseSpiderUtilForLJ.grabListPageForResold(page, houseSpiderForm);
    }

    public static ResoldHouseInfoForm grabHouseInfoFromHtmlForResold(Html html, HouseSpiderForm houseSpiderForm) {
        return HouseSpiderUtilForLJ.grabHouseInfoFromHtmlForResold(html, houseSpiderForm);
    }

}
