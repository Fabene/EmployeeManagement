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
    public List<Employee> filterEmployeesByAttributeAndValue(String attribute, String value) {
        // Utilisez la méthode existante pour filtrer les employés en fonction de l'attribut et de la valeur
        return employeeRepository.findAll((root, query, builder) -> {
            // Construire la condition de recherche en fonction de l'attribut et de la valeur spécifiés
            return builder.like(builder.lower(root.get(attribute)), "%" + value.toLowerCase() + "%");
        });
    }
    public List<Employee> filterEmployeesByAttribute(String attribute, String value) {
        // Implémentez ici la logique pour filtrer les employés en fonction de l'attribut et de la valeur
        // Utilisez les critères de requête JPA pour créer la requête de filtrage
        return employeeRepository.findAll((root, query, builder) -> {
            // Construire la condition de recherche en fonction de l'attribut et de la valeur spécifiés
            return builder.like(builder.lower(root.get(attribute)), "%" + value.toLowerCase() + "%");
        });
    }

    public List<Employee> sortEmployeesByAttribute(String attribute) {
        // Implémentez ici la logique pour trier les employés par attribut
        // Utilisez les critères de requête JPA pour créer la requête de tri
        return employeeRepository.findAll(Sort.by(attribute));
    }
}

