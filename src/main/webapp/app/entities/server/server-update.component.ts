import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IServer } from 'app/shared/model/server.model';
import { ServerService } from './server.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-server-update',
    templateUrl: './server-update.component.html'
})
export class ServerUpdateComponent implements OnInit {
    server: IServer;
    isSaving: boolean;

    users: IUser[];
    lastCheck: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected serverService: ServerService,
        protected userService: UserService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ server }) => {
            this.server = server;
            this.lastCheck = this.server.lastCheck != null ? this.server.lastCheck.format(DATE_TIME_FORMAT) : null;
        });
        this.userService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUser[]>) => response.body)
            )
            .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.server.lastCheck = this.lastCheck != null ? moment(this.lastCheck, DATE_TIME_FORMAT) : null;
        if (this.server.id !== undefined) {
            this.subscribeToSaveResponse(this.serverService.update(this.server));
        } else {
            this.subscribeToSaveResponse(this.serverService.create(this.server));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IServer>>) {
        result.subscribe((res: HttpResponse<IServer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
}
