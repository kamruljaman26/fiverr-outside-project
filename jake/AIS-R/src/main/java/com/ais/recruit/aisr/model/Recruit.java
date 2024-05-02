package com.ais.recruit.aisr.model;

import com.ais.recruit.aisr.model.enums.Branch;
import com.ais.recruit.aisr.model.enums.Level;

import java.time.LocalDateTime;

public class Recruit extends User {

    private String degree;
    private String qualification;
    private String higherQualification;
    private LocalDateTime dateOfInterview;
    private Level level;
    private Branch branch;
    private int staffId;


    public Recruit() {
    }

    public Recruit(String fullName, String address, String phone, String email, String username, String password, String degree, String qualification, String higherQualification, LocalDateTime dateOfInterview, Level level, Branch branch) {
        super(fullName, address, phone, email, username, password);
        this.degree = degree;
        this.qualification = qualification;
        this.higherQualification = higherQualification;
        this.dateOfInterview = dateOfInterview;
        this.level = level;
        this.branch = branch;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getHigherQualification() {
        return higherQualification;
    }

    public void setHigherQualification(String higherQualification) {
        this.higherQualification = higherQualification;
    }

    public LocalDateTime getDateOfInterview() {
        return dateOfInterview;
    }

    public void setDateOfInterview(LocalDateTime dateOfInterview) {
        this.dateOfInterview = dateOfInterview;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
