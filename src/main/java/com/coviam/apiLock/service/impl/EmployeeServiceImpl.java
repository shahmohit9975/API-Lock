package com.coviam.apiLock.service.impl;

import com.coviam.apiLock.dto.StatusDTO;
import com.coviam.apiLock.entity.Employee;
import com.coviam.apiLock.enums.LockType;
import com.coviam.apiLock.model.Constants;
import com.coviam.apiLock.repository.EmployeeRepository;
import com.coviam.apiLock.service.EmployeeCacheableService;
import com.coviam.apiLock.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeCacheableService employeeCacheableService;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public Boolean saveEmployee(Employee employee) {

        try {
//            Map userHash = new ObjectMapper().convertValue(employee, Map.class);
//            redisTemplate.opsForHash().put(String.valueOf(employee.getId()), employee.getName(), userHash);

            employeeRepository.save(employee);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public StatusDTO updateEmployee(int id, Employee employee, LockType lockType) {
        StatusDTO statusDTO = new StatusDTO();
        if (lockType.equals(LockType.ACQUIRE)) {
            if (!employeeCacheableService.acquireLock(id, employee)) {
                statusDTO.setStatus(false);
                statusDTO.setMessage(Constants.PROCESS_LOCKED);
                return statusDTO;
            } else {
                statusDTO.setStatus(true);
                statusDTO.setMessage("record updated...");
                return statusDTO;
            }
        } else {
            if (employeeCacheableService.releaseLock(id, employee.getName())) {
                statusDTO.setStatus(true);
                statusDTO.setMessage("record updated...");
                return statusDTO;
            } else {
                statusDTO.setStatus(false);
                statusDTO.setMessage("error in release lock...");
                return statusDTO;
            }
        }

    }
}
