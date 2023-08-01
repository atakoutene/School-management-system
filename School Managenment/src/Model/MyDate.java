/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;

/**
 *
 * @author Steve Meudje
 */
public class MyDate {
    private int id;
    private int day;
    private int month;
    private int year;

    public MyDate() {
        Calendar c;
        c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
    }

    public MyDate(long timemilli) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTimeInMillis(timemilli);
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
    }

    public MyDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setMonth(int month) {
        while(month < 0 || month > 11) {
            boolean b = false;
            while(b == false){
                try{
                    month  = Integer.parseInt(JOptionPane.showInputDialog(null, " Sorry the MONTH should be between 0 and 11"));
                    b = true;
                }
                catch(IllegalArgumentException iee){
                    System.out.println("PLease enter an Integer");
                }  
            } 
        }
        this.month = month;
    }

    public int getMonth() {
        return month;
    }

    public void setday(int day) {
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    

    @Override
    public String toString() {
        return  day + "/" + month + "/" + year ;
    }
    
    
    

}
