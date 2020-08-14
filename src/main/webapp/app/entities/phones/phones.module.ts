import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpringSharedModule } from 'app/shared/shared.module';
import { PhonesComponent } from './phones.component';
import { PhonesDetailComponent } from './phones-detail.component';
import { PhonesUpdateComponent } from './phones-update.component';
import { PhonesDeleteDialogComponent } from './phones-delete-dialog.component';
import { phonesRoute } from './phones.route';

@NgModule({
  imports: [SpringSharedModule, RouterModule.forChild(phonesRoute)],
  declarations: [PhonesComponent, PhonesDetailComponent, PhonesUpdateComponent, PhonesDeleteDialogComponent],
  entryComponents: [PhonesDeleteDialogComponent],
})
export class SpringPhonesModule {}
