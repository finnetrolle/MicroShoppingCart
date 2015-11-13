package ru.finnetrolle.examples.orphanremoval.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Good {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private ShoppingCart shoppingCart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    @OneToMany(mappedBy = "good", orphanRemoval = true, cascade = {CascadeType.ALL})
    private List<Part> parts = new ArrayList<>();

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void addPart(Part part) {
        part.setGood(this);
        this.getParts().add(part);
    }

    public Part removePart(Part part) {
        this.getParts().remove(part);
        part.setGood(null);
        return part;
    }

    public List<Part> getParts() {
        return parts;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Good(String name) {
        this.name = name;
    }

    public Good() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
