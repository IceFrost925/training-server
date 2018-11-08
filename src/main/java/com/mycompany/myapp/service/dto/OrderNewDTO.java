package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.List;

public class OrderNewDTO implements Serializable {

    private Long id;

    private Long suserId;

    private Long AddressId;

    private List bookIdList;  //json字符串

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSuserId() {
        return suserId;
    }

    public void setSuserId(Long suserId) {
        this.suserId = suserId;
    }

    public Long getAddressId() {
        return AddressId;
    }

    public void setAddressId(Long addressId) {
        AddressId = addressId;
    }

    public List getBookIdList() {
        return bookIdList;
    }

    public void setBookIdList(List bookIdList) {
        this.bookIdList = bookIdList;
    }

    @Override
    public String toString() {
        return "OrderNewDTO{" + "id=" + id + ", suserId=" + suserId + ", AddressId=" + AddressId + ", bookIdList=" + bookIdList + '}';
    }
}
