/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Control.DBConnection;
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
public class StudentProgress implements Comparable<StudentProgress> {

    private int id_student;
    private int id_level;
    private int id_semester;
    private String semester;
    private String level;

    public StudentProgress() {
    }

    public StudentProgress(int id_student, int id_level, int id_semester) {
        this.id_student = id_student;
        this.id_level = id_level;
        this.id_semester = id_semester;
        semester = getSemester();
        level = getLevel();
    }

    public int getId_student() {
        return id_student;
    }

    public void setId_student(int id_student) {
        this.id_student = id_student;
    }

    public int getId_level() {
        return id_level;
    }

    public void setId_level(int id_level) {
        this.id_level = id_level;
    }

    public int getId_semester() {
        return id_semester;
    }

    public void setId_semester(int id_semester) {
        this.id_semester = id_semester;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.id_student;
        hash = 71 * hash + this.id_level;
        hash = 71 * hash + this.id_semester;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StudentProgress other = (StudentProgress) obj;
        if (this.id_student != other.id_student) {
            return false;
        }
        if (this.id_level != other.id_level) {
            return false;
        }
        if (this.id_semester != other.id_semester) {
            return false;
        }
        return true;
    }

    public String getLevel() {
        Connection con = DBConnection.getConnection();
        try {
            String req = "select * from schoollevel where id = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, id_level);
            ResultSet result = stm.executeQuery();
            while (result.next()) {
                return result.getString("name");
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentProgress.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentProgress.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "";
    }

    public String getSemester() {
        Connection con = DBConnection.getConnection();
        try {
            String req = "select * from semester where id = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, id_semester);
            ResultSet result = stm.executeQuery();
            while (result.next()) {
                return result.getString("name") + " " + result.getInt("s_year");
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentProgress.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentProgress.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "";
    }

    @Override
    public int compareTo(StudentProgress o) {
        if (id_semester > o.id_semester) {
            return 1;
        } else if (id_semester < o.id_semester) {
            return -1;
        }
        return 0;

    }
}
