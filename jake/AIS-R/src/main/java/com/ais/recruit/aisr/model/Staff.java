package com.ais.recruit.aisr.model;

import com.ais.recruit.aisr.model.enums.Branch;
import com.ais.recruit.aisr.model.enums.Level;

public class Staff extends User{

    private int staffId;
    private Level level;
    private Branch branch;

    public Staff() {
    }

    public Staff(String fullName, String address, String phone, String email, String username, String password, int staffId, Level level, Branch branch) {
        super(fullName, address, phone, email, username, password);
        this.staffId = staffId;
        this.level = level;
        this.branch = branch;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        if (staffId <= 0) {
            throw new IllegalArgumentException("Staff ID must be positive.");
        }
        this.staffId = staffId;
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
