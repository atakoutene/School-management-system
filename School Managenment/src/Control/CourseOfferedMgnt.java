/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.CourseOffered;
import Model.Semester;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author HERMAN
 */
public class CourseOfferedMgnt {

    public static int saveCourseOffered(CourseOffered course, Semester sem) {
        Connection con = DBConnection.getConnection();
        try {
            Statement stm = con.createStatement();
            stm.executeUpdate("insert into course_offered(id_course, course_year, course_sem, id_semester) "
                    + "values (" + course.getCourse_id() + "," + course.getCourse_year() + ""
                    + ",'" + course.getCourse_semester() + "' ,"+sem.getId()+");", Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stm.getGeneratedKeys();
            int id_program = 0;
            if (rs.next()) {
                id_program = rs.getInt(1);
            }
            return id_program;

        } catch (SQLException ex) {
            Logger.getLogger(CourseOfferedMgnt.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CourseOfferedMgnt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return -1;
    }
    
    public static CourseOffered getCourseOffered(int courseOffId){
        Connection con = DBConnection.getConnection();
        CourseOffered c = null;
        try {
            String req = "select * from course_offered where id = "+courseOffId+";";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            String course_sem;
            int id, id_course,course_year, id_semester;
            while (result.next()) {
                id = result.getInt("id");
                id_course = result.getInt("id_course");
                course_year = result.getInt("course_year");
                course_sem = result.getString("course_sem");
                id_semester = result.getInt("id_semester");
                c = new CourseOffered(id, id_course, course_year, course_sem, "");
                c.setId_semester(id_semester);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDCourse.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CRUDCourse.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return c;
    }
    
    public static ObservableList<CourseOffered> getOpenings(int id_course){
        ObservableList<CourseOffered> l = FXCollections.observableArrayList();
        Connection con = DBConnection.getConnection();
        try {           
            String req = "select * "
                    + "from course_offered  where id_course = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, id_course);
            ResultSet result = stm.executeQuery();
            int i, id_c, course_year;
            String course_sem, syllabus;
            while(result.next()){
                i = result.getInt("id");
                id_c = result.getInt("id_course");
                course_year = result.getInt("course_year");
                course_sem = result.getString("course_sem");
                syllabus = result.getString("syllabus");
                String req2 ="select distinct f.title as title, p.name as name, tb.id_faculty as idFaculty, tb.type_faculty as type_faculty  "
                        + "from taugh_by tb, employee e, person p, faculty f, course_offered co "
                        + "where p.id = e.id_person and e.id = f.id_employee and tb.id_course_offered = co.id "
                        + "and f.id = tb.id_faculty and co.id = "+i+";";
                PreparedStatement stm2 = con.prepareStatement(req2);
                ResultSet result2 = stm2.executeQuery();
                CourseOffered co = new CourseOffered(i, id_course, course_year, course_sem, syllabus);
                 String lect = "", assist = "";
                while(result2.next()){
                    String styleL = result2.getString("type_faculty");
                    if(styleL.equalsIgnoreCase("Lecturer")){
                        lect = result2.getString("title") + " " + result2.getString("name");
                        co.setId_lecturer(result2.getInt("idFaculty"));
                    }else{
                        assist = result2.getString("title") + " " + result2.getString("name");
                        co.setId_assistant(result2.getInt("idFaculty"));
                    }                   
                }
                co.setLecturer(lect + " - "+assist);              
                l.add(co);
            }
            return l;
            
        } catch (SQLException ex) {
            Logger.getLogger(CourseOfferedMgnt.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CourseOfferedMgnt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return l;
    }
    
    public static void deleteCourseOpened(CourseOffered c, String path){
        Connection con = DBConnection.getConnection();
        try {
            con.setAutoCommit(false);
            String req2 = "delete  "
                    + "from taugh_by  where id_faculty = ? and id_course_offered = ? and sem_name = ? and sem_year  = ?;";
            PreparedStatement stm2 = con.prepareStatement(req2);
            stm2.setInt(1, c.getId_lecturer());
            stm2.setInt(2, c.getId());
            stm2.setString(3, c.getCourse_semester());
            stm2.setInt(4, c.getCourse_year());
            System.out.println(stm2);
            stm2.executeUpdate();
            stm2.setInt(1, c.getId_assistant());
            System.out.println(stm2);
            stm2.executeUpdate();
            String req = "delete  "
                    + "from course_offered  where id = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, c.getId());
            System.out.println(stm);
            stm.executeUpdate();
            con.commit();
            File file = new File(path);
            if(!file.delete())
                System.out.println("Deletion unsuccessful! The file might not exist or is corrupted.");
        } catch (SQLException ex) {
            Logger.getLogger(CourseOfferedMgnt.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CourseOfferedMgnt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void updateCourseOffered(CourseOffered co, int idOldLect, int idOldAssist, String courseSem, 
            int courseY, int programId, int id_semester){
        Connection con = DBConnection.getConnection();
        try {
           con.setAutoCommit(false);
            String req1 = "update course_offered  "
                    + " set course_year = ?, course_sem = ?, id_semester = ? "
                    + "where id = ? and id_course = ? and course_year = ? and course_sem = ?;";
            String req2 = "update taugh_by "
                    + " set id_faculty = ?, sem_year = ?, sem_name = ?"
                    + " where id_faculty = ? and id_course_offered = ?  and id_program = ? and sem_year = ? and sem_name = ?;";
            String req3 = "update taugh_by "
                    + " set id_faculty = ?, sem_year = ?, sem_name = ?"
                    + " where id_faculty = ? and id_course_offered = ?  and id_program = ? and sem_year = ? and sem_name = ?;";
            
            PreparedStatement stm1 = con.prepareStatement(req1);
            stm1.setInt(1, co.getCourse_year());
            stm1.setString(2, co.getCourse_semester());
            stm1.setInt(3, id_semester);
            stm1.setInt(4, co.getId());
            stm1.setInt(5, co.getCourse_id());
            stm1.setInt(6, courseY);
            stm1.setString(7, courseSem);
            stm1.executeUpdate();
            PreparedStatement stm2 = con.prepareStatement(req2);
            PreparedStatement stm3 = con.prepareStatement(req3);
            stm2.setInt(1, co.getId_lecturer());
            stm2.setInt(2, co.getCourse_year());
            stm2.setString(3, co.getCourse_semester());
            stm2.setInt(4, idOldLect);
            stm2.setInt(5, co.getId());
            stm2.setInt(6, programId);
            stm2.setInt(7, courseY);
            stm2.setString(8, courseSem);
            stm2.executeUpdate();
            stm3.setInt(1, co.getId_assistant());
            stm3.setInt(2, co.getCourse_year());
            stm3.setString(3, co.getCourse_semester());
            stm3.setInt(4, idOldAssist);
            stm3.setInt(5, co.getId());
            stm3.setInt(6, programId);
            stm3.setInt(7, courseY);
            stm3.setString(8, courseSem);
            stm3.executeUpdate();
            System.out.println(stm1);
            System.out.println(stm2);
            System.out.println(stm3);
            con.commit();
            
        }catch (SQLException ex) {
            Logger.getLogger(CourseOfferedMgnt.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CourseOfferedMgnt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
