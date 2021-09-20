import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GoodsInOrder } from '../goods-in-order';
import { OrderService } from '../order.service';

@Component({
  selector: 'app-edit-goods-in-order',
  templateUrl: './edit-goods-in-order.component.html',
  styleUrls: ['./edit-goods-in-order.component.css']
})
export class EditGoodsInOrderComponent implements OnInit {
  goodsInOrder: GoodsInOrder[] = [];
  id: number = 0;

  constructor(private orderService: OrderService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.orderService.getGoodsInOrder(this.id).subscribe(data => {
      console.log(data);
      this.goodsInOrder = data!;
    }, error => console.error(error));
  }

  onSubmit() {
    this.orderService.updateGoodsInOrder(this.id, this.goodsInOrder).subscribe(data => {
      console.log(data);     
      this.router.navigate(['/orders', this.id])
    }, error => console.error(error));
  }
}
