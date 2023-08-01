/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.MyDate;
import Model.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Steve Meudje
 */
public class LoadStudent {
    public static CRUDStudent loadStudentsFromDB() {
        Connection con = DBConnection.getConnection();
        try {
            CRUDStudent l = new CRUDStudent();
            String req = "select s.id as id, p.name as pname, p.id as idPerson, p.address as paddress, p.phonenumber as pnumber, p.email as pemail, p.gender as pgender, d.jour as djour, d.mois as dmois, d.annee as dyear, s.level as slevel, s.departement as sdepartement,"
                    + " s.studentId as sId, s.parent_info as parent_info, s.entrance_year as entrance_year, s.id_program as program_id, p.placeofbirth as birthplace  "
                    + "from mydate d, person p, student s "
                    + "where (p.id_date_birth = d.id) and (s.id_person = p.id);";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            char gender;
            int daybirth, monthbirth, yearbirth, phoneNumber,id, program_id, idPerson;
            String name, address, email, department, level, studentId, parent_info, entrance_year, birthplace;
            while (result.next()) {
                id = result.getInt("id");
                program_id = result.getInt("program_id");
                name = result.getString("pname");
                address = result.getString("paddress");
                email = result.getString("pemail");
                gender = result.getString("pgender").charAt(0);
                daybirth = result.getInt("djour");
                monthbirth = result.getInt("dmois");
                yearbirth = result.getInt("dyear");
                phoneNumber = result.getInt("pnumber");
                level = result.getString("slevel");
                department = result.getString("sdepartement");
                studentId = result.getString("sId");
                parent_info = result.getString("parent_info");
                entrance_year = result.getString("entrance_year");
                birthplace = result.getString("birthplace");
                idPerson = result.getInt("idPerson");
                Student stud = new Student(id,studentId, level, department, name, address, 
                        Integer.toString(phoneNumber), email, gender
                        , new MyDate(daybirth, monthbirth, yearbirth), parent_info, entrance_year, program_id, idPerson);
                stud.setPlaceOfBirth(birthplace);
                l.addaddress(stud);
            }
            return l;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }finally{    
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoadStudent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static Student loadStudentById(int id){
        Connection con = DBConnection.getConnection();
        Student s = new Student();
        try {     
            String req = "select s.id as id, p.name as pname, p.id as idPerson, p.address as paddress, p.phonenumber as pnumber, p.email as pemail, p.gender as pgender, d.jour as djour, d.mois as dmois, d.annee as dyear, s.level as slevel, s.departement as sdepartement,"
                    + " s.studentId as sId, s.parent_info as parent_info, s.entrance_year as entrance_year, s.id_program as program_id, p.placeofbirth as birthplace  "
                    + "from mydate d, person p, student s "
                    + "where (p.id_date_birth = d.id) and (s.id_person = p.id) and s.id = "+id+";";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            char gender;
            int daybirth, monthbirth, yearbirth, phoneNumber, program_id, idPerson;
            String name, address, email, department, level, studentId, parent_info, entrance_year, birthplace;
            if(result.next()){
                program_id = result.getInt("program_id");
                name = result.getString("pname");
                address = result.getString("paddress");
                email = result.getString("pemail");
                gender = result.getString("pgender").charAt(0);
                daybirth = result.getInt("djour");
                monthbirth = result.getInt("dmois");
                yearbirth = result.getInt("dyear");
                phoneNumber = result.getInt("pnumber");
                level = result.getString("slevel");
                department = result.getString("sdepartement");
                studentId = result.getString("sId");
                parent_info = result.getString("parent_info");
                entrance_year = result.getString("entrance_year");
                birthplace = result.getString("birthplace");
                idPerson = result.getInt("idPerson");
                s = new Student(id,studentId, level, department, name, address, 
                        Integer.toString(phoneNumber), email, gender
                        , new MyDate(daybirth, monthbirth, yearbirth), parent_info, entrance_year, program_id, idPerson);
                s.setPlaceOfBirth(birthplace);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoadStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoadStudent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return s;
    }
    
    public static Student loadStudentByRefNumber(String ref){
        Connection con = DBConnection.getConnection();
        Student s = new Student();
        try {     
            String req = "select s.id as id, p.name as pname, p.id as idPerson, p.address as paddress, p.phonenumber as pnumber, p.email as pemail, p.gender as pgender, d.jour as djour, d.mois as dmois, d.annee as dyear, s.level as slevel, s.departement as sdepartement,"
                    + " s.studentId as sId, s.parent_info as parent_info, s.entrance_year as entrance_year, s.id_program as program_id, p.placeofbirth as birthplace  "
                    + "from mydate d, person p, student s "
                    + "where (p.id_date_birth = d.id) and (s.id_person = p.id) and s.studentId = '"+ref+"';";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            char gender;
            int daybirth, monthbirth,id, yearbirth, phoneNumber, program_id, idPerson;
            String name, address, email, department, level, studentId, parent_info, entrance_year, birthplace;
            if(result.next()){
                id = result.getInt("id");
                program_id = result.getInt("program_id");
                name = result.getString("pname");
                address = result.getString("paddress");
                email = result.getString("pemail");
                gender = result.getString("pgender").charAt(0);
                daybirth = result.getInt("djour");
                monthbirth = result.getInt("dmois");
                yearbirth = result.getInt("dyear");
                phoneNumber = result.getInt("pnumber");
                level = result.getString("slevel");
                department = result.getString("sdepartement");
                studentId = result.getString("sId");
                parent_info = result.getString("parent_info");
                entrance_year = result.getString("entrance_year");
                birthplace = result.getString("birthplace");
                idPerson = result.getInt("idPerson");
                s = new Student(id,studentId, level, department, name, address, 
                        Integer.toString(phoneNumber), email, gender
                        , new MyDate(daybirth, monthbirth, yearbirth), parent_info, entrance_year, program_id, idPerson);
                s.setPlaceOfBirth(birthplace);
                return s;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoadStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoadStudent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
}
