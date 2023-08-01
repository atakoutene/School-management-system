/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author HERMAN
 */
public class StudentsProgDTO implements Comparable<StudentsProgDTO> {

    private int id;
    private String name;
    private String reference;
    private String level;
    private String program;
    private String entrance_year;

    public StudentsProgDTO() {
    }

    public StudentsProgDTO(int id, String name, String reference, String level, String program, String entrance_year) {
        this.id = id;
        this.name = name;
        this.reference = reference;
        this.level = level;
        this.program = program;
        this.entrance_year = entrance_year;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.id;
        return hash;
    }

    @Override
    public String toString() {
        return "StudentsProgDTO{" + "id=" + id + ", name=" + name + ", reference=" + reference + ", level=" + level + ", program=" + program + ", entrance_year=" + entrance_year + '}';
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
        final StudentsProgDTO other = (StudentsProgDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getEntrance_year() {
        return entrance_year;
    }

    public void setEntrance_year(String entrance_year) {
        this.entrance_year = entrance_year;
    }
    
    

    @Override
    public int compareTo(StudentsProgDTO o) {
        if (id < o.id) {
            return -1;
        } else if (id > o.id) {
            return 1;
        } else {
            return 0;
        }
    }

}
