package com.epam.jmp.jpa.repository;

import com.epam.jmp.jpa.model.Project;

public interface ProjectRepository {

    Long create(Project project);

    Project findById(Long id);

    void update(Project project);

    void delete(Long testId);
}
