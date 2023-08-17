package appmanager;

import io.qameta.allure.Step;
import model.Repository;



public class UrlSteps {

    @Step("Ссылка repositoryUrl парсится и приводится к типу class model.Repository")
    public Repository getRepository(String repositoryUrl) {
        String apiBaseUrl = "https://api.github.com";
        String apiUrl = String.format("%s/repos/%s", apiBaseUrl, repositoryUrl);
        String[] parts = apiUrl.split("/");
        String name = parts[5];
        String fullname = parts[4] + "/" + parts[5];
        Repository repositoryFromSplitUrl = new Repository().withName(name).withFullName(fullname);
        return repositoryFromSplitUrl;
    }
}
