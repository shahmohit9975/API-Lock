package com.coviam.apiLock.controller;

import com.coviam.apiLock.dto.EmployeeDTO;
import com.coviam.apiLock.dto.StatusDTO;
import com.coviam.apiLock.entity.Employee;
import com.coviam.apiLock.enums.LockType;
import com.coviam.apiLock.model.EmployeeApiPath;
import com.coviam.apiLock.service.EmployeeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = EmployeeApiPath.BASE_PATH)
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @ApiOperation("add employee")
    @PostMapping(path = EmployeeApiPath.ADD_EMPLOYEE, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addEMployee(@RequestBody EmployeeDTO employeeDTO) {
//        ModelMapper modelMapper = new ModelMapper();
//        Employee employee = modelMapper.map(emp, Employee.class);
        Employee emp = new Employee();
        BeanUtils.copyProperties(employeeDTO, emp);
        Boolean result = employeeService.saveEmployee(emp);
        StatusDTO statusDTO = new StatusDTO();
        if (result) {
            statusDTO.setStatus(true);
            statusDTO.setMessage("employee added...");
            return new ResponseEntity<>(statusDTO, HttpStatus.CREATED);
        }
        statusDTO.setStatus(false);
        statusDTO.setMessage("employee is not added...");
        return new ResponseEntity<>(statusDTO, HttpStatus.BAD_REQUEST);
    }

    @ApiOperation("update employee record")
    @PutMapping(path = EmployeeApiPath.UPDATE_EMPLOYEE, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeDTO employeeDTO, @PathVariable(value = "id") int id) {
        Employee emp = new Employee();
        BeanUtils.copyProperties(employeeDTO, emp);
        StatusDTO statusDTO = employeeService.updateEmployee(id, emp, LockType.ACQUIRE);
        if (statusDTO.isStatus()) {
            statusDTO = employeeService.updateEmployee(id, emp, LockType.RELEASE);
        }
        return new ResponseEntity<>(statusDTO, HttpStatus.OK);
    }
}
