package tests;

import appmanager.DataHelper;
import appmanager.PropertiesHelper;
import io.qameta.allure.Feature;
import io.qameta.allure.testng.AllureTestNg;
import model.Repository;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Listeners(AllureTestNg.class)
@Feature("Tests for Selenide with Api GitHub")
public class SelenideApiTests {

    public PropertiesHelper propertiesHelper = new PropertiesHelper();
    public DataHelper dataHelper = new DataHelper();

    public SelenideApiTests() throws IOException {
        propertiesHelper.loadProperties();
    }

    @DataProvider
    public Iterator<Object[]> validUrls() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/urls.csv")))) {
            String line = reader.readLine();
            while (line != null) {
                String[] split = line.split(";");
                list.add(split);
                line = reader.readLine();
            }
            return list.iterator();
        }
    }

    @Test(description = "Получение всех репозиториев, расположенных на заданном репозитории.")
    public void testGetRepositoriesListFromSelenide() throws IOException {
        List<String> RepositoriesNames = dataHelper.getRepositoriesNamesWithAs(propertiesHelper.getProperty("gitUrl"));
        dataHelper.getRepositoriesUrl(RepositoriesNames);
    }

    @Test(dataProvider = "validUrls", description = "Отправка API запроса из файла src/test/resources/urls.csv. Сравнивнение API ответа с реальной информацией, полученной из ссылки путем сплита.")
    public void testCompareInfoFromRepositories(String repositoryUrl)  {
        Repository repositoryFromResponse = dataHelper.getRepositoryFromResponse(repositoryUrl, Repository.class);
        Repository repositoryFromSplitUrl = dataHelper.getRepository(repositoryUrl);
        Assert.assertEquals(repositoryFromResponse, repositoryFromSplitUrl);
    }
}