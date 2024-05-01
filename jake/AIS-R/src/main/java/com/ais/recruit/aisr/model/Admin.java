package com.ais.recruit.aisr.model;

import com.ais.recruit.aisr.model.enums.Position;

public class Admin extends User {

    private int staffId;
    private Position position;

    public Admin() {
    }

    public Admin(String fullName, String address, String phone, String email, String username, String password, int staffId, Position position) {
        super(fullName, address, phone, email, username, password);
        this.staffId = staffId;
        this.position = position;
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return staffId + "," + getFullName() + "," + getAddress() + "," + getPhone() + "," + getEmail() + "," +
                getUsername() + "," + getPassword() + "," + position;
    }

}
