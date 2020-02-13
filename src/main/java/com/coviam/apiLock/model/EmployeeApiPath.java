package com.coviam.apiLock.model;

public interface EmployeeApiPath {
    String BASE_PATH = Constants.CONTEXT_PATH + "/api";
    String ADD_EMPLOYEE = "/add-employee";
    String GET_EMPLOYEE = "/get-employee";
    String UPDATE_EMPLOYEE = "/update-employee/{id}";
}
