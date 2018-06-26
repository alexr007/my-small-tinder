package ua.danit.controllers;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import ua.danit.dao.LikedDAO;
import ua.danit.dao.UsersDAO;
import ua.danit.model.Yamnyk_liked;
import ua.danit.model.Yamnyk_users;
import ua.danit.utils.FreemarkerInit;
import ua.danit.utils.CoockiesUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.Timestamp;
import java.util.ArrayList;
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
        String id = new CoockiesUtil().getID(req.getCookies());
        Long myID = Long.valueOf(id);
        FreemarkerInit fm = new FreemarkerInit();
        Yamnyk_users me = users.get(myID);

        Map<String, String> map = new HashMap<>();
        /*while(likedDAO.hasBeenLiked(myID, users.getAll().get(counter).getId())){
            counter++;
        }*/
        ArrayList<Yamnyk_users> unliked = likedDAO.getUnliked(myID,users.get(myID).getGender());
        map.put("name",
                unliked.get(counter).getName());
        map.put("id",
                unliked.get(counter).getId().toString());
        map.put("imgURL",
                unliked.get(counter).getImgURL());

        map.put("myName", users.get(myID).getName());

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
        Long myID = Long.valueOf(new CoockiesUtil().getID(req.getCookies()));
        int gender = users.get(myID).getGender();

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

        if(counter == likedDAO.getUnliked(myID, users.get(myID).getGender()).size()){
            resp.sendRedirect("/liked");
            counter = 0;
        }

        Map<String, String> map = new HashMap<>();

        /*while(likedDAO.hasBeenLiked(myID, users.getAll().get(counter).getId())){
            counter++;
        }*/

        map.put("name",
                likedDAO.getUnliked(myID,users.get(myID).getGender()).get(counter).getName());

        map.put("id",
                likedDAO.getUnliked(myID,users.get(myID).getGender()).get(counter).getId().toString());

        map.put("imgURL",
                likedDAO.getUnliked(myID,users.get(myID).getGender()).get(counter).getImgURL());

        map.put("myName", users.get(myID).getName());

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
