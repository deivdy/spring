import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { SpringSharedModule } from 'app/shared/shared.module';
import { SpringCoreModule } from 'app/core/core.module';
import { SpringAppRoutingModule } from './app-routing.module';
import { SpringHomeModule } from './home/home.module';
import { SpringEntityModule } from './entities/entity.module';
import { SpringAppSwaggerModule } from './swagger/swagger.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    SpringSharedModule,
    SpringCoreModule,
    SpringHomeModule,
    SpringAppSwaggerModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    SpringEntityModule,
    SpringAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class SpringAppModule {}
