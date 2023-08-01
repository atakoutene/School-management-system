/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Department;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sokeng Paul (AG)
 */
public class LoadDepartment {
    public static CRUDDepartment loadDepFromDB() {
        Connection con = DBConnection.getConnection();
        try {
            CRUDDepartment l = new CRUDDepartment();
            String req = "select d.id as depid, d.name as depname, d.id_head as depidhead "
                    + "from department d, staff s "
                    + "where d.id_head = s.id;";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery(req);
            int id,id_head;
            String name;
            while (result.next()) {
                id = result.getInt("depid");
                name = result.getString("depname");
                id_head = result.getInt("depidhead");
                l.addDep(new Department(id,name,id_head));
            }
            return l;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoadDepartment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}    
