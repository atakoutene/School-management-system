/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migration;

import Control.LoadProgram;
import Control.Tools;
import Model.CourseOffered;
import Model.Program;
import Model.ProgramCourses;
import Model.Semester;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CNC
 */
public class ArrangeDatabase {

    public static List<CourseOffered> getCoursesOffered() {
        Connection con = DBConnection2.getConnection();
        List<CourseOffered> results = new ArrayList<>();
        try {
            String req = "SELECT * "
                    + "FROM course_offered;";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            int id, id_course, id_semester, courseYear;
            String courseSem, syllabus;
            while (result.next()) {
                id = result.getInt("id");
                id_course = result.getInt("id_course");
                id_semester = result.getInt("id_semester");
                courseYear = result.getInt("course_year");
                courseSem = result.getString("course_sem");
                syllabus = result.getString("syllabus");
                CourseOffered co = new CourseOffered(id, id_course, courseYear, courseSem, syllabus);
                co.setId_semester(id_semester);
                results.add(co);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArrangeDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ArrangeDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return results;
    }

    public static void updateProgramCourse() {
        try (Connection con = DBConnection2.getConnection();) {
            List<Program> programs = LoadProgram.loadProgramFromDB().getList();
            Set<ProgramCourses> results = new HashSet<>();
            List<Semester> semesters = Tools.getSemesters();
            int i = 0;
            for (Program prog : programs) {
                for (Semester s : semesters) {
                    List<CourseOffered> myList = Tools.getProgramCourses(prog, s);
                    i += myList.size();
                    for (CourseOffered co : myList) {
                        ProgramCourses pc = new ProgramCourses();
                        ProgramCourses tmp = getProgCourse(prog.getId(), co.getCourse_id());
                        pc.setId_program(prog.getId());
                        pc.setId_course(co.getCourse_id());
                        pc.setCore(tmp.getCore());
                        pc.setId_level(tmp.getId_level());
                        pc.setNb_credits(tmp.getNb_credits());
                        pc.setPassing_grade(tmp.getPassing_grade());
                        pc.setId_semester(s.getId());
                        results.add(pc);
                        String req = "insert into program_course2 values("
                                + pc.getId_program() + ", " + pc.getId_course() + ", '"
                                + pc.getCore() + "', " + pc.getId_level() + ", "
                                + pc.getNb_credits() + ", '" + pc.getPassing_grade() + "', "
                                + pc.getId_semester() + "); ";
                        String req2 = "select * from program_course2 "
                                + "where id_program = " + pc.getId_program() + " and id_course = " + pc.getId_course() + " and "
                                + "id_semester = " + pc.getId_semester() + " ;";
                        PreparedStatement stm2 = con.prepareStatement(req2);
                        if (!stm2.executeQuery().next()) {
                            PreparedStatement stm = con.prepareStatement(req);
                            stm.executeUpdate();
                        }

                    }
                }
            }
            System.out.println(i);

        } catch (SQLException ex) {
            Logger.getLogger(ArrangeDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {
//        List<CourseOffered> myList = getCoursesOffered();
//        System.out.println(myList.size());
//        myList.stream().forEachOrdered(e -> System.out.println(e));
          updateProgramCourse();
    }

    private static ProgramCourses getProgCourse(int id_program, int id_course) {
        ProgramCourses results = new ProgramCourses();
        Connection con = DBConnection2.getConnection();
        try {
            String req = "select * "
                    + "from program_course "
                    + "where id_program = " + id_program + " and id_course = " + id_course + ";";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            int id_level, id_semester;
            double nb_credits;
            char passing_grade;
            String core;
            while (result.next()) {
                id_level = result.getInt("id_level");
                id_semester = result.getInt("id_semester");
                core = result.getString("core");
                nb_credits = result.getDouble("nb_credits");
                passing_grade = result.getString("passing_grade").charAt(0);
                results.setId_program(id_program);
                results.setId_course(id_course);
                results.setCore(core);
                results.setNb_credits(nb_credits);
                results.setId_level(id_level);
                results.setId_semester(id_semester);
                results.setPassing_grade(passing_grade);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ArrangeDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ArrangeDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return results;
    }

}
