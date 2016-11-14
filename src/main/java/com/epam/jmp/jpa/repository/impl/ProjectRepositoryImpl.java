package com.epam.jmp.jpa.repository.impl;

import com.epam.jmp.jpa.model.Project;
import com.epam.jmp.jpa.repository.ProjectRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long create(Project project) {
        entityManager.persist(project);
        return project.getId();
    }

    @Override
    public Project findById(Long id) {
        return entityManager.find(Project.class, id);
    }

    @Override
    public void update(Project project) {
        entityManager.merge(project);
    }

    @Override
    public void delete(Long id) {
        Project project = entityManager.find(Project.class, id);
        if (project != null) {
            entityManager.remove(project);
        }
    }
}
