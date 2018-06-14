package ua.danit.controllers;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class UsersServlet extends HttpServlet {
    private HashMap<Integer, String[]> users = new HashMap<>();

    public UsersServlet() {
        String[] mh = {"Mrs.Hudson",
                "https://pbs.twimg.com/profile_images/1783390898/twitter_av_400x400.png"};
        users.put(1, mh);
        String[] ye = {"Your Ex",
                "https://i.pinimg.com/originals/b0/d2/e6/b0d2e6d8d83931bf586f83d4ed189c0a.jpg"};
        users.put(2,ye);
        String[] sm = {"Stifler's Mom",
                "https://pbs.twimg.com/profile_images/989211028692127744/tWR4WHNC_400x400.jpg"};
        users.put(3, sm);
        String[] ob = {"Original Ba XX",
                "http://i57.beon.ru/53/81/228153/4/11191004/33.jpeg"};
        users.put(4, ob);
        String[] nf = {"Not First Woman In Space",
                "https://airandspace.si.edu/sites/default/files/styles" +
                        "/callout_half/public/images/news/7462h.jpg?itok=S6nexORm"};
        users.put(5, nf);
    }


    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        String appDir = System.getProperty("user.dir");
        cfg.setDirectoryForTemplateLoading(new File(appDir + "/lib/html"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);

        Map<String, String> map = new HashMap<>();
        for(Map.Entry<Integer, String[]> entry : users.entrySet()) {
            String[] value = entry.getValue();
            map.put("name",value[0]);
            map.put("imgURL",value[1]);
        }

        Template tmpl = cfg.getTemplate("users.html");
        Writer out = resp.getWriter();
        try {
            tmpl.process(map, out);
        } catch (TemplateException e1) {
            e1.printStackTrace();
        }
        out.write(tmpl.toString());
    }
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("<h2 align=\"center\">Not now</h2>");
	}
}
