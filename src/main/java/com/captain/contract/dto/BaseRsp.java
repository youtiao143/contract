package com.captain.contract.dto;


import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : liuguang
 * @date : 2018/8/20 17:23
 * @escription :
 */
public class BaseRsp<T> implements Serializable {

    /**
     * 总条数
     */
    private long total;

    /**
     * 总页数
     */
    private long totalPage;

    private T data;

    private String rspCode = "1";

    private String rspMsg;

    private String rspStatus;

    private List<T> rows;

    private Map attr;

    private int isMore;



    public BaseRsp() {

    }

    public BaseRsp(PageInfo<T> pageInfo){
        super();
        if(pageInfo != null){
            this.totalPage = pageInfo.getPageSize();
            this.isMore = pageInfo.isHasNextPage()?1:0;
            this.total = pageInfo.getTotal();
            this.rows = pageInfo.getList();
        }
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public void setRspCode(int rspCode){
        this.rspCode = rspCode+"";
    }

    public String getRspMsg() {
        return rspMsg;
    }

    public void setRspMsg(String rspMsg) {
        this.rspMsg = rspMsg;
    }

    public String getRspStatus() {
        return rspStatus;
    }

    public void setRspStatus(String rspStatus) {
        this.rspStatus = rspStatus;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getIsMore() {
        return isMore;
    }

    public void setIsMore(int isMore) {
        this.isMore = isMore;
    }

    public Map getAttr() {
        return attr;
    }

    public Object getAttr(String key) {
        if(this.attr==null){
            return null;
        }
        return attr.get(key);

    }

    public void setAttr(Map attr) {
        this.attr = attr;
    }

    public void setAttr(String key,Object value) {
        if(this.attr==null){
            this.attr = new HashMap();
        }
        this.attr.put(key,value);
    }
}
