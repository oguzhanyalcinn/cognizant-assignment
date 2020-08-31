package com.cognizant.oguzhan.tests;

import com.cognizant.oguzhan.helper.Constants;
import com.cognizant.oguzhan.helper.RequestHelper;
import com.cognizant.oguzhan.models.EmployeeDto;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

public class CreateEmployeeWithListTest extends BaseTest{
    private EmployeeDto employeeDto;
    private EmployeeDto employeeDto2;

    public void validStubForEmployeeList() throws IOException {
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

        ArrayList<EmployeeDto> employeeList = new ArrayList<>();
        employeeList.add(employeeDto);
        employeeList.add(employeeDto2);

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        objectMapper.writeValue(byteArrayOutputStream, employeeList);
        final byte[] data = byteArrayOutputStream.toByteArray();

        stubFor(post(urlPathEqualTo("/employee/createWithList"))
                .withHeader(Constants.HEADER_SUBJECT, equalTo(Constants.HEADER_OBJECT))
                .withRequestBody(equalToJson(new String(data)))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withBody("Employees have been created!")));
    }

    @Test
    public void checkEmployeesHaveBeenCreatedSuccessfully() throws IOException {
        validStubForEmployeeList();

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

        ArrayList<EmployeeDto> employeeList = new ArrayList<>();
        employeeList.add(employeeDto);
        employeeList.add(employeeDto2);

        Response response = RequestHelper.createEmployeeWithList(employeeList, requestSpec);
        Assert.assertEquals(response.statusCode(), 201);
        Assert.assertEquals(response.getBody().asString(), "Employees have been created!");
    }
}
