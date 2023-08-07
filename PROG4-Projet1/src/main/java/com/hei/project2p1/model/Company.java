package com.hei.project2p1.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class Company {
    private String companyName;
    private String description;
    private String slogan;
    private String address;
    private String contactEmail;
    private List<String> phoneNumbers;
    private String nif;
    private String stat;
    private String rcs;
    private String logo;

}
