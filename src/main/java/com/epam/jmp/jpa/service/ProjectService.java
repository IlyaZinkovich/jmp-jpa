package com.epam.jmp.jpa.service;

import com.epam.jmp.jpa.model.Project;

public interface ProjectService {

    Long create(Project project);

    Project findById(Long id);

    void update(Project project);

    void delete(Long id);
}
