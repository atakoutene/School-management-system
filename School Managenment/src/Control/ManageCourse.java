/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Course;
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
 * @author HERMAN
 */
public class ManageCourse {

    public static CRUDCourse loadCourseFromDB() {
        try(Connection con = DBConnection.getConnection();) {
            CRUDCourse l = new CRUDCourse();
            String req = "select * "
                    + "from Course;";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery(req);
            int id, hours;
            String title, code, description;
            while (result.next()) {
                id = result.getInt("id");
                title = result.getString("title");
                code = result.getString("code");
                description = result.getString("description");
                hours = result.getInt("hours");
                Course c = new Course(id, code, title, description, hours);
                c.setPrerequisite(getPrerequisite(id));
                l.addCourse(c);
            }
            return l;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Course getCourse(int id) { 
        try(Connection con = DBConnection.getConnection();) {
            CRUDCourse l = new CRUDCourse();
            String req = "select * "
                    + "from course  where id = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, id);
            ResultSet result = stm.executeQuery();
            int i, hours;
            String title, code, description;
            while (result.next()) {
                i = result.getInt("id");
                title = result.getString("title");
                code = result.getString("code");
                description = result.getString("description");
                hours = result.getInt("hours");
                Course c = new Course(i, code, title, description, hours);
                c.setPrerequisite(getPrerequisite(i));
                return c;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Course> getPrerequisite(int id_course) {
        ArrayList<Course> list = new ArrayList<>();
        
        try (Connection con = DBConnection.getConnection();){
            String req2 = "select * "
                    + "from course_preq  where id_course = ?;";
            PreparedStatement stm2 = con.prepareStatement(req2);
            stm2.setInt(1, id_course);
            ResultSet result2 = stm2.executeQuery();
            int i;
            while (result2.next()) {
                i = result2.getInt("id_prereq");
                list.add(getCourse(i));
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(ManageCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static void deletePrequisite(int id_course) {
        try (Connection con = DBConnection.getConnection();){
            String req2 = "delete "
                    + "from course_preq  where id_course = ?;";
            PreparedStatement stm2 = con.prepareStatement(req2);
            stm2.setInt(1, id_course);
            stm2.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ManageCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void deleteCourse(Course c) {
        deletePrequisite(c.getId());
        try(Connection con = DBConnection.getConnection();) {
            String req2 = "delete "
                    + "from course where id = ?;";
            PreparedStatement stm2 = con.prepareStatement(req2);
            stm2.setInt(1, c.getId());
            stm2.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ManageCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ObservableList<Course> serachCourse(ObservableList<Course> list, String y) {
        ObservableList<Course> l = FXCollections.observableArrayList();
        for (Course c : list) {
            if ((c.getCode().toLowerCase().contains(y.toLowerCase()))
                    || (c.getTitle().toLowerCase().contains(y.toLowerCase()))) {
                l.add(c);
            }
        }
        return l;
    }
}
