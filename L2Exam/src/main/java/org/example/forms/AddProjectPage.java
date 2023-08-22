package org.example.forms;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class AddProjectPage extends Form {
    private final ITextBox projectNameTextBox = AqualityServices.getElementFactory().getTextBox(By.id("projectName"), "Project name");
    private final IButton saveProjectButton = AqualityServices.getElementFactory().getButton(By.xpath("//button[@type='submit']"), "Save project");
    private final ILabel successfulSaveAlert = AqualityServices.getElementFactory().getLabel(By.cssSelector("div.alert-success"), "Success alert");

    public AddProjectPage() {
        super(By.id("addProjectForm"), "Add project page");
    }

    public void sendNewProjectName(String newProjectName) {
        projectNameTextBox.sendKeys(newProjectName);
    }

    public void clickSubmitButton() {
        saveProjectButton.click();
    }

    public boolean successAlertIsPresent() {
        return successfulSaveAlert.state().isDisplayed();
    }
}
