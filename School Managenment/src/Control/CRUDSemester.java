/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Department;
import Model.Semester;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Sokeng Paul (AG)
 */
public class CRUDSemester {
    private ArrayList<Semester> list;

    public CRUDSemester() {
        list = new ArrayList<>();
    }

    public CRUDSemester(ArrayList<Semester> list) {
        this.list = list;
    }

    public ArrayList<Semester> getList() {
        return list;
    }

    public void setList(ArrayList<Semester> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "CRUDSemester{" + "list=" + list + '}';
    }

    public boolean searchpresence(Semester s) {
        boolean b = false;
        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).getName().equalsIgnoreCase(s.getName()) && this.list.get(i).getId()==s.getId()) {
                System.out.println(s.getName() + " search presence" + this.list.get(i).getName());
                b = true;
                break;
            }
        }
        return b;
    }

    public void addSemester(Semester s) {
        //System.out.println(b.getPhoneNumber()+"vgvjnb");
        if (this.list == null) {
            this.list.add(s);
        } else if (this.searchpresence(s)) {
            System.out.println("there is already a semester for this season and year");
        } else {
            this.list.add(s);
            Collections.sort(this.list);
        }

    }

    private void removeSemDB(int semId) {
        Connection con = DBConnection.getConnection();
        try {
            String req = "delete "
                    + "from semester "
                    + "where id = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, semId);
            stm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CRUDSemester.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static Semester findSemById(int semId) {
        Connection con = DBConnection.getConnection();
        try {
            String req = "select * "
                    + "from semester "
                    + "where id = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, semId);
            ResultSet rs = stm.executeQuery();
            Semester s = new Semester();
            if(rs.next()){
                s.setId(semId);
                s.setName(rs.getString("name"));
                s.setSYear(rs.getInt("s_year"));
            }
            return s;
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CRUDSemester.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public void update(Semester oldSem, Semester newSem) {
        this.getList().remove(getSemById(oldSem.getId()));
        this.getList().add(newSem);
        updateSemDB(newSem);
        Collections.sort(this.list);
    }

    public Semester getSemById(int id) {
        for(Semester d : this.getList()) {
            if (d.getId() == id) {
                return d;
            }
        }
        return null;
    }

    private void updateSemDB(Semester sem) {
        Connection con = DBConnection.getConnection();
        try {
            String req = "update semester s "
                    + "set s.id = ?,s.name = ?, s.s_year = ? "
                    + "where s.id = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, sem.getId());
            stm.setString(2, sem.getName());
            stm.setInt(3, sem.getSYear());
            stm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CRUDSemester.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void delete(Semester y) {
        if (this.searchpresence(y) != true) {
            System.out.println("THIS SEMESTER DOES NOT EXIST ");
        } else {
            this.list.remove(y);
            removeSemDB(y.getId());
        }

    }

    public CRUDSemester searchposibilities(String y) {
        CRUDSemester l;
        l = new CRUDSemester();
        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).getName().toLowerCase().contains(y.toLowerCase())) {
                l.list.add(this.list.get(i));
            }
        }
        return l;
    }

    public static ObservableList<Department> searchposibilitie(ObservableList<Department> initList, String y) {
        ArrayList<Department> list = new ArrayList<>();
        for (int i = 0; i < initList.size(); i++) {
            if (initList.get(i).getName().toLowerCase().contains(y.toLowerCase())) {
                list.add(initList.get(i));
            }
        }
        return FXCollections.observableArrayList(list);
    }
}
