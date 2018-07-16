package ua.danit;

import ua.danit.utils.LocalTinderServer;

public class AppRuner {
	public static void main(String[] args) throws Exception {
		String port;
		port = args.length > 0 ? args[0] : "8081";

		LocalTinderServer.start(port);
	}
}