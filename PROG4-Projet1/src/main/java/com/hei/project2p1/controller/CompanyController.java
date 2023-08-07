package com.hei.project2p1.controller;

import com.hei.project2p1.model.Company;
import com.hei.project2p1.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }
    @GetMapping("/companyForm")
    public String showCompanyForm(Model model) {
        model.addAttribute("company", new Company());
        return "companyForm";
    }
    @PostMapping("/processCompanyForm")
    public String processCompanyForm(@ModelAttribute Company company,
                                     @RequestParam("logo") MultipartFile logoFile,
                                     RedirectAttributes redirectAttributes) {
        companyService.saveCompany(company, logoFile);
        redirectAttributes.addFlashAttribute("message", "Les informations de l'entreprise ont été enregistrées avec succès.");
        return "redirect:/home";
    }
    @PostMapping("/saveCompany")
    public String processCompanyForm(@ModelAttribute Company company,
                                     @RequestParam("logo") MultipartFile logoFile) {
        if (!logoFile.isEmpty()) {
        }
        return "redirect:/companyForm";
    }
}
