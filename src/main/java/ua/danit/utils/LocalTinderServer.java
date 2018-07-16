package ua.danit.utils;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ua.danit.controllers.*;
import ua.danit.dao.LikedDAO;
import ua.danit.dao.UsersDAO;
import ua.danit.filter.LoginFilter;

import javax.servlet.DispatcherType;
import java.util.Arrays;
import java.util.EnumSet;

public class LocalTinderServer {
	public static void start(String port) throws Exception{
        final UsersDAO userDAO = new UsersDAO();
        final LikedDAO likedDAO = new LikedDAO();

		new Server(Integer.parseInt(port)){{
			setHandler(new ServletContextHandler(){{
				addServlet(new ServletHolder(new UsersServlet(userDAO, likedDAO)),
						"/users/*");
				addServlet(new ServletHolder(new LikedServlet(userDAO, likedDAO)),
						"/liked/*");
				addServlet(new ServletHolder(new MessagesServlet(userDAO)),
						"/messages/*");
				addServlet(new ServletHolder(new StylesServlet()), "/assets/*");
				addServlet(new ServletHolder(new LoginServlet()), "/login");
				addServlet(new ServletHolder(new RegisterServlet()), "/register");

                for(String path : Arrays.asList("/users/*", "/messages/*", "/liked/*")){

                    addFilter(LoginFilter.class, path,
                            EnumSet.of(DispatcherType.INCLUDE, DispatcherType.REQUEST));

                }

			}});
			start();
			join();
		}};
	}
}