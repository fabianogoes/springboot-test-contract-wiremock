package com.example.demo;

import com.example.microservicebase.model.entity.CustomerEntity;
import com.example.microservicebase.ws.contract.CustomerRestContract;
import com.example.microservicebase.ws.contract.CustomerStub;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.HttpStatus;

import java.io.*;
import java.util.List;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


public class CustomerRestApiIT extends CustomerIntegrationTestCommon {

    private String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    @Test
    public void test_get_all_should_return_status_code_200_with_contenttype_json_and_one_list_greater_zero_and_the_atributes_of_contract_with_bodyfile() throws IOException {

        Class clazz = CustomerStub.class;
        InputStream inputStream = clazz.getResourceAsStream("/__files/customer.json");
        String data = readFromInputStream(inputStream);

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(CustomerRestContract.CUSTOMER_DOMAIN))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(CONTENT_TYPE_KEY, APPLICATION_JSON_UTF8_VALUE)
                       .withBody(data)
//                        .withBodyFile("customer.json")
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
                .body("$", hasKey("id"));
//
//        byte[] bytes = RestAssured.given()
//                .get(ENDPOINT_REST_URL)
//                .body().asByteArray();
//
//        List<CustomerEntity> entities = SerializationUtils.deserialize(bytes);
//        Assert.assertThat(entities.size(), is(greaterThan(0)));
//        Assert.assertThat(entities.get(0), is(notNullValue()));
//        Assert.assertThat(entities.get(0), is(instanceOf(CustomerEntity.class)));
//
//        String json = new ObjectMapper().writeValueAsString(entities.get(0));
//        Assert.assertThat(json, hasJsonPath("$.id"));
//        Assert.assertThat(json, hasJsonPath("$.name"));
    }

    @Test
    public void test_get_all_should_return_status_code_200_with_contenttype_json_and_one_list_greater_zero_and_the_atributes_of_contract() throws JsonProcessingException {
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(CustomerRestContract.CUSTOMER_DOMAIN))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(CONTENT_TYPE_KEY, APPLICATION_JSON_UTF8_VALUE)
                        .withBody(new CustomerStub().buildStubList())
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
                .body("$", hasKey("id"));

        byte[] bytes = RestAssured.given()
                .get(ENDPOINT_REST_URL)
                .body().asByteArray();

        List<CustomerEntity> entities = SerializationUtils.deserialize(bytes);
        Assert.assertThat(entities.size(), is(greaterThan(0)));
        Assert.assertThat(entities.get(0), is(notNullValue()));
        Assert.assertThat(entities.get(0), is(instanceOf(CustomerEntity.class)));

        String json = new ObjectMapper().writeValueAsString(entities.get(0));
        Assert.assertThat(json, hasJsonPath("$.id"));
        Assert.assertThat(json, hasJsonPath("$.name"));
    }

    @Test
    public void test_get_one_should_return_status_code_200_with_contenttype_json_and_the_atributes_of_contract() throws JsonProcessingException {
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(CustomerRestContract.CUSTOMER_DOMAIN.concat("/1")))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(CONTENT_TYPE_KEY, APPLICATION_JSON_UTF8_VALUE)
                        .withBody(new CustomerStub().buildStubOne())));

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(ENDPOINT_REST_URL.concat("/1"))
                .then()
                .assertThat()
                .statusCode(org.apache.http.HttpStatus.SC_OK)
                .contentType(ContentType.JSON);

        byte[] bytes = RestAssured.given()
                .get(ENDPOINT_REST_URL.concat("/1"))
                .body().asByteArray();

        CustomerEntity entity = SerializationUtils.deserialize(bytes);
        Assert.assertThat(entity, is(instanceOf(CustomerEntity.class)));

        String json = new ObjectMapper().writeValueAsString(entity);
        Assert.assertThat(json, hasJsonPath("$.id"));
        Assert.assertThat(json, hasJsonPath("$.name"));

    }

}
