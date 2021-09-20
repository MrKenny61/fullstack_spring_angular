import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GoodsInOrder } from '../goods-in-order';
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
  goodsInOrder: GoodsInOrder[] = [];
  id: number = 0;
  totalCost: number = 0;
  len = 0;

  constructor(private orderService: OrderService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.orderService.getOrderById(this.id).subscribe(data => {
      this.clientInfo = data.clientInfo!;
      this.goodsInOrder = data.goodsInOrder!;
      data.goodsInOrder!.forEach(gin => this.totalCost += gin.cost || 0);
      this.len = data.countGoodsNotInOrder || 0;
      console.log(data);
    }, error => console.error(error));
  }

  editClientInfo(id: number) {
    this.router.navigate(['/orders', id, 'edit-client-info'])
  }

  editGoodsInOrder(id: number) {
    this.router.navigate(['/orders', id, 'edit-goods'])
  }

  addGoodsInOrder(id: number) {
    this.router.navigate(['/orders', id, 'add-goods'])
  }
}
