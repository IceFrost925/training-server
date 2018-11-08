package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.List;

public class MenusListDTO implements Serializable {

    private String first;
    private List second;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public List getSecond() {
        return second;
    }

    public void setSecond(List second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "MenusListDTO{" + "first='" + first + '\'' + ", second=" + second + '}';
    }
}
