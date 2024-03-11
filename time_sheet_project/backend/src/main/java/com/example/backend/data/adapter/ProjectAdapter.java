package com.example.backend.data.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.backend.core.core_repository.IProjectCoreRepository;
import com.example.backend.core.create_model.ProjectCreateModel;
import com.example.backend.core.model.Project;
import com.example.backend.core.search_model.ProjectSearchModel;
import com.example.backend.data.entity.ProjectEntity;
import com.example.backend.data.entity.UserEntity;
import com.example.backend.data.repository.IClientJPARepository;
import com.example.backend.data.repository.IProjectJPARepository;
import com.example.backend.data.repository.IUserJPARepository;
import com.example.backend.mapper.ProjectMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class ProjectAdapter implements IProjectCoreRepository {

    private final IProjectJPARepository jpaRepository;

    private final IUserJPARepository teamMemberJPARepository;

    private final IClientJPARepository clientJPARepository;

    private final ProjectMapper mapper;

    @Override
    public Project findById(Long id) {
        ProjectEntity entity = jpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        return mapper.entityToModel(entity);

    }

    @Override
    public Project save(ProjectCreateModel projectCreateModel) {

        ProjectEntity projectEntity = mapAndPopulateProjectEntity(projectCreateModel);

        // Save the project entity
        ProjectEntity createdEntity = jpaRepository.save(projectEntity);

        // Map the created entity to a Project object and return it
        return mapper.entityToModel(createdEntity);
    }

    @Override
    public Project update(Long id, Project model) {
        ProjectEntity targetedEntity = jpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));
        targetedEntity = mapper.modelToEntityForPatch(model,
                targetedEntity);
        ProjectEntity updatedEntity = jpaRepository.save(targetedEntity);
        return mapper.entityToModel(updatedEntity);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    private Page<Project> mapEntitiesPageToModelsPage(Page<ProjectEntity> entitiesPage) {
        List<Project> projectList = entitiesPage.getContent().stream()
                .map(entity -> mapper.entityToModel(entity))
                .collect(Collectors.toList());

        return new PageImpl<>(projectList, entitiesPage.getPageable(), entitiesPage.getTotalElements());
    }

    private ProjectEntity mapAndPopulateProjectEntity(ProjectCreateModel projectCreateModel) {
        // Create a new ProjectEntity instance
        ProjectEntity projectEntity = new ProjectEntity();

        // Populate fields from ProjectCreateModel
        projectEntity.setName(projectCreateModel.getName());
        projectEntity.setDescription(projectCreateModel.getDescription());

        // Fetch TeamMemberEntity from the database using teamMemberID
        UserEntity teamMemberEntity = teamMemberJPARepository.findById(projectCreateModel.getTeamMemberID())
                .orElseThrow(() -> new IllegalArgumentException("Team member not found"));

        // Set the lead for the project
        projectEntity.setLead(teamMemberEntity);

        return projectEntity;
    }

    @Override
    public Page<Project> searchProjects(ProjectSearchModel projectSearchModel, Pageable pageable) {
        Page<ProjectEntity> entitiesPage = jpaRepository.searchProjects(projectSearchModel, pageable);
        return mapEntitiesPageToModelsPage(entitiesPage);
    }

    @Override
    public HashMap<Long, String> getProjectNamesByClientId(Long ProjectId, Long teamMemberId) {
        return jpaRepository.findProjectNamesAndIdsByClientIdAndTeamMemberId(ProjectId, teamMemberId);

    }

}