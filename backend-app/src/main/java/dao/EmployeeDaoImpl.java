package dao;

import model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// @Repository — говорит Спрингу: "Это класс для работы с БД. Создай его бин".
@Repository
// @Transactional — говорит: "Все методы этого класса должны выполняться в транзакции".
// (Открыть транзакцию -> Сделать дело -> Закоммитить). Без этого Hibernate ругается.
@Transactional
public class EmployeeDaoImpl implements EmployeeDao {

    // Внедряем ту самую Фабрику, которую настраивали в HibernateConfig
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Employee employee) {
        // Получаем текущую сессию (соединение) и сохраняем объект
        getCurrentSession().save(employee);
    }

    @Override
    public Employee findById(Long id) {
        return getCurrentSession().get(Employee.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Employee> findAll() {
        // Здесь используется HQL (Hibernate Query Language)
        // Мы пишем не "SELECT * FROM employees", а "FROM Employee" (имя Класса, а не таблицы!)
        return getCurrentSession().createQuery("from Employee").list();
    }

    // Вспомогательный метод, чтобы код был чище
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
