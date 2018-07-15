package ua.danit.filter;

import ua.danit.dao.UsersDAO;
import ua.danit.utils.CoockiesUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if(req.getCookies() != null){
            //if cookies not empty, but no authorisation found
            if(new CoockiesUtil().getID(req.getCookies()) == null){
                resp.sendRedirect("/login");
            } else if(new UsersDAO().get(Long.valueOf(
                    new CoockiesUtil()
                            .getID(req.getCookies()))) == null){

                resp.sendRedirect("/login");

            } else {
                chain.doFilter(request, response);
            }

        } else {
            resp.sendRedirect("/login");
        }
    }

    @Override
    public void destroy() {

    }
}
