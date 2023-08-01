/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;

/**
 *
 * @author HERMAN
 */
public class ProgramCourses implements Serializable, Cloneable, Comparable<ProgramCourses>{
    private int id_program;
    private int id_course;
    private String core;
    private SchoolLevel level;
    private double nb_credits;
    private char passing_grade;
    private String code_course;
    private String program_title;
    private String course_title;
    private Course course;
    private int id_level;
    private int id_semester;
    
    public ProgramCourses(){
        
    }

    public ProgramCourses(int id_program, int id_course, String core, SchoolLevel level, double nb_credits, char passing_grade) {
        this.id_program = id_program;
        this.id_course = id_course;
        this.core = core;
        this.level = level;
        this.nb_credits = nb_credits;
        this.passing_grade = passing_grade;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    
    

    public String getCourse_title() {
        return course_title;
    }

    public void setCourse_title(String course_title) {
        this.course_title = course_title;
    }
    
    public String getCode_course() {
        return code_course;
    }

    public void setCode_course(String code_course) {
        this.code_course = code_course;
    }

    public String getProgram_title() {
        return program_title;
    }

    public void setProgram_title(String program_title) {
        this.program_title = program_title;
    }
    
    

    public int getId_program() {
        return id_program;
    }

    public void setId_program(int id_program) {
        this.id_program = id_program;
    }

    public int getId_course() {
        return id_course;
    }

    public void setId_course(int id_course) {
        this.id_course = id_course;
    }

    public String getCore() {
        return core;
    }

    public void setCore(String core) {
        this.core = core;
    }

    public SchoolLevel getLevel() {
        return level;
    }

    public void setLevel(SchoolLevel level) {
        this.level = level;
    }

    public double getNb_credits() {
        return nb_credits;
    }

    public void setNb_credits(double nb_credits) {
        this.nb_credits = nb_credits;
    }

    public char getPassing_grade() {
        return passing_grade;
    }

    public void setPassing_grade(char passing_grade) {
        this.passing_grade = passing_grade;
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
        hash = 83 * hash + this.id_program;
        hash = 83 * hash + this.id_course;
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
        final ProgramCourses other = (ProgramCourses) obj;
        if (this.id_program != other.id_program) {
            return false;
        }
        if (this.id_course != other.id_course) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(ProgramCourses o) {
        return level.getName().compareToIgnoreCase(o.getLevel().getName());
    }

    @Override
    public String toString() {
        return "ProgramCourses{" + "id_program=" + id_program + ", id_course=" + id_course + ", core=" + core + ", nb_credits=" + nb_credits + ", passing_grade=" + passing_grade + ", code_course=" + code_course + ", program_title=" + program_title + ", course_title=" + course_title + ", course=" + course + ", id_level=" + id_level + ", id_semester=" + id_semester + '}';
    }

    
    
    
}
