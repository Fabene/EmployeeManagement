package com.hei.project2p1.service;

import com.hei.project2p1.model.Company;
import org.springframework.web.multipart.MultipartFile;

public interface CompanyService {
    void saveCompany(Company company, MultipartFile logoFile);
    Company getCompany();
}