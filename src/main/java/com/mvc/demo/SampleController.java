package com.mvc.demo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SampleController {
    private EmployeeDAO employeeDAO;

    public SampleController() {
    }

    @Autowired
    public void setDAO(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @PostMapping({"/insert"})
    public void SaveEmployee(@RequestBody Employee e) {
        this.employeeDAO.SaveEmployee(e);
    }

    @GetMapping({"/{id}"})
    public Employee Find(@PathVariable int id) {
        return this.employeeDAO.GetEmployee(id);
    }

    @GetMapping({"/all"})
    public List<Employee> GetAll() {
        return this.employeeDAO.GetAll();
    }

    @GetMapping({"/get/{name}"})
    public Employee GetByName(@PathVariable String name) {
        return this.employeeDAO.GetByName(name);
    }

    @DeleteMapping("/{id}")
    public void DeleteEmployee(@PathVariable int id){
        Employee employee = Find(id);
        employeeDAO.DeleteEmployee(employee);
    }
}

