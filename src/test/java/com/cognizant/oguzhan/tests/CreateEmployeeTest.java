package com.cognizant.oguzhan.tests;

import com.cognizant.oguzhan.helper.Constants;
import com.cognizant.oguzhan.helper.RequestHelper;
import com.cognizant.oguzhan.models.EmployeeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class CreateEmployeeTest extends BaseTest {
    private Response response;
    private EmployeeDto employeeDto;

    public void validStubForCreateEmployee() throws JsonProcessingException {
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

        stubFor(post(urlEqualTo("/employee"))
                .withHeader(Constants.HEADER_SUBJECT, equalTo(Constants.HEADER_OBJECT))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(employeeDto)))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("Employee has been created!")));
    }

    public void invalidStubForCreateEmployee() throws JsonProcessingException {
        employeeDto = EmployeeDto.builder()
                .employeename("OguzhanYalcin")
                .firstName("oguzhan")
                .lastName("yalcin")
                .phone("1234567890")
                .build();

        stubFor(post(urlEqualTo("/employee"))
                .withHeader(Constants.HEADER_SUBJECT, equalTo(Constants.HEADER_OBJECT))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(employeeDto)))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withBody("Error: Employee has not been created!")));
    }

    @Test
    public void checkEmployeeHasBeenCreatedSuccessfully() throws JsonProcessingException {
        validStubForCreateEmployee();

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

        response = RequestHelper.createEmployee(employeeDto, requestSpec);
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.getBody().asString(), "Employee has been created!");
    }

    @Test
    public void checkEmployeeHasNotBeenCreated() throws JsonProcessingException {
        invalidStubForCreateEmployee();

        employeeDto = EmployeeDto.builder()
                .employeename("OguzhanYalcin")
                .firstName("oguzhan")
                .lastName("yalcin")
                .phone("1234567890")
                .build();

        response = RequestHelper.createEmployee(employeeDto, requestSpec);
        Assert.assertEquals(response.statusCode(), 400);
        Assert.assertEquals(response.getBody().asString(), "Error: Employee has not been created!");
    }
}