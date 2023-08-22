import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.example.api.Api;
import org.example.constants.JsonFiles;
import org.example.forms.AddProjectPage;
import org.example.forms.HomePage;
import org.example.forms.ProjectPage;
import org.example.models.TestModel;
import org.example.utils.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestCases extends BaseClass {

    @Test
    public void testReportingPortalUIAndAPI() {
        String apiUrl = UrlAssembler.assembleApiGetTokenUrl();
        String variant = JsonFiles.TESTDATA.getValue("/variant").toString();
        HttpResponse<String> stringResponse = Api.getToken(apiUrl, variant);
        String token = stringResponse.getBody();
        Assert.assertFalse(token.isBlank(), "token has not been generated.");

        String login = JsonFiles.CONFIG.getValue("/login").toString();
        String password = JsonFiles.CONFIG.getValue("/password").toString();
        String url = UrlAssembler.assembleWebUrlWithBasicAuthentication(login, password);
        BrowserUtils.goTo(url);
        HomePage homePage = new HomePage();
        homePage.state().waitForDisplayed();
        BrowserUtils.addToken(token);
        Assert.assertTrue(homePage.state().isDisplayed(), "Projects page has not been opened.");
        BrowserUtils.refresh();
        Assert.assertEquals(homePage.getVariantNumber(), Integer.parseInt(variant), "Variant number is incorrect.");
        String projectName = JsonFiles.TESTDATA.getValue("/project_name").toString();

        int projectId = homePage.getProjectId(projectName);
        homePage.clickProjectLink(projectName);

        ProjectPage projectPage = new ProjectPage();
        projectPage.state().waitForDisplayed();
        List<TestModel> testModelList = projectPage.readTableContentIntoList();
        List<TestModel> sortedList = new ArrayList<>(testModelList);
        Collections.sort(sortedList);
        Assert.assertEquals(sortedList, testModelList, "Tests from the web page are not sorted by date descending.");

        apiUrl = UrlAssembler.assembleApiGetTestsJsonUrl();
        HttpResponse<JsonNode> jsonResponse = Api.getTestsByProjectId(apiUrl, projectId);
        TestModel[] testModelArray = JsonUtils.getJavaObjectFromJsonResponse(jsonResponse, TestModel[].class);
        Assert.assertTrue(Check.testsFromUICorrespondToThoseFromApi(testModelList, Arrays.stream(testModelArray).toList()), "Tests from UI do not correspond to those from API.");

        BrowserUtils.goBack();
        homePage.clickAddProjectButton();
        BrowserUtils.switchToLastTab();

        AddProjectPage addProjectPage = new AddProjectPage();
        addProjectPage.state().waitForDisplayed();
        String newProjectName = JsonFiles.TESTDATA.getValue("/new_project_name").toString();
        addProjectPage.sendNewProjectName(newProjectName);
        addProjectPage.clickSubmitButton();
        Assert.assertTrue(addProjectPage.successAlertIsPresent(), "There is no success alert.");
        BrowserUtils.closeTab();
        BrowserUtils.switchToLastTab();
        Assert.assertFalse(addProjectPage.state().isDisplayed(), "New project addition page has not been closed.");

        BrowserUtils.refresh();
        List<String> projectsList = homePage.getProjectNamesList();
        Assert.assertTrue(Check.hasProjectAppearedInList(projectsList, newProjectName), "New project hasn't appeared in list.");
        homePage.clickProjectLink(newProjectName);
        addProjectPage.state().waitForDisplayed();

        url = UrlAssembler.assembleApiPutTestURL();
        String sid = JsonFiles.TESTDATA.getValue("/sid").toString();
        String testName = JsonFiles.TESTDATA.getValue("/test_name").toString();
        String methodName = JsonFiles.TESTDATA.getValue("/method_name").toString();
        String env = JsonFiles.TESTDATA.getValue("/env").toString();
        String log = JsonFiles.TESTDATA.getValue("/log").toString();
        TestModel newTest = new TestModel(testName, methodName, null, null, null, null, newProjectName, sid, env);
        int testId = Integer.parseInt(Api.putTest(url, newTest).getBody());
        url = UrlAssembler.assembleApiPutTestLogURL();
        Api.putTestLog(url, testId, log);
        byte[] screenshotByte = BrowserUtils.getScreenshot();
        String screenshotString = Base64Coder.decode(screenshotByte);
        url = UrlAssembler.assembleApiPutScreenshotURL();
        Api.putTestScreenshot(url, testId, screenshotString);

        projectPage = new ProjectPage();
        projectPage.waitForTestToAppearOnPage(testName);
        List<TestModel> testsList = projectPage.readTableContentIntoList();
        Assert.assertTrue(Check.isNewTestNamePresentInTestsList(testsList, testName), "New test has appeared on page.");
    }

}
