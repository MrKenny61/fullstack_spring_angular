import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Goods } from './goods';
import { GoodsInOrder } from './goods-in-order';
import { Order } from './order';
import { OrderInfo } from './order-info';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private baseURL="http://localhost:8080/orders";

  constructor(private httpClient: HttpClient) { }

  getOrdersList(): Observable<Order[]> {
    return this.httpClient.get<Order[]>(`${this.baseURL}`);
  }

  getOrderById(id: number): Observable<OrderInfo> {
    return this.httpClient.get<OrderInfo>(`${this.baseURL}/${id}`);
  }

  createOrder(orderInfo: OrderInfo): Observable<OrderInfo> {
    return this.httpClient.post(`${this.baseURL}`, orderInfo);
  }
  
  getClientInfo(id: number): Observable<Order> {
    return this.httpClient.get<Order>(`${this.baseURL}/${id}/edit-client-info`)
  }

  updateClientInfo(id: number, clientInfo: Order): Observable<Order> {
    return this.httpClient.patch(`${this.baseURL}/${id}/update-client-info`, clientInfo);
  }
  
  getGoodsInOrder(id: number): Observable<GoodsInOrder[]> {
    return this.httpClient.get<GoodsInOrder[]>(`${this.baseURL}/${id}/edit-goods`)
  }

  updateGoodsInOrder(id: number, goodsInOrder: GoodsInOrder[]): Observable<Object> {
    return this.httpClient.patch(`${this.baseURL}/${id}/update-goods`, goodsInOrder);
  }
  
  getGoodsNotInOrder(id: number): Observable<Goods[]> {
    return this.httpClient.get<Goods[]>(`${this.baseURL}/${id}/add-goods`)
  }

  addGoodsInOrder(id: number, addedGoods: GoodsInOrder[]): Observable<Object> {
    return this.httpClient.patch(`${this.baseURL}/${id}/add-goods`, addedGoods);
  }

  deleteOrder(id: number): Observable<Object> {
    return this.httpClient.delete(`${this.baseURL}/${id}`);
  }
}
