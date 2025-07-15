package com.employee.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.employee.model.Employee;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id);

  

    Employee createEmployeeWithFiles(Employee employee, MultipartFile profilePhoto, MultipartFile resumes);


    Employee updateEmployeeWithFiles(Long id, Employee employee, MultipartFile profilePhoto, MultipartFile resume);

    void deleteEmployee(Long id);
}
