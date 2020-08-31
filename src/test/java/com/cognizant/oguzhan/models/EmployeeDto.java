package com.cognizant.oguzhan.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmployeeDto {
    private int id;
    private String employeename;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private int employeeStatus;
}
