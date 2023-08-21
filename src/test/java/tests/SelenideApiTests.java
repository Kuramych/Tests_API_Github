package tests;

import org.testng.annotations.DataProvider;
import steps.ApiSteps;
import io.qameta.allure.Feature;
import io.qameta.allure.testng.AllureTestNg;
import model.Repository;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Listeners(AllureTestNg.class)
@Feature("Тестирование репозиториев организации Selenide.")
public class SelenideApiTests {

    List<Repository> data = new ArrayList<>();

    @DataProvider(name="repositoryList")
    public Iterator<Repository> repositoryList() {
        return data.iterator();
    }

    @Test(description = "Взятие всех репозиториев организации selenide.")
    public void test1GetRepositoriesListFromSelenide() {
        data = ApiSteps.getRepositoriesFromResponse("selenide");
    }

    @Test(dataProvider = "repositoryList", description = "Сравнение действительной информации и информации с ответа.")
    public void test2CompareInfoFromRepositories(Repository repositoryFromList)  {
        Repository repositoryFromResponse = ApiSteps.getRepositoryFromResponse(repositoryFromList.getFull_name());
        Assert.assertEquals(repositoryFromList, repositoryFromResponse);
    }
}
