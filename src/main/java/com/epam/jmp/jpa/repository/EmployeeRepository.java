package com.epam.jmp.jpa.repository;

import com.epam.jmp.jpa.model.Employee;

public interface EmployeeRepository {

    Long create(Employee employee);
}
