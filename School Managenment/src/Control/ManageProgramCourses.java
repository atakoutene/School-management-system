/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Course;
import Model.MyDate;
import Model.Program;
import Model.ProgramCourses;
import Model.SchoolLevel;
import Model.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HERMAN
 */
public class ManageProgramCourses {

    public static Set<ProgramCourses> loadProgramCourses(int id_program) {
        Connection con = DBConnection.getConnection();
        try {          
            String req = "select * "
                    + "from program_course  where id_program = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, id_program);
            Set<ProgramCourses> liste = new HashSet<>();
            ResultSet result = stm.executeQuery();
            int id_course;
            double nb_credits;
            String core;
            int id_level;
            SchoolLevel level;
            char passing_grade;
            Course course;
            while (result.next()) {
                id_course = result.getInt("id_course");
                core = result.getString("core");
                id_level = result.getInt("id_level");
                level = Tools.getSchoolLevel(id_level);
                nb_credits = result.getDouble("nb_credits");
                passing_grade = (result.getString("passing_grade")).charAt(0);
                String req2 = "select * "
                        + "from course  where id = ?;";
                PreparedStatement stm2 = con.prepareStatement(req2);
                stm2.setInt(1, id_course);
                ResultSet result2 = stm2.executeQuery();
                course = new Course();
                while (result2.next()) {
                    course.setCode(result2.getString("code"));
                    course.setId(id_course);
                    course.setTitle(result2.getString("title"));
                    course.setDescription(result2.getString("description"));
                    course.setHours(result2.getInt("hours"));
                }
                ProgramCourses pcourse = new ProgramCourses();
                pcourse.setCode_course(course.getCode());
                pcourse.setCore(core);
                pcourse.setCourse_title(course.getTitle());
                pcourse.setCourse(course);
                pcourse.setId_course(id_course);
                pcourse.setId_program(id_program);
                pcourse.setLevel(level);
                pcourse.setNb_credits(nb_credits);
                pcourse.setPassing_grade(passing_grade);
                liste.add(pcourse);
            }
            con.close();
            return liste;

        } catch (SQLException ex) {
            Logger.getLogger(ManageProgramCourses.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Set<ProgramCourses> loadProgramCoursesSem(int id_course, int id_semester) {
        Connection con = DBConnection.getConnection();
        try {          
            String req = "select * "
                    + "from program_course  where id_course = ? and id_semester = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, id_course);
            stm.setInt(2, id_semester);
            Set<ProgramCourses> liste = new HashSet<>();
            ResultSet result = stm.executeQuery();
            double nb_credits;
            String core;
            int id_level, id_program;
            SchoolLevel level;
            char passing_grade;
            Course course;
            while (result.next()) {
                core = result.getString("core");
                id_program = result.getInt("id_program");
                id_level = result.getInt("id_level");
                level = Tools.getSchoolLevel(id_level);
                nb_credits = result.getDouble("nb_credits");
                passing_grade = (result.getString("passing_grade")).charAt(0);
                String req2 = "select * "
                        + "from course  where id = ?;";
                PreparedStatement stm2 = con.prepareStatement(req2);
                stm2.setInt(1, id_course);
                ResultSet result2 = stm2.executeQuery();
                course = new Course();
                while (result2.next()) {
                    course.setCode(result2.getString("code"));
                    course.setId(id_course);
                    course.setTitle(result2.getString("title"));
                    course.setDescription(result2.getString("description"));
                    course.setHours(result2.getInt("hours"));
                }
                ProgramCourses pcourse = new ProgramCourses();
                pcourse.setCode_course(course.getCode());
                pcourse.setCore(core);
                pcourse.setCourse_title(course.getTitle());
                pcourse.setCourse(course);
                pcourse.setId_course(id_course);
                pcourse.setId_program(id_program);
                pcourse.setLevel(level);
                pcourse.setNb_credits(nb_credits);
                pcourse.setPassing_grade(passing_grade);
                pcourse.setId_semester(id_semester);
                liste.add(pcourse);
            }
            con.close();
            return liste;

        } catch (SQLException ex) {
            Logger.getLogger(ManageProgramCourses.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void deleteProgramCourses(int id_program) {
        try {
            Connection con = DBConnection.getConnection();
            String req = "delete  "
                    + "from program_course where id_program = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, id_program);
            stm.executeUpdate();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ManageProgramCourses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void deleteProgramCourses(int id_program, int id_semester) {
        try {
            Connection con = DBConnection.getConnection();
            String req = "delete  "
                    + "from program_course where id_program = ? and id_semester = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, id_program);
            stm.setInt(2, id_semester);
            stm.executeUpdate();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ManageProgramCourses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Set<Student> programStudent(int id_program) {
        Set<Student> l = new HashSet<>();
        try {
            Connection con = DBConnection.getConnection();
            String req = "select s.id as id, p.name as pname,p.id as idPerson, p.address as paddress, p.phonenumber as pnumber, p.email as pemail, p.gender as pgender, d.jour as djour, d.mois as dmois, d.annee as dyear, s.level as slevel, s.departement as sdepartement,"
                    + " s.studentId as sId, s.parent_info as parent_info, s.entrance_year as entrance_year, s.id_program as program_id "
                    + "from mydate d, person p, student s "
                    + "where ((s.id_program = ?) and (p.id_date_birth = d.id) and (s.id_person = p.id))";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, id_program);
            ResultSet result = stm.executeQuery();
            char gender;
            int daybirth, monthbirth, yearbirth, phoneNumber, id, program_id,idPerson;
            String name, address, email, department, level, studentId, parent_info, entrance_year;
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
                idPerson = result.getInt("idPerson");
                Student stud = new Student(id, studentId, level, department, name, address,
                        Integer.toString(phoneNumber), email, gender,
                        new MyDate(daybirth, monthbirth, yearbirth), parent_info, entrance_year, program_id, idPerson);
                l.add(stud);
            }
            con.close();
            return l;
        } catch (SQLException ex) {
            Logger.getLogger(ManageProgramCourses.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l;
    }

     public static Set<Course> loadProgramCourses(int idProgram, int semYear, String semName, int idLevel, int idSem) {
        try {
            Connection con = DBConnection.getConnection();
            String req = "select c.id as id, co.id as coid, c.code as code, c.title as title, pc.nb_credits as credits, "
                    + "pc.passing_grade as passing_grade "
                    + "from program_course  pc, course c, course_offered co "
                    + "where pc.id_course = c.id and c.id = co.id_course and pc.id_program = ? "
                    + " and pc.id_level = ? and pc.nb_credits > 0 and co.course_year = ? and co.course_sem = ? and pc.id_semester = ?; ";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, idProgram);
            stm.setInt(2, idLevel);
            stm.setInt(3, semYear);
            stm.setString(4, semName);
            stm.setInt(5, idSem);
            Set<Course> liste = new HashSet<>();
            ResultSet result = stm.executeQuery();
            int id_course, id_Course_Offered;
            double nb_credits;
            String code, title;
            char passing_grade;
            while (result.next()) {
                id_course = result.getInt("id");
                id_Course_Offered = result.getInt("coid");
                code = result.getString("code");
                title = result.getString("title");
                nb_credits = result.getDouble("credits");
                passing_grade = (result.getString("passing_grade")).charAt(0);
                Course c = new Course();
                c.setId(id_course);
                c.setCode(code);
                c.setTitle(title);
                c.setPassingGradeForReport(passing_grade);
                c.setNbCreditsForReport(nb_credits);
                c.setId_co(id_Course_Offered);
                liste.add(c);
            }
            con.close();
            return liste;

        } catch (SQLException ex) {
            Logger.getLogger(ManageProgramCourses.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
     
    public static Set<Course> loadProgramCoursesPIng(int semYear, String semName, int sId) {
        try {
            Connection con = DBConnection.getConnection();
            String req = "select c.id as id, c.code as code, c.title as title, pc.nb_credits as credits, "
                    + "pc.passing_grade as passing_grade "
                    + "from program_course  pc, course c, course_offered co "
                    + "where pc.id_course = c.id and c.id = co.id_course and (pc.id_program <= 4 OR pc.id_program = 6) "
                    + " and pc.id_level = 1 and pc.nb_credits > 0 and co.course_year = ? and co.course_sem = ? and pc.id_semester = ?; ";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, semYear);
            stm.setString(2, semName);
            stm.setInt(3, sId);
            Set<Course> liste = new HashSet<>();
            ResultSet result = stm.executeQuery();
            int id_course;
            double nb_credits;
            String code, title;
            char passing_grade;
            while (result.next()) {
                id_course = result.getInt("id");
                code = result.getString("code");
                title = result.getString("title");
                nb_credits = result.getDouble("credits");
                passing_grade = (result.getString("passing_grade")).charAt(0);
                Course c = new Course();
                c.setId(id_course);
                c.setCode(code);
                c.setTitle(title);
                c.setPassingGradeForReport(passing_grade);
                c.setNbCreditsForReport(nb_credits);
                liste.add(c);
            }
            con.close();
            return liste;

        } catch (SQLException ex) {
            Logger.getLogger(ManageProgramCourses.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Map<Student, Set<Course>> getStudentCoursesSem(int idProgram, int idSemester, int level) {
        Set<Student> l = new HashSet<>();
        Map<Student, Set<Course>> output = new HashMap<>();
        try {
            Connection con = DBConnection.getConnection();
            String req = "select s.id as id, p.name as pname,"
                    + " s.studentId as sId  "
                    + "from student s, person p, student_level sl "
                    + "where p.id = s.id_person and s.id = sl.id_student and s.id_program = ? "
                    + " and sl.id_level = ? and sl.id_semester = ? order by p.name;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, idProgram);
            stm.setInt(2, level);
            stm.setInt(3, idSemester);
            ResultSet result = stm.executeQuery();
            int studentLevel;
            String name, studentId;
            while (result.next()) {
                Student s = new Student();
                studentLevel = result.getInt("id");
                name = result.getString("pname");
                studentId = result.getString("sId");
                s.setId(studentLevel);
                s.setName(name);
                s.setStuID(studentId);
                l.add(s);
            }
            for (Student s : l) {
                Set<Course> cList = new HashSet<>();
                String req2 = "select co.id_course as id_course, c.code as code, sc.score as score, sc.grade as grade "
                        + "from student s, student_course sc, course_offered co, course c "
                        + "where s.id = sc.id_student and co.id = sc.id_course_offered and "
                        + "c.id = co.id_course and sc.id_semester = ? "
                        + "and s.id = ? ;";
                PreparedStatement stm2 = con.prepareStatement(req2);
                stm2.setInt(1, idSemester);
                stm2.setInt(2, s.getId());
                ResultSet result2 = stm2.executeQuery();
                int idCourse;
                char letterGrade;
                double grade;
                String code = "";
                while (result2.next()) {
                    Course c = new Course();
                    idCourse = result2.getInt("id_course");
                    letterGrade = result2.getString("score").charAt(0);
                    code = result2.getString("code");
                    grade = result2.getDouble("grade");
                    c.setId(idCourse);
                    c.setCode(code);
                    c.setGradeForReport(grade);
                    c.setLetterGradeForReport(letterGrade);
                    cList.add(c);
                }
                if (!cList.isEmpty()) {
                    output.put(s, cList);
                }
            }
            con.close();
            return output;
        } catch (SQLException ex) {
            Logger.getLogger(ManageProgramCourses.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
     public static Map<Student, Set<Course>> getStudentCoursesSemIng(int idSemester, int level) {
        Set<Student> l = new HashSet<>();
        Map<Student, Set<Course>> output = new HashMap<>();
        try {
            Connection con = DBConnection.getConnection();
            String req = "select s.id as id, p.name as pname,"
                    + " s.studentId as sId  "
                    + "from student s, person p, student_level sl "
                    + "where p.id = s.id_person and s.id_program != 5 and s.id = sl.id_student "
                    + " and sl.id_level = ? and sl.id_semester = ? order by p.name;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, level);
            stm.setInt(2, idSemester);
            ResultSet result = stm.executeQuery();
            int studentLevel;
            String name, studentId;
            while (result.next()) {
                Student s = new Student();
                studentLevel = result.getInt("id");
                name = result.getString("pname");
                studentId = result.getString("sId");
                s.setId(studentLevel);
                s.setName(name);
                s.setStuID(studentId);
                l.add(s);
            }
            for (Student s : l) {
                Set<Course> cList = new HashSet<>();
                String req2 = "select co.id_course as id_course, c.code as code, sc.score as score, sc.grade as grade "
                        + "from student s, student_course sc, course_offered co, course c "
                        + "where s.id = sc.id_student and co.id = sc.id_course_offered and "
                        + "c.id = co.id_course and sc.id_semester = ? "
                        + "and s.id = ? ;";
                PreparedStatement stm2 = con.prepareStatement(req2);
                stm2.setInt(1, idSemester);
                stm2.setInt(2, s.getId());
                ResultSet result2 = stm2.executeQuery();
                int idCourse;
                char letterGrade;
                double grade;
                String code = "";
                while (result2.next()) {
                    Course c = new Course();
                    idCourse = result2.getInt("id_course");
                    letterGrade = result2.getString("score").charAt(0);
                    code = result2.getString("code");
                    grade = result2.getDouble("grade");
                    c.setId(idCourse);
                    c.setCode(code);
                    c.setGradeForReport(grade);
                    c.setLetterGradeForReport(letterGrade);
                    cList.add(c);
                }
                if (!cList.isEmpty()) {
                    output.put(s, cList);
                }
            }
            con.close();
            return output;
        } catch (SQLException ex) {
            Logger.getLogger(ManageProgramCourses.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
     
    public static Set<ProgramCourses> loadProgramCourses(int id_course, int id_semester) {
        Connection con = DBConnection.getConnection();
        try {          
            String req = "select * "
                    + "from program_course  where id_course = ? and id_semester = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, id_course);
            stm.setInt(2, id_semester);
            Set<ProgramCourses> liste = new HashSet<>();
            ResultSet result = stm.executeQuery();
            int id_program;
            double nb_credits;
            String core;
            int id_level;
            SchoolLevel level;
            char passing_grade;
            Course course;
            while (result.next()) {
                id_program = result.getInt("id_program");
                core = result.getString("core");
                id_level = result.getInt("id_level");
                level = Tools.getSchoolLevel(id_level);
                nb_credits = result.getDouble("nb_credits");
                passing_grade = (result.getString("passing_grade")).charAt(0);
                String req2 = "select * "
                        + "from course  where id = ?;";
                PreparedStatement stm2 = con.prepareStatement(req2);
                stm2.setInt(1, id_course);
                ResultSet result2 = stm2.executeQuery();
                course = new Course();
                while (result2.next()) {
                    course.setCode(result2.getString("code"));
                    course.setId(id_course);
                    course.setTitle(result2.getString("title"));
                    course.setDescription(result2.getString("description"));
                    course.setHours(result2.getInt("hours"));
                }
                ProgramCourses pcourse = new ProgramCourses();
                pcourse.setCode_course(course.getCode());
                pcourse.setCore(core);
                pcourse.setCourse_title(course.getTitle());
                pcourse.setCourse(course);
                pcourse.setId_course(id_course);
                pcourse.setId_program(id_program);
                pcourse.setLevel(level);
                pcourse.setNb_credits(nb_credits);
                pcourse.setPassing_grade(passing_grade);
                pcourse.setId_semester(id_semester);
                liste.add(pcourse);
            }
            con.close();
            return liste;

        } catch (SQLException ex) {
            Logger.getLogger(ManageProgramCourses.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static Set<ProgramCourses> loadProgramCourses(Program prog, int id_semester) {
        Connection con = DBConnection.getConnection();
        try {          
            String req = "select * "
                    + "from program_course  where id_program = ? and id_semester = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, prog.getId());
            stm.setInt(2, id_semester);
            Set<ProgramCourses> liste = new HashSet<>();
            ResultSet result = stm.executeQuery();
            int id_course;
            double nb_credits;
            String core;
            int id_level;
            SchoolLevel level;
            char passing_grade;
            Course course;
            while (result.next()) {
                id_course = result.getInt("id_course");
                core = result.getString("core");
                id_level = result.getInt("id_level");
                level = Tools.getSchoolLevel(id_level);
                nb_credits = result.getDouble("nb_credits");
                passing_grade = (result.getString("passing_grade")).charAt(0);
                String req2 = "select * "
                        + "from course  where id = ?;";
                PreparedStatement stm2 = con.prepareStatement(req2);
                stm2.setInt(1, id_course);
                ResultSet result2 = stm2.executeQuery();
                course = new Course();
                while (result2.next()) {
                    course.setCode(result2.getString("code"));
                    course.setId(id_course);
                    course.setTitle(result2.getString("title"));
                    course.setDescription(result2.getString("description"));
                    course.setHours(result2.getInt("hours"));
                }
                ProgramCourses pcourse = new ProgramCourses();
                pcourse.setCode_course(course.getCode());
                pcourse.setCore(core);
                pcourse.setCourse_title(course.getTitle());
                pcourse.setCourse(course);
                pcourse.setId_course(id_course);
                pcourse.setId_program(prog.getId());
                pcourse.setLevel(level);
                pcourse.setNb_credits(nb_credits);
                pcourse.setPassing_grade(passing_grade);
                pcourse.setId_semester(id_semester);
                liste.add(pcourse);
            }
            con.close();
            return liste;

        } catch (SQLException ex) {
            Logger.getLogger(ManageProgramCourses.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
