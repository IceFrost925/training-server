package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Shopping.
 */
@Entity
@Table(name = "shopping")
public class Shopping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_number")
    private String number;

    @Column(name = "extra_1")
    private String extra1;

    @Column(name = "extra_2")
    private String extra2;

    @ManyToOne
    private SUser suser;

    @ManyToOne
    private Books bookId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public Shopping number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExtra1() {
        return extra1;
    }

    public Shopping extra1(String extra1) {
        this.extra1 = extra1;
        return this;
    }

    public void setExtra1(String extra1) {
        this.extra1 = extra1;
    }

    public String getExtra2() {
        return extra2;
    }

    public Shopping extra2(String extra2) {
        this.extra2 = extra2;
        return this;
    }

    public void setExtra2(String extra2) {
        this.extra2 = extra2;
    }

    public SUser getSuser() {
        return suser;
    }

    public Shopping suser(SUser sUser) {
        this.suser = sUser;
        return this;
    }

    public void setSuser(SUser sUser) {
        this.suser = sUser;
    }

    public Books getBookId() {
        return bookId;
    }

    public Shopping bookId(Books books) {
        this.bookId = books;
        return this;
    }

    public void setBookId(Books books) {
        this.bookId = books;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Shopping shopping = (Shopping) o;
        if (shopping.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shopping.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Shopping{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", extra1='" + getExtra1() + "'" +
            ", extra2='" + getExtra2() + "'" +
            "}";
    }
}
