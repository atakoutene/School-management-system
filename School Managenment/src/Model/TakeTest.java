/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Control.LoadStudent;

/**
 *
 * @author metch
 */
public class TakeTest {

    private int idTest;
    private int idStudent;
    private String anonymous;
    private double score;
    private char letterGrade;
    private String studentname;
    private String registration;

    public TakeTest() {
    }

    public TakeTest(int idTest, int idStudent, String anonymous, double score, char letterGrade) {
        this.idTest = idTest;
        this.idStudent = idStudent;
        this.anonymous = anonymous;
        this.score = score;
        this.letterGrade = letterGrade;
        Student s = LoadStudent.loadStudentById(idStudent);
        this.studentname = s.getName();
        this.registration = s.getStuID();
    }

    public int getIdTest() {
        return idTest;
    }

    public void setIdTest(int idTest) {
        this.idTest = idTest;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public String getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public char getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(char letterGrade) {
        this.letterGrade = letterGrade;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.idTest;
        hash = 79 * hash + this.idStudent;
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
        final TakeTest other = (TakeTest) obj;
        if (this.idTest != other.idTest) {
            return false;
        }
        if (this.idStudent != other.idStudent) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TakeTest{" + "idTest=" + idTest + ", idStudent=" + idStudent + ", anonymous=" + anonymous + ", score=" + score + ", letterGrade=" + letterGrade + '}';
    }
}
