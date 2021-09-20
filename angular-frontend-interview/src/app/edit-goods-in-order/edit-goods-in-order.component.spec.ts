import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditGoodsInOrderComponent } from './edit-goods-in-order.component';

describe('EditGoodsInOrderComponent', () => {
  let component: EditGoodsInOrderComponent;
  let fixture: ComponentFixture<EditGoodsInOrderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditGoodsInOrderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditGoodsInOrderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
