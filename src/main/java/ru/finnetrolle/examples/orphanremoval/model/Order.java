package ru.finnetrolle.examples.orphanremoval.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "order")
    private List<Good> goods = new ArrayList<>();

    public Order(String name) {
        this.name = name;
    }

    public Order() {
    }

    public void addGood(Good good) {
        good.setOrder(this);
        this.getGoods().add(good);
    }

    public Good removeGood(Good good) {
        this.getGoods().remove(good);
        good.setOrder(null);
        return good;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Good> getGoods() {
        return goods;
    }
}
