package fr.agilit.crm.controllers;

import fr.agilit.crm.models.Interview;
import fr.agilit.crm.models.InterviewStatus;
import fr.agilit.crm.repository.InterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class InterviewController {

    @Autowired
    InterviewRepository interviewRepository;

    @GetMapping("/interviews")
    public ResponseEntity<List<Interview>> getAllInterviews() {
        try {

            List<Interview> interviews = new ArrayList<>(interviewRepository.findAll());

            if (interviews.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(interviews, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/interviews/{id}")
    public ResponseEntity<Interview> getInterviewById(@PathVariable("id") long id) {
        Optional<Interview> interviewData = interviewRepository.findById(id);

        return interviewData.map(interview -> new ResponseEntity<>(interview, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/interviews")
    public ResponseEntity<Interview> createInterview(@RequestBody Interview interview) {
        try {
            Interview newInterview = new Interview();
            newInterview.setMembers(interview.getMembers());
            newInterview.setStartDate(interview.getStartDate());
            newInterview.setEndDate(interview.getEndDate());
            newInterview.setStatus(InterviewStatus.CREATED);
            Interview _interview = interviewRepository
                    .save(newInterview);
            return new ResponseEntity<>(_interview, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/interviews/{id}")
    public ResponseEntity<Interview> updateInterview(@PathVariable("id") long id, @RequestBody Interview interview) {
        Optional<Interview> interviewData = interviewRepository.findById(id);

        if (interviewData.isPresent()) {
            Interview _interview = interviewData.get();
            _interview.setMembers(interview.getMembers());
            _interview.setStartDate(interview.getStartDate());
            _interview.setEndDate(interview.getEndDate());
            _interview.setStatus(InterviewStatus.CREATED);
            return new ResponseEntity<>(interviewRepository.save(_interview), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/interviews/{id}")
    public ResponseEntity<HttpStatus> deleteInterview(@PathVariable("id") long id) {
        try {
            interviewRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/interviews")
    public ResponseEntity<HttpStatus> deleteAllInterviews() {
        try {
            interviewRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/interviews/created")
    public ResponseEntity<List<Interview>> findByPublished() {
        try {
            List<Interview> interviews = interviewRepository.findByStatus(InterviewStatus.CREATED);

            if (interviews.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(interviews, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
