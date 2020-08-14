import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpringSharedModule } from '../shared/shared.module';

import { SWAGGER_ROUTE, SwaggerComponent } from './';

@NgModule({
  imports: [SpringSharedModule, RouterModule.forRoot([SWAGGER_ROUTE], { useHash: true })],
  declarations: [SwaggerComponent],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class SpringAppSwaggerModule {}
