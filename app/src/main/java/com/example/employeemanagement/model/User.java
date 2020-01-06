package com.example.employeemanagement.model;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name;
    private String phone;
    private String position;
    private String department;
    private String password;
    private boolean isAdmin;

    public User(int id, String name, String phone, String position, String department, String password, boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.position = position;
        this.department = department;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAddmin() {
        return isAdmin;
    }

    public void setAddmin(boolean addmin) {
        isAdmin = addmin;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", position='" + position + '\'' +
                ", department='" + department + '\'' +
                ", password='" + password + '\'' +
                ", isAddmin=" + isAdmin +
                '}';
    }
}
