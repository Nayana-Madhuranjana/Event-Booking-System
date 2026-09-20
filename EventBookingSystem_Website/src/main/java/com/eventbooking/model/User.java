package com.eventbooking.model;

import java.io.Serializable;

public class User implements Serializable {
    private String userId;
    private String fullName;
    private String email;
    private String phone;
    private String password;
    private String userType;

    public User() {}

    public User(String userId, String fullName, String email, String phone, String password, String userType) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.userType = userType;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }

    public String toFileString() {
        return String.join("|", safe(userId), safe(fullName), safe(email), safe(phone), safe(password), safe(userType));
    }

    public static User fromFileString(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 6 || p[0].equalsIgnoreCase("UserID")) return null;
        return new User(p[0], p[1], p[2], p[3], p[4], p[5]);
    }

    protected String safe(String value) {
        return value == null ? "" : value.replace("|", " ").trim();
    }
}
