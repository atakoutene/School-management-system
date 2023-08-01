/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

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
public class CRUDJoinTables {

    public static SchoolLevel searchSchoolLev(int idStud, int idLev, int idSem) {
        Connection con = DBConnection.getConnection();
        try {
            String req = "select * from student_level where id_student = ? and id_level = ? and id_semester = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, idStud);
            stm.setInt(2, idLev);
            stm.setInt(3, idSem);
            ResultSet result = stm.executeQuery();
            return result.next() ? new SchoolLevel() : null;
        } catch (SQLException ex) {
            Logger.getLogger(CRUDJoinTables.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CRUDJoinTables.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    public static void insertOrUpdateSchoolLev(int idStud, int idLev, int idSem) {
        Connection con = DBConnection.getConnection();
        try {
            if (forUpdateGood(idStud, idSem)) {
                String req = "update student_level set id_level = ? where id_student = ? and id_semester = ?;";
                PreparedStatement stm = con.prepareStatement(req);
                stm.setInt(1, idLev);
                stm.setInt(2, idStud);
                stm.setInt(3, idSem);
                stm.executeUpdate();
                return;
            }
            String req = "insert into student_level values(?,?,?);";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, idStud);
            stm.setInt(2, idLev);
            stm.setInt(3, idSem);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CRUDJoinTables.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CRUDJoinTables.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static boolean forUpdateGood(int idStud, int idSem) {
        Connection con = DBConnection.getConnection();
        try {
            String req = "select * from student_level where id_student = ? and id_semester = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, idStud);
            stm.setInt(2, idSem);
            ResultSet result = stm.executeQuery();
            return result.next();
        } catch (SQLException ex) {
            Logger.getLogger(CRUDJoinTables.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CRUDJoinTables.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
}
