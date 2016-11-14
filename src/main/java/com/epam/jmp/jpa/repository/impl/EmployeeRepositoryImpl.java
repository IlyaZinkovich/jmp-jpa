package com.epam.jmp.jpa.repository.impl;

import com.epam.jmp.jpa.model.Employee;
import com.epam.jmp.jpa.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class EmployeeRepositoryImpl implements EmployeeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long create(Employee employee) {
        entityManager.persist(employee);
        return employee.getId();
    }

    @Override
    @Transactional
    public Employee findById(Long id) {
        return entityManager.find(Employee.class, id);
    }

    @Override
    public void deleteById(Long id) {
        Employee employee = findById(id);
        if (employee != null) {
            entityManager.remove(employee);
        }
    }

    @Override
    public void update(Employee employee) {
        entityManager.merge(employee);
    }
}
