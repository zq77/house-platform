package com.platform.house.constant;

/**
 * 房产信息状态
 */
public enum HouseInfoStatus {
    // 抓取
    GRAB,

    // 创建信息
    INIT,

    // 提交待审核
    WAIT_APPROVE,

    // 拒绝
    REJECT,

    // 发布
    PUBLISHED;

}
