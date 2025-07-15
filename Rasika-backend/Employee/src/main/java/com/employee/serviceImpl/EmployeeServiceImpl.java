package com.employee.serviceImpl;

import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;
import com.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service

public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // ✅ Get all employees
    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // ✅ Get employee by ID
    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
    }

    // ✅ Create employee with optional files
    @Override
    public Employee createEmployeeWithFiles(Employee employee, MultipartFile profilePhoto, MultipartFile resume) {
        try {
            if (profilePhoto != null && !profilePhoto.isEmpty()) {
                employee.setProfilePhoto(profilePhoto.getBytes());
            }

            if (resume != null && !resume.isEmpty()) {
                employee.setResumeFile(resume.getBytes());
            }

            return employeeRepository.save(employee);
        } catch (IOException e) {
            throw new RuntimeException("File upload failed: " + e.getMessage(), e);
        }
    }

    // ✅ Update employee with optional new files
    @Override
    public Employee updateEmployeeWithFiles(Long id, Employee updatedEmployee, MultipartFile profilePhoto, MultipartFile resume) {
        Employee existing = getEmployeeById(id);

        try {
            if (profilePhoto != null && !profilePhoto.isEmpty()) {
                existing.setProfilePhoto(profilePhoto.getBytes());
            }

            if (resume != null && !resume.isEmpty()) {
                existing.setResumeFile(resume.getBytes());
            }

            return updateEmployeeFields(existing, updatedEmployee);
        } catch (IOException e) {
            throw new RuntimeException("File upload failed: " + e.getMessage(), e);
        }
    }

    // ✅ Delete employee
    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    // ✅ Update fields
    private Employee updateEmployeeFields(Employee existing, Employee updated) {
        existing.setFullName(updated.getFullName());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setGender(updated.getGender());
        existing.setDateOfBirth(updated.getDateOfBirth());
        existing.setDepartment(updated.getDepartment());
        existing.setJobTitle(updated.getJobTitle());
        existing.setDateOfJoining(updated.getDateOfJoining());
        existing.setSalary(updated.getSalary());
        existing.setEmploymentType(updated.getEmploymentType());
        existing.setAddressLine1(updated.getAddressLine1());
        existing.setAddressLine2(updated.getAddressLine2());
        existing.setCity(updated.getCity());
        existing.setState(updated.getState());
        existing.setZipCode(updated.getZipCode());
        existing.setPanNumber(updated.getPanNumber());
        existing.setAadhaarNumber(updated.getAadhaarNumber());

        return employeeRepository.save(existing);
    }
}  
