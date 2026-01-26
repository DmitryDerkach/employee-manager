package service;

import dao.EmployeeDao;
import model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// @Service — говорит Спрингу: "Это класс бизнес-логики. Создай его бин".
// Это аналог @Repository, только для другого слоя.
@Service
@Transactional // Транзакции лучше управлять на уровне Сервиса!
public class EmployeeServiceImpl implements EmployeeService {

    // Сервис "нанимает" DAO для грязной работы с базой
    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public void registerEmployee(Employee employee) {
        // Здесь могла бы быть проверка:
        // if (employee.getSalary() < 0) throw new RuntimeException("Рабский труд запрещен!");

        // Если всё ок — поручаем DAO сохранить
        employeeDao.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeDao.findById(id);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDao.findAll();
    }
}
