package ru.tarelkin.spring_backend_interview.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private String client;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date = new Date();

    private String address;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderLine> orderLines = new HashSet<>();

    public Order() {}

    public Order(Integer id, String client, Date date, String address) {
        this.id = id;
        this.client = client;
        this.date = date;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    @NonNull
    public String getClient() {
        return client;
    }

    public void setClient(@NonNull String client) {
        this.client = client;
    }

    public Date getDate() {
        return date;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    public Set<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(Set<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public String toString() {
        return client + " " + address;
    }
}

