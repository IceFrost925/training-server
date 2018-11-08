package com.mycompany.myapp.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Books entity.
 */
public class BooksDTO implements Serializable {

    private Long id;

    private String picture;

    private String name;

    private String type;

    private String number;

    private Integer count;

    private Double price;

    private String describe;

    private String extra1;

    private String extra2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getExtra1() {
        return extra1;
    }

    public void setExtra1(String extra1) {
        this.extra1 = extra1;
    }

    public String getExtra2() {
        return extra2;
    }

    public void setExtra2(String extra2) {
        this.extra2 = extra2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BooksDTO booksDTO = (BooksDTO) o;
        if(booksDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), booksDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BooksDTO{" +
            "id=" + getId() +
            ", picture='" + getPicture() + "'" +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", number='" + getNumber() + "'" +
            ", count=" + getCount() +
            ", price=" + getPrice() +
            ", describe='" + getDescribe() + "'" +
            ", extra1='" + getExtra1() + "'" +
            ", extra2='" + getExtra2() + "'" +
            "}";
    }
}
