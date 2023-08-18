package tests;

import org.testng.annotations.DataProvider;
import stepmanager.ApiSteps;
import appmanager.PropertiesHelper;
import stepmanager.UrlSteps;
import io.qameta.allure.Feature;
import io.qameta.allure.testng.AllureTestNg;
import model.Repository;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Listeners(AllureTestNg.class)
@Feature("Tests for Selenide with Api GitHub")
public class SelenideApiTests {

    public PropertiesHelper propertiesHelper;
    public ApiSteps apiSteps = new ApiSteps();
    public UrlSteps urlSteps = new UrlSteps();

    public SelenideApiTests() throws IOException {
        propertiesHelper = PropertiesHelper.getInstance();
    }

    @DataProvider(name="repositoryList")
    public Iterator<Object[]> repositoryList() {
        List<Repository> data = new ArrayList<>();
        data = apiSteps.getRepositoriesFromResponse("selenide");
        List<Object[]> list = new ArrayList<>();
        for (Repository repository : data) {
            list.add(new Object[] {repository});
        }
        return list.iterator();
    }


    @Test(description = "Получение всех репозиториев, расположенных на заданном репозитории.")
    public void testGetRepositoriesListFromSelenide() {
        apiSteps.getRepositoriesFromResponse("selenide");
    }

    @Test(dataProvider = "repositoryList", description = "Проверка, что параметры name и full_name совпадает с действительностью.")
    public void testCompareInfoFromRepositories(Repository repository)  {
        String repositoryUrl = repository.getUrl();
        Repository repositoryFromSplitUrl = urlSteps.getRepository(repositoryUrl);
        Assert.assertEquals(repository, repositoryFromSplitUrl);
    }
}
