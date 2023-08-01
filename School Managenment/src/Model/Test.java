/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Calendar;

/**
 *
 * @author metch
 */
public class Test implements Comparable<Test>{
    private int id;
    private Calendar testDate;
    private ETestType testType;
    private int course_offeredId;

    public Test() {
    }

    public Test (int id, Calendar testDate, ETestType testType, int course_offeredId) {
        this.id = id;
        this.testDate = testDate;
        this.testType = testType;
        this.course_offeredId = course_offeredId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getTestDate() {
        return testDate;
    }

    public void setTestDate(Calendar testDate) {
        this.testDate = testDate;
    }

    public ETestType getTestType() {
        return testType;
    }

    public void setTestType(ETestType testType) {
        this.testType = testType;
    }

    public int getCourse_offeredId() {
        return course_offeredId;
    }

    public void setCourse_offeredId(int course_offeredId) {
        this.course_offeredId = course_offeredId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
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
        final Test other = (Test) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Test{" + "id=" + id + ", testDate=" + testDate + ", testType=" + testType + ", course_offeredId=" + course_offeredId + '}';
    }

    @Override
    public int compareTo(Test o) {
       if (id > o.id)
           return 1;
       else
           return id < o.id ? -1 : 0;
    }
    
    
    
    
    
}
