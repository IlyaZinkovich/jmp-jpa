package com.epam.jmp.jpa.service.impl;

import com.epam.jmp.jpa.model.Employee;
import com.epam.jmp.jpa.model.Project;
import com.epam.jmp.jpa.model.Unit;
import com.epam.jmp.jpa.repository.EmployeeRepository;
import com.epam.jmp.jpa.repository.ProjectRepository;
import com.epam.jmp.jpa.repository.UnitRepository;
import com.epam.jmp.jpa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private ProjectRepository projectRepository;

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

    @Override
    public void addToUnit(Long employeeId, Long unitId) {
        Employee employee = employeeRepository.findById(employeeId);
        Unit unit = unitRepository.findById(unitId);
        employee.setUnit(unit);
        unit.getEmployees().add(employee);
        employeeRepository.update(employee);
    }

    @Override
    public void assignToProject(Long employeeId, Long projectId) {
        Employee employee = employeeRepository.findById(employeeId);
        Project project = projectRepository.findById(projectId);
        employee.getProjects().add(project);
        project.getEmployees().add(employee);
        employeeRepository.update(employee);
    }
}
