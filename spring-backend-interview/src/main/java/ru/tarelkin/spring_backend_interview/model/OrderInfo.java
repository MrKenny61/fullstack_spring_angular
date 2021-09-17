package ru.tarelkin.spring_backend_interview.model;

import java.util.List;

public class OrderInfo {
    private Order clientInfo;
    private List<GoodsList> goodsInList;
    private List<Goods> goodsNotInList;
    private GoodsList addedGoods;

    public Order getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(Order clientInfo) {
        this.clientInfo = clientInfo;
    }

    public List<GoodsList> getGoodsInList() {
        return goodsInList;
    }

    public void setGoodsInList(List<GoodsList> goodsInList) {
        this.goodsInList = goodsInList;
    }

    public List<Goods> getGoodsNotInList() {
        return goodsNotInList;
    }

    public void setGoodsNotInList(List<Goods> goodsNotInList) {
        this.goodsNotInList = goodsNotInList;
    }

    public GoodsList getAddedGoods() {
        return addedGoods;
    }

    public void setAddedGoods(GoodsList addedGoods) {
        this.addedGoods = addedGoods;
    }
}
