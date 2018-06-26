package ua.danit.utils;

import javax.servlet.http.Cookie;

public class GetFromCoockies {
    public String getID(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userID")) {
                cookie.setMaxAge(60);
                return cookie.getValue();
            }
        }
        return null;
    }
}