/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Steve Meudje
 */
public class Student extends Person implements Cloneable, Comparable<Student> {

    private int id;
    private String stuID;
    private String status;
    private String dep;
    private String currentSemester;
    private String parent_info;
    private String entrance_year;
    private int program_id;
    private String profileFile;
    private InputStream profilePhoto;
    private int idPerson;

    private ArrayList<Student_Course> listStudCourse;

    public Student() {

    }

    public Student(int id, String stuID, String status, String dep, String name, String address, String phoneNumber,
            String emailAddress, char gender, MyDate birth, String parent_info, String entrance_year, int program_id, int idPerson) {
        super(name, address, phoneNumber, emailAddress, gender, birth);
        this.listStudCourse = new ArrayList<>();
        this.stuID = stuID;
        this.status = status;
        this.dep = dep;
        this.id = id;
        this.entrance_year = entrance_year;
        this.parent_info = parent_info;
        this.program_id = program_id;
        this.idPerson = idPerson;
    }

    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    public void setCourseList(ArrayList<Student_Course> listStudCourse) {
        Collections.copy(this.listStudCourse, listStudCourse);
    }

    public ArrayList<Student_Course> getCourseList() {
        return listStudCourse;
    }

    public String getStuID() {
        return stuID;
    }

    public void setStuID(String stuID) {
        this.stuID = stuID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getParent_info() {
        return parent_info;
    }

    public void setParent_info(String parent_info) {
        this.parent_info = parent_info;
    }

    public String getEntrance_year() {
        return entrance_year;
    }

    public void setEntrance_year(String entrance_year) {
        this.entrance_year = entrance_year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProgram_id() {
        return program_id;
    }

    public void setProgram_id(int program_id) {
        this.program_id = program_id;
    }

    public String getProfileFile() {
        return profileFile;
    }

    public void setProfileFile(String profileFile) {
        this.profileFile = profileFile;
    }

    public InputStream getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(InputStream profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
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
        final Student other = (Student) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    /**
     * Override the protected clone method defined in the Object class, and
     * strengthen its accessibility
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int compareTo(Student s) {
        return this.getName().compareToIgnoreCase(s.getName());
    }

}
