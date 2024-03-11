
package com.example.backend.mapper;

import com.example.backend.core.create_model.ProjectCreateModel;
import com.example.backend.core.model.Project;
import com.example.backend.core.search_model.ProjectSearchModel;
import com.example.backend.data.entity.ProjectEntity;
import com.example.backend.web.create_dto.ProjectCreateDTO;
import com.example.backend.web.dto.ProjectDTO;
import com.example.backend.web.search_dto.ProjectSearchDTO;

import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class ProjectMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public ProjectDTO modelToDTO(Project model) {
        return modelMapper.map(model, ProjectDTO.class);

    }

    public Project dTOToModel(ProjectDTO dto) {
        return modelMapper.map(dto, Project.class);
    }

    public ProjectEntity modelToEntity(Project model) {
        return modelMapper.map(model, ProjectEntity.class);
    }

    public Project entityToModel(ProjectEntity entity) {
        return modelMapper.map(entity, Project.class);
    }

    public Project createDTOToModel(ProjectCreateDTO createDTO) {
        return modelMapper.map(createDTO, Project.class);
    }

    public List<Project> entitiesToModels(List<ProjectEntity> entities) {
        return entities.stream()
                .map(entity -> entityToModel(entity))
                .collect(Collectors.toList());
    }

    public Page<Project> entitiesPageToModelsPage(Page<ProjectEntity> entities) {
        List<Project> list = entities.getContent().stream()
                .map(entity -> entityToModel(entity))
                .collect(Collectors.toList());

        return new PageImpl<>(list, entities.getPageable(), entities.getTotalElements());
    }

    public ProjectEntity modelToEntityForPatch(Project source, ProjectEntity destination) {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(source, destination);
        // Reset skipNullEnabled to its default value
        modelMapper.getConfiguration().setSkipNullEnabled(false);
        return destination;
    }

    public ProjectCreateModel mapCreateDTOCreateModelProject(ProjectCreateDTO createDTO) {
        return modelMapper.map(createDTO, ProjectCreateModel.class);
    }

    public ProjectSearchModel mapSearchDTOSearchModelProject(ProjectSearchDTO projectSearchDTO) {
        return modelMapper.map(projectSearchDTO, ProjectSearchModel.class);

    }

    public ProjectEntity mapCreateModelEntityProject(ProjectCreateModel ProjectCreateModel) {
        return modelMapper.map(ProjectCreateModel, ProjectEntity.class);

    }

}
