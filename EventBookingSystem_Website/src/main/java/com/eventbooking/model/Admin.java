package com.eventbooking.model;

public class Admin extends User {
    private String role;

    public Admin() {}

    public Admin(String adminId, String fullName, String email, String phone, String password, String role) {
        super(adminId, fullName, email, phone, password, "Admin");
        this.role = role;
    }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public String toFileString() {
        return String.join("|", getUserId(), getFullName(), getEmail(), getPhone(), getPassword(), role == null ? "Admin" : role);
    }

    public static Admin fromFileString(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 6 || p[0].equalsIgnoreCase("AdminID")) return null;
        return new Admin(p[0], p[1], p[2], p[3], p[4], p[5]);
    }
}
