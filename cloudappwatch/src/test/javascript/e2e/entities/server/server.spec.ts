// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { ServerComponentsPage, ServerDeleteDialog, ServerUpdatePage } from './server.page-object';

const expect = chai.expect;

describe('Server e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let serverUpdatePage: ServerUpdatePage;
  let serverComponentsPage: ServerComponentsPage;
  let serverDeleteDialog: ServerDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Servers', async () => {
    await navBarPage.goToEntity('server');
    serverComponentsPage = new ServerComponentsPage();
    await browser.wait(ec.visibilityOf(serverComponentsPage.title), 5000);
    expect(await serverComponentsPage.getTitle()).to.eq('cloudappwatchApp.server.home.title');
  });

  it('should load create Server page', async () => {
    await serverComponentsPage.clickOnCreateButton();
    serverUpdatePage = new ServerUpdatePage();
    expect(await serverUpdatePage.getPageTitle()).to.eq('cloudappwatchApp.server.home.createOrEditLabel');
    await serverUpdatePage.cancel();
  });

  it('should create and save Servers', async () => {
    const nbButtonsBeforeCreate = await serverComponentsPage.countDeleteButtons();

    await serverComponentsPage.clickOnCreateButton();
    await promise.all([
      serverUpdatePage.setHostNameInput('hostName'),
      serverUpdatePage.statusSelectLastOption(),
      serverUpdatePage.setLastCheckInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      serverUpdatePage.adminSelectLastOption()
    ]);
    expect(await serverUpdatePage.getHostNameInput()).to.eq('hostName', 'Expected HostName value to be equals to hostName');
    expect(await serverUpdatePage.getLastCheckInput()).to.contain(
      '2001-01-01T02:30',
      'Expected lastCheck value to be equals to 2000-12-31'
    );
    await serverUpdatePage.save();
    expect(await serverUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await serverComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Server', async () => {
    const nbButtonsBeforeDelete = await serverComponentsPage.countDeleteButtons();
    await serverComponentsPage.clickOnLastDeleteButton();

    serverDeleteDialog = new ServerDeleteDialog();
    expect(await serverDeleteDialog.getDialogTitle()).to.eq('cloudappwatchApp.server.delete.question');
    await serverDeleteDialog.clickOnConfirmButton();

    expect(await serverComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
