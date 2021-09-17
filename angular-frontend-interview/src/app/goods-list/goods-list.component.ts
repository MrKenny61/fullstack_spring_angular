import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Goods } from '../goods';
import { GoodsService } from '../goods.service';

@Component({
  selector: 'app-goods-list',
  templateUrl: './goods-list.component.html',
  styleUrls: ['./goods-list.component.css']
})
export class GoodsListComponent implements OnInit {
  goods: Goods[] = [];

  constructor(private goodsService: GoodsService,
    private router: Router) { }

  ngOnInit(): void {
    this.getGoods();
  }

  private getGoods() {
    this.goodsService.getGoodsList().subscribe(data => {
      this.goods = data;
    });
  }

  updateGoods(id: number) {
    this.router.navigate(['goods',id,'edit']);
  }

  deleteGoods(id: number) {
    this.goodsService.deleteGoods(id).subscribe(data => {
      console.log(data);
      this.getGoods();
    });
  }

}
