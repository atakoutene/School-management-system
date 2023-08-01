/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Objects;

/**
 *
 * @author Sokeng Paul (AG)
 */
public class Student_Course implements Comparable<Student_Course>, Cloneable {

    private String coursecode;
    private String coursetitle;
    private int idStudent;
    private int idCourseOffered;
    private int idCourse;
    private double nb_credits;
    private String studentname;
    private double score;
    private String grade;
    private String passing_grade;
    private String semester;
    private String studRef;

    public Student_Course() {

    }

    public Student_Course(String coursecode, String coursetitle, int idStudent, int idCourseOffered, int idCourse, double nb_credits, String studentname, double score, String grade, String passing_grade) {
        this.coursecode = coursecode;
        this.coursetitle = coursetitle;
        this.idStudent = idStudent;
        this.idCourseOffered = idCourseOffered;
        this.idCourse = idCourse;
        this.nb_credits = nb_credits;
        this.studentname = studentname;
        this.score = score;
        this.grade = grade;
        this.passing_grade = passing_grade;
    }

    

    public String getCoursecode() {
        return coursecode;
    }

    public void setCoursecode(String coursecode) {
        this.coursecode = coursecode;
    }

    public String getCoursetitle() {
        return coursetitle;
    }

    public void setCoursetitle(String coursetitle) {
        this.coursetitle = coursetitle;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public int getIdCourseOffered() {
        return idCourseOffered;
    }

    public void setIdCourseOffered(int idCourseOffered) {
        this.idCourseOffered = idCourseOffered;
    }

    public int getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(int idCourse) {
        this.idCourse = idCourse;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPassing_grade() {
        return passing_grade;
    }

    public void setPassing_grade(String passing_grade) {
        this.passing_grade = passing_grade;
    }

    public double getNb_credits() {
        return nb_credits;
    }

    public void setNb_credits(double nb_credits) {
        this.nb_credits = nb_credits;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.coursecode);
        hash = 79 * hash + Objects.hashCode(this.coursetitle);
        hash = 79 * hash + this.idStudent;
        hash = 79 * hash + this.idCourseOffered;
        hash = 79 * hash + this.idCourse;
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.score) ^ (Double.doubleToLongBits(this.score) >>> 32));
        hash = 79 * hash + Objects.hashCode(this.grade);
        hash = 79 * hash + Objects.hashCode(this.passing_grade);
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
        final Student_Course other = (Student_Course) obj;
        if (this.idStudent != other.idStudent) {
            return false;
        }
        if (this.idCourseOffered != other.idCourseOffered) {
            return false;
        }
        if (this.idCourse != other.idCourse) {
            return false;
        }
        return true;
    }

    
    @Override
    public int compareTo(Student_Course sc) {
        if (idCourseOffered > sc.idCourseOffered && idStudent > sc.idStudent) {
            return -1;
        } else {
            if (idCourseOffered < sc.idCourseOffered && idStudent > sc.idStudent) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public Student_Course clone() throws CloneNotSupportedException {
        return (Student_Course) super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getStudRef() {
        return studRef;
    }

    public void setStudRef(String studRef) {
        this.studRef = studRef;
    }
    
            
    @Override
    public String toString() {
        return coursecode + ", " + coursetitle + ", " + idStudent + ", " + idCourseOffered + ", " + idCourse + ", " + nb_credits + ", " + score + ", " + grade + ", " + passing_grade;
    }

}
