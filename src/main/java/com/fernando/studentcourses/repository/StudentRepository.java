package com.fernando.studentcourses.repository;

import com.fernando.studentcourses.model.Student;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query("""
                SELECT s FROM Student s
                WHERE (:name IS NULL OR LOWER(CONCAT(s.firstName, ' ', s.lastName)) LIKE LOWER(CONCAT('%', :name, '%')))
                AND (:course IS NULL OR EXISTS (
                    SELECT e FROM Enrollment e
                    WHERE e.student = s
                    AND (
                        LOWER(e.course.code) LIKE LOWER(CONCAT('%', :course, '%'))
                        OR LOWER(e.course.title) LIKE LOWER(CONCAT('%', :course, '%'))
                    )
                ))
            """)
    List<Student> search(@Param("name") String name, @Param("course") String course);

}
