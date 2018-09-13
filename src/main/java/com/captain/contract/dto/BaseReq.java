package com.captain.contract.dto;

import java.io.Serializable;

/**
 * @author : liuguang
 * @date : 2018/8/20 17:23
 * @escription :
 */
public class BaseReq<T> implements Serializable {

    /**
     * 分页大小
     */
    private int limit;
    /**
     * 页数
     */
    private int page;

    T data;

    //排序字段
    private String orderBy;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
