package controller; // или com.example.controller

import model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.EmployeeService;

import java.util.List;

// @RestController — говорит Спрингу:
// 1. "Я управляю веб-запросами".
// 2. "Мои ответы — это не HTML-страницы, а чистые данные (JSON)".
@RestController
@RequestMapping("/employees") // Базовый адрес: все запросы будут начинаться с /employees
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Метод для получения всех (GET запрос)
    // Адрес будет: GET http://localhost:8080/employees
    @GetMapping
    public List<Employee> getAll() {
        return employeeService.getAllEmployees();
    }

    // Метод для получения одного по ID (GET запрос)
    // Адрес будет: GET http://localhost:8080/employees/5
    // {id} — это переменная часть пути
    @GetMapping("/{id}")
    public Employee getById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    // Метод для найма сотрудника (POST запрос)
    // Адрес будет: POST http://localhost:8080/employees
    // @RequestBody — значит "Возьми данные сотрудника из тела запроса (JSON)"
    @PostMapping
    public void register(@RequestBody Employee employee) {
        employeeService.registerEmployee(employee);
    }
}
