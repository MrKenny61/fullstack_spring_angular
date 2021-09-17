import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import { Goods } from './goods';

@Injectable({
  providedIn: 'root'
})
export class GoodsService {
  private baseURL="http://localhost:8080/goods";

  constructor(private httpClient: HttpClient) { }

  getGoodsList(): Observable<Goods[]> {
    return this.httpClient.get<Goods[]>(`${this.baseURL}`);
  }

  createGoods(goods: Goods): Observable<Goods> {
    return this.httpClient.post(`${this.baseURL}`, goods);
  }

  getGoodsById(id: number): Observable<Goods> {
    return this.httpClient.get<Goods>(`${this.baseURL}/${id}/edit`);
  }

  updateGoods(id: number, goods: Goods): Observable<Goods> {
    return this.httpClient.patch(`${this.baseURL}/${id}`, goods);
  }

  deleteGoods(id: number): Observable<Object> {
    return this.httpClient.delete(`${this.baseURL}/${id}`);
  }
}
