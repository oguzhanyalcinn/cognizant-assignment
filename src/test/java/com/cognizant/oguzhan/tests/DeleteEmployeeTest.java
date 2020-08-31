package com.cognizant.oguzhan.tests;

import com.cognizant.oguzhan.helper.Constants;
import com.cognizant.oguzhan.helper.RequestHelper;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

public class DeleteEmployeeTest extends BaseTest{
    private Response response;

    public void validStubForDeleteEmployee() {
        stubFor(delete(urlPathMatching("/employee/[a-z]*"))
                .withHeader(Constants.HEADER_SUBJECT, equalTo(Constants.HEADER_OBJECT))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("Employee has been deleted!")));
    }

    public void invalidStubForDeleteEmployee() {
        stubFor(delete(urlPathMatching("/employee/[A-Z]*"))
                .withHeader(Constants.HEADER_SUBJECT, equalTo(Constants.HEADER_OBJECT))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withBody("Employee name is invalid!")));
    }

    public void notFoundStubForDeleteEmployee() {
        stubFor(delete(urlPathMatching("/employee/[0-9]*"))
                .withHeader(Constants.HEADER_SUBJECT, equalTo(Constants.HEADER_OBJECT))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody("Employee is not found!")));
    }

    @Test
    public void checkValidDeleteEmployee() {
        validStubForDeleteEmployee();

        response = RequestHelper.deleteEmployee("oguzhan",requestSpec);
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.getBody().asString(), "Employee has been deleted!");
    }

    @Test
    public void checkInvalidDeleteEmployee() {
        invalidStubForDeleteEmployee();

        response = RequestHelper.deleteEmployee("OGUZHAN",requestSpec);
        Assert.assertEquals(response.statusCode(), 400);
        Assert.assertEquals(response.getBody().asString(), "Employee name is invalid!");
    }

    @Test
    public void checkNotFoundDeleteEmployee() {
        notFoundStubForDeleteEmployee();

        response = RequestHelper.deleteEmployee("7583473",requestSpec);
        Assert.assertEquals(response.statusCode(), 404);
        Assert.assertEquals(response.getBody().asString(), "Employee is not found!");
    }
}
