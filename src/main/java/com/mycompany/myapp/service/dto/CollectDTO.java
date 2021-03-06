package com.mycompany.myapp.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Collect entity.
 */
public class CollectDTO implements Serializable {

    private Long id;

    private String extra1;

    private String extra2;

    private Long suserId;

    private Long bookIdId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getSuserId() {
        return suserId;
    }

    public void setSuserId(Long sUserId) {
        this.suserId = sUserId;
    }

    public Long getBookIdId() {
        return bookIdId;
    }

    public void setBookIdId(Long booksId) {
        this.bookIdId = booksId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CollectDTO collectDTO = (CollectDTO) o;
        if(collectDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), collectDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CollectDTO{" +
            "id=" + getId() +
            ", extra1='" + getExtra1() + "'" +
            ", extra2='" + getExtra2() + "'" +
            "}";
    }
}
