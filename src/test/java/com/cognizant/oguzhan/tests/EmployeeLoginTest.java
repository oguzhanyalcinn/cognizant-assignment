package com.cognizant.oguzhan.tests;

import com.cognizant.oguzhan.helper.Constants;
import com.cognizant.oguzhan.helper.RequestHelper;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

public class EmployeeLoginTest extends BaseTest {
    private Response response;

    public void validStubForLogin() {
        stubFor(get(urlPathEqualTo("/employee/login"))
                .withHeader(Constants.HEADER_SUBJECT, equalTo(Constants.HEADER_OBJECT))
                .withQueryParam("employeename", equalTo("OguzhanYalcin"))
                .withQueryParam("password", equalTo("123456"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("Logged in successfully!")));
    }

    public void invalidStubForLogin() {
        stubFor(get(urlPathEqualTo("/employee/login"))
                .withHeader(Constants.HEADER_SUBJECT, equalTo(Constants.HEADER_OBJECT))
                .withQueryParam("employeename", equalTo("OguzhanYalcin"))
                .withQueryParam("password", equalTo("123456789"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withBody("Invalid credentials!")));
    }

    @Test
    public void checkLoginWithValidCredentials() {
        validStubForLogin();

        response = RequestHelper.loginEmployee("OguzhanYalcin", "123456", requestSpec);
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.getBody().asString(), "Logged in successfully!");
    }

    @Test
    public void checkLoginWithInvalidCredentials() {
        invalidStubForLogin();

        response = RequestHelper.loginEmployee("OguzhanYalcin", "123456789", requestSpec);
        Assert.assertEquals(response.statusCode(), 400);
        Assert.assertEquals(response.getBody().asString(), "Invalid credentials!");
    }
}
