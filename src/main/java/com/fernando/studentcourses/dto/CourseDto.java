package com.fernando.studentcourses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
    private Integer id;
    private String code;
    private String title;
    private String description;
    private List<StudentSummary> students; // Only show basic student info
}
