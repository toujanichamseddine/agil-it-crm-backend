package fr.agilit.crm.controllers;

import fr.agilit.crm.dto.ProjectDTO;
import fr.agilit.crm.dto.mapper.ProjectMapper;
import fr.agilit.crm.models.Project;
import fr.agilit.crm.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
@Slf4j
public class ProjectController {

    private final ProjectRepository projectRepository;

    private final ProjectMapper projectMapper;

    public ProjectController(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectDTO>> getAllProjects(@RequestParam(required = false) String title) {
        try {
            List<Project> projects = new ArrayList<>();

            if (title == null)
                projects.addAll(projectRepository.findAll());
            else
                projects.addAll(projectRepository.findByTitleContaining(title));

            if (projects.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<ProjectDTO> projectDTOS = projects.stream().map(
                    projectMapper).collect(Collectors.toList());
            return new ResponseEntity<>(projectDTOS, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error getting projects");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable("id") long id) {
        Optional<Project> projectData = projectRepository.findById(id);

        return projectData.map(project -> new ResponseEntity<>(projectMapper.apply(project), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/projects")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody Project project) {
        try {
            Project newProject = new Project(project.getTitle(), project.getDescription(), false);
            newProject.setCreationDate(project.getCreationDate());
            newProject.setUpdateDate(project.getUpdateDate());
            Project _project = projectRepository
                    .save(newProject);
            return new ResponseEntity<>(projectMapper.apply(_project), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable("id") long id, @RequestBody Project project) {
        Optional<Project> projectData = projectRepository.findById(id);

        if (projectData.isPresent()) {
            Project _project = projectData.get();
            _project.setTitle(project.getTitle());
            _project.setDescription(project.getDescription());
            _project.setPublished(project.isPublished());
            return new ResponseEntity<>(projectRepository.save(_project), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<HttpStatus> deleteProject(@PathVariable("id") long id) {
        try {
            projectRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/projects")
    public ResponseEntity<HttpStatus> deleteAllProjects() {
        try {
            projectRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/projects/published")
    public ResponseEntity<List<Project>> findByPublished() {
        try {
            List<Project> projects = projectRepository.findByPublished(true);

            if (projects.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
