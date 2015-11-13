package ru.finnetrolle.examples.orphanremoval.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "articul")
    private String articul;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "good_id")
    @JsonIgnore
    private Good good;

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public Part(String articul) {
        this.articul = articul;
    }

    public Part() {
    }

    public Long getId() {
        return id;
    }

    public String getArticul() {
        return articul;
    }


}
