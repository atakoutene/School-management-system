/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

/**
 *
 * @author Steve Meudje
 */
public class CRUDStudent {

    private ArrayList<Student> list;

    public CRUDStudent() {
        list = new ArrayList<>();
    }

    public CRUDStudent(ArrayList<Student> list) {
        this.list = list;
    }

    public ArrayList<Student> getList() {
        return list;
    }

    public void setList(ArrayList<Student> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "CRUDStudent{" + "list=" + list + '}';
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

    public void addaddress(Student b) {
        if (this.list == null) {
            this.list.add(b);
        } else if (this.searchpresence(b.getName())) {
            System.out.println("there is already someone with that name");
        } else {
            this.list.add(b);
            Collections.sort(list);
        }

    }

    public void delete(Student y) {
        Student s = LoadStudent.loadStudentById(y.getId());
        this.list.remove(y);
        removeStudentDB(s);
    }
    
    public void update(Student old, Student newStudent){
        this.getList().remove(getStudentById(old.getId()));
        this.getList().add(newStudent);
        updateStudentDB(newStudent);
        Collections.sort(this.list);
        
    }
    
    private Student getStudentById(int id){
        System.out.println(id);
        for(Student s:this.list){
            if(s.getId() == id){
                return s;
            }            
        }
        return null;
    }
    
    private void updateStudentDB(Student stud){
        Connection con = DBConnection.getConnection();
        try {
            String req = "update student s, person p, mydate d "
                    + "set s.studentId = ?, s.level = ?, s.departement = ?, s.parent_info = ?, s.entrance_year = ?, p.name = ?,"+
                    "p.address = ?, p.phonenumber = ?, p.email = ?, p.gender =?, d.jour = ?, d.mois = ?, d.annee = ? "
                    + "where s.id = ? and s.id_person = p.id and p.id_date_birth = d.id;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setString(1, stud.getStuID());
            stm.setString(2, stud.getStatus());
            stm.setString(3, stud.getDep());
            stm.setString(4, stud.getParent_info());
            stm.setString(5, stud.getEntrance_year());
            stm.setString(6, stud.getName());
            stm.setString(7, stud.getAddress());
            stm.setInt(8, Integer.parseInt(stud.getPhoneNumber()));
            stm.setString(9, stud.getEmailAddress());
            stm.setString(10, String.valueOf(stud.getGender()));
            stm.setInt(11, stud.getBirth().getDay());
            stm.setInt(12, stud.getBirth().getMonth());
            stm.setInt(13, stud.getBirth().getYear());
            stm.setInt(14, stud.getId());
            stm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void removeStudentDB(Student s) {
        Connection con = DBConnection.getConnection();
        try {
            con.setAutoCommit(false);
            String req1 = "delete from take_test "
                    + "where id_student = " + s.getId() + ";";
            PreparedStatement stm1 = con.prepareStatement(req1);
            stm1.executeUpdate();
            String req2 = "delete from student_level "
                    + "where id_student = " + s.getId() + ";";
            PreparedStatement stm2 = con.prepareStatement(req2);
            stm2.executeUpdate();
            String req3 = "delete from student_department "
                    + "where id_student = " + s.getId() + ";";
            PreparedStatement stm3 = con.prepareStatement(req3);
            stm3.executeUpdate();
            String req4 = "delete from student_attendance "
                    + "where id_student = " + s.getId() + ";";
            PreparedStatement stm4 = con.prepareStatement(req4);
            stm4.executeUpdate();
            String req5 = "delete from student_course "
                    + "where id_student = " + s.getId() + ";";
            PreparedStatement stm5 = con.prepareStatement(req5);
            stm5.executeUpdate();
            String req = "delete student, person "
                    + "from student inner join person "
                    + "where student.id = ? and student.id_person = person.id;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, s.getId());
            stm.executeUpdate();
            con.commit();
            con.close();
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

    public int searchwithid(String name) {
        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).getStuID().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }

    public  CRUDStudent searchposibilities(String y) {
        CRUDStudent l;
        l = new CRUDStudent();
        for (int i = 0; i < this.list.size(); i++) {
            if ((this.list.get(i).getName()).toLowerCase().contains(y.toLowerCase())) {
                l.list.add(this.list.get(i));
            }

        }
        return l;
    }
    
    public static ArrayList<Student> searchposibilities(Set<Student> list,String y) {
        ArrayList<Student> l = new ArrayList<>();
        for (Student s: list) {
            if ((s.getName()).toLowerCase().contains(y.toLowerCase())) {
                l.add(s);
            }

        }
        return l;
    }
}
