/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Department;
import Model.SchoolFaculty;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Sokeng Paul (AG)
 */
public class CRUDSchoolFaculty {
    
    private ArrayList<SchoolFaculty> list;

    public CRUDSchoolFaculty() {
        list = new ArrayList<>();
    }

    public CRUDSchoolFaculty(ArrayList<SchoolFaculty> list) {
        this.list = list;
    }

    public ArrayList<SchoolFaculty> getList() {
        return list;
    }

    public void setList(ArrayList<SchoolFaculty> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "CRUDSchoolFaculty{" + "list=" + list + '}';
    }

    public boolean searchpresence(String y) {
        boolean b = false;
        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).getTitle().equalsIgnoreCase(y)) {
                System.out.println(y + " search presence" + this.list.get(i).getTitle());
                b = true;
                break;
            }
        }
        return b;
    }

    public void addDep(SchoolFaculty b) {
        //System.out.println(b.getPhoneNumber()+"vgvjnb");
        if (this.list == null) {
            this.list.add(b);
        } else if (this.searchpresence(b.getTitle())) {
            System.out.println("there is already a faculty with that title");
        } else {
            this.list.add(b);
            Collections.sort(this.list);
        }

    }

    private void removeDepDB(int depId) {
        Connection con = DBConnection.getConnection();
        try {
            String req = "delete "
                    + "from schoolfaculty d"
                    + "where d.id = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, depId);
            stm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void update(SchoolFaculty oldDep, SchoolFaculty newDep) {
        this.getList().remove(getDepById(oldDep.getId()));
        this.getList().add(newDep);
        updateDepDB(newDep);
        Collections.sort(this.list);
    }

    private SchoolFaculty getDepById(int id) {
        for(SchoolFaculty d : this.getList()) {
            if (d.getId() == id) {
                return d;
            }
        }
        return null;
    }

    private void updateDepDB(SchoolFaculty sfac) {
        Connection con = DBConnection.getConnection();
        try {
            String req = "update schoolfaculty sf "
                    + "set sf.id = ?,sf.title = ?, sf.id_head = ? "
                    + "where sf.id = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, sfac.getId());
            stm.setString(2, sfac.getTitle());
            stm.setInt(3, sfac.getIdHead());
            stm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void delete(Department y) {
        if (this.searchpresence(y.getName()) != true) {
            System.out.println("THIS SCHOOL FACULTY DOES NOT EXIST ");
        } else {
            this.list.remove(y);
            removeDepDB(y.getId());
        }

    }

    public CRUDSchoolFaculty searchposibilities(String y) {
        CRUDSchoolFaculty l;
        l = new CRUDSchoolFaculty();
        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).getTitle().toLowerCase().contains(y.toLowerCase())) {
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
    
    public static Department findByName(String name){
        ObservableList<Department> depts = Tools.getDepartments();
        for(Department d: depts)
            if(d.getName().equalsIgnoreCase(name))
                return d;
        return null;
    }
    
}
