package com.example.microservicebase.ws.contract;

import com.example.microservicebase.model.entity.CustomerEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

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

    @Test
    public void test_get_all_should_return_status_code_200_with_contenttype_json_and_one_list_greater_zero_and_the_atributes_of_contract_with_bodyfile() throws JsonProcessingException {
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(CustomerRestContract.CUSTOMER_DOMAIN))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(CONTENT_TYPE_KEY, APPLICATION_JSON_UTF8_VALUE)
                        .withBodyFile("customers.json")
                )
        );

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
                .body("[0]", hasKey("id"))
                .body("[0]", hasKey("name"))
                .body("[0].id", not(isEmptyOrNullString()))
                .body("[0].name", not(isEmptyOrNullString()));
    }

}
