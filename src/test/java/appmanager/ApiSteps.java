package appmanager;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Repository;

import java.util.List;


public class ApiSteps {


    public ApiHelper apiHelper = new ApiHelper();


    @Step("Отправление API запроса на имя {0} и парсинг информации с ответа к виду {1}.")
    public <Model> Model getRepositoryFromResponse(String organisationName, Class<Model> cls) {
        Response response = apiHelper.sendGet(organisationName);
        Model repository = response.getBody().as(cls);
        return repository;
    }

    @Step("Получен список всех репозиториев организации {0}.")
    public List<Repository> getRepositoriesFromResponse(String organisationName) {
        String apiUrl = String.format("users/%s/repos", organisationName);
        Response response = apiHelper.sendGet(apiUrl);
        Repository[] repositories = response.as(Repository[].class);
        return List.of(repositories);
    }
}
