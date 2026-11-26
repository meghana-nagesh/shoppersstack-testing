package com.shoppersstack.listeners;

import com.shoppersstack.base.BaseTest;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.*;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Collects every result, takes screenshots on failure, and when the suite ends writes:
 *   reports/AutomationResults.xlsx  -> all tests (PASS / FAIL / SKIP)
 *   reports/PassedTestCases.xlsx    -> only the PASSED tests
 */
public class ResultListener implements ITestListener, ISuiteListener {

    private static final List<String[]> ROWS = Collections.synchronizedList(new ArrayList<>());
    private static final String[] HEADER = {"TC ID", "Test Scenario", "Testing Type", "Browser/Run", "Status", "Duration (s)", "Failure Reason"};

    private void record(ITestResult r, String status) {
        String desc = r.getMethod().getDescription() == null ? r.getName() : r.getMethod().getDescription();
        String id = desc.contains("|") ? desc.split("\\|")[0].trim() : r.getName();
        String scenario = desc.contains("|") ? desc.split("\\|", 2)[1].trim() : desc;
        String types = String.join(", ", r.getMethod().getGroups());
        String err = r.getThrowable() == null ? "" : String.valueOf(r.getThrowable().getMessage()).split("\n")[0];
        String secs = String.format("%.1f", (r.getEndMillis() - r.getStartMillis()) / 1000.0);
        ROWS.add(new String[]{id, scenario, types, r.getTestContext().getName(), status, secs, err});
    }

    @Override public void onTestSuccess(ITestResult r) { record(r, "PASS"); }
    @Override public void onTestSkipped(ITestResult r) { record(r, "SKIP"); }

    @Override
    public void onTestFailure(ITestResult r) {
        record(r, "FAIL");
        try {
            Object inst = r.getInstance();
            if (inst instanceof BaseTest bt && bt.getDriver() != null) {
                File src = ((TakesScreenshot) bt.getDriver()).getScreenshotAs(OutputType.FILE);
                Path dir = Path.of("reports", "screenshots");
                Files.createDirectories(dir);
                Files.copy(src.toPath(), dir.resolve(r.getName() + "_" + System.currentTimeMillis() + ".png"));
            }
        } catch (Exception ignored) { }
    }

    @Override
    public void onFinish(ISuite suite) {
        try {
            Files.createDirectories(Path.of("reports"));
            List<String[]> all = new ArrayList<>(ROWS);
            all.sort(Comparator.comparing(a -> a[0]));
            write("reports/AutomationResults.xlsx", "All Results", all);
            List<String[]> passed = new ArrayList<>();
            for (String[] row : all) if ("PASS".equals(row[4])) passed.add(row);
            write("reports/PassedTestCases.xlsx", "Passed", passed);
            System.out.println("Executed=" + all.size() + "  Passed=" + passed.size()
                    + "  -> reports/AutomationResults.xlsx and reports/PassedTestCases.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void write(String path, String sheetName, List<String[]> rows) throws Exception {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sh = wb.createSheet(sheetName);
            CellStyle head = wb.createCellStyle();
            Font bold = wb.createFont(); bold.setBold(true); head.setFont(bold);
            head.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            head.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Row h = sh.createRow(0);
            for (int i = 0; i < HEADER.length; i++) { Cell c = h.createCell(i); c.setCellValue(HEADER[i]); c.setCellStyle(head); }
            int n = 1;
            for (String[] r : rows) {
                Row row = sh.createRow(n++);
                for (int i = 0; i < r.length; i++) row.createCell(i).setCellValue(r[i]);
            }
            Row ts = sh.createRow(n + 1);
            ts.createCell(0).setCellValue("Generated: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm")));
            for (int i = 0; i < HEADER.length; i++) sh.autoSizeColumn(i);
            try (FileOutputStream out = new FileOutputStream(path)) { wb.write(out); }
        }
    }
}
