import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Goods } from '../goods';
import { GoodsList } from '../goods-list';
import { Order } from '../order';
import { OrderInfo } from '../order-info';
import { OrderService } from '../order.service';

@Component({
  selector: 'app-edit-order',
  templateUrl: './edit-order.component.html',
  styleUrls: ['./edit-order.component.css']
})
export class EditOrderComponent implements OnInit {
  clientInfo: Order = new Order();
  goodsInList: GoodsList[] = [];
  goodsNotInList: Goods[] = [];
  addedGoods: GoodsList = new GoodsList();
  orderInfo: OrderInfo = new OrderInfo();
  id: number = 0;

  constructor(private orderService: OrderService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.orderService.getOrderByIdForEdit(this.id).subscribe(data => {
      console.log(data);
      this.clientInfo = data.clientInfo!;
      this.goodsInList = data.goodsInList!;
      this.goodsNotInList = data.goodsNotInList!;
    }, error => console.error(error));

    let len = this.goodsNotInList.length;
  }

  onSubmit() {
    this.orderInfo.clientInfo = this.clientInfo;
    this.orderInfo.goodsInList = this.goodsInList;
    this.orderInfo.addedGoods = this.addedGoods;
    console.log(this.orderInfo);
    this.orderService.updateOrder(this.id, this.orderInfo).subscribe(data => {
      this.router.navigate(['/orders',this.id]);
    }, error => console.error(error));
  }

}
