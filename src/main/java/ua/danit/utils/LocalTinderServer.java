package ua.danit.utils;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ua.danit.controllers.*;
import ua.danit.controllers.filter.LoginFilter;
import ua.danit.dao.LikedDAO;
import ua.danit.dao.UsersDAO;

import javax.servlet.DispatcherType;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public class LocalTinderServer {
	public static void start() throws Exception{
        final UsersDAO userDAO = new UsersDAO();
        final LikedDAO likedDAO = new LikedDAO();
		//creating new instance of class Server from jetty lib
		//using anonymous class for not initialise any variables
		new Server(8080){{
			setHandler(new ServletContextHandler(){{
				//create endpoints for servlets without any variables
				addServlet(new ServletHolder(new UsersServlet(userDAO, likedDAO)),
						"/users/*");
				addServlet(new ServletHolder(new LikedServlet(userDAO, likedDAO)),
						"/liked/*");
				addServlet(new ServletHolder(new MessagesServlet(userDAO, likedDAO)),
						"/messages/*");
				addServlet(new ServletHolder(new StylesServlet()), "/assets/*");
				addServlet(new ServletHolder(new LoginServlet()), "/login/*");

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