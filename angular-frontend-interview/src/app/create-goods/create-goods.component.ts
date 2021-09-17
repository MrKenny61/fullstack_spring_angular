import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Goods } from '../goods';
import { GoodsService } from '../goods.service';

@Component({
  selector: 'app-create-goods',
  templateUrl: './create-goods.component.html',
  styleUrls: ['./create-goods.component.css']
})
export class CreateGoodsComponent implements OnInit {
  goods: Goods = new Goods();

  constructor(private goodsService: GoodsService,
    private router: Router) { }

  ngOnInit(): void {
  }

  onSubmit() {
    console.log(this.goods);
    this.goodsService.createGoods(this.goods).subscribe(data => {
      console.log(data);
      this.router.navigate(['/goods']);
    }, error => console.error(error));
  }

}
