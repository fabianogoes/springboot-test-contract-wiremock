package com.example.demo;

import com.example.microservicebase.ws.contract.CustomerRestContract;
import com.github.tomakehurst.wiremock.common.SingleRootFileSource;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;

@RunWith(SpringRunner.class)
@Profile("integration")
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerIntegrationTestCommon {

    @Value("${local.server.port}")
    public int serverPort;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(WireMockConfiguration.wireMockConfig().port(MOCK_HTTP_PORT)
            .httpsPort(MOCK_HTTPS_PORT)
            .fileSource(new SingleRootFileSource("classpath:__files")));

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
    }

    @Test
    public void test(){
        Assert.assertTrue(true);
    }
}