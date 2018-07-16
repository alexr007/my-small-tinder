package ua.danit.controllers;

import freemarker.template.Template;
import ua.danit.dao.UsersDAO;
import ua.danit.utils.CoockiesUtil;
import ua.danit.utils.FreemarkerInit;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        FreemarkerInit fm = new FreemarkerInit();

        PrintWriter out = resp.getWriter();
        FreemarkerInit.processTamplate(out,new HashMap<>(),"login.html",this.getClass());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
            String logout = req.getParameter("logout");

            String email = req.getParameter("email");
            String pass = req.getParameter("password");

            UsersDAO usersDAO = new UsersDAO();
            if (logout != null) {
                new CoockiesUtil().kill(req.getCookies(), resp);
                resp.sendRedirect("/login");
            } else if (usersDAO.existByEmailAndPass(email, pass)) {
                Cookie cookie = new Cookie("userID",
                        usersDAO.getByEmailAndPass(email, pass).getId().toString());
                resp.addCookie(cookie);
                resp.sendRedirect("/users");
            } else {
                resp.sendRedirect("login");
            }
    }
}
