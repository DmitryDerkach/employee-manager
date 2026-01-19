package com.example.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Генерирует геттеры, сеттеры, toString, equals
@Builder // Позволяет создавать объекты через .builder().build()
@NoArgsConstructor // Пустой конструктор (нужен для Jackson)
@AllArgsConstructor // Конструктор со всеми полями
@JsonIgnoreProperties(ignoreUnknown = true) // Если сервер вернет лишнее поле, не падать
public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String department;
    private Double salary;
}