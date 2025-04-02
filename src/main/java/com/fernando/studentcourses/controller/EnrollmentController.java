package com.fernando.studentcourses.controller;

import com.fernando.studentcourses.model.Enrollment;
import com.fernando.studentcourses.model.Enrollment.EnrollmentStatus;
import com.fernando.studentcourses.service.EnrollmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    // POST /api/enrollments
    @PostMapping
    public ResponseEntity<?> enrollStudent(
            @RequestParam Integer studentId,
            @RequestParam Integer courseId) {
        try {
            Enrollment enrollment = enrollmentService.enrollInCourse(studentId, courseId, EnrollmentStatus.ACTIVE);
            return ResponseEntity.ok(enrollment);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }
}
