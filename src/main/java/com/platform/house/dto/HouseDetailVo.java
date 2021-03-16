package com.platform.house.dto;

import java.util.List;

/**
 * @description: 新房详情页面模型
 * @author: xiaohai
 * @create: 2018-06-26 23:47
 */
public class HouseDetailVo extends HouseInfoVo {

    private List<HouseImageVo> normalImages;

    private List<HouseTypeVo> houseTypes;

    private List<HouseNewsDto> houseNewsList;

    private List<UserDto> agentList;

    public List<HouseImageVo> getNormalImages() {
        return normalImages;
    }

    public void setNormalImages(List<HouseImageVo> normalImages) {
        this.normalImages = normalImages;
    }

    public List<HouseTypeVo> getHouseTypes() {
        return houseTypes;
    }

    public void setHouseTypes(List<HouseTypeVo> houseTypes) {
        this.houseTypes = houseTypes;
    }

    public List<HouseNewsDto> getHouseNewsList() {
        return houseNewsList;
    }

    public void setHouseNewsList(List<HouseNewsDto> houseNewsList) {
        this.houseNewsList = houseNewsList;
    }

    public List<UserDto> getAgentList() {
        return agentList;
    }

    public void setAgentList(List<UserDto> agentList) {
        this.agentList = agentList;
    }
}
