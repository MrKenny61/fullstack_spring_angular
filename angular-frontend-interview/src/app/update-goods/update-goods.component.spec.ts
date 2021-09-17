import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateGoodsComponent } from './update-goods.component';

describe('UpdateGoodsComponent', () => {
  let component: UpdateGoodsComponent;
  let fixture: ComponentFixture<UpdateGoodsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateGoodsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateGoodsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
