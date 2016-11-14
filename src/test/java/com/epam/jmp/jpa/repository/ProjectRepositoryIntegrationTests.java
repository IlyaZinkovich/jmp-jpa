package com.epam.jmp.jpa.repository;

import com.epam.jmp.jpa.TestRepositoryConfig;
import com.epam.jmp.jpa.model.Project;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestRepositoryConfig.class)
@Transactional
public class ProjectRepositoryIntegrationTests {

    @Autowired
    private ProjectRepository projectRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @SqlGroup({
            @Sql(scripts = "/data.sql"),
            @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void testCreateProject() {
        Project project = new Project();
        String name = "projectName";
        project.setName(name);

        Long id = projectRepository.create(project);
        assertNotNull(id);
        Project projectFromDb = getProjectByIdFromDb(id);
        assertEquals(projectFromDb.getId(), id);
        assertEquals(projectFromDb.getName(), name);
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/data.sql"),
            @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void testUpdateProject() {
        Project project = new Project();
        Long id = 1L;
        String testName = "testName";
        project.setId(id);
        project.setName(testName);

        projectRepository.update(project);
        entityManager.flush();

        Project projectFromDb = getProjectByIdFromDb(id);
        assertEquals(projectFromDb, project);
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/data.sql"),
            @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void testDeleteProjectById() {
        Long id = 1L;

        projectRepository.delete(id);
        entityManager.flush();

        Project projectFromDb = getProjectByIdFromDb(id);
        assertNull(projectFromDb);
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/data.sql"),
            @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void testDeleteProjectById_NotFound() {
        Long nonexistentId = 3000L;

        projectRepository.delete(nonexistentId);

        Project projectFromDb = getProjectByIdFromDb(nonexistentId);
        assertNull(projectFromDb);
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/data.sql"),
            @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void testFindProjectById() {
        Long id = 1L;

        Project project = projectRepository.findById(id);

        Project projectFromDb = getProjectByIdFromDb(id);
        assertEquals(projectFromDb, project);
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/data.sql"),
            @Sql(scripts = "/truncate.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void testFindProjectById_NotFound() {
        Long nonexistentId = 3000L;

        Project project = projectRepository.findById(nonexistentId);

        assertNull(project);
        Project projectFromDb = getProjectByIdFromDb(nonexistentId);
        assertNull(projectFromDb);
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
}
