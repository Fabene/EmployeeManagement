package com.hei.project2p1.service;

import com.hei.project2p1.model.Employee;

import com.hei.project2p1.repository.EmployeeRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        // Utilise le repository pour récupérer la liste des employés depuis la base de données
        return employeeRepository.findAll();
    }

    public void addEmployee(Employee employee) throws IOException {
        // Utilise le repository pour enregistrer le nouvel employé dans la base de données
        employeeRepository.save(employee);
    }


    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    // Méthode pour traiter l'image de l'employé
    public void processEmployeePhoto(Employee employee, MultipartFile photo) {
        try {
            // Vérifier si un fichier d'image est téléchargé
            if (photo != null && !photo.isEmpty()) {
                // Si un fichier d'image est téléchargé, lisez le contenu du fichier et attribuez-le à la propriété 'photo' de l'employé
                employee.setImageData(photo.getBytes());
            } else {
                // Si aucun fichier d'image n'est téléchargé, attribuez une valeur null à la propriété 'photo' de l'employé
                employee.setPhoto(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Gérer l'erreur ici, par exemple : logger.error("Erreur lors du traitement de l'image", e);
        }
    }

    public Optional<Employee> getEmployeeById(Long EmployeeId) {
       return employeeRepository.findById(EmployeeId);

    }
}
