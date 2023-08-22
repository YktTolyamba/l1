package org.example.utils;

import org.example.models.TestModel;

import java.util.List;

public class Check {

    public static boolean hasProjectAppearedInList(List<String> projects, String newProjectName) {
        for (String projectName : projects) {
            if (projectName.equals(newProjectName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean testsFromUICorrespondToThoseFromApi(List<TestModel> testsFromUI, List<TestModel> testsFromApi) {
        for (TestModel testModel : testsFromUI) {
            if (!(testsFromApi.contains(testModel))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNewTestNamePresentInTestsList(List<TestModel> testsList, String testName) {
        for (TestModel test : testsList) {
            if (test.getName().equals(testName)) {
                return true;
            }
        }
        return false;
    }

}
