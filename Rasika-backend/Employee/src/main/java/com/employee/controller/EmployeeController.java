package com.employee.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.employee.localDateAdapter.LocalDateAdapter;
import com.employee.model.Employee;
import com.employee.service.EmployeeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:3000") // React frontend
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    // ✅ Get all employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    // ✅ Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    // ✅ Create new employee with files
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Employee> createEmployee(
            @RequestParam("employee") String employeeJson,
            @RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto,
            @RequestParam(value = "resume", required = false) MultipartFile resume
    ) throws IOException {

        Employee employee = gson.fromJson(employeeJson, Employee.class);
        return ResponseEntity.ok(employeeService.createEmployeeWithFiles(employee, profilePhoto, resume));
    }

    // ✅ Update employee with files
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @RequestParam("employee") String employeeJson,
            @RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto,
            @RequestParam(value = "resume", required = false) MultipartFile resume
    ) throws IOException {

        Employee emp = gson.fromJson(employeeJson, Employee.class);
        return ResponseEntity.ok(employeeService.updateEmployeeWithFiles(id, emp, profilePhoto, resume));
    }

    // ✅ Delete employee
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }
}
