/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Steve Meudje
 */
public class Faculty extends Employee implements Cloneable, Comparable<Faculty> {
    private int id;
    private long officehours;
    private String rank;
    private String title;
    private String departement;
    private String specialty;

    public Faculty() {
        this.officehours = 40;
        this.rank = "String";
    }
    
    

    public Faculty(int id, long officehours, String rank, String title, String departement, String specialty, String office, double salary, MyDate dateHired, String name, String address, String phoneNumber, String emailAddress, char gender, MyDate birth) {
        super(office, salary, dateHired, name, address, phoneNumber, emailAddress, gender, birth);
        this.officehours = officehours;
        this.rank = rank;
        this.title = title;
        this.departement = departement;
        this.specialty = specialty;
        setId(id);
    }

    public long getOfficehours() {
        return officehours;
    }

    public void setOfficehours(long officehours) {
        this.officehours = officehours;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
    
    

    @Override
    public String toString() {
        return title + " " + getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    /**
     * Override the protected clone method defined in the Object class, and
     * strengthen its accessibility
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    public int compareTo(Faculty s){
        return this.getName().compareToIgnoreCase(s.getName());
    }
    
    
}
