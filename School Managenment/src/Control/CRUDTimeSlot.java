/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.TimeSlot;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sokeng Paul (AG)
 */
public class CRUDTimeSlot {
    public static void insertTimeSlot(TimeSlot ts){
        Connection con = DBConnection.getConnection();
        try{
            String req = "INSERT INTO timeslot(startTime, endTime, dateCourse, dayCourse) VALUES(?,?,?,?);";
            PreparedStatement stm2 = con.prepareStatement(req);
            stm2.setString(1, ts.getStartTime());
            stm2.setString(2, ts.getEndTime());
            stm2.setString(3, ts.getDateCourse());
            stm2.setString(4, ts.getDayCourse().toString());
            stm2.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CRUDTimeSlot.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
}
