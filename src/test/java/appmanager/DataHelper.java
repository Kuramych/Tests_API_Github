package appmanager;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class DataHelper {


    public ApiHelper apiHelper = new ApiHelper();


    @Step("Отправление API запроса по ссылке url и парсинг информации с ответа к виду {1} .")
    public <Model> Model getRepositoryFromResponse(String url, Class<Model> cls) {
        Response response = apiHelper.sendGet(url);
        Model repository = response.getBody().as(cls);
        return repository;
    }

    @Step("Получение списка всех репозиториев организации selenide")
    public List<String> getRepositoriesNamesWithAs(String url) {
        Response response = apiHelper.sendGet(url);
        Repository[] repositories = response.as(Repository[].class);

        List<String> repositoryNames = new ArrayList<>();

        for (Repository repository : repositories) {
            repositoryNames.add(repository.getName());
        }
        return repositoryNames;
    }

    @Step("Форматирование имен репозиториев в ссылки для API запросов.")
    public List<String> getRepositoriesUrl(List<String> repositoryNames) throws IOException {
        List<String> repositoriesUrls = new ArrayList<>();
        for (String repName : repositoryNames) {
            repositoriesUrls.add(String.format("https://api.github.com/repos/selenide/%s", repName));
        }
        File file = new File("src/test/resources/urls.csv");
        saveAsCSV(repositoriesUrls, file);
        return repositoriesUrls;
    }


    @Step("Сохранение ссылок в файл.")
    public void saveAsCSV(List<String> repositoriesUrls, File file) throws IOException {
        try (Writer writer = new FileWriter(file)) {
            for (String repositoriesUrl : repositoriesUrls) {
                writer.write(String.format("%s;\n", repositoriesUrl));
            }
        }
    }

    @Step("Ссылка repositoryUrl парсится и приводится к типу class model.Repository")
    public Repository getRepository(String repositoryUrl) {
        String[] parts = repositoryUrl.split("/");
        String name = parts[5];
        String fullname = parts[4] + "/" + parts[5];
        Repository repositoryFromSplitUrl = new Repository().withName(name).withFullName(fullname);
        return repositoryFromSplitUrl;
    }
}
