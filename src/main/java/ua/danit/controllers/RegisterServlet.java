package ua.danit.controllers;

import freemarker.template.Template;
import ua.danit.dao.UsersDAO;
import ua.danit.model.User;
import ua.danit.utils.FreemarkerInit;
import ua.danit.utils.GeneratorID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        FreemarkerInit fm = new FreemarkerInit();

        Template tmpl = fm.getCfg().getTemplate("register.html");
        resp.getWriter().write(tmpl.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String email = req.getParameter("email");
        String pass = req.getParameter("password");
        int gender = "male".equals(req.getParameter("gender")) ? 1 : 0;
        String imgURL = req.getParameter("imgURL");
        String name = req.getParameter("name");

        UsersDAO usersDAO = new UsersDAO();
        usersDAO.save(new User(
                Long.valueOf(new GeneratorID().generateNewID()),
                name,
                gender,
                imgURL,
                pass,
                email
        ));
        Cookie cookie = new Cookie("userID",
                usersDAO.getByEmailAndPass(email, pass).getId().toString());

        resp.addCookie(cookie);
        resp.sendRedirect("/users");


    }
}
