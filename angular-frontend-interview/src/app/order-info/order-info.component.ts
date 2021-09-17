import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { GoodsList } from '../goods-list';
import { Order } from '../order';
import { OrderInfo } from '../order-info';
import { OrderService } from '../order.service';

@Component({
  selector: 'app-order-info',
  templateUrl: './order-info.component.html',
  styleUrls: ['./order-info.component.css']
})
export class OrderInfoComponent implements OnInit {
  clientInfo: Order = new Order();
  goodsInList: GoodsList[] = [];
  id: number = 0;
  totalCost: number = 0;

  constructor(private orderService: OrderService,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.orderService.getOrderById(this.id).subscribe(data => {
      this.clientInfo = data.clientInfo!;
      this.goodsInList = data.goodsInList!;
      data.goodsInList!.forEach(gin => this.totalCost += gin.cost || 0)
      console.log(data);
    }, error => console.error(error));
  }

}
