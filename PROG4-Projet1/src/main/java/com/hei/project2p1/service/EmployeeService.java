package com.hei.project2p1.service;

import com.hei.project2p1.model.Employee;

import com.hei.project2p1.repository.EmployeeRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;


    public List<Employee> getEmployeesFromSession(HttpSession session) {
        List<Employee> employees = (List<Employee>) session.getAttribute("employees");
        if (employees == null) {
            employees = new ArrayList<>();
            session.setAttribute("employees", employees);
        }
        return employees;
    }

    public List<Employee> getEmployees() {

        return employeeRepository.findAll();
    }

    public void addEmployee(Employee employee) throws IOException {

        employeeRepository.save(employee);
    }


    public void save(Employee employee) {
        employeeRepository.save(employee);
    }


    public void processEmployeePhoto(Employee employee, MultipartFile photo) {
        try {
            if (photo != null && !photo.isEmpty()) {
                employee.setImageData(photo.getBytes());
            } else {
                employee.setPhoto(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<Employee> getEmployeeById(Long EmployeeId) {
       return employeeRepository.findById(EmployeeId);

    }
    public List<Employee> filterEmployeesByAttributeAndValue(String attribute, String value) {
        return employeeRepository.findAll((root, query, builder) -> {
            return builder.like(builder.lower(root.get(attribute)), "%" + value.toLowerCase() + "%");
        });
    }
    public List<Employee> filterEmployeesByAttribute(String attribute, String value) {
        return employeeRepository.findAll((root, query, builder) -> {
            return builder.like(builder.lower(root.get(attribute)), "%" + value.toLowerCase() + "%");
        });
    }

    public List<Employee> sortEmployeesByAttribute(String attribute) {

        return employeeRepository.findAll(Sort.by(attribute));
    }
    public boolean isPhoneNumberExists(String phoneNumber) {
        List<Employee> employeesWithPhoneNumber = employeeRepository.findByPhones(phoneNumber);
        return !employeesWithPhoneNumber.isEmpty();
    }
}

