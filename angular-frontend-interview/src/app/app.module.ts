import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { GoodsListComponent } from './goods-list/goods-list.component';
import { CreateGoodsComponent } from './create-goods/create-goods.component';
import { FormsModule } from '@angular/forms';
import { UpdateGoodsComponent } from './update-goods/update-goods.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { OrdersListComponent } from './orders-list/orders-list.component';
import { OrderInfoComponent } from './order-info/order-info.component';
import { CreateOrderComponent } from './create-order/create-order.component';
import { EditClientInfoComponent } from './edit-client-info/edit-client-info.component';
import { EditGoodsInOrderComponent } from './edit-goods-in-order/edit-goods-in-order.component';
import { AddGoodsInOrderComponent } from './add-goods-in-order/add-goods-in-order.component';

@NgModule({
  declarations: [
    AppComponent,
    GoodsListComponent,
    CreateGoodsComponent,
    UpdateGoodsComponent,
    PageNotFoundComponent,
    OrdersListComponent,
    OrderInfoComponent,
    CreateOrderComponent,
    EditClientInfoComponent,
    EditGoodsInOrderComponent,
    AddGoodsInOrderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
