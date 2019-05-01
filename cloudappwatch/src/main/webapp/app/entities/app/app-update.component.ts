import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IApp } from 'app/shared/model/app.model';
import { AppService } from './app.service';
import { IServer } from 'app/shared/model/server.model';
import { ServerService } from 'app/entities/server';

@Component({
    selector: 'jhi-app-update',
    templateUrl: './app-update.component.html'
})
export class AppUpdateComponent implements OnInit {
    app: IApp;
    isSaving: boolean;

    servers: IServer[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected appService: AppService,
        protected serverService: ServerService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ app }) => {
            this.app = app;
        });
        this.serverService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IServer[]>) => mayBeOk.ok),
                map((response: HttpResponse<IServer[]>) => response.body)
            )
            .subscribe((res: IServer[]) => (this.servers = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.app.id !== undefined) {
            this.subscribeToSaveResponse(this.appService.update(this.app));
        } else {
            this.subscribeToSaveResponse(this.appService.create(this.app));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IApp>>) {
        result.subscribe((res: HttpResponse<IApp>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackServerById(index: number, item: IServer) {
        return item.id;
    }
}
