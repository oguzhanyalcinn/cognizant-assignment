package com.cognizant.oguzhan.tests;

import com.cognizant.oguzhan.helper.Constants;
import com.cognizant.oguzhan.helper.RequestHelper;
import com.cognizant.oguzhan.models.EmployeeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class UpdateEmployeeTest extends BaseTest{
    private Response response;
    private EmployeeDto employeeDto;

    public void validStubForUpdateEmployee() throws JsonProcessingException {
        employeeDto = EmployeeDto.builder()
                .employeename("Oguzhan Yalcin")
                .firstName("Oguzhan")
                .lastName("Yalcin")
                .phone("987654321")
                .id(1)
                .email("oguzhan1903yalcin@gmail.com")
                .employeeStatus(1)
                .password("123456")
                .build();

        stubFor(put(urlPathMatching("/employee/[a-z]*"))
                .withHeader(Constants.HEADER_SUBJECT, equalTo(Constants.HEADER_OBJECT))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(employeeDto)))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("Employee has been updated!")));
    }

    public void invalidStubForUpdateEmployee() throws JsonProcessingException {
        employeeDto = EmployeeDto.builder()
                .employeename("Oguzhan Yalcin")
                .firstName("Oguzhan")
                .lastName("Yalcin")
                .phone("987654321")
                .id(1)
                .email("oguzhan1903yalcin@gmail.com")
                .employeeStatus(1)
                .password("123456")
                .build();

        stubFor(put(urlPathMatching("/employee/[A-Z]*"))
                .withHeader(Constants.HEADER_SUBJECT, equalTo(Constants.HEADER_OBJECT))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(employeeDto)))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withBody("Employee name is invalid!")));
    }

    public void notFoundStubForUpdateEmployee() throws JsonProcessingException {
        employeeDto = EmployeeDto.builder()
                .employeename("Oguzhan Yalcin")
                .firstName("Oguzhan")
                .lastName("Yalcin")
                .phone("987654321")
                .id(1)
                .email("oguzhan1903yalcin@gmail.com")
                .employeeStatus(1)
                .password("123456")
                .build();

        stubFor(put(urlPathMatching("/employee/[0-9]*"))
                .withHeader(Constants.HEADER_SUBJECT, equalTo(Constants.HEADER_OBJECT))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(employeeDto)))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody("Employee is not found!")));
    }

    @Test
    public void checkValidUpdateEmployee() throws JsonProcessingException {
        validStubForUpdateEmployee();

        employeeDto = EmployeeDto.builder()
                .employeename("Oguzhan Yalcin")
                .firstName("Oguzhan")
                .lastName("Yalcin")
                .phone("987654321")
                .id(1)
                .email("oguzhan1903yalcin@gmail.com")
                .employeeStatus(1)
                .password("123456")
                .build();

        response = RequestHelper.updateEmployee(employeeDto,requestSpec,"oguzhan");
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.getBody().asString(), "Employee has been updated!");
    }

    @Test
    public void checkInvalidUpdateEmployee() throws JsonProcessingException {
        invalidStubForUpdateEmployee();

        employeeDto = EmployeeDto.builder()
                .employeename("Oguzhan Yalcin")
                .firstName("Oguzhan")
                .lastName("Yalcin")
                .phone("987654321")
                .id(1)
                .email("oguzhan1903yalcin@gmail.com")
                .employeeStatus(1)
                .password("123456")
                .build();

        response = RequestHelper.updateEmployee(employeeDto,requestSpec,"OGUZHAN");
        Assert.assertEquals(response.statusCode(), 400);
        Assert.assertEquals(response.getBody().asString(), "Employee name is invalid!");
    }

    @Test
    public void checkNotFoundUpdateEmployee() throws JsonProcessingException {
        notFoundStubForUpdateEmployee();

        employeeDto = EmployeeDto.builder()
                .employeename("Oguzhan Yalcin")
                .firstName("Oguzhan")
                .lastName("Yalcin")
                .phone("987654321")
                .id(1)
                .email("oguzhan1903yalcin@gmail.com")
                .employeeStatus(1)
                .password("123456")
                .build();

        response = RequestHelper.updateEmployee(employeeDto,requestSpec,"7583473");
        Assert.assertEquals(response.statusCode(), 404);
        Assert.assertEquals(response.getBody().asString(), "Employee is not found!");
    }
}
