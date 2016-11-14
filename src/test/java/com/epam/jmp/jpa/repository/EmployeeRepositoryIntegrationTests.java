package com.epam.jmp.jpa.repository;

import com.epam.jmp.jpa.TestRepositoryConfig;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.jmp.jpa.model.EmployeeStatus.BILLABLE;
import static com.epam.jmp.jpa.model.EmployeeStatus.NON_BILLABLE;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestRepositoryConfig.class)
@Transactional
public class EmployeeRepositoryIntegrationTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @SqlGroup({
            @Sql(scripts = "/data.sql"),
            @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void testCreateEmployee() {
        String name = "Bob";
        String city = "London";
        String street = "Liberty";
        String house = "1";
        String apartment = "12";
        Employee employee = new Employee();
        PersonalInfo personalInfo = new PersonalInfo(name);
        employee.setPersonalInfo(personalInfo);
        Address address = new Address(city, street, house, apartment);
        employee.setAddress(address);
        Unit unit = new Unit("unit");
        employee.setUnit(unit);
        employee.setStatus(BILLABLE);

        Long id = employeeRepository.create(employee);
        entityManager.flush();

        assertNotNull(id);
        Employee employeeFromDb = getEmployeeByIdFromDb(id);
        assertEquals(personalInfo, employeeFromDb.getPersonalInfo());
        assertEquals(address, employeeFromDb.getAddress());
        assertEquals(unit, employeeFromDb.getUnit());
    }


    @Test
    @SqlGroup({
            @Sql(scripts = "/data.sql"),
            @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void testUpdateEmployee() {
        String name = "Bob";
        Long id = 1L;
        String city = "London";
        String street = "Liberty";
        String house = "1";
        String apartment = "12";
        EmployeeStatus status = NON_BILLABLE;
        Address address = new Address(city, street, house, apartment);
        Employee employee = new Employee();
        employee.setId(id);
        PersonalInfo personalInfo = new PersonalInfo(name);
        employee.setPersonalInfo(personalInfo);
        employee.setAddress(address);
        Unit unit = new Unit("unit");
        employee.setUnit(unit);
        Project firstProject = getProjectByIdFromDb(1L);
        firstProject.getEmployees().add(employee);
        Project secondProject = getProjectByIdFromDb(2L);
        secondProject.getEmployees().add(employee);
        List<Project> projects = asList(firstProject, secondProject);
        employee.setProjects(projects);
        employee.setStatus(status);

        employeeRepository.update(employee);
        entityManager.flush();

        Employee employeeFromDb = getEmployeeByIdFromDb(id);
        assertEquals(personalInfo, employeeFromDb.getPersonalInfo());
        assertEquals(address, employeeFromDb.getAddress());
        assertEquals(status, employeeFromDb.getStatus());
        List<Project> employeeProjects = getEmployeeProjectsByIdFromDb(id);
        assertEquals(projects, employeeProjects);
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/data.sql"),
            @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void testFindEmployeeById() {
        Long id = 2L;

        Employee employee = employeeRepository.findById(id);

        assertEquals(id, employee.getId());
        Employee employeeFromDb = getEmployeeByIdFromDb(id);
        assertEquals(employee.getPersonalInfo(), employeeFromDb.getPersonalInfo());
        assertEquals(employee.getAddress(), employeeFromDb.getAddress());
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/data.sql"),
            @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void testFindEmployeeById_NotFound() {
        Long nonexistentId = 3L;

        Employee employee = employeeRepository.findById(nonexistentId);

        assertNull(employee);
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/data.sql"),
            @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void testDeleteEmployeeById() {
        Long id = 2L;

        employeeRepository.deleteById(id);
        entityManager.flush();

        Employee employeeFromDb = getEmployeeByIdFromDb(id);
        assertNull(employeeFromDb);
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/data.sql"),
            @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void testDeleteEmployeeById_NotFound() {
        Long nonexistentId = 3L;

        employeeRepository.deleteById(nonexistentId);

        Employee employeeFromDb = getEmployeeByIdFromDb(nonexistentId);
        assertNull(employeeFromDb);
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/data.sql"),
            @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void testAddEmployeeToUnit() {
        Long employeeId = 1L;
        Employee employee = getEmployeeByIdFromDb(employeeId);
        Long unitId = 2L;
        Unit unit = getUnitByIdFromDb(unitId);
        unit.getEmployees().add(employee);
        employee.setUnit(unit);

        employeeRepository.update(employee);
        entityManager.flush();

        Employee employeeFromDb = getEmployeeByIdFromDb(employeeId);
        assertEquals(employeeFromDb.getUnit(), employee.getUnit());
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/data.sql"),
            @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void testAssignEmployeeToProject() {
        Long employeeId = 1L;
        Employee employee = getEmployeeByIdFromDb(employeeId);
        List<Project> employeeProjects = getEmployeeProjectsByIdFromDb(employeeId);

        Long projectId = 1L;
        Project project = getProjectByIdFromDb(projectId);
        List<Employee> projectEmployees = getEmployeesByProjectIdFromDb(projectId);
        projectEmployees.add(employee);
        project.setEmployees(projectEmployees);

        employeeProjects.add(project);
        employee.setProjects(employeeProjects);

        employeeRepository.update(employee);
        entityManager.flush();

        List<Project> employeeProjectsFromDb = getEmployeeProjectsByIdFromDb(employeeId);
        assertNotNull(employeeProjectsFromDb);
        assertEquals(1, employeeProjectsFromDb.size());
        assertEquals(project, employeeProjectsFromDb.get(0));
    }

    private Employee getEmployeeByIdFromDb(Long employeeId) {
        return jdbcTemplate.query("SELECT EMPLOYEE.ID, EMPLOYEE.STATUS, EMPLOYEE.CITY, EMPLOYEE.STREET, " +
                        "EMPLOYEE.HOUSE, EMPLOYEE.APARTMENT, UNIT.ID, UNIT.NAME, PERSONAL_INFO.ID, PERSONAL_INFO.NAME " +
                        "FROM EMPLOYEE " +
                        "JOIN UNIT ON EMPLOYEE.UNIT_ID=UNIT.ID " +
                        "JOIN PERSONAL_INFO ON EMPLOYEE.PERSONAL_INFO_ID=PERSONAL_INFO.ID " +
                        "WHERE EMPLOYEE.ID=?",
                new Object[]{employeeId}, this::extractEmployeeFromResultSet);
    }

    private List<Employee> getEmployeesByProjectIdFromDb(Long projectId) {
        return jdbcTemplate.queryForList("SELECT EMPLOYEE.ID, EMPLOYEE.STATUS, EMPLOYEE.CITY, EMPLOYEE.STREET, " +
                        "EMPLOYEE.HOUSE, EMPLOYEE.APARTMENT, UNIT.ID, UNIT.NAME, PERSONAL_INFO.ID, PERSONAL_INFO.NAME " +
                        "FROM EMPLOYEE " +
                        "JOIN UNIT ON EMPLOYEE.UNIT_ID=UNIT.ID " +
                        "JOIN PERSONAL_INFO ON EMPLOYEE.PERSONAL_INFO_ID=PERSONAL_INFO.ID " +
                        "JOIN EMPLOYEE_PROJECTS ON EMPLOYEE.ID=EMPLOYEE_PROJECTS.EMPLOYEE_ID " +
                        "WHERE EMPLOYEE_PROJECTS.PROJECT_ID=?",
                new Object[]{projectId}, Employee.class);
    }

    private List<Project> getEmployeeProjectsByIdFromDb(Long employeeId) {
        return jdbcTemplate.query("SELECT PROJECT.ID, PROJECT.NAME FROM PROJECT " +
                        "JOIN EMPLOYEE_PROJECTS ON EMPLOYEE_PROJECTS.PROJECT_ID=PROJECT.ID " +
                        "WHERE EMPLOYEE_PROJECTS.EMPLOYEE_ID=?", new Object[]{employeeId}, this::extractProjectsFromResultSet);
    }

    private Employee extractEmployeeFromResultSet(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) return null;
        Employee employee = new Employee();
        employee.setId(resultSet.getLong("EMPLOYEE.ID"));
        employee.setStatus(EmployeeStatus.valueOf(resultSet.getString("EMPLOYEE.STATUS")));
        employee.setAddress(new Address(resultSet.getString("EMPLOYEE.CITY"), resultSet.getString("EMPLOYEE.STREET"),
                resultSet.getString("EMPLOYEE.HOUSE"), resultSet.getString("EMPLOYEE.APARTMENT")));
        Unit unit = new Unit();
        unit.setId(resultSet.getLong("UNIT.ID"));
        unit.setName(resultSet.getString("UNIT.NAME"));
        employee.setUnit(unit);
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setId(resultSet.getLong("PERSONAL_INFO.ID"));
        personalInfo.setName(resultSet.getString("PERSONAL_INFO.NAME"));
        employee.setPersonalInfo(personalInfo);
        return employee;
    }

    private Unit getUnitByIdFromDb(Long testId) {
        return jdbcTemplate
                .query("SELECT ID, NAME FROM UNIT WHERE ID=?",
                        new Object[]{ testId },
                        this::extractUnitFromResultSet);
    }

    private Unit extractUnitFromResultSet(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) return null;
        Unit unit = new Unit();
        unit.setId(resultSet.getLong("ID"));
        unit.setName(resultSet.getString("NAME"));
        return unit;
    }

    private Project getProjectByIdFromDb(Long projectId) {
        return jdbcTemplate
                .query("SELECT ID, NAME FROM PROJECT WHERE ID=?",
                        new Object[]{ projectId },
                        this::extractProjectFromResultSet);
    }

    private Project extractProjectFromResultSet(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) return null;
        Project project = new Project();
        project.setId(resultSet.getLong("ID"));
        project.setName(resultSet.getString("NAME"));
        return project;
    }

    private List<Project> extractProjectsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Project> projects = new ArrayList<>();
        while (resultSet.next()) {
            Project project = new Project();
            project.setId(resultSet.getLong("PROJECT.ID"));
            project.setName(resultSet.getString("PROJECT.NAME"));
            projects.add(project);
        }
        return projects;
    }
}
