package com.coviam.apiLock.listener;

import com.coviam.apiLock.dto.EmployeeDTO;
import com.coviam.apiLock.dto.StatusDTO;
import com.coviam.apiLock.entity.Employee;
import com.coviam.apiLock.enums.LockType;
import com.coviam.apiLock.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    @Autowired
    EmployeeService employeeService;

    @KafkaListener(topics = "employeeUpdateApi", groupId = "group-id", containerFactory = "kafkaListenerContainerFactory")
    public void consumeUserJSON(EmployeeDTO employeeDTO) throws InterruptedException {
        Thread.sleep(7000);
        Employee emp = new Employee();
        BeanUtils.copyProperties(employeeDTO, emp);
        StatusDTO statusDTO = employeeService.updateEmployee(emp.getId(), emp, LockType.ACQUIRE);
        if (statusDTO.isStatus()) {
            statusDTO = employeeService.updateEmployee(emp.getId(), emp, LockType.RELEASE);
        }
        System.out.println("kafka consumer : " + statusDTO.toString());
    }

}
