package com.epam.jmp.jpa.repository.impl;

import com.epam.jmp.jpa.model.Employee;
import com.epam.jmp.jpa.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long create(Employee employee) {
        entityManager.persist(employee);
        return employee.getId();
    }
}
