/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Objects;

/**
 *
 * @author Steve Meudje
 */
public class Employee extends Person{
    private int id;
    private String office;
    private double salary;
    private MyDate dateHired;

    public Employee(String office, double salary, MyDate dateHired) {
        this.office = office;
        this.salary = salary;
        this.dateHired = dateHired;
    }

    public Employee(String office, double salary, MyDate dateHired, String name, String address, String phoneNumber, String emailAddress, char gender, MyDate birth) {
        super(name, address, phoneNumber, emailAddress, gender, birth);
        this.office = office;
        this.salary = salary;
        this.dateHired = dateHired;
    }

    

    public Employee() {
        this.office = "s7";
        this.dateHired = new MyDate();
        this.salary = 2000;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public MyDate getDateHired() {
        return dateHired;
    }

    public void setDateHired(MyDate dateHired) {
        this.dateHired = dateHired;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.office);
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.salary) ^ (Double.doubleToLongBits(this.salary) >>> 32));
        hash = 67 * hash + Objects.hashCode(this.dateHired);
        return hash;
    }

    @Override
    public String toString() {
        return "Employee{" +" name="+ super.getName()  + '}';
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
        final Employee other = (Employee) obj;
        if (Double.doubleToLongBits(this.salary) != Double.doubleToLongBits(other.salary)) {
            return false;
        }
        if (!Objects.equals(this.office, other.office)) {
            return false;
        }
        if (!Objects.equals(this.dateHired, other.dateHired)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
}
