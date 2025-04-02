package com.fernando.studentcourses.repository;

import com.fernando.studentcourses.model.Course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query("""
                SELECT c FROM Course c
                WHERE (:title IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', :title, '%'))
                       OR LOWER(c.code) LIKE LOWER(CONCAT('%', :title, '%')))
                AND (:student IS NULL OR EXISTS (
                    SELECT e FROM Enrollment e
                    WHERE e.course = c
                    AND LOWER(CONCAT(e.student.firstName, ' ', e.student.lastName)) LIKE LOWER(CONCAT('%', :student, '%'))
                ))
            """)
    List<Course> search(@Param("title") String title, @Param("student") String student);
}