package com.example.backend.core.service_Implementation;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.backend.core.core_repository.IProjectCoreRepository;
import com.example.backend.core.create_model.ProjectCreateModel;
import com.example.backend.core.model.Project;
import com.example.backend.core.search_model.ProjectSearchModel;
import com.example.backend.core.service.IProjectService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ProjectServiceImplementation implements IProjectService {

    @Autowired
    private final IProjectCoreRepository coreProjectRepository;

    @Override
    public Project getProjectById(Long id) {
        Project foundProject = coreProjectRepository.findById(id);
        return foundProject;
    }

    @Override
    public Project createProject(ProjectCreateModel projectCreateModel) {
        Project savedProject = coreProjectRepository.save(projectCreateModel);
        return savedProject;
    }

    @Override
    public Project updateProject(Long id, Project project) {
        Project updatedProject = coreProjectRepository.update(id, project);
        return updatedProject;
    }

    @Override
    public void deleteProject(Long id) {
        coreProjectRepository.deleteById(id);
    }

    @Override
    public Page<Project> searchProjects(ProjectSearchModel projectSearchModel, Pageable pageable) {
        Page<Project> searchResults = coreProjectRepository.searchProjects(projectSearchModel, pageable);
        return searchResults;

    }

    @Override
    public HashMap<Long, String> getProjectNamesByClientId(Long clientId, Long teamMemberId) {
        return coreProjectRepository.getProjectNamesByClientId(clientId, teamMemberId);

    }
}
