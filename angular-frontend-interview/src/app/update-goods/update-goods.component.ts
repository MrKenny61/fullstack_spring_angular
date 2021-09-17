import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Goods } from '../goods';
import { GoodsService } from '../goods.service';

@Component({
  selector: 'app-update-goods',
  templateUrl: './update-goods.component.html',
  styleUrls: ['./update-goods.component.css']
})
export class UpdateGoodsComponent implements OnInit {
  goods: Goods = new Goods();
  id: number = 0;

  constructor(private goodsService: GoodsService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id']

    this.goodsService.getGoodsById(this.id).subscribe(data => {
      this.goods = data;
    }, error => console.error(error));
  }

  onSubmit() {
    console.log(this.goods);
    this.goodsService.updateGoods(this.id, this.goods).subscribe(data => {
      this.router.navigate(['/goods']);
    }, error => console.error(error));
  }
}
