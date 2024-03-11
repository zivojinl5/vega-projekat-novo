package com.example.backend.web.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDTO {
    private Long id;

    private LocalDate date;
    private String description;
    private double hours;
    private double overtimeHours;

    private ClientDTO client;
    private ProjectDTO project;
    private CategoryDTO category;
    private UserDTO user;

}