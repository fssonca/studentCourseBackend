package com.fernando.studentcourses.dto;

import com.fernando.studentcourses.model.Enrollment.EnrollmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDto {
    private Integer id;
    private Integer studentId;
    private Integer courseId;
    private EnrollmentStatus status;
}
