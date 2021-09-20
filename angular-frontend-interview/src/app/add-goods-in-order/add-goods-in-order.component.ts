import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Goods } from '../goods';
import { GoodsInOrder } from '../goods-in-order';
import { OrderService } from '../order.service';

@Component({
  selector: 'app-add-goods-in-order',
  templateUrl: './add-goods-in-order.component.html',
  styleUrls: ['./add-goods-in-order.component.css']
})
export class AddGoodsInOrderComponent implements OnInit {
  goodsNotInOrder: Goods[] = [];
  addedGoods: GoodsInOrder[] = [];
  id: number = 0;

  constructor(private orderService: OrderService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.orderService.getGoodsNotInOrder(this.id).subscribe(data => {
      console.log(data);
      this.goodsNotInOrder = data;
      for (let i = 0; i < data.length; i++) {
        this.addedGoods.push(new GoodsInOrder(data[i].name, 0))
      }
    }, error => console.error(error));
  }

  onSubmit() {
    let totalCount = 0;
    for (let i = 0; i < this.addedGoods.length; i++) {
      if (this.addedGoods[i].count! == null) {
        this.addedGoods[i].count! = 0;
      }
      totalCount += this.addedGoods[i].count!;
    }
    
    if (totalCount > 0) {
      this.orderService.addGoodsInOrder(this.id, this.addedGoods).subscribe(data => {
        console.log(data);
        this.router.navigate(['/orders', this.id]);
      })
    } else alert("Select at least one product");
  }

}
