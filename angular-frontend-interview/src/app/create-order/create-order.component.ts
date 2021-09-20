import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Goods } from '../goods';
import { GoodsInOrder } from '../goods-in-order';
import { GoodsService } from '../goods.service';
import { Order } from '../order';
import { OrderInfo } from '../order-info';
import { OrderService } from '../order.service';

@Component({
  selector: 'app-create-order',
  templateUrl: './create-order.component.html',
  styleUrls: ['./create-order.component.css']
})
export class CreateOrderComponent implements OnInit {
  clientInfo: Order = new Order();
  goodsInOrder: GoodsInOrder[] = [];
  orderInfo: OrderInfo = new OrderInfo();

  constructor(private goodsService: GoodsService,
    private orderService: OrderService,
    private router: Router) { }

  ngOnInit(): void {
    this.goodsService.getGoodsList().subscribe(data => {
      for (let i = 0; i < data.length; i++) {
        this.goodsInOrder[i] = data[i];
        this.goodsInOrder[i].count = 0;
      }
      console.log(data);
      
    });
  }

  onSubmit(){
    let totalCount = 0;
    for (let i = 0; i < this.goodsInOrder.length; i++) {
      if (this.goodsInOrder[i].count! == null) {
        this.goodsInOrder[i].count! = 0;
      }
      totalCount += this.goodsInOrder[i].count!;
    }
    if (totalCount > 0) {
      this.orderInfo.clientInfo = this.clientInfo;
      this.orderInfo.goodsInOrder = this.goodsInOrder;
      console.log(this.orderInfo);
      this.orderService.createOrder(this.orderInfo).subscribe(data => {
        console.log(data);
        this.router.navigate(['/orders']);
      }, error => console.error(error));
    } else alert("Select at least one product");    
  }
}
