package ua.danit.model;

import ua.danit.utils.GeneratorID;

public class Yamnyk_users {
    private Long id;
    private String name;
    private int gender;
    private String imgURL;
    private String password;
    private String email;


    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Yamnyk_users() {
        id = Long.valueOf(GeneratorID.generateNewID());
    }

    public Yamnyk_users(String name, String imgURL) {
        id = Long.valueOf(GeneratorID.generateNewID());
        this.name = name;
        this.imgURL = imgURL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
