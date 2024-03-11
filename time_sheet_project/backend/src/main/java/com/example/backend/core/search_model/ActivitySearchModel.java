package com.example.backend.core.search_model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ActivitySearchModel {
    private Long userId;
    private LocalDate starDate;
    private LocalDate endDate;

}
