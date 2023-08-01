/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Schedule;
import Model.Semester;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sokeng Paul (AG)
 */
public class CRUDSchedule {

    public static void deleteSchedule(int scheduleID, int roomID) {
        Connection con = DBConnection.getConnection();
        try {
            String req = "DELETE "
                    + "FROM schedule "
                    + "WHERE id = " + scheduleID + " and id_room = " + roomID + ";";
            PreparedStatement stm = con.prepareStatement(req);
            stm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void updateSchedule(Schedule schedule) {
        Connection con = DBConnection.getConnection();
        try {
            String req = "UPDATE schedule s "
                    + "SET s.id_room = " + schedule.getId_room() + ", s.id_course_offered = " + schedule.getId_course_offered() + ", "
                    + "s.id_time_slot = " + schedule.getId_time_slot() + " "
                    + "WHERE s.id = ?;";
//            System.out.println(req);
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, schedule.getId());
            stm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void insertSchedule(int courseOffId, int roomId, int timeslotId, Semester sem) {
        Connection con = DBConnection.getConnection();
        try {
            con.setAutoCommit(false);
            PreparedStatement stm = con.prepareStatement("INSERT INTO schedule(id_course_offered, id_room, id_time_slot) values (?,?,?);",
                    Statement.RETURN_GENERATED_KEYS);
            
            stm.setInt(1, courseOffId);
            stm.setInt(2, roomId);
            stm.setInt(3, timeslotId);
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            int id_schedule = 1;
            if (rs.next()) {
                id_schedule = rs.getInt(1);
            }
            String reqS = "select id_student from student_course where id_course_offered = "+courseOffId+""
                    + " and id_semester = "+sem.getId()+" ;";
            PreparedStatement sel = con.prepareStatement(reqS);
            ResultSet result = sel.executeQuery();
            String req = "insert into student_attendance(id_student, id_schedule) values (?,?);";
            PreparedStatement stm2 = con.prepareStatement(req);
            int studentId;
            while(result.next()){
                studentId = result.getInt("id_student");
                stm2.setInt(1, studentId);
                stm2.setInt(2, id_schedule);
                stm2.executeUpdate();
            }
            con.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CRUDSchedule.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
