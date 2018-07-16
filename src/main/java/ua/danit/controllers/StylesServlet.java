package ua.danit.controllers;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class StylesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String url = req.getPathInfo();
        url = url.substring(1,url.length());
        InputStream resource = this.getClass().getClassLoader().getResourceAsStream(url);

        ServletOutputStream outputStream = resp.getOutputStream();
        IOUtils.copy(resource, outputStream);
        resource.close();
        outputStream.close();
    }
}