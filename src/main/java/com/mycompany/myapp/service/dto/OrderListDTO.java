package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.Address;
import com.mycompany.myapp.domain.Books;
import com.mycompany.myapp.domain.SUser;
import com.mycompany.myapp.domain.Shopping;

import java.util.List;

public class OrderListDTO {
    private Long id;

    private SUser sUser ;

    private Address address;

    private List<Shopping> shoppings;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SUser getsUser() {
        return sUser;
    }

    public void setsUser(SUser sUser) {
        this.sUser = sUser;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Shopping> getShoppings() {
        return shoppings;
    }

    public void setShoppings(List<Shopping> shoppings) {
        this.shoppings = shoppings;
    }

    @Override
    public String toString() {
        return "OrderListDTO{" + "id=" + id + ", sUser=" + sUser + ", address=" + address + ", shoppings=" + shoppings + '}';
    }
}
