package com.mvc.demo;

import java.util.List;

public interface EmployeeDAO {
    void SaveEmployee(Employee employee);

    Employee GetEmployee(int id);

    List<Employee> GetAll();

    Employee GetByName(String name);

    void DeleteEmployee(Employee employee);

}