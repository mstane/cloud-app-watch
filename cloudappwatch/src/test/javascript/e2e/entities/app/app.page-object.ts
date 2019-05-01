import { element, by, ElementFinder } from 'protractor';

export class AppComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-app div table .btn-danger'));
    title = element.all(by.css('jhi-app div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class AppUpdatePage {
    pageTitle = element(by.id('jhi-app-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    commandLineInput = element(by.id('field_commandLine'));
    serviceFlagInput = element(by.id('field_serviceFlag'));
    statusSelect = element(by.id('field_status'));
    serverSelect = element(by.id('field_server'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setCommandLineInput(commandLine) {
        await this.commandLineInput.sendKeys(commandLine);
    }

    async getCommandLineInput() {
        return this.commandLineInput.getAttribute('value');
    }

    getServiceFlagInput() {
        return this.serviceFlagInput;
    }
    async setStatusSelect(status) {
        await this.statusSelect.sendKeys(status);
    }

    async getStatusSelect() {
        return this.statusSelect.element(by.css('option:checked')).getText();
    }

    async statusSelectLastOption() {
        await this.statusSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async serverSelectLastOption() {
        await this.serverSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async serverSelectOption(option) {
        await this.serverSelect.sendKeys(option);
    }

    getServerSelect(): ElementFinder {
        return this.serverSelect;
    }

    async getServerSelectedOption() {
        return this.serverSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class AppDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-app-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-app'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
