package com.cognizant.oguzhan.tests;

import com.cognizant.oguzhan.helper.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Rule;

public class BaseTest {
    public RequestSpecification requestSpec;
    public ObjectMapper objectMapper = new ObjectMapper();

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9876);

    @Before
    public void createRequestSpec() {
        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(9876).
                addHeader(Constants.HEADER_SUBJECT, Constants.HEADER_OBJECT).
                build();
    }
}
