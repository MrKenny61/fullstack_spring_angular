package ru.tarelkin.spring_backend_interview.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "goods")
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private int price;

    @OneToMany(mappedBy = "goods", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderLine> orderLines = new HashSet<>();

    public Goods(Integer id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Goods() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Set<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(Set<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    @Override
    public String toString() {
        return "ID " + id + "; Product " + name + "; Price " + price + "; OrderLines " + orderLines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Goods)) return false;
        Goods goods = (Goods) o;
        return price == goods.price && Objects.equals(id, goods.id) && name.equals(goods.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }
}
