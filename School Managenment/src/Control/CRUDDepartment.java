/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Department;
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
public class CRUDDepartment {
    
    private ArrayList<Department> list;

    public CRUDDepartment() {
        list = new ArrayList<>();
    }

    public CRUDDepartment(ArrayList<Department> list) {
        this.list = list;
    }

    public ArrayList<Department> getList() {
        return list;
    }

    public void setList(ArrayList<Department> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "CRUDDepartment{" + "list=" + list + '}';
    }

    public boolean searchpresence(String y) {
        boolean b = false;
        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).getName().equalsIgnoreCase(y)) {
                System.out.println(y + " search presence" + this.list.get(i).getName());
                b = true;
                break;
            }
        }
        return b;
    }

    public void addDep(Department b) {
        //System.out.println(b.getPhoneNumber()+"vgvjnb");
        if (this.list == null) {
            this.list.add(b);
        } else if (this.searchpresence(b.getName())) {
            System.out.println("there is already a department with that name");
        } else {
            this.list.add(b);
            Collections.sort(this.list);
        }

    }

    private void removeDepDB(int depId) {
        Connection con = DBConnection.getConnection();
        try {
            String req = "delete "
                    + "from department "
                    + "where id = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, depId);
            stm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void update(Department oldDep, Department newDep) {
        this.getList().remove(getDepById(oldDep.getId()));
        this.getList().add(newDep);
        updateDepDB(newDep);
        Collections.sort(this.list);
    }

    private Department getDepById(int id) {
        for(Department d : this.getList()) {
            if (d.getId() == id) {
                return d;
            }
        }
        return null;
    }

    private void updateDepDB(Department dep) {
        Connection con = DBConnection.getConnection();
        try {
            String req = "update department d "
                    + "set d.id = ?,d.name = ?, d.id_head = ? "
                    + "where d.id = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, dep.getId());
            stm.setString(2, dep.getName());
            stm.setInt(3, dep.getIdHead());
            stm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void delete(Department y) {
        if (this.searchpresence(y.getName()) != true) {
            System.out.println("THIS DEPARTMENT DOES NOT EXIST ");
        } else {
            this.list.remove(y);
            removeDepDB(y.getId());
        }

    }

    public CRUDDepartment searchposibilities(String y) {
        CRUDDepartment l;
        l = new CRUDDepartment();
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
    
    public static Department findByName(String name){
        ObservableList<Department> depts = Tools.getDepartments();
        for(Department d: depts)
            if(d.getName().equalsIgnoreCase(name))
                return d;
        return null;
    }
    
}
