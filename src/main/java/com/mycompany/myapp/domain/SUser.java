package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SUser.
 */
@Entity
@Table(name = "s_user")
public class SUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "image")
    private String image;

    @Column(name = "email")
    private String email;

    @Column(name = "passwd")
    private String passwd;

    @Column(name = "tell")
    private String tell;

    @Column(name = "integral")
    private Integer integral;

    @Column(name = "extra_1")
    private String extra1;

    @Column(name = "extra_2")
    private String extra2;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public SUser username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public SUser image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public SUser email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public SUser passwd(String passwd) {
        this.passwd = passwd;
        return this;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getTell() {
        return tell;
    }

    public SUser tell(String tell) {
        this.tell = tell;
        return this;
    }

    public void setTell(String tell) {
        this.tell = tell;
    }

    public Integer getIntegral() {
        return integral;
    }

    public SUser integral(Integer integral) {
        this.integral = integral;
        return this;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getExtra1() {
        return extra1;
    }

    public SUser extra1(String extra1) {
        this.extra1 = extra1;
        return this;
    }

    public void setExtra1(String extra1) {
        this.extra1 = extra1;
    }

    public String getExtra2() {
        return extra2;
    }

    public SUser extra2(String extra2) {
        this.extra2 = extra2;
        return this;
    }

    public void setExtra2(String extra2) {
        this.extra2 = extra2;
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
        SUser sUser = (SUser) o;
        if (sUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SUser{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", image='" + getImage() + "'" +
            ", email='" + getEmail() + "'" +
            ", passwd='" + getPasswd() + "'" +
            ", tell='" + getTell() + "'" +
            ", integral=" + getIntegral() +
            ", extra1='" + getExtra1() + "'" +
            ", extra2='" + getExtra2() + "'" +
            "}";
    }
}
