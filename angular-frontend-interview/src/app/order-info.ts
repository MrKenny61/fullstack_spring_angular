import { Goods } from "./goods";
import { GoodsList } from "./goods-list";
import { Order } from "./order";

export class OrderInfo {
    constructor(public clientInfo?: Order, public goodsInList?: GoodsList[], public goodsNotInList?: Goods[], public addedGoods?: GoodsList) {}
}
