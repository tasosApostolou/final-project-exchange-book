package gr.aueb.cf.setup;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportSetup {
    private ExtentSparkReporter reporter;
    private ExtentReports extent;
    private static ReportSetup SINGLETON = null; //LAZY_IMPL: a shared default report for all tests
    private static final String SHARED_REPORT_PATH = System.getProperty("user.dir")+"\\TestReports\\defaultFile.html";
    private static ReportSetup getDefaultReport() {
        if(SINGLETON == null){
            SINGLETON = new ReportSetup();
            SINGLETON.setReporter(new ExtentSparkReporter(SHARED_REPORT_PATH));
            SINGLETON.reporter.config().setReportName("Automation Test Report");
            SINGLETON.reporter.config().setDocumentTitle("Test Results");
            SINGLETON.setExtent( new ExtentReports());
            SINGLETON.extent.attachReporter(SINGLETON.reporter);
            SINGLETON.extent.setSystemInfo("Key main extent report","value main extent report");
        }

        return SINGLETON;
    }

    public ReportSetup() {

    }

    public static ExtentReports getDefaultExtent(){
        return getDefaultReport().extent;
    }

    // API utility methods to custom new report dynamically
    public void setPath(String reporterPath){
        this.reporter = new ExtentSparkReporter(reporterPath);
    }
    public void setName(String name){
        this.reporter.config().setReportName(name);
    }

    public void setTitle(String title){
        this.reporter.config().setDocumentTitle(title);
    }

    public void setExtentAttachReporter(){
        if(this.extent == null){
            this.extent = new ExtentReports();
        }
        this.extent.attachReporter(this.reporter);
    }
    public void setExtentSystemInfo(String k, String v){
        this.extent.setSystemInfo(k,v);
    }



    public void setReporter(ExtentSparkReporter reporter) {
        this.reporter = reporter;
    }

    public void setExtent(ExtentReports extent) {
        this.extent = extent;
    }

    public ExtentSparkReporter getReporter() {
        return this.reporter;
    }

    public ExtentReports getExtent() {
        return this.extent;
    }

    public void flush(){
        if (this.extent != null) {
            this.extent.flush();
        }
    }

}
