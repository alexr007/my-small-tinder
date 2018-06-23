package ua.danit.controllers;

import freemarker.template.Template;
import ua.danit.dao.UsersDAO;
import ua.danit.model.Yamnyk_users;
import ua.danit.utils.FreemarkerInit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FreemarkerInit fm = new FreemarkerInit();

        Template tmpl = fm.getCfg().getTemplate("login.html");
        resp.getWriter().write(tmpl.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String pass = req.getParameter("password");

        UsersDAO usersDAO = new UsersDAO();
        if(usersDAO.getByEmailAndPass(email, pass)){
            resp.sendRedirect("/users");
        } else {
            resp.sendRedirect("login");
        }
    }
}
