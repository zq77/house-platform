package com.platform.house.dto;

/**
 * Created by Office on 2019/3/18.
 */
public class LoginDto {
    private String username;
    private String jSessionId;
    private Boolean isAdmin;
    private Boolean isStoreAdmin;
    private Boolean isAgent;
    private Boolean isNormalUser;
    private Boolean isWechatUser;

    public LoginDto() {
        this.isAdmin = false;
        this.isStoreAdmin = false;
        this.isAgent = false;
        this.isNormalUser = false;
        this.isWechatUser = false;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getjSessionId() {
        return jSessionId;
    }

    public void setjSessionId(String jSessionId) {
        this.jSessionId = jSessionId;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getStoreAdmin() {
        return isStoreAdmin;
    }

    public void setStoreAdmin(Boolean storeAdmin) {
        isStoreAdmin = storeAdmin;
    }

    public Boolean getAgent() {
        return isAgent;
    }

    public void setAgent(Boolean agent) {
        isAgent = agent;
    }

    public Boolean getNormalUser() {
        return isNormalUser;
    }

    public void setNormalUser(Boolean normalUser) {
        isNormalUser = normalUser;
    }

    public Boolean getWechatUser() {
        return isWechatUser;
    }

    public void setWechatUser(Boolean wechatUser) {
        isWechatUser = wechatUser;
    }
}
