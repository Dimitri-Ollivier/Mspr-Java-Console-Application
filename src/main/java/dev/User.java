package dev;

import java.util.List;

public class User {
    private final String name;
    private final String surname;
    private String job;
    private String password;
    private List<String> materials;
    private String photo;
    private String htmlFileContent;

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMaterials(List<String> materials) {
        this.materials = materials;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setHtmlFileContent(String htmlContent) {
        this.htmlFileContent = htmlContent;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getJob() {
        return this.job;
    }

    public String getPassword() {
        return this.password;
    }


    public List<String> getMaterials() {
        return this.materials;
    }

    public String getPhoto() {
        return this.photo;
    }

    public String getHtmlFileContent() {
        return this.htmlFileContent;
    }
}
