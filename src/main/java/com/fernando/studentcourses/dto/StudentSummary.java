package com.fernando.studentcourses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentSummary {
    private Integer id;
    private String firstName;
    private String lastName;
}
