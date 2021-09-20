import { GoodsInOrder } from "./goods-in-order";
import { Order } from "./order";

export class OrderInfo {
    constructor(public clientInfo?: Order, public goodsInOrder?: GoodsInOrder[], public countGoodsNotInOrder?: number, public addedGoods?: GoodsInOrder[]) {}
}
