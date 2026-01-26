package dao;

import model.Employee;

import java.util.List;

public interface EmployeeDao {
    // Сохранить нового сотрудника
    void save(Employee employee);

    // Найти сотрудника по ID
    Employee findById(Long id);

    // Получить список всех сотрудников
    List<Employee> findAll();
}
