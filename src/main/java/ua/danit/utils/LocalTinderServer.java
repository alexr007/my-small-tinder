package ua.danit.utils;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ua.danit.controllers.*;
import ua.danit.dao.LikedDAO;
import ua.danit.dao.MessagesDAO;
import ua.danit.dao.UsersDAO;
import ua.danit.filter.LoginFilter;

import javax.servlet.DispatcherType;
import java.util.Arrays;
import java.util.EnumSet;

public class LocalTinderServer {
	public static void start(String port) throws Exception{
        final UsersDAO userDAO = new UsersDAO();
        final LikedDAO likedDAO = new LikedDAO();
		final MessagesDAO messagesDAO = new MessagesDAO();

		new Server(Integer.parseInt(port)){{
			setHandler(new ServletContextHandler(){{
				addServlet(new ServletHolder(new UsersServlet(userDAO, likedDAO)),
						"/users/*");
				addServlet(new ServletHolder(new LikedServlet(userDAO, likedDAO)),
						"/liked/*");
				addServlet(new ServletHolder(new MessagesServlet(userDAO, messagesDAO)),
						"/messages/*");
				addServlet(new ServletHolder(new StylesServlet()), "/assets/*");
				addServlet(new ServletHolder(new LoginServlet(userDAO)), "/login");
				addServlet(new ServletHolder(new RegisterServlet(userDAO)), "/register");

                addFilter(new FilterHolder(new LoginFilter(userDAO)),
						"/*",EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));

			}});
			start();
			join();
		}};
	}
}