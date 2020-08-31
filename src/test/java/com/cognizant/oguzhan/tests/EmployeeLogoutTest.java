package com.cognizant.oguzhan.tests;

import com.cognizant.oguzhan.helper.Constants;
import com.cognizant.oguzhan.helper.RequestHelper;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class EmployeeLogoutTest extends BaseTest {

    public void validStubForLogout() {
        stubFor(get(urlPathEqualTo("/employee/logout"))
                .withHeader(Constants.HEADER_SUBJECT, equalTo(Constants.HEADER_OBJECT))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("Logged out successfully!")));
    }

    @Test
    public void checkLogoutEndpoint() {
        validStubForLogout();

        Response response = RequestHelper.logoutEmployee(requestSpec);
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.getBody().asString(), "Logged out successfully!");
    }
}
