import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateGoodsComponent } from './create-goods/create-goods.component';
import { CreateOrderComponent } from './create-order/create-order.component';
import { EditOrderComponent } from './edit-order/edit-order.component';
import { GoodsListComponent } from './goods-list/goods-list.component';
import { OrderInfoComponent } from './order-info/order-info.component';
import { OrdersListComponent } from './orders-list/orders-list.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { UpdateGoodsComponent } from './update-goods/update-goods.component';

const routes: Routes = [
  {path: 'goods', component: GoodsListComponent},
  {path: 'goods/new', component: CreateGoodsComponent},
  {path: 'goods/:id/edit', component: UpdateGoodsComponent},
  {path: 'orders', component: OrdersListComponent},
  {path: 'orders/new', component: CreateOrderComponent},
  {path: 'orders/:id', component: OrderInfoComponent},
  {path: 'orders/:id/edit', component: EditOrderComponent},
  {path: '', redirectTo: 'orders', pathMatch: 'full'},
  {path: '**', component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
