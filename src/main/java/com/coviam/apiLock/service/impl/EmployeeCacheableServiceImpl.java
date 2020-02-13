package com.coviam.apiLock.service.impl;

import com.coviam.apiLock.dto.EmployeeDTO;
import com.coviam.apiLock.entity.Employee;
import com.coviam.apiLock.enums.LockType;
import com.coviam.apiLock.model.Constants;
import com.coviam.apiLock.repository.EmployeeRepository;
import com.coviam.apiLock.service.EmployeeCacheableService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class EmployeeCacheableServiceImpl implements EmployeeCacheableService {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    KafkaTemplate<String, EmployeeDTO> kafkaTemplate;

    @Override
    public boolean acquireLock(int id, Employee employee) {
        String key = id + Constants.HYPHEN + employee.getName();
        try {
            System.out.println("----------");
            if (Objects.isNull(redisTemplate.opsForValue().get(key)) || LockType.RELEASE.name()
                    .equals(redisTemplate.opsForValue().get(key))) {
                redisTemplate.opsForValue().set(key, LockType.ACQUIRE.name());
                employeeRepository.save(employee);
                System.out.println("before thread");
                Thread.sleep(7000);
                System.out.println("after thread");
                return true;
            } else {
                EmployeeDTO employeeDTO = new EmployeeDTO();
                BeanUtils.copyProperties(employee, employeeDTO);
                kafkaTemplate.send(Constants.TOPIC, employeeDTO);
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean releaseLock(int id, String name) {
        String key = id + Constants.HYPHEN + name;
        try {
            redisTemplate.opsForValue().set(key, LockType.RELEASE.name());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
