package com.mvc.demo;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {
    private SessionFactory sessionFactory;

    @Autowired
    public EmployeeDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void SaveEmployee(Employee employee) {
        Session session = this.sessionFactory.getCurrentSession();
        session.saveOrUpdate(employee);
    }

    @Transactional
    public Employee GetEmployee(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        return (Employee)session.find(Employee.class, id);
    }

    @Transactional
    public List<Employee> GetAll() {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createQuery("from Employee").list();
    }

    @Transactional
    public Employee GetByName(String name) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Employee where name=:name");
        query.setParameter("name", name);
        Employee employee = (Employee)query.uniqueResult();
        return employee;
    }

    @Transactional
    public void DeleteEmployee(Employee employee) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(employee);
    }
}
