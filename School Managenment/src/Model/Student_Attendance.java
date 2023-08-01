/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Sokeng Paul (AG)
 */
public class Student_Attendance {
    private int id_student;
    private int id_schedule;
    private final StringProperty presence = new SimpleStringProperty();
    private String studentName;
    private String studentRef;
    private EDay course_day;
    private String course_date;   
    private String startTime;   
    private String endTime;
    private String course_title;
    
    public Student_Attendance(){}

    public Student_Attendance(int id_student, int id_schedule, String presence, String studentName, String studentRef) {
        this.id_student = id_student;
        this.id_schedule = id_schedule;
        this.setPresence(presence);
        this.studentName = studentName;
        this.studentRef = studentRef;
    }

    public Student_Attendance(int id_student, int id_schedule, String presence, String studentName, String studentRef, EDay course_day, String course_date, String startTime, String endTime, String course_title) {
        this.id_student = id_student;
        this.id_schedule = id_schedule;
        this.setPresence(presence);
        this.studentName = studentName;
        this.studentRef = studentRef;
        this.course_day = course_day;
        this.course_date = course_date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.course_title = course_title;
    }
    
    public Student_Attendance(int id_student, int id_schedule, String presence) {
        this.id_student = id_student;
        this.id_schedule = id_schedule;
        this.setPresence(presence);
    }

    public int getId_student() {
        return id_student;
    }

    public void setId_student(int id_student) {
        this.id_student = id_student;
    }

    public int getId_schedule() {
        return id_schedule;
    }

    public void setId_schedule(int id_schedule) {
        this.id_schedule = id_schedule;
    }

    public String getPresence() {
        return presence.get();
    }

    public void setPresence(String presence) {
        this.presence.set(presence) ;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentRef() {
        return studentRef;
    }

    public void setStudentRef(String studentRef) {
        this.studentRef = studentRef;
    }

    public EDay getCourse_day() {
        return course_day;
    }

    public void setCourse_day(EDay course_day) {
        this.course_day = course_day;
    }

    public String getCourse_date() {
        return course_date;
    }

    public void setCourse_date(String course_date) {
        this.course_date = course_date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCourse_title() {
        return course_title;
    }

    public void setCourse_title(String course_title) {
        this.course_title = course_title;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.id_student;
        hash = 89 * hash + this.id_schedule;
        hash = 89 * hash + Objects.hashCode(this.presence);
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
        final Student_Attendance other = (Student_Attendance) obj;
        if (this.id_student != other.id_student) {
            return false;
        }
        return this.id_schedule == other.id_schedule;
    }
    
    public StringProperty presenceProperty(){
        return presence;
    }

    @Override
    public String toString() {
        return "Student_Attendance{" + "id_student=" + id_student + ", id_schedule=" + id_schedule + ", presence=" + presence + '}';
    }
    
    
}
