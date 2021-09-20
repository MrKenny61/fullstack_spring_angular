import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Order } from '../order';
import { OrderService } from '../order.service';

@Component({
  selector: 'app-edit-client-info',
  templateUrl: './edit-client-info.component.html',
  styleUrls: ['./edit-client-info.component.css']
})
export class EditClientInfoComponent implements OnInit {
  clientInfo: Order = new Order();
  id: number = 0;

  constructor(private orderService: OrderService,
    private route: ActivatedRoute,
    private router: Router) { }

    ngOnInit(): void {
      this.id = this.route.snapshot.params['id'];
  
      this.orderService.getClientInfo(this.id).subscribe(data => {
        console.log(data);
        this.clientInfo = data;
      }, error => console.error(error));
    }

    onSubmit() {
      this.orderService.updateClientInfo(this.id, this.clientInfo).subscribe(data => {       
        console.log(data);         
        this.router.navigate(['/orders',this.id]);
      }, error => console.error(error));
    }

}
