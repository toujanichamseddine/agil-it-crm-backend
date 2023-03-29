package fr.agilit.crm.repository;

import fr.agilit.crm.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByPublished(boolean published);

    List<Project> findByTitleContaining(String title);
}
