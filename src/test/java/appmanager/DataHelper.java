package appmanager;

import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static io.qameta.allure.Allure.step;

public class DataHelper {


    public ApiHelper apiHelper = new ApiHelper();


    @Step("Информация с API ответа приводится к типу jsonpath. Полученный jsonpath форматируюется в список, содержащий элементы {1}." +
            " Из полученного списка объектов извлекаются имена репозиториев.")
    public <Model> List<Model> parseJsonAsRepository(JsonPath jsonPath, Class<Model> cls) {
        List<Model> repositories = jsonPath.getList("", cls);
        return repositories;
    }



    /*public List<Repository> getRepositoriesFromResponse(String url) throws IOException {
        JsonPath jsonPath = apiHelper.getJsonFromRequest(url);

        List<Repository> repository = parseJsonAsRepository(jsonPath);

        return repository;
    }
     */

    @Step("Отправка API запроса по ссылке {0} и форматирование информации с ответа в {1}")
    public <Model> Model getRepositoryFromResponse(String url, Class<Model> cls) {
        Response response = apiHelper.sendGet(url);
        /*
        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Unexpected code " + response.getStatusCode());
        }
        if (response.body().toString().isEmpty()) {
            throw new RuntimeException("Response is empty!");
        }
         */
        Model repository = response.getBody().as(cls);
        return repository;
    }

    @Step("Получаем имена репозиториев (repositoryNames), которые распологаются на адресе {0}.")
    public List<String> getRepositoriesNames(String url, Class<Repository> cls) {
        JsonPath jsonPath = apiHelper.sendGet(url).jsonPath();

        List<Repository> repositories = parseJsonAsRepository(jsonPath, cls);
        List<String> repositoryNames = new ArrayList<>();

        for (Repository repository : repositories) {
            repositoryNames.add(repository.getName());
        }
        return repositoryNames;
    }

    @Step("Полученные имена (repositoryNames) форматируются в ссылки для API запросов (repositoriesUrls) и сохраняются в файл urls.csv")
    public List<String> getRepositoriesUrl(List<String> repositoryNames) throws IOException {
        List<String> repositoriesUrls = new ArrayList<>();
        for (String repName : repositoryNames) {
            repositoriesUrls.add(String.format("https://api.github.com/repos/selenide/%s", repName));
        }
        File file = new File("src/test/resources/urls.csv");
        saveAsCSV(repositoriesUrls, file);
        return repositoriesUrls;
    }


    @Step("Сохранение ссылок (repositoriesUrls).")
    public void saveAsCSV(List<String> repositoriesUrls, File file) throws IOException {
        try (Writer writer = new FileWriter(file)) {
            for (String repositoriesUrl : repositoriesUrls) {
                writer.write(String.format("%s;\n", repositoriesUrl));
            }
        }
    }

    @Step("Ссылка {0} из файла urls.csv разделяется по знаку '/', после чего информация приводится к типу class model.Repository")
    public Repository getRepository(String repositoryUrl) {
        String[] parts = repositoryUrl.split("/");
        String name = parts[5];
        String fullname = parts[4] + "/" + parts[5];
        Repository repositoryFromSplitUrl = new Repository().withName(name).withFullName(fullname);
        return repositoryFromSplitUrl;
    }
}
