/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migration;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author HERMAN
 */
public class DBConnection2 {

    private static Connection connection = null;

    //PreparedStatement prestatement = null;
    public static Connection getConnection() {
        try {

            String url = "jdbc:mysql://localhost/schoolmanagement2";
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, "root", "herman24");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return connection;
    }

    public static void majProgramCourseTable() {

    }
}
