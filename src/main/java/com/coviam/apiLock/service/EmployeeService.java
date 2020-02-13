package com.coviam.apiLock.service;

import com.coviam.apiLock.dto.StatusDTO;
import com.coviam.apiLock.entity.Employee;
import com.coviam.apiLock.enums.LockType;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface EmployeeService {
    Boolean saveEmployee(Employee employee);

    StatusDTO updateEmployee(int id, Employee employee, LockType acquire);

    CompletableFuture<List<Employee>> getAllEmployees() throws InterruptedException;
}
