/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Herman Mekontso
 */
public class Program implements Serializable, Cloneable, Comparable<Program>{
    private int id;
    private String title;
    private int id_department;
    private int id_schoolfaculty;
    private Set<ProgramCourses> programCourses = new HashSet<>();
    
    public Program(){
        
    }

    public Program(String title, int id,int id_department,int id_schoolfaculty) {
        this.title = title;
        this.id = id;
        this.id_department =  id_department;
        this.id_schoolfaculty =  id_schoolfaculty;
    }

    public Set<ProgramCourses> getProgramCourses() {
        return programCourses;
    }

    public void setProgramCourses(Set<ProgramCourses> programCourses) {
        this.programCourses = programCourses;
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId_department() {
        return id_department;
    }

    public void setId_department(int id_department) {
        this.id_department = id_department;
    }

    public int getId_schoolfaculty() {
        return id_schoolfaculty;
    }

    public void setId_schoolfaculty(int id_schoolfaculty) {
        this.id_schoolfaculty = id_schoolfaculty;
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int compareTo(Program o) {
        return title.compareTo(o.title);
    }

    @Override
    public String toString() {
        return title;
    }
    
    
    
}
