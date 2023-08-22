package org.example.forms;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.example.models.TestModel;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProjectPage extends Form {
    private final String newTestXpath = "//a[contains(text(), '%s')]";
    private final ILabel table = AqualityServices.getElementFactory().getLabel(By.id("allTests"), "Table");
    private final By linkLocator = By.xpath("//a");
    private final By rowLocator = By.xpath("//tr");
    private final By columnLocator = By.xpath("//td");
    private final By spanLocator = By.xpath("//span");
    private List<ILabel> rows = new ArrayList<>();

    public ProjectPage() {
        super(By.xpath("//button[contains(@data-target, '#addTest')]"), "Project page");
    }

    public List<TestModel> readTableContentIntoList() {
        initializeRowsList();
        List<TestModel> testModelList = new ArrayList<>();
        for (ILabel row : rows) {
            testModelList.add(getTestModelFromRow(row));
        }
        testModelList.removeAll(Collections.singleton(null));
        return testModelList;
    }

    private void initializeRowsList() {
        table.state().waitForClickable();
        rows = table.findChildElements(rowLocator, "Rows list", ElementType.LABEL);
    }

    private TestModel getTestModelFromRow(ILabel row) {
        List<ILabel> columns = row.findChildElements(columnLocator, ElementType.LABEL);
        System.out.println(columns.size());
        if (columns.size() > 0) {
            String name = columns.get(0).findChildElement(linkLocator, ElementType.LABEL).getText();
            String method = columns.get(1).getText();
            String status = columns.get(2).findChildElement(spanLocator, ElementType.LABEL).getText().toLowerCase();
            String startTime = columns.get(3).getText();
            String endTime = columns.get(4).getText();
            if (endTime.length() == 0) {
                endTime = null;
            }
            String duration = columns.get(5).getText();
            return new TestModel(name, method, status, startTime, endTime, duration, null, null, null);
        }
        return null;
    }

    public void waitForTestToAppearOnPage(String testName) {
        AqualityServices.getElementFactory().getLink(By.xpath(String.format(newTestXpath, testName)), "New test link").state().waitForClickable();
    }
}
