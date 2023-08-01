/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Staff;
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
public class LoadStaff {
    public static CRUDStaff loadStaffFromDB() {
        Connection con = DBConnection.getConnection();
        try {
            CRUDStaff l = new CRUDStaff();
            String req = "select s.id as id, p.name as pname, p.address as paddress, p.phonenumber as pnumber, p.email as pemail, p.gender as pgender, d.jour as djour, d.mois as dmois, d.annee as dyear, e.salary as esalary, hire.jour as hjour, hire.mois as hmois, "
                    + "hire.annee as hannee, e.office as eoffice, s.title as stitle, s.titre as titre  "
                    + "from mydate d, mydate hire, person p, employee e, staff s "
                    + "where (s.id_employee = e.id) and (e.id_person = p.id) and (p.id_date_birth = d.id)  and (e.id_date_hired = hire.id);";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery(req);
            String name, address, email, office, title, ty, titre;
            char gender;
            int daybirth, monthbirth, yearbirth, dayhired, monthhired, yearhired, phoneNumber,id;
            double salary;

            while (result.next()) {
                id = result.getInt("id");
                name = result.getString("pname");
                address = result.getString("paddress");
                email = result.getString("pemail");
                office = result.getString("eoffice");
                title = result.getString("stitle");
                gender = result.getString("pgender").charAt(0);
                daybirth = result.getInt("djour");
                monthbirth = result.getInt("dmois");
                yearbirth = result.getInt("dyear");
                salary = result.getDouble("esalary");
                dayhired = result.getInt("hjour");
                monthhired = result.getInt("hmois");
                yearhired = result.getInt("hannee");
                phoneNumber = result.getInt("pnumber");
                titre = result.getString("titre");
                l.addstaff(new Staff(id,title, titre,office, salary, new MyDate(dayhired, monthhired, yearhired), name, address, Integer.toString(phoneNumber), email, gender, new MyDate(daybirth, monthbirth, yearbirth)));
            }
            return l;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoadStaff.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
