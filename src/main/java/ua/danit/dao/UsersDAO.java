package ua.danit.dao;


import ua.danit.model.UserDemo;

import java.util.ArrayList;

public class UsersDAO extends ArrayList<UserDemo> {

    public UsersDAO() {
        add(new UserDemo("Mrs.Hudson",
                "https://pbs.twimg.com/profile_images/1783390898/twitter_av_400x400.png"));
        add(new UserDemo("Your Ex",
                "https://i.pinimg.com/originals/b0/d2/e6/b0d2e6d8d83931bf586f83d4ed189c0a.jpg"));
        add(new UserDemo("Stifler's Mom",
                "https://pbs.twimg.com/profile_images/989211028692127744/tWR4WHNC_400x400.jpg"));
        add(new UserDemo("Original Ba XX",
                "http://i57.beon.ru/53/81/228153/4/11191004/33.jpeg"));
        add(new UserDemo("Not First Woman In Space",
                "https://airandspace.si.edu/sites/default/files/styles" +
                        "/callout_half/public/images/news/7462h.jpg?itok=S6nexORm"));
    }
}
