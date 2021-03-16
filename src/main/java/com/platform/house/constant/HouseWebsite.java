package com.platform.house.constant;

/**
 * Created by Office on 2018/12/27.
 */
public enum HouseWebsite {
    LJ(
            "链家",
            "lianjia.com",
            "https://$1.fang.lianjia.com/loupan/pg$2/",
            "https://$1.fang.lianjia.com/loupan/p_$2/",
            "xiangqing/",
            "huxingtu/",
            "xiangce/",
            "https://$1.lianjia.com/ershoufang/pg$2/",
            "https://$1.lianjia.com/ershoufang/$2.html"
    ),
    BK(
            "贝壳",
            "ke.com",
            "https://$1.fang.ke.com/loupan/pg$2/",
            "https://$1.fang.ke.com/loupan/p_$2/",
            "xiangqing/",
            "huxingtu/",
            "xiangce/",
            "https://$1.ke.com/ershoufang/pg$2/",
            "https://$1.ke.com/ershoufang/$2.html"
    ),
    AJK(
            "安居客",
            "lianjia.com",
            "https://$1.fang.anjuke.com/loupan/all/pg$2/",
            "https://$1.fang.anjuke.com/loupan/$2.html",
            "",
            "",
            "",
            "https://$1.anjuke.com/sale/p$2/",
            "https://$1.anjuke.com/prop/view/$2"
    ),
    YYHF(
            "优优好房",
            "haofang.net",
            "https://$1.haofang.net/xinfang/p$2.html",
            "https://$1.haofang.net/xinfang/$2.html",
            "",
            "",
            "",
            "https://$1.haofang.net/ershoufang/p$2.html",
            "https://$1.haofang.net/ershoufang/$2.html"
    );

    HouseWebsite(String name, String domain, String newHouseListUrl, String newHouseDetailUrl, String newHouseDetailPartUrl, String newHouseTypePartUrl, String newHouseImagePartUrl, String resoldHouseListUrl, String resoldHouseDetailUrl) {
        this.name = name;
        this.domain = domain;
        this.newHouseListUrl = newHouseListUrl;
        this.newHouseDetailUrl = newHouseDetailUrl;
        this.newHouseDetailPartUrl = newHouseDetailPartUrl;
        this.newHouseTypePartUrl = newHouseTypePartUrl;
        this.newHouseImagePartUrl = newHouseImagePartUrl;
        this.resoldHouseListUrl = resoldHouseListUrl;
        this.resoldHouseDetailUrl = resoldHouseDetailUrl;
    }

    public static HouseWebsite getWebsiteByName(String name) {
        HouseWebsite website = LJ;
        for (HouseWebsite houseWebsite : HouseWebsite.values()) {
            if (houseWebsite.toString().equals(name)) {
                website = houseWebsite;
                break;
            }
        }
        return website;
    }

    private String name;
    private String domain;
    private String newHouseListUrl;
    private String newHouseDetailUrl;
    private String newHouseDetailPartUrl;
    private String newHouseTypePartUrl;
    private String newHouseImagePartUrl;
    private String resoldHouseListUrl;
    private String resoldHouseDetailUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getNewHouseListUrl() {
        return newHouseListUrl;
    }

    public String getNewHouseDetailPartUrl() {
        return newHouseDetailPartUrl;
    }

    public void setNewHouseDetailPartUrl(String newHouseDetailPartUrl) {
        this.newHouseDetailPartUrl = newHouseDetailPartUrl;
    }

    public String getNewHouseTypePartUrl() {
        return newHouseTypePartUrl;
    }

    public void setNewHouseTypePartUrl(String newHouseTypePartUrl) {
        this.newHouseTypePartUrl = newHouseTypePartUrl;
    }

    public String getNewHouseImagePartUrl() {
        return newHouseImagePartUrl;
    }

    public void setNewHouseImagePartUrl(String newHouseImagePartUrl) {
        this.newHouseImagePartUrl = newHouseImagePartUrl;
    }

    public void setNewHouseListUrl(String newHouseListUrl) {
        this.newHouseListUrl = newHouseListUrl;
    }

    public String getNewHouseDetailUrl() {
        return newHouseDetailUrl;
    }

    public void setNewHouseDetailUrl(String newHouseDetailUrl) {
        this.newHouseDetailUrl = newHouseDetailUrl;
    }

    public String getResoldHouseListUrl() {
        return resoldHouseListUrl;
    }

    public void setResoldHouseListUrl(String resoldHouseListUrl) {
        this.resoldHouseListUrl = resoldHouseListUrl;
    }

    public String getResoldHouseDetailUrl() {
        return resoldHouseDetailUrl;
    }

    public void setResoldHouseDetailUrl(String resoldHouseDetailUrl) {
        this.resoldHouseDetailUrl = resoldHouseDetailUrl;
    }
}
