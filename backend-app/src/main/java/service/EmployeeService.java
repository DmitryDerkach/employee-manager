package service; // или com.example.service, если ты вернул пакеты

import model.Employee;

import java.util.List;

public interface EmployeeService {
    void registerEmployee(Employee employee); // Назвали по-деловому: "Зарегистрировать"

    Employee getEmployeeById(Long id);

    List<Employee> getAllEmployees();
}
