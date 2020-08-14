import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { SwaggerComponent } from './swagger.component';

export const SWAGGER_ROUTE: Route = {
  path: 'swagger',
  component: SwaggerComponent,
  data: {
    authorities: [],
    pageTitle: 'swagger.title',
  },
  canActivate: [UserRouteAccessService],
};
