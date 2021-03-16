package com.platform.house.utils;

import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

/**
 * @description:
 * @author: xiaohai
 * @create: 2018-06-29 23:47
 */
public class PageVo<T> {

    private List<T> content;

    private int page;

    private int size;

    private long total;

    private boolean hasNext;

    private int totalPages;

    public PageVo(Pageable pageable) {
        this.setContent(Collections.emptyList());
        this.setPage(pageable.getPageNumber() + 1);
        this.setSize(pageable.getPageSize());
        this.setTotal(0L);

        resetArgs();
    }

    public PageVo(List<T> content, Pageable pageable, long total) {
        this.setContent(content);
        this.setPage(pageable.getPageNumber() + 1);
        this.setSize(pageable.getPageSize());
        this.setTotal(total);

        resetArgs();
    }

    public PageVo(List<T> content, int page, int size, long total) {
        this.setContent(content);
        this.setPage(page + 1);
        this.setSize(size);
        this.setTotal(total);

        resetArgs();
    }

    private void resetArgs() {
        setTotalPages(getSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) getSize()));
        setHasNext(getPage() + 1 < getTotalPages());
    }

    public List<T> getContent() {
        return content;
    }

    private void setContent(List<T> content) {
        this.content = content;
    }

    public int getPage() {
        return page;
    }

    private void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    private void setSize(int size) {
        this.size = size;
    }

    public long getTotal() {
        return total;
    }

    private void setTotal(long total) {
        this.total = total;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    private void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public int getTotalPages() {
        return totalPages;
    }

    private void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
