package steps;

import http.ApiHelper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Repository;

import java.util.List;


public class ApiSteps {

    public static ApiHelper apiHelper = new ApiHelper();

    @Step("Получен репозиторий организации {0}")
    public static Repository getRepositoryFromResponse(String organisationName) {
        String apiUrl = String.format("repos/%s", organisationName);
        Response response = apiHelper.sendGet(apiUrl);
        apiHelper.responseCheck(response);
        Repository repository = response.as(Repository.class);
        return repository;
    }

    @Step("Получен список всех репозиториев организации {0}.")
    public static List<Repository> getRepositoriesFromResponse(String organisationName) {
        String apiUrl = String.format("users/%s/repos", organisationName);
        Response response = apiHelper.sendGet(apiUrl);
        apiHelper.responseCheck(response);
        Repository[] repositories = response.as(Repository[].class);
        return List.of(repositories);
    }
}
