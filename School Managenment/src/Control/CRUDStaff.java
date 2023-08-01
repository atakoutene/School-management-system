/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Staff;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Steve Meudje
 */
public class CRUDStaff {

    private ArrayList<Staff> list;
      
    public CRUDStaff() {
        list = new ArrayList<>();
    }

    public CRUDStaff(ArrayList<Staff> list) {
        this.list = list;
    }

    public ArrayList<Staff> getList() {
        return list;
    }

    public void setList(ArrayList<Staff> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "CRUDStaff{" + "list=" + list + '}';
    }

    public boolean searchpresence(String y) {
        boolean b = false;
        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).getName().equals(y)) {
                System.out.println(y + " search presence" + this.list.get(i).getName());

                b = true;
                break;
            }
        }
        return b;
    }

   
    public void addstaff(Staff b) {
        //System.out.println(b.getPhoneNumber()+"vgvjnb");
        if (this.list == null) {
            this.list.add(b);
        } else if (this.searchpresence(b.getName())) {
            System.out.println("there is already someone with that name");
        } else {
            this.list.add(b);
            Collections.sort(list);
        }

    }

    private void removeStaffDB(int staffId) {
        Connection con = DBConnection.getConnection();
        try {
            String req = "delete s, p, e "
                    + "from staff s inner join employee e inner join person p "
                    + "where s.id = ? and s.id_employee = e.id and e.id_person = p.id ";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, staffId);
            stm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void delete(Staff y) {      
            this.list.remove(y);
            removeStaffDB(y.getId());
    }

    public void update(Staff old, Staff newStaff) {
        this.getList().remove(getStaffById(old.getId()));
        this.getList().add(newStaff);
        updateStaffDB(newStaff);
        Collections.sort(this.list);
    }

    private Staff getStaffById(int id) {
        for (Staff s : this.list) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    private void updateStaffDB(Staff staff) {
        Connection con = DBConnection.getConnection();
        try {
            String req = "update staff s, employee e, person p, mydate d "
                    + "set s.title = ?, e.salary = ?, e.office = ?, p.name = ?,"
                    + "p.address = ?, p.phonenumber = ?, p.email = ?, p.gender =?, d.jour = ?, d.mois = ?, d.annee = ? "
                    + "where s.id = ? and s.id_employee = e.id and e.id_person = p.id and p.id_date_birth = d.id;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setString(1, staff.getTitle());
            stm.setDouble(2, staff.getSalary());
            stm.setString(3, staff.getOffice());
            stm.setString(4, staff.getName());
            stm.setString(5, staff.getAddress());
            stm.setInt(6, Integer.parseInt(staff.getPhoneNumber()));
            stm.setString(7, staff.getEmailAddress());
            stm.setString(8, String.valueOf(staff.getGender()));
            stm.setInt(9, staff.getBirth().getDay());
            stm.setInt(10, staff.getBirth().getMonth());
            stm.setInt(11, staff.getBirth().getYear());
            stm.setInt(12, staff.getId());
            stm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int searchPosition(String name) {
        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }

    public CRUDStaff searchposibilities(String y) {
        CRUDStaff l;
        l = new CRUDStaff();
        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).getName().toLowerCase().contains(y.toLowerCase())) {
                l.list.add(this.list.get(i));
            }

        }
        return l;
    }
    
    public static ObservableList<Staff> searchposibilitie(ObservableList<Staff> initList,String y){
        ArrayList<Staff> list = new ArrayList<>();
        for (int i = 0; i < initList.size(); i++) {
            if (initList.get(i).getName().toLowerCase().contains(y.toLowerCase())) {
                list.add(initList.get(i));
            }

        }
        return FXCollections.observableArrayList(list);
    }

}
