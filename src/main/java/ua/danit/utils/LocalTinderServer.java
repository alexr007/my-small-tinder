package ua.danit.utils;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ua.danit.controllers.LikedServlet;
import ua.danit.controllers.MessagesServlet;
import ua.danit.controllers.StylesServlet;
import ua.danit.controllers.UsersServlet;
import ua.danit.dao.LikedDAO;
import ua.danit.dao.UsersDAO;

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
			}});
			start();
			join();
		}};
	}
}