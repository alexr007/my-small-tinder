package ua.danit.controllers;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import ua.danit.dao.LikedDAO;
import ua.danit.dao.UsersDAO;
import ua.danit.model.Yamnyk_liked;
import ua.danit.utils.FreemarkerInit;
import ua.danit.utils.GetFromCoockies;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class UsersServlet extends HttpServlet {
    private final UsersDAO users;
    private final LikedDAO likedDAO;
    private static int counter = 0;

    public UsersServlet(UsersDAO userDAO, LikedDAO likedDAO) {
        this.users = userDAO;
        this.likedDAO = likedDAO;
    }


    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = new GetFromCoockies().getID(req.getCookies());
        Long myID = Long.valueOf(id);
        FreemarkerInit fm = new FreemarkerInit();

        Map<String, String> map = new HashMap<>();
        while(likedDAO.hasBeenLiked(myID, users.getAll().get(counter).getId())){
            counter++;
        }
        map.put("name", users.getAll().get(counter).getName());
        map.put("id", users.getAll().get(counter).getId().toString() );
        map.put("imgURL", users.getAll().get(counter).getImgURL());

        Template tmpl = fm.getCfg().getTemplate("like-page.html");
        Writer out = resp.getWriter();
        try {
            tmpl.process(map, out);
            counter++;
        } catch (TemplateException e1) {
            e1.printStackTrace();
        }
    }
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String liked = req.getParameter("liked");
        Long myID = Long.valueOf(new GetFromCoockies().getID(req.getCookies()));

        if(liked!=null){
            if(likedDAO.hasBeenLiked(myID, Long.valueOf(liked))){
                Yamnyk_liked lkd = new Yamnyk_liked();
                lkd.setLike_id(Long.valueOf(liked));
                lkd.setWho(myID);
                lkd.setWhom(Long.valueOf(liked));
                lkd.setTime(new Timestamp(System.currentTimeMillis()));
                likedDAO.update(lkd);
            } else {
                Yamnyk_liked lkd = new Yamnyk_liked();
                lkd.setLike_id(Long.valueOf(liked));
                lkd.setWho(myID);
                lkd.setWhom(Long.valueOf(liked));
                lkd.setTime(new Timestamp(System.currentTimeMillis()));
                likedDAO.save(lkd);
            }
        }

        FreemarkerInit fm = new FreemarkerInit();

        if(counter == users.getAll().size()){
            resp.sendRedirect("/liked");
            counter = 0;
        }

        Map<String, String> map = new HashMap<>();

        while(likedDAO.hasBeenLiked(myID, users.getAll().get(counter).getId())){
            counter++;
        }

        map.put("name", users.getAll().get(counter).getName());
        map.put("id", users.getAll().get(counter).getId().toString());
        map.put("imgURL", users.getAll().get(counter).getImgURL());

        Template tmpl = fm.getCfg().getTemplate("like-page.html");
        Writer out = resp.getWriter();
        try {
            tmpl.process(map, out);
            counter++;
        } catch (TemplateException e1) {
            e1.printStackTrace();
        }
	}
}
