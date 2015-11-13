package ru.finnetrolle.examples.orphanremoval.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class ShoppingCart {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "shoppingCart", cascade = {CascadeType.PERSIST})
    private List<Good> goods = new ArrayList<>();

    public void addGood(Good good) {
        good.setShoppingCart(this);
        this.getGoods().add(good);
    }

    public Good removeGood(Good good) {
        this.getGoods().remove(good);
        good.setShoppingCart(null);
        return good;
    }

    public ShoppingCart() {
    }

    public List<Good> getGoods() {
        return goods;
    }

    public ShoppingCart(Long id, String name) {
        this.name = name; this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
