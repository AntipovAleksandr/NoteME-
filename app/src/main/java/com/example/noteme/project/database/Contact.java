package com.example.noteme.project.database;

public class Contact {

    private String contactID;
    private String filePath;
    private String name;
    private String email;
    private String number;
    private String description;

    public String getContactID() {
        return contactID;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Contact(String contactID, String filePath, String name, String email, String number, String description) {
        this.contactID = contactID;
        this.filePath = filePath;
        this.name = name;
        this.email = email;
        this.number = number;
        this.description = description;
    }
}

