/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Person;
import Model.SchoolLevel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author metch
 */
public class CRUDPerson {
    public static void deletePerson(int id){
        Connection con = DBConnection.getConnection();
        int id_date = 0;
        try {
            String req0 = "select id_date_birth from person where id = "+id+";";
            PreparedStatement stm0 = con.prepareStatement(req0);
            ResultSet result = stm0.executeQuery();
            if(result.next()){
                id_date =  result.getInt("id_date_birth");
            }
            con.setAutoCommit(false);
            String req1 = "delete from person "
                    + "where id = " + id + ";";
            PreparedStatement stm1 = con.prepareStatement(req1);
            stm1.executeUpdate();
            String req2 = "delete from mydate "
                    + "where id = " + id_date + ";";
            PreparedStatement stm2 = con.prepareStatement(req2);
            stm2.executeUpdate();
            con.commit();
            
        } catch (SQLException ex) {
            Logger.getLogger(CRUDPerson.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CRUDPerson.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
