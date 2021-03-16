package com.platform.house.dto;

/**
 * @description:
 * @author: xiaohai
 * @create: 2018-09-09 21:00
 */
public class BaseImageVo {

    private Long id;

    private String url;

    public BaseImageVo() {
    }

    public BaseImageVo(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
