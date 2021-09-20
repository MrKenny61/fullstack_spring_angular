import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Order } from '../order';
import { OrderService } from '../order.service';

@Component({
  selector: 'app-orders-list',
  templateUrl: './orders-list.component.html',
  styleUrls: ['./orders-list.component.css']
})
export class OrdersListComponent implements OnInit {
  orders: Order[] = [];

  constructor(private orderService: OrderService,
    private router: Router) { }

  ngOnInit(): void {
    this.getOrders();
  }

  private getOrders() {
    this.orderService.getOrdersList().subscribe(data => {
      this.orders = data;
    });
  }

  orderInfo(id: number) {
    this.router.navigate(['orders',id]);
  }

  deleteOrder(id: number) {
    this.orderService.deleteOrder(id).subscribe(data => {
      console.log(data);
      this.getOrders();
    });
  }
}
