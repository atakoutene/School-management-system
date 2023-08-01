/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.ETestType;
import Model.TakeTest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

/**
 *
 * @author metch
 */
public class CRUDTest {

    public static void insertTest(Calendar calendar, ETestType testType, int course_offeredId) {
        Connection con = DBConnection.getConnection();
        try {
            String req1 = "INSERT INTO test(test_date, test_type, course_offered_id) VALUES (?,?,?); ";
            PreparedStatement stm1 = con.prepareStatement(req1);
            java.sql.Date javaSqlDate = new java.sql.Date(calendar.getTime().getTime());
            stm1.setDate(1, javaSqlDate);
            stm1.setString(2, testType.getCode());
            stm1.setInt(3, course_offeredId);
            stm1.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CRUDTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CRUDTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void insertStudetntTestGrades(Calendar calendar, ETestType testType, int course_offeredId,
        ObservableList<TakeTest> list) {
        Connection con = DBConnection.getConnection();
        try {
            con.setAutoCommit(false);
            java.sql.Date javaSqlDate = new java.sql.Date(calendar.getTime().getTime());
            String req1 = "INSERT INTO test(test_date, test_type, course_offered_id) VALUES ('" + javaSqlDate + "','" + testType.getCode() + "'," + course_offeredId + "); ";
            Statement stm1 = con.createStatement();
            stm1.executeUpdate(req1, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stm1.getGeneratedKeys();
            int testId = 1;
            if (rs.next()) {
                testId = rs.getInt(1);
            }
            String req2 = "INSERT INTO take_test(id_test, id_student, ananonymous, score, lettergrade) VALUES (?,?,?,?,?); ";
            for (TakeTest test : list) {
                PreparedStatement stm2 = con.prepareStatement(req2);
                stm2.setInt(1, testId);
                stm2.setInt(2, test.getIdStudent());
                stm2.setString(3, test.getAnonymous());
                stm2.setDouble(4, test.getScore());
                stm2.setString(5, Tools.getLettergrade(test.getScore()));
                stm2.executeUpdate();
            }
            con.commit();
        } catch (SQLException ex) {
            Logger.getLogger(CRUDTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CRUDTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void updateTestGades(ObservableList<TakeTest> testGrades, Calendar calendar){
        if(testGrades == null || testGrades.isEmpty())
            return ;
        Connection con = DBConnection.getConnection();
        try {
            con.setAutoCommit(false);
            
            java.sql.Date javaSqlDate = new java.sql.Date(calendar.getTime().getTime());
            String req0 = "update test set test_date = ? where id = ?";
            PreparedStatement stm0 = con.prepareStatement(req0);
            stm0.setDate(1, javaSqlDate);
            stm0.setInt(2, testGrades.get(0).getIdTest());
            stm0.executeUpdate();
            
            String req = "update take_test set ananonymous = ?, score = ?, lettergrade = ? where id_test = ? and id_student = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            for(TakeTest t: testGrades){
                stm.setString(1, t.getAnonymous());
                stm.setDouble(2, t.getScore());
                stm.setString(3, String.valueOf(t.getLetterGrade()));
                stm.setInt(4, t.getIdTest());
                stm.setInt(5, t.getIdStudent());
                stm.executeUpdate();
            }
            con.commit();        
        }catch (SQLException ex) {
            Logger.getLogger(CRUDTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CRUDTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
