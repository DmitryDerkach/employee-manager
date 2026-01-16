import config.HibernateConfig;
import dao.EmployeeDao;
import model.Employee;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("----- STARTING APPLICATION -----");

        ApplicationContext context = new AnnotationConfigApplicationContext(HibernateConfig.class);

        // 1. Получаем наш новый DAO из контекста
        // Обрати внимание: мы просим Интерфейс (EmployeeDao), а Спринг дает Реализацию (EmployeeDaoImpl)
        EmployeeDao employeeDao = context.getBean(EmployeeDao.class);

        // 2. Создаем сотрудника (пока он только в памяти Java)
        Employee newEmployee = new Employee("Ivan", "Ivanov", "ivan@example.com", "IT", 50000.0);

        // 3. Сохраняем в базу!
        System.out.println("Saving employee...");
        employeeDao.save(newEmployee);
        System.out.println("Employee saved! ID: " + newEmployee.getId());

        // 4. Проверяем, что он там есть — читаем из базы
        List<Employee> allEmployees = employeeDao.findAll();
        System.out.println("Total employees in DB: " + allEmployees.size());

        for (Employee emp : allEmployees) {
            System.out.println("Found: " + emp.getFirstName() + " " + emp.getLastName());
        }

        System.out.println("----- FINISHED -----");
    }
}
