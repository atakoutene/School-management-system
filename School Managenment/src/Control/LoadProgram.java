/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Program;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import migration.DBConnection2;

/**
 *
 * @author Herman Mekontso
 */
public class LoadProgram {

    public static CRUDProgram loadProgramFromDB() {
        Connection con = DBConnection.getConnection();
        try {
            CRUDProgram l = new CRUDProgram();
            String req = "select p.id as pid, p.title as ptitle, p.id_department as iddept, p.id_schoolfaculty as idfac  "
                    + "from program p ;";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery(req);
            int id, id_department, id_schoollevel;
            String title = " ";
            while (result.next()) {
                id = result.getInt("pid");
                title = result.getString("ptitle");
                id_department = result.getInt("iddept");
                id_schoollevel = result.getInt("idfac");
                Program prog = new Program(title, id, id_department, id_schoollevel);
//                prog.setProgramCourses(ManageProgramCourses.loadProgramCourses(id));
                l.addProgram(prog);
            }

            return l;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoadProgram.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static Program getProgram(int id) {
        Connection con = DBConnection.getConnection();
        try {
            CRUDProgram l = new CRUDProgram();
            String req = "select * "
                    + "from program  where id = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, id);
            ResultSet result = stm.executeQuery();
            int i, id_department, id_schoolfaculty;
            String title;
            while (result.next()) {
                i = result.getInt("id");
                title = result.getString("title");
                id_department = result.getInt("id_department");
                id_schoolfaculty = result.getInt("id_schoolfaculty");
                return new Program(title, id, id_department, id_schoolfaculty);
            }
            return null;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoadProgram.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
