package ua.danit.controllers;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import ua.danit.dao.LikedDAO;
import ua.danit.dao.MessagesDAO;
import ua.danit.dao.UsersDAO;
import ua.danit.model.Yamnyk_messages;
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
import java.util.HashMap;
import java.util.Map;

public class MessagesServlet extends HttpServlet {
    private final UsersDAO users;
    private final LikedDAO likedDAO;

    public MessagesServlet(UsersDAO userDAO, LikedDAO likedDAO) {
        this.users = userDAO;
        this.likedDAO = likedDAO;
    }


    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long myID = Long.valueOf(new CoockiesUtil().getID(req.getCookies()));

        FreemarkerInit fm = new FreemarkerInit();

        Map<String, Object> map = new HashMap<>();
        Yamnyk_users user = createUserFromURI(req);
        map.put("me",new UsersDAO().get(myID));
        map.put("user", user);
        map.put("messages", new MessagesDAO().getByFromTo(myID, user.getId()));

        Template tmpl = fm.getCfg().getTemplate("chat.html");
        Writer out = resp.getWriter();
        try {
            tmpl.process(map, out);
        } catch (TemplateException e1) {
            e1.printStackTrace();
        }
//        Template tmpl = cfg.getTemplate("messages.html");
//        Writer out = resp.getWriter();
//        out.write(tmpl.toString());
    }

    @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String text = req.getParameter("textMSG");
        Long myID = Long.valueOf(new CoockiesUtil().getID(req.getCookies()));

        Yamnyk_users user = createUserFromURI(req);

        MessagesDAO messagesDAO = new MessagesDAO();
        Yamnyk_messages msg = new Yamnyk_messages();

        msg.setText(text);
        msg.setMessageTime(new Timestamp(System.currentTimeMillis()));
        msg.setSender(myID);
        msg.setRecipient(user.getId());

        messagesDAO.save(msg);

        FreemarkerInit fm = new FreemarkerInit();

        Map<String, Object> map = new HashMap<>();
        map.put("me",new UsersDAO().get(myID));
        map.put("user", user);
        map.put("messages", new MessagesDAO().getByFromTo(myID,user.getId()));

        Template tmpl = fm.getCfg().getTemplate("chat.html");
        Writer out = resp.getWriter();
        try {
            tmpl.process(map, out);
        } catch (TemplateException e1) {
            e1.printStackTrace();
        }
	}

    private Yamnyk_users createUserFromURI(HttpServletRequest req) {
        String[] uriParam = req.getRequestURI().split("/");
        Long userID = Long.valueOf(uriParam[uriParam.length -1]);
        return users.get(userID);
    }
}
