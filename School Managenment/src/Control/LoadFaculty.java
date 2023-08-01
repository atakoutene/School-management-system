/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Faculty;
import Model.MyDate;
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
public class LoadFaculty {
    public static CRUDFaculty loadFacultyFromDB() {
        Connection con = DBConnection.getConnection();
        try {    
            CRUDFaculty l = new CRUDFaculty();
            String req = "select f.id as fid, p.name as pname, p.address as paddress, p.phonenumber as pnumber, "
                    + "p.email as pemail, p.gender as pgender, d.jour as djour, d.mois as dmois, d.annee as dyear, "
                    + "e.salary as esalary, hire.jour as hjour, hire.mois as hmois, hire.annee as hannee, "
                    + "e.office as eoffice, f.officehours as fofficehours, "
                    + "f.position as fposition, f.title as ftitle, f.departement as fdepartement, f.specialty as fspecialty  "
                    + "from mydate d, mydate hire, person p, employee e, faculty f "
                    + "where (p.id_date_birth = d.id) and (e.id_person = p.id) and (e.id_date_hired = hire.id) and (f.id_employee = e.id)"
                    + "            and p.name != 'NoLecturer Assigned';";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery(req);
            char gender;
            int daybirth, monthbirth, yearbirth, dayhired, monthhired, yearhired, phoneNumber,id;
            long officehours;
            double salary;
            String name = "", address, email, office, rank, title, departement, specialty;
            while (result.next()) {
                id = result.getInt("fid");
                name = result.getString("pname");
                address = result.getString("paddress");
                email = result.getString("pemail");
                office = result.getString("eoffice");
                rank = result.getString("fposition");
                gender = result.getString("pgender").charAt(0);
                daybirth = result.getInt("djour");
                monthbirth = result.getInt("dmois");
                yearbirth = result.getInt("dyear");
                salary = result.getDouble("esalary");
                dayhired = result.getInt("hjour");
                monthhired = result.getInt("hmois");
                yearhired = result.getInt("hannee");
                phoneNumber = result.getInt("pnumber");
                officehours = result.getInt("fofficehours");
                title = result.getString("ftitle");
                departement = result.getString("fdepartement");
                specialty = result.getString("fspecialty");
                l.addfaculty(new Faculty(id,officehours, rank, title, departement, specialty, office, salary, new MyDate(dayhired, monthhired, yearhired), name, address, Integer.toString(phoneNumber), email, gender, new MyDate(daybirth, monthbirth, yearbirth)));
            }
            return l;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoadFaculty.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    public static CRUDFaculty loadFacultyFromDBDepartment(String deptName) {
        Connection con = DBConnection.getConnection();
        try {
            CRUDFaculty l = new CRUDFaculty();
            String req = "select f.id as fid, p.name as pname, p.address as paddress, p.phonenumber as pnumber, "
                    + "p.email as pemail, p.gender as pgender, d.jour as djour, d.mois as dmois, d.annee as dyear, "
                    + "e.salary as esalary, hire.jour as hjour, hire.mois as hmois, hire.annee as hannee, "
                    + "e.office as eoffice, f.officehours as fofficehours, "
                    + "f.position as fposition, f.title as ftitle, f.departement as fdepartement, f.specialty as fspecialty  "
                    + "from mydate d, mydate hire, person p, employee e, faculty f "
                    + "where (p.id_date_birth = d.id) and (e.id_person = p.id) and (e.id_date_hired = hire.id) and (f.id_employee = e.id)"
                    + "            and p.name != 'NoLecturer Assigned' and f.departement = '"+deptName+"';";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery(req);
            char gender;
            int daybirth, monthbirth, yearbirth, dayhired, monthhired, yearhired, phoneNumber,id;
            long officehours;
            double salary;
            String name = "", address, email, office, rank, title, departement, specialty;
            while (result.next()) {
                id = result.getInt("fid");
                name = result.getString("pname");
                address = result.getString("paddress");
                email = result.getString("pemail");
                office = result.getString("eoffice");
                rank = result.getString("fposition");
                gender = result.getString("pgender").charAt(0);
                daybirth = result.getInt("djour");
                monthbirth = result.getInt("dmois");
                yearbirth = result.getInt("dyear");
                salary = result.getDouble("esalary");
                dayhired = result.getInt("hjour");
                monthhired = result.getInt("hmois");
                yearhired = result.getInt("hannee");
                phoneNumber = result.getInt("pnumber");
                officehours = result.getInt("fofficehours");
                title = result.getString("ftitle");
                departement = result.getString("fdepartement");
                specialty = result.getString("fspecialty");
                l.addfaculty(new Faculty(id,officehours, rank, title, departement, specialty, office, salary, new MyDate(dayhired, monthhired, yearhired), name, address, Integer.toString(phoneNumber), email, gender, new MyDate(daybirth, monthbirth, yearbirth)));
            }
            return l;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoadFaculty.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    public static CRUDFaculty loadFacultyFromDB2() {
        Connection con = DBConnection.getConnection();
        try {
            CRUDFaculty l = new CRUDFaculty();
            String req = "select f.id as fid, p.name as pname, p.address as paddress, p.phonenumber as pnumber, "
                    + "p.email as pemail, p.gender as pgender, d.jour as djour, d.mois as dmois, d.annee as dyear, "
                    + "e.salary as esalary, hire.jour as hjour, hire.mois as hmois, hire.annee as hannee, "
                    + "e.office as eoffice, f.officehours as fofficehours, "
                    + "f.position as fposition, f.title as ftitle, f.departement as fdepartement, f.specialty as fspecialty  "
                    + "from mydate d, mydate hire, person p, employee e, faculty f "
                    + "where (p.id_date_birth = d.id) and (e.id_person = p.id) and (e.id_date_hired = hire.id) and (f.id_employee = e.id) ;";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery(req);
            char gender;
            int daybirth, monthbirth, yearbirth, dayhired, monthhired, yearhired, phoneNumber,id;
            long officehours;
            double salary;
            String name = "", address, email, office, rank, title, departement, specialty;
            while (result.next()) {
                id = result.getInt("fid");
                name = result.getString("pname");
                address = result.getString("paddress");
                email = result.getString("pemail");
                office = result.getString("eoffice");
                rank = result.getString("fposition");
                gender = result.getString("pgender").charAt(0);
                daybirth = result.getInt("djour");
                monthbirth = result.getInt("dmois");
                yearbirth = result.getInt("dyear");
                salary = result.getDouble("esalary");
                dayhired = result.getInt("hjour");
                monthhired = result.getInt("hmois");
                yearhired = result.getInt("hannee");
                phoneNumber = result.getInt("pnumber");
                officehours = result.getInt("fofficehours");
                title = result.getString("ftitle");
                departement = result.getString("fdepartement");
                specialty = result.getString("fspecialty");
                l.addfaculty(new Faculty(id,officehours, rank, title, departement, specialty, office, salary, new MyDate(dayhired, monthhired, yearhired), name, address, Integer.toString(phoneNumber), email, gender, new MyDate(daybirth, monthbirth, yearbirth)));
            }
            return l;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoadFaculty.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
