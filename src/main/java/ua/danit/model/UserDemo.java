package ua.danit.model;

import ua.danit.utils.GeneratorID;

public class UserDemo {
    String name;
    String imgURL;
    Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public void setImgURL(String inhURL) {
        this.imgURL = inhURL;
    }

    public UserDemo(String name, String imgURL) {
        this.name = name;
        this.imgURL = imgURL;
        this.id = GeneratorID.generateNewID();
    }
}
