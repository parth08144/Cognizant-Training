package com.cognizant.ormlearn.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.ormlearn.model.Employee;
import com.cognizant.ormlearn.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public void addEmployee(Employee employee) {

        employeeRepository.save(employee);
    }

    @Transactional
    public Employee getEmployee(Integer id) {

        Optional<Employee> result = employeeRepository.findById(id);

        return result.orElse(null);
    }

    @Transactional
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Transactional
    public void updateEmployee(Employee employee) {
        employeeRepository.save(employee);   
    }

    @Transactional
    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }

    @Transactional
    public long getEmployeeCount() {
        return employeeRepository.count();
    }
}
