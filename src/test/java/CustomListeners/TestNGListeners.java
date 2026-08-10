package CustomListeners;

import io.PaySky.pages.utiles.AllureUtils;
import io.PaySky.pages.utiles.ScreenShotUtils;
import org.testng.*;

public class TestNGListeners implements ITestListener, IRetryAnalyzer, IInvokedMethodListener {

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            System.out.println(method.getTestMethod().getMethodName() + " started");
        }
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            ScreenShotUtils.takeScreenShotForElement(Base.BaseTest.driver, testResult.getName());
            System.out.println(method.getTestMethod().getMethodName() + " finished");
        }

    }

    public void onTestSuccess(ITestResult result) {

        System.out.println(result.getMethod().getMethodName() + " passed");
    }

    public void onTestFailure(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " failed");
    }

    public void onTestSkipped(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " skipped");
    }

    public void onExecutionStart() {
        System.out.println("Execution started");
        AllureUtils.cleanAllureResults();
    }

    public void onExecutionFinish() {
        System.out.println("Execution finished");
        AllureUtils.setAllureEnvironment();
    }


    private int attemps = 0;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (iTestResult.getStatus() == ITestResult.FAILURE && attemps == 0) {
            attemps++;
            return true;
        }
        return false;
    }

}
