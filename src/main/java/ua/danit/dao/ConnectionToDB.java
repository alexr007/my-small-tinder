package ua.danit.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDB {
    //amazon base
    private static final String DB_URL = "jdbc:mysql://danit.cukm9c6zpjo8.us-west-2.rds.amazonaws.com:3306/fs5";
    private static final String USERNAME = "fs5_user";
    private static final String USER_PASS = "bArceloNa";

    //postgresql
    /*private static final String DB_URL = "jdbc:postgresql://localhost:5432/my-small-tinder";
    private static final String USERNAME = "yamnyk";
    private static final String USER_PASS = "notAunicorN2018";*/

    protected Connection getConnection(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(DB_URL,USERNAME, USER_PASS);
        } catch (SQLException e){
            e.printStackTrace();
        }
         return connection;
    }
}
