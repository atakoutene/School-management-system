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
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Herman Mekontso
 */
public class CRUDCourse {

    private ArrayList<Course> list;

    public CRUDCourse() {
        list = new ArrayList<>();
    }

    public CRUDCourse(ArrayList<Course> list) {
        this.list = list;
    }

    public ArrayList<Course> getList() {
        return list;
    }

    public void setList(ArrayList<Course> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "CRUDCourse{" + "list=" + list + '}';
    }

    public boolean searchpresence(String y) {
        boolean b = false;
        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).getCode().equalsIgnoreCase(y)) {
                System.out.println(y + " search presence" + this.list.get(i).getTitle());
                b = true;
                break;
            }
        }
        return b;
    }

    public void addCourse(Course b) {
        //System.out.println(b.getPhoneNumber()+"vgvjnb");
//        if (this.list == null) {
//            this.list.add(b);
//        } else if (this.searchpresence(b.getCode())) {
//            System.out.println("there is already a course with that code");
//        } else {
            this.list.add(b);
//        }

    }

    private void removeCourseDB(int courseId) {
        Connection con = DBConnection.getConnection();
        try {
            String req = "delete "
                    + "from course c"
                    + "where c.id = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, courseId);
            stm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void update(Course old, Course newCourse) {
        this.getList().remove(getCourseById(old.getId()));
        this.getList().add(newCourse);
        updateCourseDB(newCourse);
        Collections.sort(this.list);
    }

    private Course getCourseById(int id) {
        for(Course c : this.getList()) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }
    public static Course getCourse(int courseId) {
        Connection con = DBConnection.getConnection();
        Course c = null;
        try {
            String req = "select * from course where id = "+courseId+";";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            String code, title, description;
            int id, hrs;
            while (result.next()) {
                id = result.getInt("id");
                code = result.getString("code");
                title = result.getString("title");
                description = result.getString("description");
                hrs = result.getInt("hours");
                c = new Course(id, code, title, description, hrs);
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

    private void updateCourseDB(Course course) {
        Connection con = DBConnection.getConnection();
        try {
            String req = "update course c "
                    + "set c.code = ?,c.title = ?, c.description = ?, c.hours = ? "
                    + "where c.id = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setString(1, course.getCode());
            stm.setString(2, course.getTitle());
            stm.setString(3, course.getDescription());
            stm.setInt(4, course.getHours());
            stm.setInt(5, course.getId());
            stm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void delete(Course y) {
        if (this.searchpresence(y.getCode()) != true) {
            System.out.println("THIS COURSE DOES NOT EXIST ");
        } else {
            this.list.remove(y);
            removeCourseDB(y.getId());
        }

    }

    public CRUDCourse searchposibilities(String y) {
        CRUDCourse l;
        l = new CRUDCourse();
        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).getCode().toLowerCase().contains(y.toLowerCase())) {
                l.list.add(this.list.get(i));
            }
        }
        return l;
    }

    public static ObservableList<Course> searchposibilitie(ObservableList<Course> initList, String y) {
        ArrayList<Course> list = new ArrayList<>();
        for (int i = 0; i < initList.size(); i++) {
            if (initList.get(i).getCode().toLowerCase().contains(y.toLowerCase())) {
                list.add(initList.get(i));
            }
        }
        return FXCollections.observableArrayList(list);
    }
    
    public static ObservableList<Course> getPrerequisites(int courseid){
        ObservableList<Course> results = FXCollections.observableArrayList();
        Connection con = DBConnection.getConnection();
        try{
            String req = "SELECT c.code as coursecode, c.title as coursetitle, cp.id_prereq as courseid, "
                        + "c.description as coursedescription, c.hours as coursehours "
                        +"FROM course c, course_preq cp " 
                        +"WHERE c.id = cp.id_prereq and cp.id_course="+courseid+";"; 
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            int id,hours;
            String code,description,title;
            while(result.next()){
                id=result.getInt("courseid");
                hours=result.getInt("coursehours");
                code=result.getString("coursecode");
                description=result.getString("coursedescription");
                title=result.getString("coursetitle");
                results.add(new Course(id,code,title,description,hours));
            }
            //c.setPrerequisite(results);
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(CRUDCourse.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CRUDCourse.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

}
