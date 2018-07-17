package ua.danit.controllers;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import ua.danit.dao.LikedDAO;
import ua.danit.dao.MessagesDAO;
import ua.danit.dao.UsersDAO;
import ua.danit.model.Message;
import ua.danit.model.User;
import ua.danit.utils.FreemarkerInit;
import ua.danit.utils.CoockiesUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class MessagesServlet extends HttpServlet {
    private final UsersDAO users;
    private final MessagesDAO messagesDAO;

    public MessagesServlet(UsersDAO userDAO, MessagesDAO messagesDAO) {
        this.messagesDAO = messagesDAO;
        this.users = userDAO;
    }


    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long myID = Long.valueOf(new CoockiesUtil().getID(req.getCookies()));

        FreemarkerInit fm = new FreemarkerInit();

        Map<String, Object> map = new HashMap<>();
        User user = createUserFromURI(req);
        map.put("me",users.get(myID));
        map.put("user", user);
        map.put("messages", messagesDAO.getByFromTo(myID, user.getId()));

        PrintWriter out = resp.getWriter();
        FreemarkerInit.processTamplate(out,map,"chat.html",this.getClass());
    }

    @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String text = req.getParameter("textMSG");
        Long myID = Long.valueOf(new CoockiesUtil().getID(req.getCookies()));

        Message msg = new Message();
        User user = createUserFromURI(req);

        msg.setText(text);
        msg.setMessageTime(new Timestamp(System.currentTimeMillis()));
        msg.setSender(myID);
        msg.setRecipient(user.getId());

        messagesDAO.save(msg);

        FreemarkerInit fm = new FreemarkerInit();

        Map<String, Object> map = new HashMap<>();
        map.put("me",users.get(myID));
        map.put("user", user);
        map.put("messages", messagesDAO.getByFromTo(myID,user.getId()));

        PrintWriter out = resp.getWriter();
        FreemarkerInit.processTamplate(out,map,"chat.html",this.getClass());
	}

    private User createUserFromURI(HttpServletRequest req) {
        String[] uriParam = req.getRequestURI().split("/");
        Long userID = Long.valueOf(uriParam[uriParam.length -1]);
        return users.get(userID);
    }
}
