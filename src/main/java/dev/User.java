package dev;

import java.awt.*;
import java.util.List;

public class User {
    String name;
    String surname;
    String job;
    String password;
    List<String> materials;
    Image photo;
    String htmlFileContent;
    Boolean isUserDataChange;

    public User(String name, String surname, Boolean isUserDataChange) {
        this.name = name;
        this.surname = surname;
        this.isUserDataChange = isUserDataChange;
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

    public void setPhoto(Image photo) {
        this.photo = photo;
    }

    public void setHtmlFileContent() {
        this.htmlFileContent = this.surname.charAt(0) + this.name + ".html";
    }

    public void setIsUserDataChange(Boolean isUserDataChange) {
        this.isUserDataChange = isUserDataChange;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public Image getPhoto() {
        return this.photo;
    }

    public String getHtmlFileContent() {
        return this.htmlFileContent;
    }

    public Boolean getIsUserDataChange() {
        return this.isUserDataChange;
    }
}
