package com.epam.jmp.jpa.repository;

import com.epam.jmp.jpa.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static com.epam.jmp.jpa.model.EmployeeStatus.BILLABLE;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestRepositoryConfig.class)
@Transactional
public class EmployeeRepositoryIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*
    Create Employee / Unit / Project
Find employee / Unit / Project by id
Delete employee / Unit / Project by id
Update Employee / Unit / Project by id
Add Employee to Unit by id’s
Assign Employee for Project by id’s
     */

    @Test
    @SqlGroup({
            @Sql(scripts = "/data.sql"),
            @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void createEmployee() {
        String name = "Bob";
        Employee employee = new Employee();
        employee.setPersonalInfo(new PersonalInfo(name));
        employee.setAddress(new Address("London", "Liberty", "1", "12"));
        employee.setUnit(new Unit());
        employee.setProjects(asList(new Project(), new Project()));
        employee.setStatus(BILLABLE);

        Long id = employeeRepository.create(employee);

        assertNotNull(id);
    }
}
