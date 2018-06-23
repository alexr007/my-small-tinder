package ua.danit.controllers;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import ua.danit.dao.LikedDAO;
import ua.danit.dao.MessagesDAO;
import ua.danit.dao.UsersDAO;
import ua.danit.model.Yamnyk_messages;
import ua.danit.model.Yamnyk_users;

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

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        String appDir = System.getProperty("user.dir");
        cfg.setDirectoryForTemplateLoading(new File(appDir
                + "/src/main/resources/static/"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);

        Map<String, Object> map = new HashMap<>();
        Yamnyk_users user = createUserFromURI(req);
        map.put("user", user);
        map.put("messages", new MessagesDAO().getByFromTo((long)123, user.getId()));

        Template tmpl = cfg.getTemplate("chat.html");
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

        Yamnyk_users user = createUserFromURI(req);

        MessagesDAO messagesDAO = new MessagesDAO();
        Yamnyk_messages msg = new Yamnyk_messages();

        msg.setText(text);
        msg.setMessageTime(new Timestamp(System.currentTimeMillis()));
        msg.setSender((long)123);
        msg.setRecipient(user.getId());

        messagesDAO.save(msg);

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        String appDir = System.getProperty("user.dir");
        cfg.setDirectoryForTemplateLoading(new File(appDir
                + "/src/main/resources/static/"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);

        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("sanded", new MessagesDAO().getByFromTo((long)123,user.getId()));

        Template tmpl = cfg.getTemplate("chat.html");
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
