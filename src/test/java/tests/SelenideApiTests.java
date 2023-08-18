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
    public Iterator<Object[]> repositoryList() {
        List<Object[]> list = new ArrayList<>();
        for (Repository repository : data) {
            list.add(new Object[] {repository});
        }
        return list.iterator();
    }

    @Test(description = "Получение всех репозиториев, расположенных на заданном репозитории.")
    public void test1GetRepositoriesListFromSelenide() {
        data = ApiSteps.getRepositoriesFromResponse("selenide");
    }

    @Test(dataProvider = "repositoryList", description = "Проверка, что параметры name и full_name совпадает с действительностью.")
    public void test2CompareInfoFromRepositories(Repository repositoryFromList)  {
        Repository repositoryFromResponse = ApiSteps.getRepositoryFromResponse(repositoryFromList.getFull_name(), Repository.class);
        Assert.assertEquals(repositoryFromList, repositoryFromResponse);
    }
}
