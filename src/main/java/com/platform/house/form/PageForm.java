package com.platform.house.form;

public class PageForm {
    private Integer currentPage;
    private Integer pageSize;
    private String keywords;

    public Integer getCurrentPage() {
        if (currentPage == null) {
            return 1;
        }
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        if (pageSize == null) {
            return 10;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageRequestPage() {
        if (currentPage > 0) {
            return currentPage - 1;
        }
        return currentPage;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}