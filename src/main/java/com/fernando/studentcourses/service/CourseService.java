package com.fernando.studentcourses.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fernando.studentcourses.model.Course;
import com.fernando.studentcourses.model.Enrollment;
import com.fernando.studentcourses.model.Student;
import com.fernando.studentcourses.repository.CourseRepository;
import com.fernando.studentcourses.repository.EnrollmentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public CourseService(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    // create a course
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    // get all courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // get course by id
    public Course getCourseById(Integer id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("course not found with id " + id));
    }

    // update course
    public Course updateCourse(Integer id, Course newCourseData) {
        Course courseData = getCourseById(id);

        courseData.setTitle(newCourseData.getTitle());
        courseData.setCode(newCourseData.getCode());
        courseData.setDescription(newCourseData.getDescription());

        return courseRepository.save(courseData);
    }

    // delete course
    public void deleteCourse(Integer id) {
        Course course = getCourseById(id);
        courseRepository.delete(course);
    }

    // search course
    public List<Course> searchCourses(String title, String student) {
        return courseRepository.search(title, student);
    }

    // get all students enrolled in a course (using enrollmentRepository)
    public List<Student> getStudentsForCourse(Integer courseId) {
        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(courseId);
        return enrollments.stream()
                .map(Enrollment::getStudent)
                .collect(Collectors.toList());
    }
}
