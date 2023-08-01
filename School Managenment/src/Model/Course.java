/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HERMAN
 */
public class Course implements Comparable<Course>, Serializable, Cloneable {
    private int id;
    private int id_co;
    private String code;
    private String title;
    private String description;
    private int hours;
    private double nbCreditsForReport;
    private double gradeForReport;
    private char letterGradeForReport;
    private char passingGradeForReport;
    private List<Course> prerequisite = new ArrayList<>();
    
    public Course(){
        
    }

    public Course(int id, String code, String title, String description, int hours) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.description = description;
        this.hours = hours;
    }

    
    public List<Course> getPrerequisite() {
        return prerequisite;
    }

    public void setPrerequisite(List<Course> prerequisite) {
        this.prerequisite = prerequisite;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getId_co() {
        return id_co;
    }

    public void setId_co(int id_co) {
        this.id_co = id_co;
    }
    

    @Override
    public int compareTo(Course o) {
        if(id == o.id)
            return 0;
        else if(id > o.id)
            return 1;
        else
            return -1;
    }

    @Override
    public Object clone() {
        try {
            return super.clone(); //To change body of generated methods, choose Tools | Templates.
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
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
        final Course other = (Course) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if(code.equals("MATH 1113"))
            return code + " - " + title+ " (" + description+")";
        else
            return code + " - " + title;
    }

    public double getNbCreditsForReport() {
        return nbCreditsForReport;
    }

    public void setNbCreditsForReport(double nbCreditsForReport) {
        this.nbCreditsForReport = nbCreditsForReport;
    }

    public double getGradeForReport() {
        return gradeForReport;
    }

    public void setGradeForReport(double gradeForReport) {
        this.gradeForReport = gradeForReport;
    }

    public char getLetterGradeForReport() {
        return letterGradeForReport;
    }

    public void setLetterGradeForReport(char letterGradeForReport) {
        this.letterGradeForReport = letterGradeForReport;
    }

    public char getPassingGradeForReport() {
        return passingGradeForReport;
    }

    public void setPassingGradeForReport(char passingGradeForReport) {
        this.passingGradeForReport = passingGradeForReport;
    }
           
}
