package com.cognizant.oguzhan.tests;

import com.cognizant.oguzhan.helper.Constants;
import com.cognizant.oguzhan.helper.RequestHelper;
import com.cognizant.oguzhan.models.EmployeeDto;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class GetEmployeeTest extends BaseTest {
    private Response response;

    public void validStubForGetEmployee() throws IOException {
        EmployeeDto employeeDto = EmployeeDto.builder()
                .employeename("Oguzhan Yalcin")
                .firstName("Oguzhan")
                .lastName("Yalcin")
                .phone("987654321")
                .id(1)
                .email("oguzhan1903yalcin@gmail.com")
                .employeeStatus(1)
                .password("123456")
                .build();

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        objectMapper.writeValue(byteArrayOutputStream, employeeDto);
        final byte[] data = byteArrayOutputStream.toByteArray();

        stubFor(get(urlPathMatching("/employee/[a-z]*"))
                .withHeader(Constants.HEADER_SUBJECT, equalTo(Constants.HEADER_OBJECT))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(new String(data))));
    }

    public void invalidStubForGetEmployee() {
        stubFor(get(urlPathMatching("/employee/[A-Z]*"))
                .withHeader(Constants.HEADER_SUBJECT, equalTo(Constants.HEADER_OBJECT))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withBody("Invalid employee name!")));
    }

    public void notFoundStubForGetEmployee() {
        stubFor(get(urlPathMatching("/employee/[0-9]*"))
                .withHeader(Constants.HEADER_SUBJECT, equalTo(Constants.HEADER_OBJECT))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody("Employee Not Found!")));
    }

    @Test
    public void checkValidGetEmployee() throws IOException {
        validStubForGetEmployee();

        response = RequestHelper.getEmployee("oguzhan", requestSpec);
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.getBody().jsonPath().getString("employeename"), "Oguzhan Yalcin");
        Assert.assertEquals(response.getBody().jsonPath().getString("firstName"), "Oguzhan");
        Assert.assertEquals(response.getBody().jsonPath().getString("lastName"), "Yalcin");
        Assert.assertEquals(response.getBody().jsonPath().getString("phone"), "987654321");
        Assert.assertEquals(response.getBody().jsonPath().getInt("id"), 1);
        Assert.assertEquals(response.getBody().jsonPath().getString("email"), "oguzhan1903yalcin@gmail.com");
        Assert.assertEquals(response.getBody().jsonPath().getInt("employeeStatus"), 1);
        Assert.assertEquals(response.getBody().jsonPath().getString("password"), "123456");
    }

    @Test
    public void checkInvalidGetEmployee() {
        invalidStubForGetEmployee();

        response = RequestHelper.getEmployee("OGUZHAN", requestSpec);
        Assert.assertEquals(response.statusCode(), 400);
        Assert.assertEquals(response.getBody().asString(), "Invalid employee name!");
    }

    @Test
    public void checkNotFoundGetEmployee() {
        notFoundStubForGetEmployee();

        response = RequestHelper.getEmployee("7583473", requestSpec);
        Assert.assertEquals(response.statusCode(), 404);
        Assert.assertEquals(response.getBody().asString(), "Employee Not Found!");
    }
}
