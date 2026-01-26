import config.HibernateConfig;
import model.Employee;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.EmployeeService;

import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("----- STARTING APPLICATION -----");

        ApplicationContext context = new AnnotationConfigApplicationContext(HibernateConfig.class);

        // ВАЖНО: Теперь мы просим у Спринга не DAO, а СЕРВИС!
        // Мы говорим: "Дай нам менеджера, а не рабочего"
        EmployeeService employeeService = context.getBean(EmployeeService.class);

        // Создаем сотрудника (меняем email, чтобы не было ошибки уникальности!)
        Employee newEmployee = new Employee("Petr", "Petrov", "petr@example.com", "Sales", 40000.0);

        System.out.println("Registering employee via Service...");

        // Вызываем метод бизнес-логики
        employeeService.registerEmployee(newEmployee);

        System.out.println("Employee registered!");

        // Проверяем через сервис
        List<Employee> allEmployees = employeeService.getAllEmployees();
        for (Employee emp : allEmployees) {
            System.out.println("Found: " + emp.getFirstName() + " (" + emp.getEmail() + ")");
        }
    }
}