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
public class Staff extends Employee implements Cloneable, Comparable<Staff> {
  
    private int id;
    
    private String title;
    
    private String titre;

    public Staff() {
        this.title = "Chef";
        
    }

    public Staff(int id,String title, String titre,String office, double salary, MyDate dateHired, String name, String address, String phoneNumber, String emailAddress, char gender, MyDate birth) {
        super(office, salary, dateHired, name, address, phoneNumber, emailAddress, gender, birth);
        this.title = title;
        this.titre = titre;
        setId(id);
    }
    
    

    public Staff(String office, double salary, MyDate dateHired, String name, String address, String phoneNumber, String emailAddress, char gender, MyDate birth) {
        super(office, salary, dateHired, name, address, phoneNumber, emailAddress, gender, birth);
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return titre + " " +getName();
    }
    
    @Override
    /**
     * Override the protected clone method defined in the Object class, and
     * strengthen its accessibility
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    @Override
    public int compareTo(Staff s){
        return this.getName().compareToIgnoreCase(s.getName());
    }
    
    
}
