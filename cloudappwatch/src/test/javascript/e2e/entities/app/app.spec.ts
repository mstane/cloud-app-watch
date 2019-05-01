/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AppComponentsPage, AppDeleteDialog, AppUpdatePage } from './app.page-object';

const expect = chai.expect;

describe('App e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let appUpdatePage: AppUpdatePage;
    let appComponentsPage: AppComponentsPage;
    let appDeleteDialog: AppDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Apps', async () => {
        await navBarPage.goToEntity('app');
        appComponentsPage = new AppComponentsPage();
        await browser.wait(ec.visibilityOf(appComponentsPage.title), 5000);
        expect(await appComponentsPage.getTitle()).to.eq('cloudappwatchApp.app.home.title');
    });

    it('should load create App page', async () => {
        await appComponentsPage.clickOnCreateButton();
        appUpdatePage = new AppUpdatePage();
        expect(await appUpdatePage.getPageTitle()).to.eq('cloudappwatchApp.app.home.createOrEditLabel');
        await appUpdatePage.cancel();
    });

    it('should create and save Apps', async () => {
        const nbButtonsBeforeCreate = await appComponentsPage.countDeleteButtons();

        await appComponentsPage.clickOnCreateButton();
        await promise.all([
            appUpdatePage.setCommandLineInput('commandLine'),
            appUpdatePage.statusSelectLastOption(),
            appUpdatePage.serverSelectLastOption()
        ]);
        expect(await appUpdatePage.getCommandLineInput()).to.eq('commandLine');
        const selectedServiceFlag = appUpdatePage.getServiceFlagInput();
        if (await selectedServiceFlag.isSelected()) {
            await appUpdatePage.getServiceFlagInput().click();
            expect(await appUpdatePage.getServiceFlagInput().isSelected()).to.be.false;
        } else {
            await appUpdatePage.getServiceFlagInput().click();
            expect(await appUpdatePage.getServiceFlagInput().isSelected()).to.be.true;
        }
        await appUpdatePage.save();
        expect(await appUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await appComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last App', async () => {
        const nbButtonsBeforeDelete = await appComponentsPage.countDeleteButtons();
        await appComponentsPage.clickOnLastDeleteButton();

        appDeleteDialog = new AppDeleteDialog();
        expect(await appDeleteDialog.getDialogTitle()).to.eq('cloudappwatchApp.app.delete.question');
        await appDeleteDialog.clickOnConfirmButton();

        expect(await appComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
