package ua.danit.utils;

import ua.danit.controllers.UsersServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ua.danit.dao.UsersDAO;

public class LocalTinderServer {
	public static void start() throws Exception{
        final UsersDAO userDAO = new UsersDAO();
		//creating new instance of class Server from jetty lib
		//using anonymous class for not initialise any variables
		new Server(8080){{
			setHandler(new ServletContextHandler(){{
				//create endpoint for servlet without any variables
				addServlet(new ServletHolder(new UsersServlet(userDAO)), "/users/*");
			}});
			start();
			join();
		}};
	}
}