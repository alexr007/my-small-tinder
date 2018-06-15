package ua.danit.model;

public class UserDemo {
    String name;
    String inhURL;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInhURL() {
        return inhURL;
    }

    public void setInhURL(String inhURL) {
        this.inhURL = inhURL;
    }

    public UserDemo(String name, String inhURL) {
        this.name = name;
        this.inhURL = inhURL;
    }
}
