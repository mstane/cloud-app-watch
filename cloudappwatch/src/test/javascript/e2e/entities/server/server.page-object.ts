import { element, by, ElementFinder } from 'protractor';

export class ServerComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-server div table .btn-danger'));
  title = element.all(by.css('jhi-server div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class ServerUpdatePage {
  pageTitle = element(by.id('jhi-server-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  hostNameInput = element(by.id('field_hostName'));
  statusSelect = element(by.id('field_status'));
  lastCheckInput = element(by.id('field_lastCheck'));
  adminSelect = element(by.id('field_admin'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setHostNameInput(hostName) {
    await this.hostNameInput.sendKeys(hostName);
  }

  async getHostNameInput() {
    return await this.hostNameInput.getAttribute('value');
  }

  async setStatusSelect(status) {
    await this.statusSelect.sendKeys(status);
  }

  async getStatusSelect() {
    return await this.statusSelect.element(by.css('option:checked')).getText();
  }

  async statusSelectLastOption(timeout?: number) {
    await this.statusSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setLastCheckInput(lastCheck) {
    await this.lastCheckInput.sendKeys(lastCheck);
  }

  async getLastCheckInput() {
    return await this.lastCheckInput.getAttribute('value');
  }

  async adminSelectLastOption(timeout?: number) {
    await this.adminSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async adminSelectOption(option) {
    await this.adminSelect.sendKeys(option);
  }

  getAdminSelect(): ElementFinder {
    return this.adminSelect;
  }

  async getAdminSelectedOption() {
    return await this.adminSelect.element(by.css('option:checked')).getText();
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ServerDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-server-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-server'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
