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
public class SchoolFaculty implements Comparable<SchoolFaculty> {
    private int id;
    private String title;
    private int idHead;

    public SchoolFaculty() {
    }

    public SchoolFaculty(int id, String title, int idHead) {
        this.id = id;
        this.title = title;
        this.idHead = idHead;
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

    public int getIdHead() {
        return idHead;
    }

    public void setIdHead(int idHead) {
        this.idHead = idHead;
    }
   

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.title);
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
        final SchoolFaculty other = (SchoolFaculty) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(SchoolFaculty o) {
        return title.compareToIgnoreCase(o.title);
    }

    @Override
    public String toString() {
        return title;
    }
    
}
