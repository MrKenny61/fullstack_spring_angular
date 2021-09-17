import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
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

  getOrderByIdForEdit(id: number): Observable<OrderInfo> {
    return this.httpClient.get<OrderInfo>(`${this.baseURL}/${id}/edit`);
  }

  createOrder(orderInfo: OrderInfo): Observable<OrderInfo> {
    return this.httpClient.post(`${this.baseURL}`, orderInfo);
  }

  updateOrder(id: number, orderInfo: OrderInfo): Observable<OrderInfo> {
    return this.httpClient.patch(`${this.baseURL}/${id}`, orderInfo);
  }

  deleteOrder(id: number): Observable<Object> {
    return this.httpClient.delete(`${this.baseURL}/${id}`);
  }
}
