package fr.agilit.crm.repository;

import fr.agilit.crm.models.Interview;
import fr.agilit.crm.models.InterviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    List<Interview> findByStatus(InterviewStatus status);
}
