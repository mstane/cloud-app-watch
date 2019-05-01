import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CloudappwatchSharedModule } from 'app/shared';
import {
    AppComponent,
    AppDetailComponent,
    AppUpdateComponent,
    AppDeletePopupComponent,
    AppDeleteDialogComponent,
    appRoute,
    appPopupRoute
} from './';

const ENTITY_STATES = [...appRoute, ...appPopupRoute];

@NgModule({
    imports: [CloudappwatchSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [AppComponent, AppDetailComponent, AppUpdateComponent, AppDeleteDialogComponent, AppDeletePopupComponent],
    entryComponents: [AppComponent, AppUpdateComponent, AppDeleteDialogComponent, AppDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CloudappwatchAppModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
