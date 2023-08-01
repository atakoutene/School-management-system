/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Semester;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sokeng Paul (AG)
 */
public class LoadSemester {
    public static CRUDSemester loadSemFromDB() {
        Connection con = DBConnection.getConnection();
        try {
            CRUDSemester l = new CRUDSemester();
            String req = "select s.id as semid, s.name as semname, s.s_year as semyear "
                    + "from semester s";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery(req);
            int id,s_year;
            String name;
            while (result.next()) {
                id = result.getInt("semid");
                name = result.getString("semname");
                s_year = result.getInt("semyear");
                l.addSemester(new Semester(id,name,s_year));
            }
            return l;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoadSemester.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
