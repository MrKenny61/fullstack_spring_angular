package ru.tarelkin.spring_backend_interview.model;

public class GoodsList {
    private Integer id;
    private String name;
    private int count;
    private int price;
    private boolean deleted = false;

    public GoodsList(Integer id, String name, int count, int price) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.price = price;
    }

    public GoodsList() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCost() {
        return count * price;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String toString() {
        return "Product name: " + name + "; Price: " + price + "; "
                + "Count: " + count + "; Total cost: " + count * price
                + "\n===============";
    }
}
