package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "tell")
    private String tell;

    @Column(name = "company")
    private String company;

    @Column(name = "email")
    private String email;

    @Column(name = "content")
    private String content;

    @Column(name = "flag")
    private Boolean flag;

    @Column(name = "extra_1")
    private String extra1;

    @Column(name = "extra_2")
    private String extra2;

    @ManyToOne
    private SUser suser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Address name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTell() {
        return tell;
    }

    public Address tell(String tell) {
        this.tell = tell;
        return this;
    }

    public void setTell(String tell) {
        this.tell = tell;
    }

    public String getCompany() {
        return company;
    }

    public Address company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public Address email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public Address content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean isFlag() {
        return flag;
    }

    public Boolean getFlag() {
        return flag;
    }

    public Address flag(Boolean flag) {
        this.flag = flag;
        return this;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getExtra1() {
        return extra1;
    }

    public Address extra1(String extra1) {
        this.extra1 = extra1;
        return this;
    }

    public void setExtra1(String extra1) {
        this.extra1 = extra1;
    }

    public String getExtra2() {
        return extra2;
    }

    public Address extra2(String extra2) {
        this.extra2 = extra2;
        return this;
    }

    public void setExtra2(String extra2) {
        this.extra2 = extra2;
    }

    public SUser getSuser() {
        return suser;
    }

    public Address suser(SUser sUser) {
        this.suser = sUser;
        return this;
    }

    public void setSuser(SUser sUser) {
        this.suser = sUser;
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
        Address address = (Address) o;
        if (address.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), address.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", tell='" + getTell() + "'" +
            ", company='" + getCompany() + "'" +
            ", email='" + getEmail() + "'" +
            ", content='" + getContent() + "'" +
            ", flag='" + getFlag() + "'" +
            ", extra1='" + getExtra1() + "'" +
            ", extra2='" + getExtra2() + "'" +
            "}";
    }
}
