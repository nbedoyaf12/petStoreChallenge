package listeners;

import io.qameta.allure.Attachment;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class AllureTestListener extends AllureTestNg implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        saveTextLog(result.getMethod().getMethodName() + " failed!");
    }

    @Attachment(value = "Test Log", type = "text/plain")
    public String saveTextLog(String message) {
        return message;
    }
}
