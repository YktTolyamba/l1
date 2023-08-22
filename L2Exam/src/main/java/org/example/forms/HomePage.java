package org.example.forms;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.example.utils.StringUtils;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends Form {
    private final ILabel variantLabel = AqualityServices.getElementFactory().getLabel(By.xpath("//footer[@class='footer']//child::span"), "Variant");
    private final IButton addProjectButton = AqualityServices.getElementFactory().getButton(By.xpath("//a[@href='addProject']"), "Add project");
    private List<ILink> projectLinksList = new ArrayList<>();

    public HomePage() {
        super(By.xpath("//button[@data-target='#addProject']"), "Projects list");
    }

    public int getVariantNumber() {
        String variantLabelText = variantLabel.getText();
        return StringUtils.getNumberFromString(variantLabelText, " ");
    }

    public int getProjectId(String projectName) {
        projectLinksList = getProjectLinksList();
        for (ILink projectLink : projectLinksList) {
            if (projectLink.getText().equals(projectName)) {
                String href = projectLink.getAttribute("href");
                return StringUtils.getNumberFromString(href, "=");
            }
        }
        throw new RuntimeException("Project \"" + projectName + "\" not found in projects list!");
    }

    public void clickAddProjectButton() {
        addProjectButton.click();
    }

    public List<String> getProjectNamesList() {
        projectLinksList = getProjectLinksList();
        List<String> projectsList = new ArrayList<>();
        for (ILink projectLink : projectLinksList) {
            projectsList.add(projectLink.getText());
        }
        return projectsList;
    }

    public void clickProjectLink(String projectName) {
        projectLinksList = getProjectLinksList();
        for (ILink projectLink : projectLinksList) {
            if (projectLink.getText().equals(projectName)) {
                projectLink.click();
                return;
            }
        }
    }

    private List<ILink> getProjectLinksList() {
        return AqualityServices.getElementFactory().findElements(By.xpath("//a[@class='list-group-item']"), ElementType.LINK);
    }
}
