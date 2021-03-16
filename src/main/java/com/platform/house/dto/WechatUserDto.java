package com.platform.house.dto;

/**
 * Created by Office on 2019/3/23.
 */
public class WechatUserDto {
    private String username;
    private Long userId;
    private String unionId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}
