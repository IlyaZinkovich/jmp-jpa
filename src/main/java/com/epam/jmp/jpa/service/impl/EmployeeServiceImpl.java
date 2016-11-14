package com.epam.jmp.jpa.service.impl;

import com.epam.jmp.jpa.model.Employee;
import com.epam.jmp.jpa.repository.EmployeeRepository;
import com.epam.jmp.jpa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Long create(Employee employee) {
        return employeeRepository.create(employee);
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public void update(Employee employee) {
        employeeRepository.update(employee);
    }
}
