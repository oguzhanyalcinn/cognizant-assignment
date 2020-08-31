package com.cognizant.oguzhan.helper;

import com.cognizant.oguzhan.models.EmployeeDto;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class RequestHelper {
    private static Response response;

    public static Response createEmployee(EmployeeDto employeeDto, RequestSpecification requestSpec) {
        response = given().log().all()
                .spec(requestSpec).body(employeeDto)
                .when().post("/employee")
                .then().extract().response();

        response.body().prettyPrint();
        response.getBody().asString();
        return response;
    }

    public static Response createEmployeeWithArray(EmployeeDto[] employeeDto, RequestSpecification requestSpec) {
        response = given().log().all()
                .spec(requestSpec).body(employeeDto)
                .when().post("/employee/createWithArray")
                .then().extract().response();

        response.body().prettyPrint();
        response.getBody().asString();
        return response;
    }

    public static Response createEmployeeWithList(ArrayList<EmployeeDto> employeeDto, RequestSpecification requestSpec) {
        response = given().log().all()
                .spec(requestSpec).body(employeeDto)
                .when().post("/employee/createWithList")
                .then().extract().response();

        response.body().prettyPrint();
        response.getBody().asString();
        return response;
    }

    public static Response deleteEmployee(String employeeName, RequestSpecification requestSpec) {
        response = given().log().all()
                .spec(requestSpec)
                .when().delete("/employee/" + employeeName)
                .then().extract().response();

        response.body().prettyPrint();
        response.getBody().asString();
        return response;
    }

    public static Response loginEmployee(String employeeName, String password, RequestSpecification requestSpec) {
        response = given().log().all()
                .spec(requestSpec).queryParam("employeename", employeeName).queryParam("password", password)
                .when().get("/employee/login")
                .then().extract().response();

        response.body().prettyPrint();
        response.getBody().asString();
        return response;
    }

    public static Response logoutEmployee(RequestSpecification requestSpec) {
        response = given().log().all()
                .spec(requestSpec)
                .when().get("/employee/logout")
                .then().extract().response();

        response.body().prettyPrint();
        response.getBody().asString();
        return response;
    }

    public static Response getEmployee(String employeeName, RequestSpecification requestSpec) {
        response = given().log().all()
                .spec(requestSpec)
                .when().get("/employee/" + employeeName)
                .then().extract().response();

        response.body().prettyPrint();
        response.getBody().asString();
        return response;
    }

    public static Response updateEmployee(EmployeeDto employeeDto, RequestSpecification requestSpec, String employeeName) {
        response = given().log().all()
                .spec(requestSpec).body(employeeDto)
                .when().put("/employee/" + employeeName)
                .then().extract().response();

        response.body().prettyPrint();
        response.getBody().asString();
        return response;
    }
}
