/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package databasecontroller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Phil
 */
public class DBHelper {
    // JDBC driver name and database URL

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/";
    //  Database credentials
    static final String USER = "root";
    static final String PASS = "qiushen@83";

    public static Connection getConnectionFromCertainDB(String dbName) {

        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL + dbName, USER, PASS);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return conn;
    }
}

