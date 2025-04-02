package com.fernando.studentcourses.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fernando.studentcourses.dto.CourseDto;
import com.fernando.studentcourses.dto.StudentSummary;
import com.fernando.studentcourses.model.Course;
import com.fernando.studentcourses.model.Student;
import com.fernando.studentcourses.service.CourseService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // GET all courses
    @GetMapping
    public List<CourseDto> getAllCourses() {
        return courseService.getAllCourses().stream().map(this::toDto).collect(Collectors.toList());
    }

    // POST create course
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDto createCourse(@RequestBody CourseDto request) {
        Course course = new Course();
        course.setCode(request.getCode());
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());

        return toDto(courseService.createCourse(course));
    }

    // GET course by id
    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Integer id) {
        try {
            Course course = courseService.getCourseById(id);
            return ResponseEntity.ok(toDto(course));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT update course
    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable Integer id, @RequestBody CourseDto request) {
        try {
            Course updated = new Course();
            updated.setCode(request.getCode());
            updated.setTitle(request.getTitle());
            updated.setDescription(request.getDescription());
            Course result = courseService.updateCourse(id, updated);
            return ResponseEntity.ok(toDto(result));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE course
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable Integer id) {
        courseService.deleteCourse(id);
    }

    // GET search courses
    @GetMapping("/search")
    public List<CourseDto> searchCourses(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String student) {
        return courseService.searchCourses(title, student)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // mapper: Course -> CourseDto
    private CourseDto toDto(Course course) {
        List<Student> students = courseService.getStudentsForCourse(course.getId());
        List<StudentSummary> studentSummaries = students.stream()
                .map(student -> new StudentSummary(student.getId(), student.getFirstName(), student.getLastName()))
                .collect(Collectors.toList());

        return new CourseDto(
                course.getId(),
                course.getCode(),
                course.getTitle(),
                course.getDescription(),
                studentSummaries);
    }
}
