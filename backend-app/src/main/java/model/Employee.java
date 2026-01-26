package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

// @Entity — Самая важная аннотация.
// Она говорит Hibernate-у: "Этот класс — не просто Java-код, это проекция строки в базе данных".
@Entity

// @Table — Говорит: "В базе данных таблица называется 'employees'".
// Если не написать, он будет искать таблицу с именем класса "Employee".
@Table(name = "employees")
public class Employee {

    // @Id — Говорит: "Это поле — уникальный ключ (Primary Key)".
    // Без него Hibernate не поймет, как отличать сотрудников друг от друга.
    @Id
    // @GeneratedValue — Говорит: "Не проси меня придумывать ID вручную (1, 2, 3...).
    // Пусть база данных сама генерирует следующий номер (Auto Increment)".
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column — Настройка колонки.
    // nullable = false — значит поле "Имя" не может быть пустым (NOT NULL в SQL).
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    // unique = true — значит email не может повторяться у двух разных людей.
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "department")
    private String department;

    @Column(name = "salary")
    private Double salary;

    // --- ОБЯЗАТЕЛЬНО ДЛЯ HIBERNATE ---

    // 1. Пустой конструктор (No-Args Constructor)
    // Hibernate использует его, чтобы создать объект, когда читает данные из базы.
    // Если его не будет — получишь ошибку.
    public Employee() {
    }

    // 2. Конструктор с полями (для нашего удобства, чтобы создавать новых сотрудников)
    public Employee(String firstName, String lastName, String email, String department, Double salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.department = department;
        this.salary = salary;
    }

    // 3. Геттеры и Сеттеры
    // Hibernate не лезет в поля напрямую (private), он использует эти методы.
    // В IntelliJ IDEA их можно сгенерировать: Правая кнопка -> Generate -> Getter and Setter -> Выбрать всё.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
