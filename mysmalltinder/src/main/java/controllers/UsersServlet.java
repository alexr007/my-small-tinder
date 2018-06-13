package controllers;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

public class UsersServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
		String appDir = System.getProperty("user.dir");
		cfg.setDirectoryForTemplateLoading(new File(appDir + "/mysmalltinder/lib/html"));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);
		cfg.setWrapUncheckedExceptions(true);
		
		Template tmpl = cfg.getTemplate("users.html");
		resp.getWriter().write(String.valueOf(tmpl));
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().print("<h1 align=\"center\">YEAH!You pressed the button and it worked!</h1>");
	}
}
