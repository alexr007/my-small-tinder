package ua.danit.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class FreemarkerInit {
    private Configuration cfg;
    private String appDir = System.getProperty("user.dir");

    public static void processTamplate(PrintWriter writer, Map<String, Object> var, String tmplName, Class servletClass) throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        cfg.setClassLoaderForTemplateLoading(servletClass.getClassLoader(), "/servlet");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        Template tmplt = cfg.getTemplate(tmplName);

        try {
            tmplt.process(var, writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
