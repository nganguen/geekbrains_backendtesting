package com.geekbrains.springbackendtest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RestAssuredProductTestTests {
    static RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(8189)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    // Домашнее задание:
    // 1. Протестировать CRUD-операции для продуктов
    // 2. Проверить что при отправке некорректных запросов
    // ProductController должен выдать 400 (возможно придется где-то
    // ProductController подкрутить)
    // 3. Проверить корректность сообщения об ошибке
    // в случае POST/PUT запросов

    @Test
    public void createPositiveTest() {
        ProductTest p = new ProductTest(null, "Bacon", 10, "Food");
        given()
                .spec(requestSpecification)
                .body(p)
                .when()
                .post("market/api/v1/products")
                .then()
                .statusCode(201)
                .log().ifValidationFails(LogDetail.BODY)
                .body("title", equalTo("Bacon"));
    }

    @Test
    public void createNegativeTest() {
        ProductTest p = new ProductTest(null, "Bacon", 10, "Flowers");
        given()
                .spec(requestSpecification)
                .body(p)
                .when()
                .post("market/api/v1/products")
                .then()
                .statusCode(400)
                .log().ifValidationFails(LogDetail.BODY)
                .body("message", equalTo("Bad request data"));

        System.out.println("---------Next request-------");
        p = new ProductTest(null, null, 10, "Food");
        given()
                .spec(requestSpecification)
                .body(p)
                .when()
                .post("market/api/v1/products")
                .then()
                .statusCode(400)
                .log().ifValidationFails(LogDetail.BODY)
                .body("message", equalTo("Bad request data"));
    }

    @Test
    public void getPositiveTest() {
        given()
                .spec(requestSpecification)
                .get("market/api/v1/products")
                .then()
                .statusCode(200)
                .log().ifValidationFails(LogDetail.BODY)
                .body("[0].categoryTitle", equalTo("Food"));

        System.out.println("---------Next request-------");

        given()
                .spec(requestSpecification)
                .get("market/api/v1/products/1")
                .then()
                .statusCode(200)
                .body("title", equalTo("Milk"))
                .body("categoryTitle", equalTo("Food"));
    }

    @Test
    public void getNegativeTest() {
        given()
                .spec(requestSpecification)
                .get("market/api/v1/products/100")
                .then()
                .statusCode(404)
                .body("message", equalTo("Unable to find product with id: 100"));
    }

    @Test
    public void updatePositiveTest() {
        ProductTest p = new ProductTest(1L, "Bread", 10, "Food");
        given()
                .spec(requestSpecification)
                .body(p)
                .put("market/api/v1/products")
                .then()
                .statusCode(200)
                .log().ifValidationFails(LogDetail.BODY)
                .body("id", equalTo(1))
                .body("title", equalTo("Bread"))
                .body("price", equalTo(10));
    }

    @Test
    public void updateNegativeTest() {
        ProductTest p = new ProductTest(100L, "Bread", 10, "Food");
        given()
                .spec(requestSpecification)
                .body(p)
                .put("market/api/v1/products")
                .then()
                .statusCode(400)
                .body("message", equalTo("Product with id: 100 doesn't exist"));

        System.out.println("---------Next request-------");
        p = new ProductTest(null, "Bread", 10, "Food");
        given()
                .spec(requestSpecification)
                .body(p)
                .when()
                .put("market/api/v1/products")
                .then()
                .statusCode(400)
                .log().ifValidationFails(LogDetail.BODY)
                .body("message", equalTo("Id must be not null for new entity"));

        System.out.println("---------Next request-------");
        p = new ProductTest(2L, null, 10, "Food");
        given()
                .spec(requestSpecification)
                .body(p)
                .when()
                .put("market/api/v1/products")
                .then()
                .statusCode(400)
                .log().ifValidationFails(LogDetail.BODY)
                .body("message", equalTo("Bad request data"));
    }

    @Test
    public void deletePositiveTest() {
        given()
                .spec(requestSpecification)
                .delete("market/api/v1/products/1")
                .then()
                .statusCode(200)
                .log().ifValidationFails(LogDetail.BODY);
    }

    @Test
    public void deleteNegativeTest() {
        given()
                .spec(requestSpecification)
                .delete("market/api/v1/products/100")
                .then()
                .statusCode(400)
                .log().ifValidationFails(LogDetail.BODY);
    }
}

