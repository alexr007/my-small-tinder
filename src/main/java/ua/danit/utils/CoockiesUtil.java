package ua.danit.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

public class CoockiesUtil {
    public String getID(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userID")) {
                cookie.setMaxAge(60);
                return cookie.getValue();
            }
        }
        return null;
    }
    public void kill(Cookie[] cookies, HttpServletResponse resp){
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
        }
    }
}