import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Goods } from '../goods';
import { GoodsList } from '../goods-list';
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
  goodsList: GoodsList[] = [];
  orderInfo: OrderInfo = new OrderInfo();

  constructor(private goodsService: GoodsService,
    private orderService: OrderService,
    private router: Router) { }

  ngOnInit(): void {
    this.goodsService.getGoodsList().subscribe(data => {
      for (let i = 0; i < data.length; i++) {
        this.goodsList[i] = data[i];
        this.goodsList[i].count = 0;
      }
      console.log(this.goodsList);
      
    });
  }

  onSubmit(){
    let totalCount = 0;
    this.goodsList.forEach(gl => totalCount += gl.count!);
    if (totalCount > 0) {
      this.orderInfo.clientInfo = this.clientInfo;
      this.orderInfo.goodsInList = this.goodsList;
      console.log(this.orderInfo);
      this.orderService.createOrder(this.orderInfo).subscribe(data => {
        console.log(data);
        this.router.navigate(['/orders']);
      }, error => console.error(error));
    } else alert("Select at least one product");    
  }
}
