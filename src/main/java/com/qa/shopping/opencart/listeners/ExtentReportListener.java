package com.qa.shopping.opencart.listeners;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.qa.shopping.opencart.factory.DriverFactory;

public class ExtentReportListener extends DriverFactory implements ITestListener {

    private static final String OUTPUT_FOLDER = "./report/";
//    private static final String FILE_NAME = "TestExecutionReport_" + getCurrentDateAndTime() + ".html";
    private static final String FILE_NAME = "TestExecutionReport.html";
    private static ExtentReports extent = init();
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static ExtentReports extentReports;
    private String methodName;
    private String className;

    private static ExtentReports init() {
        Path path = Paths.get(OUTPUT_FOLDER);
        // if directory exists?
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                // fail to create directory
                e.printStackTrace();
            }
        }

        extentReports = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter(OUTPUT_FOLDER + FILE_NAME);
        reporter.config().setReportName("Automation Test Results");
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("System", System.getProperties().getProperty("os.name"));
        extentReports.setSystemInfo("Author", "45117005");
        extentReports.setSystemInfo("Build#", "1.0");

        return extentReports;
    }

    public synchronized void onStart(ITestContext context) {
        System.out.println("Test Suite started!");
    }

    public synchronized void onFinish(ITestContext context) {
        System.out.println(("Test Suite is ending!"));
        extent.flush();
        test.remove();
    }

    public synchronized void onTestStart(ITestResult result) {
        methodName = result.getMethod().getMethodName();
//        qualifiedName = result.getMethod().getQualifiedName();
//        int last = qualifiedName.lastIndexOf(".");
//        int mid = qualifiedName.substring(0, last).lastIndexOf(".");
//        className = qualifiedName.substring(mid + 1, last);
        className = getClassName(result);
        System.out.println(methodName + " started!");
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),
                result.getMethod().getDescription());

        extentTest.assignCategory(result.getTestContext().getSuite().getName());
        /*
         * methodName = StringUtils.capitalize(StringUtils.join(StringUtils.
         * splitByCharacterTypeCamelCase(methodName), StringUtils.SPACE));
         */
        extentTest.assignCategory(className);
        test.set(extentTest);
        test.get().getModel().setStartTime(getTime(result.getStartMillis()));
    }

    public synchronized void onTestSuccess(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " passed!"));
        test.get().pass("PASSED");
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    public synchronized void onTestFailure(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " failed!"));
        methodName = result.getMethod().getMethodName();
        className = getClassName(result);
        System.out.println("Class Name: " + className);
        test.get().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(getScreenshot(className, methodName)).build());
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
        /**
         * you can also use below code to add/也可以这样来添加:
         *         ExtentTest test = (ExtentTest)result.getAttribute("test");
         *         test.addScreenCaptureFromPath(getScreenshot());
         */
        }

    public synchronized void onTestSkipped(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " skipped!"));
        methodName = result.getMethod().getMethodName();
        className = getClassName(result);
        test.get().skip(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(getScreenshot(className, methodName)).build());
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
    }

    public Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    private static String getCurrentDateAndTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    public String getClassName(ITestResult result) {
        String qualifiedName = result.getMethod().getQualifiedName();
        int last = qualifiedName.lastIndexOf(".");
        int mid = qualifiedName.substring(0, last).lastIndexOf(".");
        className = qualifiedName.substring(mid + 1, last);
        return className;
    }

}
