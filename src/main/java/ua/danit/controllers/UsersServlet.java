package ua.danit.controllers;

import ua.danit.dao.LikedDAO;
import ua.danit.dao.UsersDAO;
import ua.danit.model.Like;
import ua.danit.model.User;
import ua.danit.utils.FreemarkerInit;
import ua.danit.utils.CoockiesUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UsersServlet extends HttpServlet {
    private final UsersDAO usersDAO;
    private final LikedDAO likedDAO;
    private static int counter;

    public UsersServlet(UsersDAO userDAO, LikedDAO likedDAO) {
        this.usersDAO = userDAO;
        this.likedDAO = likedDAO;
    }

    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String id = new CoockiesUtil().getID(req.getCookies());
        Long myID = Long.valueOf(id);
        FreemarkerInit fm = new FreemarkerInit();
        User me = usersDAO.get(myID);

        counter = 0;
        Map<String, Object> map = new HashMap<>();
        ArrayList<User> unliked = likedDAO.getUnliked(myID, usersDAO.get(myID).getGender());
        map.put("name", unliked.get(counter).getName());
        map.put("id", unliked.get(counter).getId().toString());
        map.put("imgURL", unliked.get(counter).getImgURL());
        map.put("myName", usersDAO.get(myID).getName());

        PrintWriter out = resp.getWriter();
        FreemarkerInit.processTamplate(out,map,"like-page.html",this.getClass());
        counter++;
    }
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String liked = req.getParameter("liked");
        Long myID = Long.valueOf(new CoockiesUtil().getID(req.getCookies()));
        int gender = usersDAO.get(myID).getGender();

        if(liked!=null){
            if(likedDAO.hasBeenLiked(myID, Long.valueOf(liked))){
                Like lkd = new Like();
                lkd.setLike_id(Long.valueOf(liked));
                lkd.setWho(myID);
                lkd.setWhom(Long.valueOf(liked));
                lkd.setTime(new Timestamp(System.currentTimeMillis()));
                likedDAO.update(lkd);
            } else {
                Like lkd = new Like();
                lkd.setLike_id(Long.valueOf(liked));
                lkd.setWho(myID);
                lkd.setWhom(usersDAO.get(Long.valueOf(liked)).getId());
                lkd.setTime(new Timestamp(System.currentTimeMillis()));
                likedDAO.save(lkd);
            }
        }

        FreemarkerInit fm = new FreemarkerInit();

        if(counter == likedDAO.getUnliked(myID, usersDAO.get(myID).getGender()).size()){
            resp.sendRedirect("/liked");
            counter = 0;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("name", likedDAO.getUnliked(myID, usersDAO.get(myID).getGender()).get(counter).getName());
        map.put("id", likedDAO.getUnliked(myID, usersDAO.get(myID).getGender()).get(counter).getId().toString());
        map.put("imgURL", likedDAO.getUnliked(myID, usersDAO.get(myID).getGender()).get(counter).getImgURL());
        map.put("myName", usersDAO.get(myID).getName());

        PrintWriter out = resp.getWriter();
        FreemarkerInit.processTamplate(out,map,"like-page.html",this.getClass());
        counter++;
	}
}
