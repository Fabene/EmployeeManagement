package com.hei.project2p1.service;

import com.hei.project2p1.model.Company;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CompanyServiceImpl implements CompanyService {
    private Company company;

    @Override
    public void saveCompany(Company company, MultipartFile logoFile) {
        this.company = company;
    }

    @Override
    public Company getCompany() {
        return company;
    }
}