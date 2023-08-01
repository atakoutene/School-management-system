/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Steve Meudje
 */
public class Person {
    private int id;
    private SimpleStringProperty name = new SimpleStringProperty();
    private String address;
    private SimpleStringProperty phoneNumber = new SimpleStringProperty();
    private String emailAddress;
    private char gender; 
    private MyDate birth;
    private String placeOfBirth;

    public Person() {
        this.birth = new MyDate();
    }


    

    public Person(String name, String address, String phoneNumber, String emailAddress, char gender, MyDate birth) {
        this.name = new SimpleStringProperty(name);
        this.address = address;
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.emailAddress = emailAddress;
        this.gender = gender;
        this.birth = birth;
    }
    
        public String getName() {
        return name.getValue();
    }

    public void setName(String name) {
        
        this.name.setValue(name);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber.getValue();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.setValue(phoneNumber);
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public MyDate getBirth() {
        return birth;
    }

    public void setBirth(MyDate birth) {
        this.birth = birth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    @Override
    public String toString() {
        return "Person{" + "name=" + name.getValue() + ", address=" + address + ", phoneNumber=" + phoneNumber.getValue() + ", emailAddress=" + emailAddress + ", gender=" + gender + ", birth=" + birth + '}';
    }
    
    
    

    
}
