package stepmanager;

import io.qameta.allure.Step;
import model.Repository;



public class UrlSteps {

    @Step("Ссылка {repositoryUrl} парсится и приводится к типу class model.Repository")
    public Repository getRepository(String repositoryUrl) {
        String[] parts = repositoryUrl.split("/");
        String name = parts[5];
        String fullname = parts[4] + "/" + parts[5];
        Repository repositoryFromSplitUrl = new Repository().withName(name).withFullName(fullname);
        return repositoryFromSplitUrl;
    }
}
