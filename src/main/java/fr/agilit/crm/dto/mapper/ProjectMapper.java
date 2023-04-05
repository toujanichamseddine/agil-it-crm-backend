package fr.agilit.crm.dto.mapper;

import fr.agilit.crm.dto.ProjectDTO;
import fr.agilit.crm.models.Project;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProjectMapper implements Function<Project, ProjectDTO> {
    @Override
    public ProjectDTO apply(Project project) {
        return new ProjectDTO(project.getId(), project.getTitle(), project.getDescription());
    }
}
