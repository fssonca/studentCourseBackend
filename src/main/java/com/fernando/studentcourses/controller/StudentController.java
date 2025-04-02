package com.fernando.studentcourses.controller;

import com.fernando.studentcourses.dto.StudentDto;
import com.fernando.studentcourses.dto.CourseSummary;
import com.fernando.studentcourses.model.Course;
import com.fernando.studentcourses.model.Student;
import com.fernando.studentcourses.service.StudentService;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // GET all students
    @GetMapping
    public List<StudentDto> getAllStudents() {
        return studentService.getAllStudents().stream().map(this::toDto).collect(Collectors.toList());
    }

    // POST create a student
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDto createStudent(@RequestBody StudentDto request) {
        Student student = new Student();
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        return toDto(studentService.createStudent(student));
    }

    // GET student by id
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Integer id) {
        try {
            Student student = studentService.getStudentById(id);
            return ResponseEntity.ok(toDto(student));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT update student
    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable Integer id, @RequestBody StudentDto request) {
        try {
            Student updated = new Student();
            updated.setFirstName(request.getFirstName());
            updated.setLastName(request.getLastName());

            Student result = studentService.updateStudent(id, updated);
            return ResponseEntity.ok(toDto(result));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE student
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Integer id) {
        studentService.deleteStudent(id);
    }

    // GET search student
    @GetMapping("/search")
    public List<StudentDto> searchStudents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String course) {
        return studentService.searchStudents(name, course)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Mapper: Student -> StudentDto
    private StudentDto toDto(Student student) {
        List<Course> courses = studentService.getCoursesForStudent(student.getId());
        List<CourseSummary> courseSummaries = courses.stream()
                .map(course -> new CourseSummary(course.getId(), course.getCode(), course.getTitle()))
                .collect(Collectors.toList());

        return new StudentDto(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                courseSummaries);
    }
}