package appmanager;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiHelper {

    RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
            .setBaseUri("https://api.github.com/")
            .setContentType(ContentType.JSON)
            .addHeader("Accept", ContentType.JSON.toString())
            .log(LogDetail.ALL);
    RequestSpecification requestSpec = requestSpecBuilder.build();

    ResponseSpecification normalResponseSpec= new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectBody(not(empty()))
            .log(LogDetail.ALL)
            .build();


    ResponseSpecification errorResponseSpec404 = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .build();

    ResponseSpecification errorResponseSpec500 = new ResponseSpecBuilder()
            .expectStatusCode(500)
            .build();
    @Step("Отправляем API запрос на {0}. В запросе присутствует спецификация ответа normalResponseSpec, которая включает в себя " +
            "проверку на код 200 и на непустое тело ответа.")
    public Response sendGet(String url) {
        return constructorSendGet(url, normalResponseSpec);
    }

    @Step("Отправляем API запрос на {0}. В запросе присутствует спецификация ответа errorResponseSpec, которая включает в себя " +
            "проверку на код 404 и на пустое тело ответа.")
    public Response sendGetWithErrorResponse(String url) {
        return constructorSendGet(url, errorResponseSpec404);
    }

    private Response constructorSendGet(String url, ResponseSpecification responseSpec) {
        return given()
                .spec(requestSpec)
                .get(url)
                .then()
                .spec(responseSpec)
                .extract().response();
    }
}

