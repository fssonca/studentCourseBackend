package com.fernando.studentcourses.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fernando.studentcourses.model.Course;
import com.fernando.studentcourses.model.Enrollment;
import com.fernando.studentcourses.model.Student;
import com.fernando.studentcourses.repository.EnrollmentRepository;
import com.fernando.studentcourses.repository.StudentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;

    public StudentService(
            StudentRepository studentRepository,
            EnrollmentRepository enrollmentRepository) {
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    // create student
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    // get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // get student by id
    public Student getStudentById(Integer id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id " + id));
    }

    // update student
    public Student updateStudent(Integer id, Student newStudentData) {
        Student studentData = getStudentById(id);
        studentData.setFirstName(newStudentData.getFirstName());
        studentData.setLastName(newStudentData.getLastName());
        return studentRepository.save(studentData);
    }

    // delete student
    public void deleteStudent(Integer id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }

    // search student
    public List<Student> searchStudents(String name, String course) {
        return studentRepository.search(name, course);
    }

    // get all courses a student is enrolled in (using enrollmentRepository)
    public List<Course> getCoursesForStudent(Integer studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        return enrollments.stream()
                .map(Enrollment::getCourse)
                .collect(Collectors.toList());
    }
}
