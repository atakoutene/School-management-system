/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

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
public class MaxCredits {
    public static double getMaxCredits(){
        Connection con = DBConnection.getConnection();
        double output = 0.0;
        try {
            String req = "SELECT value from maxcredits;";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();     
            if (result.next()) {
                output = result.getDouble("value");
            }          
        } catch (SQLException ex) {
            Logger.getLogger(MaxCredits.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(MaxCredits.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return output;
    }
    public static void updateMaxCredits(double newVal){
        Connection con = DBConnection.getConnection();  
        try {
            String req = "update maxcredits set value = ? ;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setDouble(1, newVal);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MaxCredits.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(MaxCredits.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
