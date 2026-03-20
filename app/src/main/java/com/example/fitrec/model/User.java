package com.example.fitrec.model;

import java.io.Serializable;
import com.google.gson.annotations.SerializedName;

public class User implements Serializable {
    private Long id;
    private String password;
    private String gender;
    private int age;
    private String stylePreferences;
    private String email;
    private String name;
    private String bodyType;
    private String occasion;

    // Empty constructor
    public User() {
    }

    // Full constructor
    public User(String email, String password, String name, String gender,
                int age, String stylePreferences, String bodyType, String occasion) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.stylePreferences = stylePreferences;
        this.bodyType = bodyType;
        this.occasion = occasion;
    }

    // Getters & Setters for all fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getStylePreferences() {
        return stylePreferences;
    }

    public void setStylePreferences(String stylePreferences) {
        this.stylePreferences = stylePreferences;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBodyType() {
        return bodyType;
    }
    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getOccasion() {
        return occasion;
    }
    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }
}