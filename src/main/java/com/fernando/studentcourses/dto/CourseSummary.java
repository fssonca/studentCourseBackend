package com.fernando.studentcourses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseSummary {
    private Integer id;
    private String code;
    private String title;
}
