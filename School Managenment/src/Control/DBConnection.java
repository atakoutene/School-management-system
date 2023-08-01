/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 *
 * @author HERMAN
 */
public class DBConnection {

    private static Connection connection = null;

    //PreparedStatement prestatement = null;
    public static Connection getConnection() {
        return connect();
    }

    public static Connection connect() {
        InputStream stream = null;
        String user = "", password = "", url = "";

        try {
//            stream = DBConnection.class.getResource("databaseconnect.dat").openStream();

            stream = new FileInputStream("config.properties");
            Properties prop = new Properties();

            if (stream == null) {
                System.out.println("Sorry, unable to find config.properties");
                return null;
            }

            //load a properties file from class path, inside static method
            prop.load(stream);
            user = prop.getProperty("db.user");
            password = prop.getProperty("db.password");
            url = prop.getProperty("db.url");
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return connection;
    }

    public static void writeToConfigFile(String user, String password, String url) {
        try (OutputStream output = new FileOutputStream("config.properties")) {

            Properties prop = new Properties();

            // set the properties value
            prop.setProperty("db.url", url);
            prop.setProperty("db.user", user);
            prop.setProperty("db.password", password);

            // save properties to project root folder
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
