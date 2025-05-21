package tests;

import core.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ExcelUtils;

import java.io.IOException;

public class TC01 extends BaseTest {
    @Test(dataProvider = "testData")
    public void test(String username, String password){
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigateToPage("https://sma.tec.sh/admin/login");
        loginPage.login(username, password);
    }

    @DataProvider(name = "testData")
    public Object[][] getData() throws IOException {
        return ExcelUtils.getTableArray("src/Data.xlsx", "Sheet1",0,2);
    }
}
