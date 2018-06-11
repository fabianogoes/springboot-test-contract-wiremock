package com.example.microservicebase.ws.contract;

import com.example.microservicebase.ContractITApplication;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.parsing.Parser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RunWith(SpringRunner.class)
@Profile("integration")
@SpringBootTest(classes = ContractITApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerIntegrationTestCommon {

    @Value("${local.server.port}")
    public int serverPort;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(WireMockConfiguration.wireMockConfig().port(MOCK_HTTP_PORT).httpsPort(MOCK_HTTPS_PORT));

    public static final String CONTENT_TYPE_KEY = "Content-Type";
    public static final int MOCK_HTTP_PORT = 8089;
    public static final int MOCK_HTTPS_PORT = 8443;
    public static final String MOCK_HOST = "http://localhost";
    public static final String ENDPOINT_REST_URL = MOCK_HOST + ":" + String.valueOf(MOCK_HTTP_PORT) + CustomerRestContract.CUSTOMER_DOMAIN;
    public static final String RESTASSURED_ANONYMOUS_JSON = "$";

    @Before
    public void setup() throws Exception {
        RestAssured.port = serverPort;
        RestAssured.defaultParser = Parser.JSON;
        Locale.setDefault(new Locale("pt", "BR"));

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(CustomerRestContract.CUSTOMER_DOMAIN))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(CONTENT_TYPE_KEY, APPLICATION_JSON_UTF8_VALUE)
                        .withBodyFile("customers.json")));

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(CustomerRestContract.CUSTOMER_DOMAIN.concat("/1")))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(CONTENT_TYPE_KEY, APPLICATION_JSON_UTF8_VALUE)
                        .withBodyFile("customer.json")));
    }

    @Test
    public void test(){
        Assert.assertTrue(true);
    }
}