package com.coviam.apiLock.service;

import com.coviam.apiLock.entity.Employee;

public interface EmployeeCacheableService {
    boolean acquireLock(int id, Employee employee);

    boolean releaseLock(int id, String name);
}
