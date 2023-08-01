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
public class Taught_By{
    private int idFaculty;
    private int idCourseOffered;
    private int idProgram;
    private int idRoom;
    private Room placeName;
    private String facultyName;
    private String courseName;
    private String startTime;
    private String endTime;
    private String courseDate;
    private EDay courseDay;
    
    public Taught_By(){
        
    }

    public Taught_By(int idFaculty, int idCourseOffered, int idProgram, int idRoom, String facultyName, String courseName, String startTime, String endTime, EDay courseDay, String courseDate) {
        this.idFaculty = idFaculty;
        this.idCourseOffered = idCourseOffered;
        this.idProgram = idProgram;
        this.idRoom = idRoom;
        this.facultyName = facultyName;
        this.courseName = courseName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.courseDay = courseDay;
        this.courseDate = courseDate;
    }

    public Room getPlaceName() {
        return placeName;
    }

    public void setPlaceName(Room placeName) {
        this.placeName = placeName;
    }

    

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getIdFaculty() {
        return idFaculty;
    }

    public void setIdFaculty(int idFaculty) {
        this.idFaculty = idFaculty;
    }

    public int getIdCourseOffered() {
        return idCourseOffered;
    }

    public void setIdCourseOffered(int idCourseOffered) {
        this.idCourseOffered = idCourseOffered;
    }

    public int getIdProgram() {
        return idProgram;
    }

    public void setIdProgram(int idProgram) {
        this.idProgram = idProgram;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
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

    public EDay getCourseDay() {
        return courseDay;
    }

    public void setCourseDay(EDay courseDay) {
        this.courseDay = courseDay;
    }

    public String getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(String courseDate) {
        this.courseDate = courseDate;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.idFaculty;
        hash = 59 * hash + this.idCourseOffered;
        hash = 59 * hash + this.idProgram;
        hash = 59 * hash + this.idRoom;
        hash = 59 * hash + Objects.hashCode(this.startTime);
        hash = 59 * hash + Objects.hashCode(this.endTime);
        hash = 59 * hash + Objects.hashCode(this.courseDay);
        hash = 59 * hash + Objects.hashCode(this.courseDate);
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
        final Taught_By other = (Taught_By) obj;
        if (this.idFaculty != other.idFaculty) {
            return false;
        }
        if (this.idCourseOffered != other.idCourseOffered) {
            return false;
        }
        if (this.idProgram != other.idProgram) {
            return false;
        }
        if (this.idRoom != other.idRoom) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Taught_By{" + "idFaculty=" + idFaculty + ", idCourseOffered=" + idCourseOffered + ", idProgram=" + idProgram + ", idRoom=" + idRoom + ", startTime=" + startTime + ", endTime=" + endTime + ", courseDay=" + courseDay + ", courseDate=" + courseDate + '}';
    }

    
    
}
