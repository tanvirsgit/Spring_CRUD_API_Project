package com.mvc.demo;

import java.util.List;

public interface EmployeeDAO {
    void SaveEmployee(Employee var1);

    Employee GetEmployee(int var1);

    List<Employee> GetAll();

    Employee GetByName(String var1);
}