package com.epam.jmp.jpa.repository;

import com.epam.jmp.jpa.TestRepositoryConfig;
import com.epam.jmp.jpa.model.Unit;
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

import static org.junit.Assert.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestRepositoryConfig.class)
@Transactional
public class UnitRepositoryIntegrationTests {

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @SqlGroup({
            @Sql(scripts = "/data.sql"),
            @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void createUnit() {
        Unit unit = new Unit();
        String name = "name";
        unit.setName(name);

        Long id = unitRepository.create(unit);

        assertNotNull(id);
        Unit unitFromDb = getUnitByIdFromDb(id);
        assertEquals(id, unitFromDb.getId());
        assertEquals(unit, unitFromDb);
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/data.sql"),
            @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void testUpdateUnit() {
        Unit unit = new Unit();
        Long id = 1L;
        String name = "name";
        unit.setId(id);
        unit.setName(name);

        unitRepository.update(unit);
        entityManager.flush();

        Unit unitFromDb = getUnitByIdFromDb(id);
        assertEquals(unit.getName(), unitFromDb.getName());
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/data.sql"),
            @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void testFindUnitById() {
        Long testId = 1L;

        Unit unit = unitRepository.findById(testId);

        assertNotNull(unit);
        Unit unitFromDb = getUnitByIdFromDb(testId);
        assertEquals(unit, unitFromDb);
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/data.sql"),
            @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void testFindUnitById_NotFound() {
        Long nonexistentId = 3000L;

        Unit unit = unitRepository.findById(nonexistentId);

        assertNull(unit);
        Unit unitFromDb = getUnitByIdFromDb(nonexistentId);
        assertNull(unitFromDb);
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/data.sql"),
            @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void testDeleteUnitById() {
        Long testId = 1L;

        unitRepository.deleteById(testId);
        entityManager.flush();

        Unit unitFromDb = getUnitByIdFromDb(testId);
        assertNull(unitFromDb);
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/data.sql"),
            @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void testDeleteEmployeeById_NotFound() {
        Long nonexistentId = 3000L;

        unitRepository.deleteById(nonexistentId);

        Unit unitFromDb = getUnitByIdFromDb(nonexistentId);
        assertNull(unitFromDb);
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
}
