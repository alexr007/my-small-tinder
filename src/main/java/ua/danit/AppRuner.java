package ua.danit;

import ua.danit.utils.Choice;
import ua.danit.utils.LocalTinderServer;

public class AppRuner {
	public static void main(String[] args) throws Exception {
		Choice choice = new Choice();
		LocalTinderServer.start();
	}
}
