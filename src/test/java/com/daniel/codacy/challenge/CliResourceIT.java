package com.daniel.codacy.challenge;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
public class CliResourceIT {

    @Test
    public void  testDefaultsCommitHistory() {
        given()
                .when()
                .get("/codacy/log/dfcmmendes/codacy-challenge")
                .then()
                .statusCode(OK.getStatusCode())
                .body("page", equalTo(1),
                        "per_page", equalTo(5),
                        "count", equalTo(5),
                        "items", hasSize(5),
                        "items[0].message", equalTo("Change tests"));
    }

    @Test
    public void  testCommitHistory() {
        given()
                .when()
                .get("/codacy/log/dfcmmendes/codacy-challenge?page=6&per_page=1")
                .then()
                .statusCode(OK.getStatusCode())
                .body("page", equalTo(6),
                        "per_page", equalTo(1),
                        "count", equalTo(1),
                        "items", hasSize(1),
                        "items[0].message", equalTo("Clone and log without date"));
    }

    @Test
    public void  testCommitHistoryNotFound() {
        given()
                .when()
                .get("/codacy/log/dfcmmendes/non-existing-repo")
                .then()
                .statusCode(NOT_FOUND.getStatusCode());
    }
}
