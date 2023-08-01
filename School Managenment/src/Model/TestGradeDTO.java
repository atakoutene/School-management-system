/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Objects;

/**
 *
 * @author metch
 */
public class TestGradeDTO implements Comparable<TestGradeDTO>{
    private String courseName;
    private String testType;
    private double grade;
    private char letterGrade;

    public TestGradeDTO() {
    }

    public TestGradeDTO(String courseName, String testType, double grade, char letterGrade) {
        this.courseName = courseName;
        this.testType = testType;
        this.grade = grade;
        this.letterGrade = letterGrade;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public char getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(char letterGrade) {
        this.letterGrade = letterGrade;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this.courseName);
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
        final TestGradeDTO other = (TestGradeDTO) obj;
        if (!Objects.equals(this.courseName, other.courseName)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(TestGradeDTO o) {
        return courseName.compareTo(o.courseName);
    }
    
    
}
