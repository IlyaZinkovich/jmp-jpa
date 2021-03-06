package com.epam.jmp.jpa.service;

import com.epam.jmp.jpa.model.Employee;

public interface EmployeeService {

    Long create(Employee employee);

    Employee findById(Long id);

    void deleteById(Long id);

    void update(Employee employee);

    void addToUnit(Long employeeId, Long unitId);

    void assignToProject(Long employeeId, Long projectId);
}
