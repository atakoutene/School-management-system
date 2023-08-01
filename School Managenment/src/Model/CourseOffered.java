/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Control.CRUDCourse;
import java.io.Serializable;

/**
 *
 * @author HERMAN
 */
public class CourseOffered implements Serializable, Cloneable, Comparable<CourseOffered>{
    private int id;
    private int course_id;
    private int course_year;
    private String course_semester;
    private String syllabus;
    private String lecturer;
    private int id_lecturer;
    private int id_assistant;
    private int id_semester;
    private Course c;
    
    
    public CourseOffered(){}

    public CourseOffered(int id, int course_id, int course_year, String course_semester, String syllabus) {
        this.id = id;
        this.course_id = course_id;
        this.course_year = course_year;
        this.course_semester = course_semester;
        this.syllabus = syllabus;
        c = CRUDCourse.getCourse(course_id);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getCourse_year() {
        return course_year;
    }

    public void setCourse_year(int course_year) {
        this.course_year = course_year;
    }

    public String getCourse_semester() {
        return course_semester;
    }

    public void setCourse_semester(String course_semester) {
        this.course_semester = course_semester;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public int getId_lecturer() {
        return id_lecturer;
    }

    public void setId_lecturer(int id_lecturer) {
        this.id_lecturer = id_lecturer;
    }

    public int getId_assistant() {
        return id_assistant;
    }

    public void setId_assistant(int id_assistant) {
        this.id_assistant = id_assistant;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
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
        final CourseOffered other = (CourseOffered) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString(){
        if(c!=null)
            return c.getCode() + " - "+c.getTitle();
        else
            return "";
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    public Course getC() {
        return c;
    }

    @Override
    public int compareTo(CourseOffered o) {
        Course cco = CRUDCourse.getCourse(o.getCourse_id());
        if(cco.getId() == o.getCourse_id())
            return 0;
        else if(cco.getId() > o.getCourse_id())
            return 1;
        else
            return -1;
    }

    public int getId_semester() {
        return id_semester;
    }

    public void setId_semester(int id_semester) {
        this.id_semester = id_semester;
    }
    
    
    
}
