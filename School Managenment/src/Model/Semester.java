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
public class Semester implements Serializable, Cloneable, Comparable<Semester>{
    private int id;
    private String name;
    private int sYear;
    
    public Semester(){
        
    }

    public Semester(int id, String name, int sYear) {
        this.id = id;
        this.name = name;
        this.sYear = sYear;
    }

    public Semester(String name, int sYear) {
        this.name = name;
        this.sYear = sYear;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSYear() {
        return sYear;
    }

    public void setSYear(int sYear) {
        this.sYear = sYear;
    }

    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.id;
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
        final Semester other = (Semester) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return  name + " " + sYear;
    }
    
    
            
    @Override
    public int compareTo(Semester o) {
        if(sYear < o.sYear)
            return -1;
        else if (sYear > o.sYear)
                return 1;
             else
                return 0;
    }
    
}
