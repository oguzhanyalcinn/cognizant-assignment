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
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

public class CreateEmployeeWithArrayTest extends BaseTest{
    private EmployeeDto employeeDto;
    private EmployeeDto employeeDto2;

    public void validStubForEmployeeArray() throws IOException {
        employeeDto = EmployeeDto.builder()
                .employeename("OguzhanYalcin")
                .firstName("oguzhan")
                .lastName("yalcin")
                .phone("1234567890")
                .id(5)
                .email("oguzhan@yalcin.com")
                .employeeStatus(2)
                .password("123456")
                .build();

        employeeDto2 = EmployeeDto.builder()
                .employeename("Dennis Berk")
                .firstName("Dennis")
                .lastName("Berk")
                .phone("1234567890")
                .id(45)
                .email("denniis@berk.com")
                .employeeStatus(2)
                .password("123456789")
                .build();

        EmployeeDto[] employeeArray = new EmployeeDto[2];
        employeeArray[0] = employeeDto;
        employeeArray[1] = employeeDto2;

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        objectMapper.writeValue(byteArrayOutputStream, employeeArray);
        final byte[] data = byteArrayOutputStream.toByteArray();

        stubFor(post(urlPathEqualTo("/employee/createWithArray"))
                .withHeader(Constants.HEADER_SUBJECT, equalTo(Constants.HEADER_OBJECT))
                .withRequestBody(equalToJson(new String(data)))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withBody("Employees have been created!")));
    }

    @Test
    public void checkEmployeeHasBeenCreatedSuccessfully() throws IOException {
        validStubForEmployeeArray();

        employeeDto = EmployeeDto.builder()
                .employeename("OguzhanYalcin")
                .firstName("oguzhan")
                .lastName("yalcin")
                .phone("1234567890")
                .id(5)
                .email("oguzhan@yalcin.com")
                .employeeStatus(2)
                .password("123456")
                .build();

        employeeDto2 = EmployeeDto.builder()
                .employeename("Dennis Berk")
                .firstName("Dennis")
                .lastName("Berk")
                .phone("1234567890")
                .id(45)
                .email("denniis@berk.com")
                .employeeStatus(2)
                .password("123456789")
                .build();

        EmployeeDto[] employeeArray = new EmployeeDto[2];
        employeeArray[0] = employeeDto;
        employeeArray[1] = employeeDto2;

        Response response = RequestHelper.createEmployeeWithArray(employeeArray, requestSpec);
        Assert.assertEquals(response.statusCode(), 201);
        Assert.assertEquals(response.getBody().asString(), "Employees have been created!");
    }
}