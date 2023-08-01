/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Student_Attendance;
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
public class CRUDStudentAttendance {

    public static void insertAttendances(Student_Attendance sat) {
        Connection con = DBConnection.getConnection();
        try {
            String req = "UPDATE student_attendance "
                    + "SET presence = ? "
                    + "WHERE id_student = " + sat.getId_student() + " and id_schedule = " + sat.getId_schedule() + ";";
            PreparedStatement stm2 = con.prepareStatement(req);
            stm2.setString(1, sat.getPresence());
            stm2.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CRUDTimeSlot.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void deleteAttendances(int studentId, int scheduleId) {
        Connection con = DBConnection.getConnection();
        try {
            String req = "DELETE "
                    + "FROM student_attendance "
                    + "WHERE id_student = " + studentId + " and id_schedule = " + scheduleId + ";";
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

    public static void deleteAttendancesCourse(int studentId, int co) {
        Connection con = DBConnection.getConnection();
        try {
            String req1 = "select distinct id from schedule where id_course_offered = " + co + ";";

            PreparedStatement stm1 = con.prepareStatement(req1);
            ResultSet result = stm1.executeQuery();
            int idSchedule;
            while (result.next()) {
                idSchedule = result.getInt("id");
                String req = "DELETE "
                        + "FROM student_attendance "
                        + "WHERE id_student = " + studentId + " and id_schedule = " + idSchedule + ";";
                PreparedStatement stm = con.prepareStatement(req);
                stm.executeUpdate();
            }

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
}
