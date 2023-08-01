/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.CourseOffered;
import Model.EDay;
import Model.Program;
import Model.Taught_By;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sokeng Paul (AG)
 */
public class CRUDTaughtBy {
    public static void deleteTaughtBy(Program p, String d, CourseOffered courseOff){
        Connection con = DBConnection.getConnection();
        try{
            String req = "DELETE  "
                    + "FROM taugh_by "
                    + "WHERE id_course_offered = "+courseOff.getId()+" and id_program = "+p.getId()+" and date_course = '"+d+"';";
            PreparedStatement stm = con.prepareStatement(req);
            stm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void updateTaughtBy(Taught_By tby){
        Connection con = DBConnection.getConnection();
        try{
            String req = "UPDATE taugh_by t " 
                    +"SET t.id_room = "+tby.getIdRoom()+", t.start_time = '"+tby.getStartTime()+", "
                    + "t.end_time = '"+tby.getEndTime()+"', t.day_course = '"+tby.getCourseDay()+", t.date_course = '"+tby.getCourseDate()+"' " 
                    +"WHERE t.id_faculty = ? and t.id_course_offered = ? and t.id_program = ? ";
                    //+ "and t.id_room = ? and t.start_time = ? and t.end_time = ? and t.day_course =? and t.date_course = ?;";
            System.out.println(req);
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, tby.getIdFaculty());
            stm.setInt(2, tby.getIdCourseOffered());
            stm.setInt(3, tby.getIdProgram());
            stm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }        
    }
    
    public static void insertTaughtBy(int facultyId, int courseOffId, int programId, EDay day){
        Connection con = DBConnection.getConnection();
        try{            
            PreparedStatement stm = con.prepareStatement("INSERT INTO taugh_by (id_faculty, id_course_offered, id_program, day_course) values (?,?,?,?);");
            stm.setInt(1, facultyId);
            stm.setInt(2, courseOffId);
            stm.setInt(3, programId);
            stm.setString(7, day.getCode());
            stm.executeUpdate();            
        } catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CRUDStudentCourse.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void insertTaughtBy2(CourseOffered courseOff, Taught_By tby, Program p) {
            Connection con = DBConnection.getConnection();          
            try {
                con.setAutoCommit(false);
                PreparedStatement stm = con.prepareStatement("INSERT INTO taugh_by(id_faculty, id_course_offered, id_program, id_room, start_time, end_time,day_course,sem_name,sem_year,date_course) values (?,?);");
                stm.setInt(1,courseOff.getId_lecturer());
                stm.setInt(2,courseOff.getId());
                stm.setInt(3,p.getId());
                stm.setInt(4,Tools.getRoomID(tby.getPlaceName().getName()));
                stm.setString(5, tby.getStartTime());
                stm.setString(6, tby.getEndTime());
                stm.setString(7, tby.getCourseDay().getCode());
                //stm.setString(8,type_faculty);
                stm.setString(9,courseOff.getCourse_semester());
                stm.setInt(10,courseOff.getCourse_year());
                stm.setString(11, tby.getCourseDate());
                stm.executeUpdate();
                con.commit();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CRUDStudentCourse.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
}
