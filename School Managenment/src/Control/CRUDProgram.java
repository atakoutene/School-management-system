/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Program;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Herman Mekontso
 */
public class CRUDProgram {

    private ArrayList<Program> list;

    public CRUDProgram() {
        list = new ArrayList<>();
    }

    public CRUDProgram(ArrayList<Program> list) {
        this.list = list;
    }

    public ArrayList<Program> getList() {
        return list;
    }

    public void setList(ArrayList<Program> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "CRUDProgram{" + "list=" + list + '}';
    }

    public boolean searchpresence(String y) {
        boolean b = false;
        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).getTitle().equals(y)) {
                System.out.println(y + " search presence" + this.list.get(i).getTitle());
                b = true;
                break;
            }
        }
        return b;
    }

    public void addProgram(Program b) {
        //System.out.println(b.getPhoneNumber()+"vgvjnb");
        if (this.list == null) {
            this.list.add(b);
        } else if (this.searchpresence(b.getTitle())) {
            System.out.println("there is already a program with that title");
        } else {
            this.list.add(b);
            Collections.sort(this.list);
        }
    }

    private void removeProgramDB(int programId) {
        Connection con = DBConnection.getConnection();
        try {
            String req = "delete "
                    + "from program p"
                    + "where p.id = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, programId);
            stm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void update(Program old, Program newProgram) {
        this.getList().remove(getProgramById(old.getId()));
        this.getList().add(newProgram);
        updateProgramDB(newProgram);
        Collections.sort(this.list);
    }
    
    private Program getProgramById(int id) {
        for (Program p : this.getList()) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
    
    private void updateProgramDB(Program program) {
        Connection con = DBConnection.getConnection();
        try {
            String req = "update program p "
                    + "set p.title = ?, "
                    + "where p.id = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setString(1, program.getTitle());
            stm.setInt(2, program.getId());
            stm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void delete(Program y) {
        if (this.searchpresence(y.getTitle()) != true) {
            System.out.println("THERE IS NO Program WITH THAT TITLE");
        } else {
            this.list.remove(y);
            removeProgramDB(y.getId());
        }
    }

    public CRUDProgram searchposibilities(String y) {
        CRUDProgram l;
        l = new CRUDProgram();
        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).getTitle().toLowerCase().contains(y.toLowerCase())) {
                l.list.add(this.list.get(i));
            }
        }
        return l;
    }
    
    public static ObservableList<Program> searchposibilitie(ObservableList<Program> initList,String y){
        ArrayList<Program> list = new ArrayList<>();
        for (int i = 0; i < initList.size(); i++) {
            if (initList.get(i).getTitle().toLowerCase().contains(y.toLowerCase())) {
                list.add(initList.get(i));
            }
        }
        return FXCollections.observableArrayList(list);
    }
}
