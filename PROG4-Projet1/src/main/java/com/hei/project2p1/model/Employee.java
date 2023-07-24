package com.hei.project2p1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String sex;
    private String phones;
    private String address;
    private String personalEmail;
    private String workEmail;
    private String cinNumber;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date cinDateOfIssue;

    private String cinPlaceOfIssue;
    private String jobTitle;
    private int childrenCount;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date departureDate;

    private String socioProfessionalCategory;
    private String cnapsNumber;

    @Transient
    private MultipartFile photo;

    @Column(name = "image_data")
    private byte[] imageData;

    // ... Getters and Setters ...

    public String getImageUrl() {
        if (imageData != null && imageData.length > 0) {
            return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imageData);
        } else {
            return ""; // URL d'une image par défaut si aucune photo n'est présente
        }
    }
    public void updateEmployee(Employee updatedEmployee) {
        this.firstName = updatedEmployee.getFirstName();
        this.lastName = updatedEmployee.getLastName();
        this.birthDate = updatedEmployee.getBirthDate();
        this.sex = updatedEmployee.getSex();
        this.phones = updatedEmployee.getPhones();
        this.address = updatedEmployee.getAddress();
        this.personalEmail = updatedEmployee.getPersonalEmail();
        this.workEmail = updatedEmployee.getWorkEmail();
        this.cinNumber = updatedEmployee.getCinNumber();
        this.cinDateOfIssue = updatedEmployee.getCinDateOfIssue();
        this.cinPlaceOfIssue = updatedEmployee.getCinPlaceOfIssue();
        this.jobTitle = updatedEmployee.getJobTitle();
        this.childrenCount = updatedEmployee.getChildrenCount();
        this.hireDate = updatedEmployee.getHireDate();
        this.departureDate = updatedEmployee.getDepartureDate();
        this.socioProfessionalCategory = updatedEmployee.getSocioProfessionalCategory();
        this.cnapsNumber = updatedEmployee.getCnapsNumber();
    }
}
