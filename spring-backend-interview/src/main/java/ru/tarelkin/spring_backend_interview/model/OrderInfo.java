package ru.tarelkin.spring_backend_interview.model;

import java.util.List;

public class OrderInfo {
    private Order clientInfo;
    private List<GoodsInOrder> goodsInOrder;
    private int countGoodsNotInOrder;
    private List<GoodsInOrder> addedGoods;

    public Order getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(Order clientInfo) {
        this.clientInfo = clientInfo;
    }

    public List<GoodsInOrder> getGoodsInOrder() {
        return goodsInOrder;
    }

    public void setGoodsInOrder(List<GoodsInOrder> goodsInList) {
        this.goodsInOrder = goodsInList;
    }

    public int getCountGoodsNotInOrder() {
        return countGoodsNotInOrder;
    }

    public void setCountGoodsNotInOrder(int countGoodsNotInOrder) {
        this.countGoodsNotInOrder = countGoodsNotInOrder;
    }

    public List<GoodsInOrder> getAddedGoods() {
        return addedGoods;
    }

    public void setAddedGoods(List<GoodsInOrder> addedGoods) {
        this.addedGoods = addedGoods;
    }


}
