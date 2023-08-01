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
public class Department implements Serializable, Cloneable, Comparable<Department>{
    private int id;
    private String name;
    private int idHead;
    private Staff head;
    private String headName;
    
    public Department(){
        
    }

    public Department(int id, String name, int idHead) {
        this.id = id;
        this.name = name;
        this.idHead = idHead;
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

    public int getIdHead() {
        return idHead;
    }

    public void setIdHead(int idHead) {
        this.idHead = idHead;
    }

    public Staff getHead() {
        return head;
    }

    public void setHead(Staff head) {
        this.head = head;
    }

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
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
        final Department other = (Department) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
    
    
    
    @Override
    public int compareTo(Department o) {
        return name.compareTo(o.name);
    }
    
}
