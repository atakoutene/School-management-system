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
public class TimeSlot {
    private int id;
    private String startTime;
    private String endTime;
    private String dateCourse;
    private EDay dayCourse;
    
    public TimeSlot(){}

    public TimeSlot(int id, String startTime, String endTime, String dateCourse, EDay dayCourse) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dateCourse = dateCourse;
        this.dayCourse = dayCourse;
    }

    public TimeSlot(String startTime, String endTime, String dateCourse, EDay dayCourse) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.dateCourse = dateCourse;
        this.dayCourse = dayCourse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDateCourse() {
        return dateCourse;
    }

    public void setDateCourse(String dateCourse) {
        this.dateCourse = dateCourse;
    }

    public EDay getDayCourse() {
        return dayCourse;
    }

    public void setDayCourse(EDay dayCourse) {
        this.dayCourse = dayCourse;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.startTime);
        hash = 29 * hash + Objects.hashCode(this.endTime);
        hash = 29 * hash + Objects.hashCode(this.dateCourse);
        hash = 29 * hash + Objects.hashCode(this.dayCourse);
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
        final TimeSlot other = (TimeSlot) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.startTime, other.startTime)) {
            return false;
        }
        if (!Objects.equals(this.endTime, other.endTime)) {
            return false;
        }
        if (!Objects.equals(this.dateCourse, other.dateCourse)) {
            return false;
        }
        if (this.dayCourse != other.dayCourse) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "TimeSlot{" + "id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + ", dateCourse=" + dateCourse + ", dayCourse=" + dayCourse + '}';
    }
    
}
