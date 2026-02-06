package model; // ⚠️ Должно совпадать с первой строкой в Employee.java

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

    @Test
    public void shouldCreateEmployeeObject() {
        // 1. Arrange (Подготовка)
        Employee employee = new Employee();
        employee.setFirstName("Ivan");
        employee.setLastName("Ivanov");
        employee.setEmail("ivan@test.com");
        employee.setDepartment("IT");       // ✅ Исправлено (было setJobTitle)
        employee.setSalary(1000.0);         // ✅ Это поле у тебя тоже есть

        // 2. Act & Assert (Проверка)
        assertNotNull(employee);
        assertEquals("Ivan", employee.getFirstName());
        assertEquals("Ivanov", employee.getLastName());
        assertEquals("ivan@test.com", employee.getEmail());
        assertEquals("IT", employee.getDepartment()); // ✅ Исправлено
        assertEquals(1000.0, employee.getSalary());
    }
}