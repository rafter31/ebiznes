import { EcommerceUiPage } from './app.po';

describe('ecommerce-ui App', () => {
  let page: EcommerceUiPage;

  beforeEach(() => {
    page = new EcommerceUiPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
