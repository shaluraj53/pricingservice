package com.exam.pricing.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class User {

    private String id;

    private String name;

    private String mobile;

    private String role;

    private LocalDate createdOn;

}
