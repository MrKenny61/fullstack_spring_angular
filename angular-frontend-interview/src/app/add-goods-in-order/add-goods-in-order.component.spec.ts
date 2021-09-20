import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddGoodsInOrderComponent } from './add-goods-in-order.component';

describe('AddGoodsInOrderComponent', () => {
  let component: AddGoodsInOrderComponent;
  let fixture: ComponentFixture<AddGoodsInOrderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddGoodsInOrderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddGoodsInOrderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
