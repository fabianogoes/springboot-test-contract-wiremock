package com.example.microservicebase.ws.contract;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.*;

public class CustomerRestControllerTestCustomer extends CustomerIntegrationTestCommon {

    @Test
    public void test_get_all_should_return_status_code_200_with_contenttype_json_and_one_list_greater_zero_and_the_atributes_of_contract(){
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get(ENDPOINT_REST_URL)
                .then()
                .assertThat()
                .statusCode(org.apache.http.HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body("size", is(greaterThan(0))) // is list
                .body("size", is(equalTo(3)))
                // validate the contract
                .body("[0]", hasKey("id"))
                .body("[0]", hasKey("name"))
                .body("[0].id", not(isEmptyOrNullString()))
                .body("[0].name", not(isEmptyOrNullString()));
    }

    @Test
    public void test_get_one_should_return_status_code_200_with_contenttype_json_and_the_atributes_of_contract(){
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get(ENDPOINT_REST_URL.concat("/1"))
                .then()
                .assertThat()
                .statusCode(org.apache.http.HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                // validate the contract
                .body("$", hasKey("id"))
                .body("$", hasKey("name"))
                .body("id", not(isEmptyOrNullString()))
                .body("name", not(isEmptyOrNullString()))
                .body("id", is(equalTo(1)));
    }

}
