    package com.hei.project2p1.controller;

    import com.hei.project2p1.model.Employee;
    import com.hei.project2p1.service.EmployeeService;
    import lombok.AllArgsConstructor;
    import org.springframework.beans.propertyeditors.CustomDateEditor;
    import org.springframework.http.HttpHeaders;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.MediaType;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.WebDataBinder;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;


    import java.io.IOException;
    import java.text.SimpleDateFormat;
    import java.util.Date;
    import java.util.Optional;

    @AllArgsConstructor
    @Controller
    public class EmployeeController {
        private final EmployeeService employeeService;

        @GetMapping("/")
        public String index(Model model) {
            // Récupère la liste des employés à partir du service
            // et l'ajoute au modèle pour l'affichage dans la vue
            model.addAttribute("employees", employeeService.getEmployees());

            // Ajoute un nouvel employé vide au modèle pour le formulaire
            model.addAttribute("newEmployee", new Employee());
            return "index";
        }

        @GetMapping("/UploadImage/{id}")
        public ResponseEntity<byte[]> getImage(@PathVariable Long id) throws IOException {
            // Récupérer l'employé par son id en utilisant le service
            Optional<Employee> employeeOptional = employeeService.getEmployeeById(id);

            // Vérifier si l'employé existe dans la base de données
            if (employeeOptional.isPresent()) {
                Employee employee = employeeOptional.get();

                // Récupérer les données binaires de la propriété 'photo' de l'employé
                MultipartFile photo = employee.getPhoto();
                byte[] photoData = photo.getBytes();


                // Vérifier si l'employé a une photo
                if (photoData != null && photoData.length > 0) {
                    // Construire une réponse HTTP avec les données binaires en tant que corps de la réponse
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.IMAGE_JPEG);
                    headers.setContentLength(photoData.length);
                    return new ResponseEntity<>(photoData, headers, HttpStatus.OK);
                }
            }

            // Renvoyer une réponse vide si l'employé n'existe pas dans la base de données ou s'il n'a pas de photo
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        @InitBinder
        public void initBinder(WebDataBinder binder) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        }
        @PostMapping("/addEmployee")
        public String addEmployee(@ModelAttribute("newEmployee") Employee employee,
                                  @RequestParam("photo") MultipartFile photo,
                                  @RequestParam("birthDate") Date birthDate) {
            // Traiter l'image de l'employé et la stocker dans l'attribut 'photo' de l'employé
            employeeService.processEmployeePhoto(employee, photo);

            // Définir la date de naissance
            employee.setBirthDate(String.valueOf(birthDate));

            // Enregistrer l'employé dans la base de données
            employeeService.save(employee);
            return "redirect:/";
        }
        @GetMapping("/updateEmployee/{id}")
        public String showUpdateForm(@PathVariable Long id, Model model) {
            Optional<Employee> employeeOptional = employeeService.getEmployeeById(id);

            if (employeeOptional.isPresent()) {
                Employee employee = employeeOptional.get();
                model.addAttribute("employee", employee);
                return "update_employee";
            } else {
                return "redirect:/";
            }
        }


        @GetMapping("/updateEmployeeForm/{id}")
        public String showUpdateEmployeeForm(@PathVariable Long id, Model model) {
            // Récupérer l'employé par son ID depuis la base de données
            Employee employee = employeeService.getEmployeeById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID: " + id));

            // Ajouter l'employé au modèle pour afficher les valeurs dans le formulaire
            model.addAttribute("employee", employee);

            // Charger la page "update_employee.html" pour afficher le formulaire
            return "update_employee";
        }
        @PostMapping("/saveUpdateEmployee/{id}")
        public String saveUpdate(@PathVariable Long id, @ModelAttribute Employee updatedEmployee) {
            // Récupérer l'employé existant par son ID depuis la base de données
            Employee existingEmployee = employeeService.getEmployeeById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID: " + id));

            // Mettre à jour les informations de l'employé existant avec les nouvelles informations
            existingEmployee.updateEmployee(updatedEmployee);

            // Enregistrer les modifications dans la base de données
            employeeService.save(existingEmployee);

            // Rediriger vers la page principale (ou autre page de votre choix) après la mise à jour
            return "redirect:/";
        }

    }


