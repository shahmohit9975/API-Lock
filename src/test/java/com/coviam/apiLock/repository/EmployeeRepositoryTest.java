package com.coviam.apiLock.repository;

import com.coviam.apiLock.entity.Employee;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void saveEmployeeTest() {

        Employee employee = new Employee();
        employee.setName("abc");
        employee.setSalary(1000);
        testEntityManager.persist(employee);
        testEntityManager.flush();
        Assert.assertNotNull(employeeRepository.getOne(employee.getId()));
        assertThat(employee).isEqualTo(employeeRepository.findById(employee.getId()).get());
    }

    @Test
    public void updateEmployeeTest() {

        Employee employee = new Employee();
        employee.setName("xyz");
        employee.setSalary(1000);
        testEntityManager.persist(employee);
        employee.setSalary(2000);
        testEntityManager.persist(employee);
        testEntityManager.flush();
        Assert.assertNotNull(employeeRepository.getOne(employee.getId()));
        assertThat(employee).isEqualTo(employeeRepository.findById(employee.getId()).get());
    }
}
