package com.esen.bookstore.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Map;
import java.util.Objects;

@Entity
public class Bookstore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String location;
    private Double priceModifier;
    private Double moneyInRegister;

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<Book, Integer> inventory;


    public Bookstore(Long id, String location, Double priceModifier, Double moneyInRegister) {
        this.id = id;
        this.location = location;
        this.priceModifier = priceModifier;
        this.moneyInRegister = moneyInRegister;
    }

    public Bookstore() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getPriceModifier() {
        return priceModifier;
    }

    public void setPriceModifier(Double priceModifier) {
        this.priceModifier = priceModifier;
    }

    public Double getMoneyInRegister() {
        return moneyInRegister;
    }

    public void setMoneyInRegister(Double moneyInRegister) {
        this.moneyInRegister = moneyInRegister;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bookstore bookstore = (Bookstore) o;
        return Objects.equals(id, bookstore.id) && Objects.equals(location, bookstore.location) && Objects.equals(priceModifier, bookstore.priceModifier) && Objects.equals(moneyInRegister, bookstore.moneyInRegister);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location, priceModifier, moneyInRegister);
    }
}
