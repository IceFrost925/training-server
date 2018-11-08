package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Menus.
 */
@Entity
@Table(name = "menus")
public class Menus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "second")
    private String second;

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

    public String getType() {
        return type;
    }

    public Menus type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSecond() {
        return second;
    }

    public Menus second(String second) {
        this.second = second;
        return this;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getExtra1() {
        return extra1;
    }

    public Menus extra1(String extra1) {
        this.extra1 = extra1;
        return this;
    }

    public void setExtra1(String extra1) {
        this.extra1 = extra1;
    }

    public String getExtra2() {
        return extra2;
    }

    public Menus extra2(String extra2) {
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
        Menus menus = (Menus) o;
        if (menus.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), menus.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Menus{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", second='" + getSecond() + "'" +
            ", extra1='" + getExtra1() + "'" +
            ", extra2='" + getExtra2() + "'" +
            "}";
    }
}
