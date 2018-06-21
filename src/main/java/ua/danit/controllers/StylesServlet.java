package ua.danit.controllers;

import com.google.common.io.ByteStreams;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet(loadOnStartup = 1, urlPatterns = "/assets/*")
public class StylesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getPathInfo();
        URL file = getClass().getClassLoader().getResource(url);
        InputStream in = file.openStream();
        ServletOutputStream out = resp.getOutputStream();
        ByteStreams.copy(in, out);
        in.close();
        out.close();
    }
}
