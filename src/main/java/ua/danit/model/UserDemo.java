package ua.danit.model;

public class UserDemo {
    String name;
    String imgURL;

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
    }
}
