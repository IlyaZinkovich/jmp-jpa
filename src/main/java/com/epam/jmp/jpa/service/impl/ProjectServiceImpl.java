package com.epam.jmp.jpa.service.impl;

import com.epam.jmp.jpa.model.Project;
import com.epam.jmp.jpa.repository.ProjectRepository;
import com.epam.jmp.jpa.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Long create(Project project) {
        return projectRepository.create(project);
    }

    @Override
    public Project findById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public void update(Project project) {
        projectRepository.update(project);
    }

    @Override
    public void delete(Long id) {
        projectRepository.delete(id);
    }
}
