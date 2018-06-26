package ua.danit.controllers;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import ua.danit.dao.LikedDAO;
import ua.danit.dao.UsersDAO;
import ua.danit.model.Yamnyk_users;
import ua.danit.utils.FreemarkerInit;
import ua.danit.utils.CoockiesUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LikedServlet extends HttpServlet {
    private final ArrayList<Yamnyk_users> users;
    private final LikedDAO likedDAO;

    public LikedServlet(UsersDAO userDAO, LikedDAO likedDAO) {
        this.users = userDAO.getAll();
        this.likedDAO = likedDAO;
    }


    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long myID = Long.valueOf(new CoockiesUtil().getID(req.getCookies()));

        FreemarkerInit fm = new FreemarkerInit();

        Map<String, Object> map = new HashMap<>();
        map.put("liked", likedDAO.getLiked(myID));
        map.put("users", users);
        map.put("myName", new UsersDAO().get(myID).getName());

        Template tmpl = fm.getCfg().getTemplate("people-list.html");
        Writer out = resp.getWriter();
        try {
            tmpl.process(map, out);
        } catch (TemplateException e1) {
            e1.printStackTrace();
        }
    }
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String prevLogin = req.getParameter("liked");

        FreemarkerInit fm = new FreemarkerInit();

        Template tmpl = fm.getCfg().getTemplate("people-list.html");
        resp.getWriter().write(tmpl.toString());
	}
}
