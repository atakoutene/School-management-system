/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Course;
import Model.Semester;
import Model.Student_Course;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Sokeng Paul (AG)
 */
public class CRUDStudentCourse {

    private static boolean validatePrerequisite(int studentId, int courseId) {
        Connection con = DBConnection.getConnection();
        try {
            String req = "SELECT distinct s.id as studentId, c.id as courseID, c.code as courseCode, c.title as courseTitle, sc.grade as grade, pc.passing_grade as passGrade, sc.score as letterGrade "
                    + "FROM student_course sc, student s, course_offered co, course c, program_course pc "
                    + "WHERE s.id = sc.id_student and c.id = co.id_course and co.id = sc.id_course_offered and c.id = pc.id_course and sc.score <= pc.passing_grade and s.id = " + studentId + " and c.id = " + courseId + ";";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            if(result.next())
                return true;
        } catch (SQLException ex) {
            Logger.getLogger(CRUDCourse.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CRUDCourse.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    public static boolean hasValidatedPreReq(int studentId, ObservableList<Course> courses){
        for(Course c: courses){
            if(!validatePrerequisite(studentId, c.getId()))
                return false;
        }
        return true;
    }
    
    public static void deleteStudentCourseSem(int studId, int coId, int semId){
        Connection con = DBConnection.getConnection();
        try{
            String req = "DELETE  "
                    + "FROM student_course "
                    + "WHERE id_student = "+studId+" and id_course_offered = "+coId+" and id_semester = "+semId+";";
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
    
    public static void updateStudentCourseSem(Student_Course sc, Semester sem){
        Connection con = DBConnection.getConnection();
        sc.setGrade(Tools.getLettergrade(sc.getScore()));
//        System.out.println(sc.getIdStudent());
//        System.out.println(sc.getIdCourseOffered());
//        System.out.println(sem.getId());
        try{
            String req = "UPDATE student_course s " 
                    +"SET s.grade = "+sc.getScore()+", s.score = '"+sc.getGrade()+"'"
                    + ", s.decision = '"+getDecision(sc)+"' " 
                    +"WHERE s.id_student = ? and s.id_course_offered = ? and s.id_semester = ? ;";
            System.out.println(req);
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, sc.getIdStudent());
            stm.setInt(2, sc.getIdCourseOffered());
            stm.setInt(3, sem.getId());
            stm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    public static void insertStudentCourseSem(int studId, int coId, int semId){
        Connection con = DBConnection.getConnection();
        try{            
            PreparedStatement stm = con.prepareStatement("INSERT INTO student_course (id_student, id_course_offered, id_semester) values (?,?,?); ");
            stm.setInt(1, studId);
            stm.setInt(2, coId);
            stm.setInt(3, semId);
            stm.executeUpdate();  
            Tools.checkAndRegister(studId, semId);
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
    
    public static int countCredits(ObservableList<Student_Course> list){
        int total = 0;
        for(int i=0; i<list.size();i++){
            total += list.get(i).getNb_credits();
        }
        System.out.println(total);
        return total;
    }
    
    
    public static String getDecision(Student_Course sc){
        if(sc.getPassing_grade().compareTo(sc.getGrade()) >= 0)
            return "Passed";
        else
            return "Failed";       
    }
    
    public static ObservableList<Student_Course> searchposibilitie(ObservableList<Student_Course> initList, String y) {
        ArrayList<Student_Course> list = new ArrayList<>();
        for (Student_Course sc : initList) {
            if (sc.getSemester().toLowerCase().contains(y.toLowerCase())) {
                list.add(sc);
            }
        }
        return FXCollections.observableArrayList(list);
    }
    
    

}
