import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-swagger',
  templateUrl: './swagger.component.html',
  styleUrls: ['swagger.component.scss'],
})
export class SwaggerComponent implements OnInit {
  message: string;

  constructor() {
    this.message = 'SwaggerComponent message';
  }

  ngOnInit(): void {}
}
