package utils;

import controllers.UsersServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class LocalTinderServer {
	public static void start() throws Exception{
		//creating new instance of class Server from jetty lib
		//using anonymous class for not initialise any variables
		new Server(8080){{
			setHandler(new ServletContextHandler(){{
				//create endpoint for servlet without any variables
				addServlet(new ServletHolder(new UsersServlet()), "/users/*");
			}});
			start();
			join();
		}};
	}
}