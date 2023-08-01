/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Faculty;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Steve Meudje
 */
public class CRUDFaculty {

    private ArrayList<Faculty> list;

    public CRUDFaculty() {
        list = new ArrayList<>();
    }

    public CRUDFaculty(ArrayList<Faculty> list) {
        this.list = list;
    }

    public ArrayList<Faculty> getList() {
        return list;
    }

    public void setList(ArrayList<Faculty> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "CRUDFaculty{" + "list=" + list + '}';
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

    public void addfaculty(Faculty b) {
        //System.out.println(b.getPhoneNumber()+"vgvjnb");
        if (this.list == null) {
            this.list.add(b);
        } else if (this.searchpresence(b.getName())) {
            System.out.println("there is already someone with that name");
        } else {
            this.list.add(b);
            Collections.sort(this.list);
        }

    }

    private void removeFacultyDB(int facultyId) {
        CRUDStudent l = new CRUDStudent();
        Connection con = DBConnection.getConnection();
        try {
            con.setAutoCommit(false);
            String req1 = "delete from taugh_by "
                    + "where id_faculty = " + facultyId + ";";
            PreparedStatement stm1 = con.prepareStatement(req1);
            stm1.executeUpdate();
            String req = "delete f, p, e "
                    + "from faculty f inner join employee e inner join person p "
                    + "where f.id = ? and f.id_employee = e.id and e.id_person = p.id ";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, facultyId);
            stm.executeUpdate();
            con.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CRUDFaculty.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void update(Faculty old, Faculty newFaculty) {
        this.getList().remove(getFacultyById(old.getId()));
        this.getList().add(newFaculty);
        updateFacultyDB(newFaculty);
        Collections.sort(this.list);
    }

    private Faculty getFacultyById(int id) {
        for (Faculty s : this.getList()) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    private void updateFacultyDB(Faculty faculty) {
        Connection con = DBConnection.getConnection();
        try {
            String req = "update faculty f, employee e, person p, mydate d "
                    + "set f.officehours = ?, f.position = ?, f.title = ?, f.departement = ?, f.specialty = ?, "
                    + "e.salary = ?, e.office = ?, p.name = ?,"
                    + "p.address = ?, p.phonenumber = ?, p.email = ?, p.gender =?, d.jour = ?, d.mois = ?, d.annee = ? "
                    + "where f.id = ? and f.id_employee = e.id and e.id_person = p.id and p.id_date_birth = d.id;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, (int) faculty.getOfficehours());
            stm.setString(2, faculty.getRank());
            stm.setString(3, faculty.getTitle());
            stm.setString(4, faculty.getDepartement());
            stm.setString(5, faculty.getSpecialty());
            stm.setDouble(6, faculty.getSalary());
            stm.setString(7, faculty.getOffice());
            stm.setString(8, faculty.getName());
            stm.setString(9, faculty.getAddress());
            stm.setInt(10, Integer.parseInt(faculty.getPhoneNumber()));
            stm.setString(11, faculty.getEmailAddress());
            stm.setString(12, String.valueOf(faculty.getGender()));
            stm.setInt(13, faculty.getBirth().getDay());
            stm.setInt(14, faculty.getBirth().getMonth());
            stm.setInt(15, faculty.getBirth().getYear());
            stm.setInt(16, faculty.getId());
            stm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void delete(Faculty y) {
        this.list.remove(y);
        removeFacultyDB(y.getId());
    }

    public CRUDFaculty searchposibilities(String y) {
        CRUDFaculty l;
        l = new CRUDFaculty();
        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).getName().toLowerCase().contains(y.toLowerCase())) {
                l.list.add(this.list.get(i));
            }

        }
        return l;
    }

}
