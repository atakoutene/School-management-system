/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Course;
import Model.CourseOffered;
import Model.CourseOfferedComparator;
import Model.Department;
import Model.DumyDouble;
import Model.EDay;
import Model.ETestType;
import Model.Grade;
import Model.MyDate;
import Model.Program;
import Model.ProgramCourses;
import Model.Room;
import Model.Schedule;
import Model.SchoolFaculty;
import Model.SchoolLevel;
import Model.Semester;
import Model.Staff;
import Model.Student;
import Model.StudentProgress;
import Model.Student_Course;
import Model.StudentsProgDTO;
import Model.TakeTest;
import Model.Taught_By;
import Model.TestGradeDTO;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.util.StringConverter;

/**
 *
 * @author HERMAN
 */
public class Tools {

    public static Semester currentSemester = new Semester();

    public static Image loadImageProfile(int studentId) {
        try (Connection con = DBConnection.getConnection();) {
            String req = "select profilepicture  "
                    + "from student  where id = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, studentId);
            ResultSet result = stm.executeQuery();
            if (result.next()) {
                InputStream is = result.getBinaryStream("profilepicture");
                Image image = new Image(is);
                return image;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseOfferedMgnt.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static InputStream loadImageProfiles(int studentId) {
        try (Connection con = DBConnection.getConnection();) {
            String req = "select profilepicture  "
                    + "from student  where id = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, studentId);
            ResultSet result = stm.executeQuery();
            if (result.next()) {
                InputStream is = result.getBinaryStream("profilepicture");

                return is;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseOfferedMgnt.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String loadImageName(int studentId) {
        try (Connection con = DBConnection.getConnection();) {
            String req = "select profilename  "
                    + "from student  where id = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, studentId);
            ResultSet result = stm.executeQuery();
            if (result.next()) {
                return result.getString("profilename");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseOfferedMgnt.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<Department> getDepartments() {
        List<Department> results = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();) {
            String req = "select * from department;";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            while (result.next()) {
                Department dept = new Department();
                dept.setId(result.getInt("id"));
                int idHead = result.getInt("id_head");
                dept.setIdHead(idHead);
                dept.setName(result.getString("name"));
                String req2 = "select s.id as id, p.name as pname, p.address as paddress, p.phonenumber as pnumber, p.email as pemail, p.gender as pgender, d.jour as djour, d.mois as dmois, d.annee as dyear, e.salary as esalary, hire.jour as hjour, hire.mois as hmois, "
                        + "hire.annee as hannee, e.office as eoffice, s.title as stitle, s.titre as titre  "
                        + "from mydate d, mydate hire, person p, employee e, staff s "
                        + "where (s.id_employee = e.id) and (e.id_person = p.id) and (p.id_date_birth = d.id)  and (e.id_date_hired = hire.id) and (s.id = ?);";
                PreparedStatement stm2 = con.prepareStatement(req2);
                stm2.setInt(1, idHead);
                ResultSet result2 = stm2.executeQuery();
                if (result2.next()) {
                    String name, address, email, office, title, ty, titre;
                    char gender;
                    int daybirth, monthbirth, yearbirth, dayhired, monthhired, yearhired, phoneNumber, id;
                    double salary;
                    id = result2.getInt("id");
                    name = result2.getString("pname");
                    address = result2.getString("paddress");
                    email = result2.getString("pemail");
                    office = result2.getString("eoffice");
                    title = result2.getString("stitle");
                    gender = result2.getString("pgender").charAt(0);
                    daybirth = result2.getInt("djour");
                    monthbirth = result2.getInt("dmois");
                    yearbirth = result2.getInt("dyear");
                    salary = result2.getDouble("esalary");
                    dayhired = result2.getInt("hjour");
                    monthhired = result2.getInt("hmois");
                    yearhired = result2.getInt("hannee");
                    phoneNumber = result2.getInt("pnumber");
                    titre = result2.getString("titre");
                    dept.setHeadName(titre + " " + name);
                    dept.setHead(new Staff(id, title, titre, office, salary, new MyDate(dayhired, monthhired, yearhired), name, address, Integer.toString(phoneNumber), email, gender, new MyDate(daybirth, monthbirth, yearbirth)));
                }
                results.add(dept);
            }
            Collections.sort(results);
            return FXCollections.observableArrayList(results);

        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Department getDepartmentByName(String dname) {
        Department dept = new Department();
        try (Connection con = DBConnection.getConnection();) {
            PreparedStatement stm = con.prepareStatement("select * from department where name = ?;");
            stm.setString(1, dname);
            ResultSet result = stm.executeQuery();

            if (result.next()) {
                dept.setId(result.getInt("id"));
                int idHead = result.getInt("id_head");
                dept.setIdHead(idHead);
                dept.setName(result.getString("name"));
                String req2 = "select s.id as id, p.name as pname, p.address as paddress, p.phonenumber as pnumber, p.email as pemail, p.gender as pgender, d.jour as djour, d.mois as dmois, d.annee as dyear, e.salary as esalary, hire.jour as hjour, hire.mois as hmois, "
                        + "hire.annee as hannee, e.office as eoffice, s.title as stitle, s.titre as titre  "
                        + "from mydate d, mydate hire, person p, employee e, staff s "
                        + "where (s.id_employee = e.id) and (e.id_person = p.id) and (p.id_date_birth = d.id)  and (e.id_date_hired = hire.id) and (s.id = ?);";
                PreparedStatement stm2 = con.prepareStatement(req2);
                stm2.setInt(1, idHead);
                ResultSet result2 = stm2.executeQuery();
                if (result2.next()) {
                    String name, address, email, office, title, ty, titre;
                    char gender;
                    int daybirth, monthbirth, yearbirth, dayhired, monthhired, yearhired, phoneNumber, id;
                    double salary;
                    id = result2.getInt("id");
                    name = result2.getString("pname");
                    address = result2.getString("paddress");
                    email = result2.getString("pemail");
                    office = result2.getString("eoffice");
                    title = result2.getString("stitle");
                    gender = result2.getString("pgender").charAt(0);
                    daybirth = result2.getInt("djour");
                    monthbirth = result2.getInt("dmois");
                    yearbirth = result2.getInt("dyear");
                    salary = result2.getDouble("esalary");
                    dayhired = result2.getInt("hjour");
                    monthhired = result2.getInt("hmois");
                    yearhired = result2.getInt("hannee");
                    phoneNumber = result2.getInt("pnumber");
                    titre = result2.getString("titre");
                    dept.setHeadName(titre + " " + name);
                    dept.setHead(new Staff(id, title, titre, office, salary, new MyDate(dayhired, monthhired, yearhired), name, address, Integer.toString(phoneNumber), email, gender, new MyDate(daybirth, monthbirth, yearbirth)));

                }
                return dept;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Department saveDepartment(Department dept) {
        try (Connection con = DBConnection.getConnection();) {
            String req = "INSERT INTO department(name, id_head) VALUES ('" + dept.getName() + "'," + dept.getIdHead() + "); ";
            PreparedStatement stm = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            int id_department = 0;
            if (rs.next()) {
                id_department = rs.getInt(1);
            }
            dept.setId(id_department);
            return dept;
        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void updateDepartment(Department d) {
        try (Connection con = DBConnection.getConnection();) {
            PreparedStatement stm = con.prepareStatement("update department set name = ?, id_head = ? where id = ?;");
            stm.setString(1, d.getName());
            stm.setInt(2, d.getIdHead());
            stm.setInt(3, d.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ObservableList<Semester> getSemesters() {
        ObservableList<Semester> results = FXCollections.observableArrayList();
        try (Connection con = DBConnection.getConnection();) {
            String req = "select * from semester order by id desc;";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                int sYear = result.getInt("s_year");
                results.add(new Semester(id, name, sYear));
            }
//            Collections.sort(results);
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static ObservableList<SchoolFaculty> getSchoolFaculties() {
        ObservableList<SchoolFaculty> results = FXCollections.observableArrayList();
        try (Connection con = DBConnection.getConnection();) {
            String req = "select * from schoolfaculty order by id desc;";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String title = result.getString("title");
                int id_head = result.getInt("id_head");
                results.add(new SchoolFaculty(id, title, id_head));
            }
//            Collections.sort(results);
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<SchoolLevel> getSchoolLevels() {
        ObservableList<SchoolLevel> results = FXCollections.observableArrayList();
        try (Connection con = DBConnection.getConnection();) {
            String req = "select * from schoollevel;";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                results.add(new SchoolLevel(id, name));
            }
            Collections.sort(results);
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Semester saveSemester(Semester sem) {
        try (Connection con = DBConnection.getConnection();) {
            //On récupère l'identifiant du semestre précédent
            int prev_sem = 0;
            String rq = "SELECT max(id) as max_id from semester";
            PreparedStatement st = con.prepareStatement(rq);
            ResultSet result = st.executeQuery();
            if (result.next()) {
                prev_sem = result.getInt("max_id");
            }

            //create a new semester
            String req = "INSERT INTO semester(name, s_year) VALUES ('" + sem.getName() + "'," + sem.getSYear() + "); ";
            PreparedStatement stm = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            int id_sem = 0;
            if (rs.next()) {
                id_sem = rs.getInt(1);
            }
            sem.setId(id_sem);

            //Open the courses for that semester
            //Step 1 (tranfer the courses for each program)
            req = "insert into program_course(id_program, id_course, core, id_level, nb_credits, passing_grade, id_semester)\n"
                    + "select  id_program, id_course, core, id_level, nb_credits, passing_grade, id_semester + 1\n"
                    + "from program_course\n"
                    + "where id_semester = " + prev_sem + ";";
            stm = con.prepareStatement(req);
            stm.executeUpdate();

            //Step2 (open the courses this semester)
            req = "insert into course_offered(id_course, course_year, course_sem, syllabus, id_semester)\n"
                    + "select id_course, course_year, course_sem, syllabus, id_semester + 1\n"
                    + "from course_offered \n"
                    + "where id_semester = " + prev_sem + ";";
            stm = con.prepareStatement(req);
            stm.executeUpdate();

            //Step3 (update the semester name and the semester year)
            req = "update course_offered set course_sem = '" + sem.getName() + "', course_year = " + sem.getSYear() + " where id_semester = " + id_sem + ";";
            stm = con.prepareStatement(req);
            stm.executeUpdate();

            //Step4 (transfering the teacher)
            req = "insert into taugh_by(id_faculty, id_course_offered, id_program, type_faculty, sem_name, sem_year)\n"
                    + "select distinct T.id_faculty, coo.id, T.id_program, T.type_faculty, coo.course_sem, coo.course_year\n"
                    + "from (select tb.id_faculty, tb.id_program, tb.type_faculty, co.id_course as id_course \n"
                    + "from course_offered co, taugh_by tb\n"
                    + "where co.id = tb.id_course_offered and co.id_semester = "+prev_sem+") as T, course_offered coo\n"
                    + "where coo.id_semester = "+id_sem+" and T.id_course = coo.id_course;;";
            stm = con.prepareStatement(req);
            stm.executeUpdate();

            return sem;
        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void updateSemester(Semester sem) {
        try (Connection con = DBConnection.getConnection();) {
            PreparedStatement stm = con.prepareStatement("update semester set name = ?, s_year = ? where id = ?;");
            stm.setString(1, sem.getName());
            stm.setInt(2, sem.getSYear());
            stm.setInt(3, sem.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ObservableList<StudentsProgDTO> getStudentsDept(int idDept) {
        ObservableList<StudentsProgDTO> liste = FXCollections.observableArrayList();
        try (Connection con = DBConnection.getConnection();) {
            String req = "select s.id as id, p.name as pname, p.address as paddress, p.phonenumber as pnumber, p.email as pemail, p.gender as pgender, d.jour as djour, d.mois as dmois, d.annee as dyear, s.level as slevel, s.departement as sdepartement,"
                    + " s.studentId as sId, s.parent_info as parent_info, s.entrance_year as entrance_year, s.id_program as program_id, dept.name as deptname, prog.title as ptitle  "
                    + "from mydate d, person p, student s, department dept, student_department sd, program prog  "
                    + "where (p.id_date_birth = d.id) and (s.id_person = p.id) and (s.id = sd.id_student) and "
                    + "(sd.id_department = dept.id) and (s.id_program = prog.id) and (dept.id = ?) group by s.level, s.entrance_year;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, idDept);
            ResultSet result = stm.executeQuery();
            int id;
            String name, reference, level, program, entrance_year;
            while (result.next()) {
                id = result.getInt("id");
                name = result.getString("pname");
                reference = result.getString("sId");
                level = result.getString("slevel");
                entrance_year = result.getString("entrance_year");
                program = result.getString("ptitle");
                liste.add(new StudentsProgDTO(id, name, reference, level, program, entrance_year));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return liste;
    }

    public static ObservableList<Course> getCourses() {
        ObservableList<Course> results = FXCollections.observableArrayList();
        try (Connection con = DBConnection.getConnection();) {
            String req = "select * from course;";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            String code, title, description;
            int id, hrs;
            while (result.next()) {
                id = result.getInt("id");
                code = result.getString("code");
                title = result.getString("title");
                description = result.getString("description");
                hrs = result.getInt("hours");
                results.add(new Course(id, code, title, description, hrs));
            }
            Collections.sort(results);
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(CRUDCourse.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static ObservableList<Semester> getProgramSemesters(Program p) {
        ObservableList<Semester> results = FXCollections.observableArrayList();
        try (Connection con = DBConnection.getConnection();) {
            String req = "select distinct sem.s_year as semyear, sem.name as semname, sem.id as semid "
                    + "from  course c, program p, course_offered co, semester sem, program_course pc "
                    + "where p.id = pc.id_program and c.id = co.id_course and c.id = pc.id_course "
                    + "and co.course_year = sem.s_year and co.course_sem = sem.name and p.id = " + p.getId() + ";";
            System.out.println(req);
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            int semId, semYear;
            String semName;
            while (result.next()) {
                semId = result.getInt("semid");
                semYear = result.getInt("semyear");
                semName = result.getString("semname");
                results.add(new Semester(semId, semName, semYear));
            }
            Collections.sort(results);
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<CourseOffered> getProgramCourses(Program p, Semester sem) {
        ObservableList<CourseOffered> results = FXCollections.observableArrayList();
        try (Connection con = DBConnection.getConnection();) {
            String req = "select co.id as courseoffid, c.id as courseid, co.course_year as courseyear, "
                    + "co.course_sem as coursesem,co.syllabus as coursesyllabus, co.id_semester as semesterId "
                    + "from course c, program_course pc, course_offered co "
                    + "where c.id = pc.id_course and pc.id_program = " + p.getId() + " and pc.id_course = co.id_course and pc.id_semester = co.id_semester and co.id_semester = " + sem.getId() + " ;";
            PreparedStatement stm = con.prepareStatement(req);
            System.out.println(stm);
            ResultSet result = stm.executeQuery();
            int courseId, year, courseoffId;
            String semester, syllabus;
            while (result.next()) {
                courseId = result.getInt("courseid");
                courseoffId = result.getInt("courseoffid");
                semester = result.getString("coursesem");
                syllabus = result.getString("coursesyllabus");
                year = result.getInt("courseyear");
                String req2 = "select distinct f.title as title, p.name as name, tb.id_faculty as idFaculty, tb.type_faculty as type_faculty  "
                        + "from taugh_by tb, employee e, person p, faculty f, course_offered co "
                        + "where p.id = e.id_person and e.id = f.id_employee and tb.id_course_offered = co.id "
                        + "and f.id = tb.id_faculty and co.id = " + courseoffId + ";";
                PreparedStatement stm2 = con.prepareStatement(req2);
                ResultSet result2 = stm2.executeQuery();
                CourseOffered co = new CourseOffered(courseoffId, courseId, year, semester, syllabus);
                String lect = "", assist = "";
                while (result2.next()) {
                    String styleL = result2.getString("type_faculty");
                    if (styleL.equalsIgnoreCase("Lecturer")) {
                        lect = result2.getString("title") + " " + result2.getString("name");
                        co.setId_lecturer(result2.getInt("idFaculty"));
                    } else {
                        assist = result2.getString("title") + " " + result2.getString("name");
                        co.setId_assistant(result2.getInt("idFaculty"));
                    }
                }
                co.setLecturer(lect + " - " + assist);
                results.add(co);
            }
            Collections.sort(results, new CourseOfferedComparator());
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<CourseOffered> studCourseSem(Student stud, Semester sem) {
        ObservableList<CourseOffered> results = FXCollections.observableArrayList();
        try (Connection con = DBConnection.getConnection();) {
            String req = "select co.id as courseoffid, c.id as courseid, co.course_year as courseyear, "
                    + "co.course_sem as coursesem, co.syllabus as coursesyllabus "
                    + "from course c, student_course sc, course_offered co "
                    + "where c.id = co.id_course and co.id = sc.id_course_offered and sc.id_student = " + stud.getId()
                    + " and sc.id_semester = " + sem.getId() + ";";
//            System.out.println(req);
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            int courseId, year, courseoffId;
            String semester, syllabus;
            while (result.next()) {
                courseId = result.getInt("courseid");
                courseoffId = result.getInt("courseoffid");
                semester = result.getString("coursesem");
                syllabus = result.getString("coursesyllabus");
                year = result.getInt("courseyear");
                String req2 = "select distinct f.title as title, p.name as name, tb.id_faculty as idFaculty, tb.type_faculty as type_faculty  "
                        + "from taugh_by tb, employee e, person p, faculty f, course_offered co "
                        + "where p.id = e.id_person and e.id = f.id_employee and tb.id_course_offered = co.id "
                        + "and f.id = tb.id_faculty and co.id = " + courseoffId + ";";
                PreparedStatement stm2 = con.prepareStatement(req2);
                ResultSet result2 = stm2.executeQuery();
                CourseOffered co = new CourseOffered(courseoffId, courseId, year, semester, syllabus);
                String lect = "";
                while (result2.next()) {
                    String styleL = result2.getString("type_faculty");
                    if (styleL.equals("Lecturer")) {
                        lect += result2.getString("title") + " " + result2.getString("name");
                        co.setId_lecturer(result2.getInt("idFaculty"));
                    } else {
                        lect += " - " + result2.getString("title") + " " + result2.getString("name");
                        co.setId_assistant(result2.getInt("idFaculty"));
                    }
                }
                co.setLecturer(lect);
                results.add(co);
            }
            Collections.sort(results);
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<SchoolLevel> getSchoolLevel() {
        ObservableList<SchoolLevel> results = FXCollections.observableArrayList();
        try (Connection con = DBConnection.getConnection();) {
            String req = "select * from schoollevel;";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            String name;
            int id;
            while (result.next()) {
                id = result.getInt("id");
                name = result.getString("name");
                results.add(new SchoolLevel(id, name));
            }
            Collections.sort(results);
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static SchoolLevel getSchoolLevel(int id) {
        try (Connection con = DBConnection.getConnection();) {
            String req = "select * from schoollevel "
                    + "where id = " + id + ";";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            String name;
            while (result.next()) {
                name = result.getString("name");
                return new SchoolLevel(id, name);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static SchoolLevel getSchoolLevel(String level) {
        try (Connection con = DBConnection.getConnection();) {
            String req = "SELECT sl.id as schoollevel"
                    + " FROM schoollevel sl "
                    + "WHERE sl.name = '" + level + "';";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            int id;
            String thelevel;
            while (result.next()) {
                id = result.getInt("schoollevel");
                return new SchoolLevel(id, level);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<Student_Course> getStudentCourses(Student stud, SchoolLevel sl, Semester sem) {
        ObservableList<Student_Course> results = FXCollections.observableArrayList();
        try (Connection con = DBConnection.getConnection();) {

            String requete = "select distinct co.id idCourseOffered, pc.id_course idCourse, c.code as coursecode, "
                    + " c.title as  coursetitle, pc.passing_grade as passing_grade, pc.nb_credits as nbCredits "
                    + "from program_course pc, course_offered co, course c "
                    + "where pc.id_program = ? and co. id_semester = ? and pc.id_course = co.id_course and pc.id_semester = ? and pc.id_course = c.id;";
            PreparedStatement stm0 = con.prepareStatement(requete);
            stm0.setInt(1, stud.getProgram_id());
            stm0.setInt(2, sem.getId());
            stm0.setInt(3, sem.getId());
            ResultSet result0 = stm0.executeQuery();
            String code, title, pass, name;
            int idCoff, idCourse;
            double grade = 0.0, nb_credits;
            while (result0.next()) {
                code = result0.getString("coursecode");
                name = stud.getName();
                title = result0.getString("coursetitle");
                idCoff = result0.getInt("idCourseOffered");
                idCourse = result0.getInt("idCourse");
                nb_credits = result0.getDouble("nbCredits");
                pass = result0.getString("passing_grade");
                Student_Course sc = new Student_Course(code, title, stud.getId(), idCoff, idCourse, nb_credits, name, grade, "F", pass);
                results.add(sc);
            }
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(CRUDCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<Student_Course> getStudentCourses(Course c) {
        ObservableList<Student_Course> results = FXCollections.observableArrayList();
        try (Connection con = DBConnection.getConnection();) {
            String req = "select distinct pe.name as persname, "
                    + " co.course_year as cyear, co.course_sem as csem, s.studentId as refNum "
                    + "from student s, course_offered co, student_course sc, course c, person pe "
                    + "where s.id_person = pe.id and co.id_course=c.id and  s.id = sc.id_student  and sc.id_course_offered = co.id and c.id = " + c.getId() + ";";

            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            String name, refNum;
            System.out.println(req);
            int studId;
            double grade = 0.0;
            while (result.next()) {
                name = result.getString("persname");
                refNum = result.getString("refNum");
                Student_Course std = new Student_Course("", "", 0, 0, 0, 0, name, grade, "F", "");
                std.setStudRef(refNum);
                std.setSemester(result.getString("csem") + " " + result.getInt("cyear"));
                results.add(std);
            }
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(CRUDCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<Student_Course> getStudentCourses(Course c, Semester sem) {
        ObservableList<Student_Course> results = FXCollections.observableArrayList();
        try (Connection con = DBConnection.getConnection();) {
            String req = "select distinct pe.name as persname, "
                    + " co.course_year as cyear, co.course_sem as csem, s.studentId as refNum "
                    + "from student s, course_offered co, student_course sc, course c, person pe "
                    + "where s.id_person = pe.id and co.id_course=c.id and  s.id = sc.id_student  and sc.id_course_offered = co.id "
                    + "and c.id = " + c.getId() + " and co.id_semester = " + sem.getId() + ";";
            System.out.println(req);
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            String name, refNum;
            double grade = 0.0;
            while (result.next()) {
                name = result.getString("persname");
                refNum = result.getString("refNum");
                Student_Course std = new Student_Course("", "", 0, 0, 0, 0, name, grade, "F", "");
                std.setStudRef(refNum);
                std.setSemester(result.getString("csem") + " " + result.getInt("cyear"));
                results.add(std);
            }
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(CRUDCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<Student_Course> getStudentCoursesTaken(Student stud, SchoolLevel sl, Semester sem) {
        ObservableList<Student_Course> results = FXCollections.observableArrayList();
        try (Connection con = DBConnection.getConnection();) {
            String req = "select distinct co.id as idCourseOffered, c.id as idCourse, c.code as coursecode, c.description as coursedescription, "
                    + "c.title as coursetitle, c.hours as coursehours, pc.passing_grade as passing_grade, pc.nb_credits as nbCredits, "
                    + "sc.grade as grade, sc.score "
                    + "from course_offered co, student_course sc, course c, program_course pc "
                    + "where sc.id_student = " + stud.getId() + " and sc.id_semester = " + sem.getId() + "  and co.id = sc.id_course_offered and "
                    + "sc.id_semester = co.id_semester and co.id_course = c.id and "
                    + "pc.id_course = co.id_course and co.id_semester = pc.id_semester and pc.id_program = " + stud.getProgram_id() + ";";
            System.out.println(req);
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            String code, title, pass, score, name;
            int idCoff, idCourse;
            double nb_credits;
            double grade;
            while (result.next()) {
                code = result.getString("coursecode");
                name = stud.getName();
                title = result.getString("coursetitle");
                idCoff = result.getInt("idCourseOffered");
                idCourse = result.getInt("idCourse");
                grade = result.getDouble("grade");
                nb_credits = result.getDouble("nbCredits");
                pass = result.getString("passing_grade");
                score = result.getString("score");
                results.add(new Student_Course(code, title, stud.getId(), idCoff, idCourse, nb_credits, name, grade, score, pass));
            }
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(CRUDCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean hasPassedCourse(int stud_id, int id_course) {
        boolean result = false;
        try (Connection con = DBConnection.getConnection();) {
            String req = "select * "
                    + "from student_course sc, course c, course_offered co "
                    + "where c.id = co.id_course and sc.id_course_offered = co.id and "
                    + "sc.id_student = " + stud_id + " and c.id = " + id_course + " and sc.decision = 'passed'; ";
            System.out.println(req);
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet ret = stm.executeQuery();
            if (ret.next()) {
                result = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(CRUDCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static ObservableList<Student_Course> getAllStudentGrades(Program p, CourseOffered co, Semester s) {
        ObservableList<Student_Course> results = FXCollections.observableArrayList();
        try (Connection con = DBConnection.getConnection();) {
            String req = "select distinct pe.name as studentname, s.id as sid, co.id as idCourseOffered, c.id as idCourse, c.code as coursecode, c.description as coursedescription,"
                    + "c.title as coursetitle, c.hours as coursehours, pc.passing_grade as passing_grade, pc.nb_credits as nbCredits, sc.grade as grade, sc.score "
                    + "from person pe, student s, course_offered co, student_course sc, course c, program_course pc, program p, semester sem "
                    + "where co.id_course=c.id and pc.id_course = co.id_course and p.id = pc.id_program and co.id = sc.id_course_offered "
                    + "and s.id = sc.id_student and sem.id = " + s.getId() + " and sem.id = sc.id_semester and pc.id_semester = sem.id "
                    + "and p.id = " + p.getId() + " and sem.s_year = " + s.getSYear() + " and sem.name = '" + s.getName() + "' "
                    + " and pe.id = s.id_person and co.id = " + co.getId() + ";";
            System.out.println(req);
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            String code, title, pass, score, name;
            int idCourse, idstudent;
            double grade, nb_credits;
            while (result.next()) {
                code = result.getString("coursecode");
                idstudent = result.getInt("sid");
                name = result.getString("studentname");
                title = result.getString("coursetitle");
                idCourse = result.getInt("idCourseOffered");
                grade = result.getDouble("grade");
                nb_credits = result.getDouble("nbCredits");
                pass = result.getString("passing_grade");
                score = result.getString("score");
                results.add(new Student_Course(code, title, idstudent, co.getId(), idCourse, nb_credits, name, grade, score, pass));
            }
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(CRUDCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<TakeTest> getStudentTestGrades(Program p, CourseOffered co, Semester s, Calendar calendar, ETestType testType) {
        ObservableList<TakeTest> results = FXCollections.observableArrayList();
        try (Connection con = DBConnection.getConnection();) {
            java.sql.Date javaSqlDate = new java.sql.Date(calendar.getTime().getTime());
            String req = "select distinct pe.name as studentname, s.id as sid,"
                    + " tt.score as score, tt.lettergrade as lettergrade, s.studentId as refNum, "
                    + " tt.ananonymous as anonymous, t.id as testId "
                    + "from person pe, student s, course_offered co, test t, take_test tt , student_course sc "
                    + "where t.course_offered_id = co.id and tt.id_test = t.id and sc.id_student = s.id and s.id = tt.id_student "
                    + "and sc.id_course_offered = " + co.getId() + " and sc.id_semester = " + s.getId() + " "
                    + " and co.course_year = " + s.getSYear() + " and co.course_sem = '" + s.getName() + "' "
                    + " and pe.id = s.id_person and co.id = " + co.getId() + " and t.test_date = '" + javaSqlDate + "';";
            System.out.println(req);
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            String anonymous, studentname, registration;
            char letterGrade;
            int idTest, idStudent;
            double score;
            while (result.next()) {
                anonymous = result.getString("anonymous");
                idStudent = result.getInt("sid");
                studentname = result.getString("studentname");
                registration = result.getString("refNum");
                idTest = result.getInt("testId");
                score = result.getDouble("score");
                letterGrade = result.getString("lettergrade").charAt(0);
                results.add(new TakeTest(idTest, idStudent, anonymous, score, letterGrade));
            }
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(CRUDCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<Student_Course> getStudentCoursesTakenperSemesterLevel(Student stud, Semester sem, SchoolLevel sl) {
        ObservableList<Student_Course> results = FXCollections.observableArrayList();
        try (Connection con = DBConnection.getConnection();) {

            String req = "select distinct pe.name as studentname, co.id as idCourseOffered, c.id as idCourse, c.code as coursecode, c.description as coursedescription, "
                    + "c.title as coursetitle, c.hours as coursehours, pc.passing_grade as passing_grade, pc.nb_credits as nbCredits, "
                    + "sc.grade as grade, sc.score "
                    + "from course_offered co, student_course sc, course c, program_course pc, student s, person pe "
                    + "where pe.id = s.id_person and s.id = sc.id_student and sc.id_student = " + stud.getId() + " and sc.id_semester = " + sem.getId() + "  "
                    + "and co.id = sc.id_course_offered and sc.id_semester = co.id_semester and "
                    + "co.id_course = c.id and pc.id_course = co.id_course and co.id_semester = pc.id_semester and "
                    + "pc.id_course = co.id_course and pc.id_program = " + stud.getProgram_id() + ";";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            String code, title, pass, score, name;
            int idCoff, idCourse;
            double grade, nb_credits;
            while (result.next()) {
                code = result.getString("coursecode");
                name = result.getString("studentname");
                title = result.getString("coursetitle");
                idCoff = result.getInt("idCourseOffered");
                idCourse = result.getInt("idCourse");
                grade = result.getDouble("grade");
                nb_credits = result.getDouble("nbCredits");
                pass = result.getString("passing_grade");
                score = result.getString("score");
                results.add(new Student_Course(code, title, stud.getId(), idCoff, idCourse, nb_credits, name, grade, score, pass));
            }
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(CRUDCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<StudentsProgDTO> getProgram(int idStudent) {
        ObservableList<StudentsProgDTO> results = FXCollections.observableArrayList();
        try (Connection con = DBConnection.getConnection();) {
            String req = "select s.id as id, p.name as pname, p.address as paddress, p.phonenumber as pnumber, p.email as pemail, p.gender as pgender, d.jour as djour, d.mois as dmois, d.annee as dyear, s.level as slevel, s.departement as sdepartement,"
                    + " s.studentId as sId, s.parent_info as parent_info, s.entrance_year as entrance_year, s.id_program as program_id, dept.name as deptname, prog.title as ptitle  "
                    + "from mydate d, person p, student s, department dept, student_department sd, program prog  "
                    + "where (p.id_date_birth = d.id) and (s.id_person = p.id) and (s.id = sd.id_student) and "
                    + "(sd.id_department = dept.id) and (s.id_program = prog.id) and s.id = " + idStudent + ";";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            int id;
            String thename, reference, level, program, entrance_year;
            while (result.next()) {
                id = result.getInt("id");
                thename = result.getString("pname");
                reference = result.getString("sId");
                level = result.getString("slevel");
                program = result.getString("ptitle");
                entrance_year = result.getString("entrance_year");
                results.add(new StudentsProgDTO(id, thename, reference, level, program, entrance_year));
            }
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(CRUDCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Semester getSemesterFromList(String name, int semYear, ObservableList<Semester> semesters) {
        for (Semester s : semesters) {
            if (s.getName().equals(name) && s.getSYear() == semYear) {
                return s;
            }
        }
        return null;
    }

    public static void openPDFFile(String path) {
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(path);
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {

            }
        }
    }

    public static StringConverter<LocalDate> getDateConverter() {
        // Converter
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter
                    = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        return converter;
    }

    public static String getLettergrade(double grade) {
        if (grade <= 100.0 && grade >= 90.0) {
            return "A";
        } else if (grade <= 90.0 && grade >= 80.0) {
            return "B";
        } else if (grade <= 80.0 && grade >= 70.0) {
            return "C";
        } else if (grade <= 70.0 && grade >= 60.0) {
            return "D";
        } else {
            return "F";
        }
    }

    public static ObservableList<TestGradeDTO> getStudentTestGrades(ObservableList<Student_Course> tcourses, int semId) {
        ObservableList<TestGradeDTO> results = FXCollections.observableArrayList();
        try (Connection con = DBConnection.getConnection();) {
            for (Student_Course sc : tcourses) {
                String req = "select distinct c.code as coursecode, "
                        + "c.title as coursetitle, "
                        + "t.test_type as testtype, "
                        + "tt.score as grade, "
                        + "tt.lettergrade as lettergrade "
                        + " from course c, course_offered co, student_course sc, test t, take_test tt "
                        + " where c.id = co.id_course and co.id = sc.id_course_offered and sc.id_course_offered = t.course_offered_id and "
                        + " t.id = tt.id_test  and tt.id_student = sc.id_student "
                        + " and sc.id_student = " + sc.getIdStudent() + " and "
                        + " sc.id_course_offered = " + sc.getIdCourseOffered() + " and "
                        + " sc.id_semester = " + semId + ";";
                System.out.println(req);
                PreparedStatement stm = con.prepareStatement(req);
                ResultSet result = stm.executeQuery();
                String courseName;
                String testType;
                double grade;
                char letterGrade;
                while (result.next()) {
                    courseName = result.getString("coursecode") + " - " + result.getString("coursetitle");
                    testType = result.getString("testtype");
                    grade = result.getDouble("grade");
                    letterGrade = result.getString("lettergrade").charAt(0);
                    results.add(new TestGradeDTO(courseName, testType, grade, letterGrade));
                }
            }
            Collections.sort(results);

        } catch (SQLException ex) {
            Logger.getLogger(CRUDCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }

    public static ObservableList<Taught_By> getSchedulesDay(Program p, String d, Semester sem, CourseOffered courseOff, EDay day) {
        ObservableList<Taught_By> results = FXCollections.observableArrayList();
        try (Connection con = DBConnection.getConnection();) {
            String req = "SELECT t.id_faculty as teacherId, t.id_course_offered as idCourseOffered, t.id_program as programId, t.id_room as roomId, "
                    + "t.start_time as startTime, t.end_time as endTime, t.date_course as courseDate, c.title as courseTitle, pe.name as facultyName "
                    + "FROM taugh_by t, faculty f, course_offered co, program p, room r, program_course pc, course c, employee e, person pe "
                    + "WHERE f.id = t.id_faculty and  co.id = t.id_course_offered and p.id = t.id_program and r.id = t.id_room and c.id = co.id_course and c.id = pc.id_course "
                    + "and e.id = f.id_employee and pe.id = e.id_person and t.id_course_offered = " + courseOff.getId() + " "
                    + "t.id_program = " + p.getId() + " and co.course_year = '" + sem.getSYear() + "' and co.course_sem = '" + sem.getName() + "';";
            System.out.println(req);
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            int idFaculty, idCourseOff, idProgram, idRoom;
            String startTime, endTime, courseDate, courseTitle, facultyName;
            while (result.next()) {
                idFaculty = result.getInt("teacherId");
                idCourseOff = result.getInt("idCourseOffered");
                idProgram = result.getInt("id_program");
                idRoom = result.getInt("roomId");
                startTime = result.getString("startTime");
                endTime = result.getString("endTime");
                courseDate = result.getString("courseDate");
                facultyName = result.getString("courseTitle");
                courseTitle = result.getString("facultyName");
                results.add(new Taught_By(idFaculty, idCourseOff, idProgram, idRoom, facultyName, courseTitle, startTime, endTime, day, courseDate));
            }
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<Taught_By> getTaughBys(Program p, String d, Semester sem, CourseOffered courseOff) {
        ObservableList<Taught_By> results = FXCollections.observableArrayList();
        try (Connection con = DBConnection.getConnection();) {
            String req = "SELECT DISTINCT t.id_faculty as teacherId, t.id_course_offered as idCourseOffered, t.id_program as programId, t.id_room as roomId, "
                    + "t.start_time as startTime, t.end_time as endTime,t.day_course as courseDay, t.date_course as courseDate, c.title as courseTitle, pe.name as facultyName "
                    + "FROM taugh_by t, faculty f, course_offered co, program p, room r, program_course pc, course c, employee e, person pe "
                    + "WHERE f.id = t.id_faculty and  co.id = t.id_course_offered and p.id = t.id_program and r.id = t.id_room and c.id = co.id_course and c.id = pc.id_course "
                    + "and e.id = f.id_employee and pe.id = e.id_person and t.id_course_offered = " + courseOff.getId() + " and "
                    + "t.id_program = " + p.getId() + " and co.course_year = '" + sem.getSYear() + "' and co.course_sem = '" + sem.getName() + "';";
            System.out.println(req);
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            int idFaculty, idCourseOff, idRoom;
            String startTime, endTime, courseDate, courseTitle, facultyName;
            EDay courseDay;
            while (result.next()) {
                idFaculty = result.getInt("teacherId");
                idCourseOff = result.getInt("idCourseOffered");
                idRoom = result.getInt("roomId");
                startTime = result.getString("startTime");
                endTime = result.getString("endTime");
                courseDate = result.getString("courseDate");
                courseDay = EDay.getValue(result.getString("courseDay"));
                facultyName = result.getString("facultyName");
                courseTitle = result.getString("courseTitle");
                Taught_By tby = new Taught_By(idFaculty, idCourseOff, p.getId(), idRoom, facultyName, courseTitle, startTime, endTime, courseDay, courseDate);
                tby.setPlaceName(Tools.getRoom(idRoom));
                results.add(tby);
            }
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<Schedule> getSchedules(Program p, Semester sem, CourseOffered courseOff) {
        ObservableList<Schedule> results = FXCollections.observableArrayList();
        try (Connection con = DBConnection.getConnection();) {
            String req = "";
            if (courseOff == null) {
                req = "SELECT DISTINCT f.title as fTitle, s.id as scheduleId, s.id_course_offered as courseOffId, c.title as courseTitle, c.id as courseId, s.id_room as roomId, r.name as roomName, s.id_time_slot as timeSlotId,"
                        + "t.startTime as startTime, t.endTime as endTime, t.dateCourse as courseDate, t.dayCourse as courseDay, f.id as facultyId, pe.name as facultyName "
                        + "FROM faculty f, schedule s, course c, course_offered co, room r, person pe, employee e, timeslot t, taugh_by tby, program p "
                        + "WHERE f.id_employee = e.id and pe.id = e.id_person and c.id = co.id_course and co.id = s.id_course_offered and p.id = tby.id_program and "
                        + " r.id = s.id_room and t.id = s.id_time_slot and f.id = tby.id_faculty and co.id = tby.id_course_offered  and "
                        + "tby.id_program = " + p.getId() + " and co.course_year = '" + sem.getSYear() + "' and tby.type_faculty = 'Lecturer' and co.course_sem = '" + sem.getName() + "';";
            } else {
                req = "SELECT DISTINCT f.title as fTitle, s.id as scheduleId, s.id_course_offered as courseOffId, c.title as courseTitle, c.id as courseId, s.id_room as roomId, r.name as roomName, s.id_time_slot as timeSlotId,"
                        + "t.startTime as startTime, t.endTime as endTime, t.dateCourse as courseDate, t.dayCourse as courseDay, f.id as facultyId, pe.name as facultyName "
                        + "FROM faculty f, schedule s, course c, course_offered co, room r, person pe, employee e, timeslot t, taugh_by tby, program p "
                        + "WHERE f.id_employee = e.id and pe.id = e.id_person and c.id = co.id_course and co.id = s.id_course_offered and p.id = tby.id_program and "
                        + " r.id = s.id_room and t.id = s.id_time_slot and f.id = tby.id_faculty and co.id = tby.id_course_offered and s.id_course_offered = " + courseOff.getId() + " and "
                        + "tby.id_program = " + p.getId() + " and co.course_year = '" + sem.getSYear() + "' and tby.type_faculty = 'Lecturer' and co.course_sem = '" + sem.getName() + "';";
            }

            System.out.println(req);
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            int idFaculty, idCourseOff, idRoom, idSchedule, idtimeslot, idCourse;
            String startTime, endTime, courseDate, courseTitle, facultyName;
            EDay courseDay;
            while (result.next()) {
                idFaculty = result.getInt("facultyId");
                idtimeslot = result.getInt("timeSlotId");
                idSchedule = result.getInt("scheduleId");
                idCourseOff = result.getInt("courseOffId");
                idCourse = result.getInt("courseId");
                idRoom = result.getInt("roomId");
                startTime = result.getString("startTime");
                endTime = result.getString("endTime");
                courseDate = result.getString("courseDate");
                courseDay = EDay.getValue(result.getString("courseDay"));
                facultyName = result.getString("fTitle") + ". " + result.getString("facultyName");
                String possibleAssist = getAssistant(idCourseOff, sem.getName(), sem.getSYear());
                if (!possibleAssist.contains("Assigned")) {
                    facultyName += " / " + possibleAssist;
                }
                courseTitle = result.getString("courseTitle");
                Schedule sche = new Schedule(idSchedule, idCourse, idCourseOff, courseTitle, idRoom, Tools.getRoom(idRoom), idtimeslot, courseDay, courseDate, startTime, endTime, idFaculty, facultyName);
                results.add(sche);
            }
            Iterator<Schedule> iter = results.iterator();
            while (iter.hasNext()) {
                Schedule s = iter.next();
                if (s.getFacultyName().contains("Assigned")) {
                    iter.remove();
                }
            }
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<Schedule> getSchedulesWithDate(Program p, String d, Semester sem, CourseOffered courseOff) {
        ObservableList<Schedule> results = FXCollections.observableArrayList();
        try (Connection con = DBConnection.getConnection();) {
            String req = "";
            if (courseOff == null) {
                req = "SELECT DISTINCT f.title as fTitle, s.id as scheduleId, s.id_course_offered as courseOffId, c.title as courseTitle, c.id as courseId, s.id_room as roomId, r.name as roomName, s.id_time_slot as timeSlotId,"
                        + "t.startTime as startTime, t.endTime as endTime, t.dateCourse as courseDate, t.dayCourse as courseDay, f.id as facultyId, pe.name as facultyName "
                        + "FROM faculty f, schedule s, course c, course_offered co, room r, person pe, employee e, timeslot t, taugh_by tby, program p "
                        + "WHERE f.id_employee = e.id and pe.id = e.id_person and c.id = co.id_course and co.id = s.id_course_offered and p.id = tby.id_program "
                        + "and r.id = s.id_room and t.id = s.id_time_slot and f.id = tby.id_faculty and co.id = tby.id_course_offered  and "
                        + "tby.id_program = " + p.getId() + " and co.course_year = '" + sem.getSYear() + "' and tby.type_faculty = 'Lecturer' and co.course_sem = '" + sem.getName() + "' and t.dateCourse = '" + d + "';";

            } else {
                req = "SELECT DISTINCT f.title as fTitle, s.id as scheduleId, s.id_course_offered as courseOffId, c.title as courseTitle, c.id as courseId, s.id_room as roomId, r.name as roomName, s.id_time_slot as timeSlotId,"
                        + "t.startTime as startTime, t.endTime as endTime, t.dateCourse as courseDate, t.dayCourse as courseDay, f.id as facultyId, pe.name as facultyName "
                        + "FROM faculty f, schedule s, course c, course_offered co, room r, person pe, employee e, timeslot t, taugh_by tby, program p "
                        + "WHERE f.id_employee = e.id and pe.id = e.id_person and c.id = co.id_course and co.id = s.id_course_offered and p.id = tby.id_program "
                        + "and r.id = s.id_room and t.id = s.id_time_slot and f.id = tby.id_faculty and co.id = tby.id_course_offered and s.id_course_offered = " + courseOff.getId() + " and "
                        + "tby.id_program = " + p.getId() + " and co.course_year = '" + sem.getSYear() + "' and tby.type_faculty = 'Lecturer' and co.course_sem = '" + sem.getName() + "' and t.dateCourse = '" + d + "';";

            }
            System.out.println(req);
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            int idFaculty, idCourseOff, idRoom, idSchedule, idtimeslot, idCourse;
            String startTime, endTime, courseDate, courseTitle, facultyName;
            EDay courseDay;
            while (result.next()) {
                idFaculty = result.getInt("facultyId");
                idtimeslot = result.getInt("timeSlotId");
                idSchedule = result.getInt("scheduleId");
                idCourseOff = result.getInt("courseOffId");
                idCourse = result.getInt("courseId");
                idRoom = result.getInt("roomId");
                startTime = result.getString("startTime");
                endTime = result.getString("endTime");
                courseDate = result.getString("courseDate");
                courseDay = EDay.getValue(result.getString("courseDay"));
                facultyName = result.getString("fTitle") + ". " + result.getString("facultyName");
                String possibleAssist = getAssistant(idCourseOff, sem.getName(), sem.getSYear());
                if (!possibleAssist.contains("Assigned")) {
                    facultyName += " / " + possibleAssist;
                }
                courseTitle = result.getString("courseTitle");
                Schedule sche = new Schedule(idSchedule, idCourse, idCourseOff, courseTitle, idRoom, Tools.getRoom(idRoom), idtimeslot, courseDay, courseDate, startTime, endTime, idFaculty, facultyName);
                results.add(sche);
            }
            Iterator<Schedule> iter = results.iterator();
            while (iter.hasNext()) {
                Schedule s = iter.next();
                if (s.getFacultyName().contains("Assigned")) {
                    iter.remove();
                }
            }
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static CourseOffered getCourseOffered(int courseOffId) {
        CourseOffered co = new CourseOffered();
        try (Connection con = DBConnection.getConnection();) {
            String req = "SELECT DISTINCT c.id as cid, c.id_course as courseId, c.course_year as courseYear, c.course_sem as courseSem, c.syllabus as courseSyllabus, "
                    + "t.id_faculty as facultyId, t.type_faculty as facultyType, p.name as lecturer"
                    + "FROM course_offered c, taugh_by t, faculty f, employee e, person p"
                    + "WHERE c.id = t.id_course_offered and c.course_sem = t.sem_name and c.course_year = t.sem_year"
                    + "and f.id = t.id_faculty and e.id = f.id_employee and p.id = e.id_person and c.id = " + courseOffId + ";";
            System.out.println(req);
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            int courseId, courseYear, facultyId;
            String courseSem, syllabus, lecturer;
            while (result.next()) {
                courseId = result.getInt("courseId");
                courseYear = result.getInt("courseYear");
                courseSem = result.getString("courseSem");
                syllabus = result.getString("courseSyllabus");
                facultyId = result.getInt("facultyId");
                lecturer = result.getString("lecturer");
                co = new CourseOffered(courseOffId, courseId, courseYear, courseSem, syllabus);
                co.setId_lecturer(facultyId);
                co.setLecturer(lecturer);
            }
            return co;
        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Room getRoom(int roomId) {

        Room room = new Room();
        try (Connection con = DBConnection.getConnection();) {
            String req = "SELECT DISTINCT r.name as roomName, r.capacity as roomCapacity "
                    + "FROM room r "
                    + "WHERE  r.id = " + roomId + ";";
            System.out.println(req);
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            int capacity;
            String roomName;
            while (result.next()) {
                capacity = result.getInt("roomCapacity");
                roomName = result.getString("roomName");
                room = new Room(roomId, roomName, capacity);
            }
            return room;
        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static int getRoomID(String roomName) {
        try (Connection con = DBConnection.getConnection();) {
            String req = "SELECT DISTINCT id  "
                    + "FROM room "
                    + "WHERE  name = '" + roomName + "';";
            System.out.println(req);
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            int roomID = 0;
            while (result.next()) {
                roomID = result.getInt("id");
            }
            return roomID;
        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public static void insertDumpTaugh(int course, int program_id, int lecturer, String type, String sem, int year) {
        try (Connection con = DBConnection.getConnection();) {
            String req = "insert into taugh_by(id_faculty,id_course_offered,id_program,type_faculty,sem_name,sem_year) values(?,?,?,?,?,?)";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, lecturer);
            stm.setInt(2, course);
            stm.setInt(3, program_id);
            stm.setString(4, type);
            stm.setString(5, sem);
            stm.setInt(6, year);
            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(CRUDCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Semester findSemester(String name, int year) {
        Semester sem = new Semester();
        try (Connection con = DBConnection.getConnection();) {
            String req = "select * from semester where name = '" + name + "' and s_year = " + year + ";";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            if (result.next()) {
                int i = result.getInt("id");
                sem.setName(name);
                sem.setSYear(year);
                sem.setId(i);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sem;
    }

    public static String getAcademicYear(Semester sem) {
        if (sem.getName().equalsIgnoreCase("Spring")) {
            return (sem.getSYear() - 1) + " - " + sem.getSYear();
        } else {
            return sem.getSYear() + " - " + (sem.getSYear() + 1);
        }
    }

    public static Pair<Double, Double> calculatePoints(int studentId, DumyDouble total) {
        List<Student_Course> courses = new ArrayList<>();
        Pair<Double, Double> output = new Pair<>(0.0, 0.0);
        try (Connection con = DBConnection.getConnection();) {
            String req = "select distinct sc.id_student as id_student, sc.id_course_offered as id_course_offered, co.id_course as id_course, sc.grade as grade, "
                    + " sc.score as lettergrade, pc.nb_credits as nb_credits, concat(co.course_sem, ' ', co.course_year) as semester, pc.passing_grade as passing_grade  "
                    + "from student_course sc, program_course pc, student s, course_offered co "
                    + "where pc.id_course = co.id_course and sc.id_course_offered = co.id and pc.id_program = s.id_program "
                    + " and sc.id_student = s.id and pc.nb_credits != 1.5  and pc.id_semester = sc.id_semester and sc.grade > 0 and s.id = " + studentId + ";";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            System.out.println("Here");
            System.out.println(req);
            while (result.next()) {
                Student_Course c = new Student_Course();
                c.setIdStudent(studentId);
                c.setIdCourseOffered(result.getInt("id_course_offered"));
                c.setIdCourse(result.getInt("id_course"));
                c.setNb_credits(result.getDouble("nb_credits"));
                c.setScore(result.getDouble("grade"));
                c.setGrade(result.getString("lettergrade"));
                c.setSemester(result.getString("semester"));
                c.setPassing_grade(result.getString("passing_grade"));
                courses.add(c);
            }
            System.out.println("The number of courses is: " + courses.size());
            boolean enter;
            do {
                enter = false;
                Student_Course scc = new Student_Course();
                if (!courses.isEmpty()) {
                    scc = courses.get(0);
                }
                Iterator<Student_Course> it = courses.iterator();
                List<Student_Course> gradeForACourse = new ArrayList<>();
                while (it.hasNext()) {
                    enter = true;
                    Student_Course sc1 = it.next();

                    if (scc.getIdCourse() == sc1.getIdCourse()) {
                        gradeForACourse.add(sc1);
                        it.remove();
                    }
                }
                if (!gradeForACourse.isEmpty()) {
                    Pair<Double, Double> gradePair = getUtilGPA(gradeForACourse, total);
                    output.setLeft(output.getLeft() + gradePair.getLeft());
                    output.setRight(output.getRight() + gradePair.getRight());
                    System.out.println(total.getValue());
                }
            } while (enter == true);
        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }

    public static Pair<Double, Double> calculatePointsPKF(int studentId, DumyDouble total) {
        List<Student_Course> courses = new ArrayList<>();
        Pair<Double, Double> output = new Pair<>(0.0, 0.0);
        try (Connection con = DBConnection.getConnection();) {
            String req = "select distinct sc.id_student as id_student, sc.id_course_offered as id_course_offered, co.id_course as id_course, sc.grade as grade, "
                    + " sc.score as lettergrade, pc.nb_credits as nb_credits, concat(co.course_sem, ' ', co.course_year) as semester, pc.passing_grade as passing_grade  "
                    + "from student_course sc, program_course pc, student s, course_offered co "
                    + "where pc.id_course = co.id_course and sc.id_course_offered = co.id and pc.id_program = s.id_program "
                    + " and sc.id_student = s.id and pc.id_semester = sc.id_semester and sc.grade > 0 and s.id = " + studentId + " group by co.id;";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            while (result.next()) {
                Student_Course c = new Student_Course();
                c.setIdStudent(studentId);
                c.setIdCourseOffered(result.getInt("id_course_offered"));
                c.setIdCourse(result.getInt("id_course"));
                c.setNb_credits(result.getDouble("nb_credits"));
                c.setScore(result.getDouble("grade"));
                c.setGrade(result.getString("lettergrade"));
                c.setSemester(result.getString("semester"));
                c.setPassing_grade(result.getString("passing_grade"));
                courses.add(c);
            }
            boolean enter;
            do {
                enter = false;
                Student_Course scc = new Student_Course();
                if (!courses.isEmpty()) {
                    scc = courses.get(0);
                }
                Iterator<Student_Course> it = courses.iterator();
                List<Student_Course> gradeForACourse = new ArrayList<>();
                while (it.hasNext()) {
                    enter = true;
                    Student_Course sc1 = it.next();

                    if (scc.getIdCourse() == sc1.getIdCourse()) {
                        gradeForACourse.add(sc1);
                        it.remove();
                    }
                }
                if (!gradeForACourse.isEmpty()) {
                    Pair<Double, Double> gradePair = getUtilGPA(gradeForACourse, total);
                    output.setLeft(output.getLeft() + gradePair.getLeft());
                    output.setRight(output.getRight() + gradePair.getRight());
                    System.out.println(total.getValue());
                }
            } while (enter == true);
        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }

    public static Pair<Double, Double> calculatePointsPKFPeriodic(int studentId, DumyDouble total, int idStartSem, int idEndSem) {
        List<Student_Course> courses = new ArrayList<>();
        Pair<Double, Double> output = new Pair<>(0.0, 0.0);
        try (Connection con = DBConnection.getConnection();) {
            String req = "select distinct sc.id_student as id_student, sc.id_course_offered as id_course_offered, co.id_course as id_course, sc.grade as grade, "
                    + " sc.score as lettergrade, pc.nb_credits as nb_credits, concat(co.course_sem, ' ', co.course_year) as semester, pc.passing_grade as passing_grade  "
                    + "from student_course sc, program_course pc, student s, course_offered co "
                    + "where pc.id_course = co.id_course and sc.id_course_offered = co.id and pc.id_program = s.id_program "
                    + " and sc.id_student = s.id and sc.grade > 0 and s.id = " + studentId + " "
                    + "and pc.id_semester = sc.id_semester and co.id_semester >= " + idStartSem + " and co.id_semester <= " + idEndSem + " group by co.id;";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            while (result.next()) {
                Student_Course c = new Student_Course();
                c.setIdStudent(studentId);
                c.setIdCourseOffered(result.getInt("id_course_offered"));
                c.setIdCourse(result.getInt("id_course"));
                c.setNb_credits(result.getDouble("nb_credits"));
                c.setScore(result.getDouble("grade"));
                c.setGrade(result.getString("lettergrade"));
                c.setSemester(result.getString("semester"));
                c.setPassing_grade(result.getString("passing_grade"));
                courses.add(c);
            }
            boolean enter;
            do {
                enter = false;
                Student_Course scc = new Student_Course();
                if (!courses.isEmpty()) {
                    scc = courses.get(0);
                }
                Iterator<Student_Course> it = courses.iterator();
                List<Student_Course> gradeForACourse = new ArrayList<>();
                while (it.hasNext()) {
                    enter = true;
                    Student_Course sc1 = it.next();

                    if (scc.getIdCourse() == sc1.getIdCourse()) {
                        gradeForACourse.add(sc1);
                        it.remove();
                    }
                }
                if (!gradeForACourse.isEmpty()) {
                    Pair<Double, Double> gradePair = getUtilGPA(gradeForACourse, total);
                    output.setLeft(output.getLeft() + gradePair.getLeft());
                    output.setRight(output.getRight() + gradePair.getRight());
                    System.out.println(total.getValue());
                }
            } while (enter == true);
        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }

    public static Pair<DumyDouble, Double> calculateGPA(int studentId) {
        DumyDouble total = new DumyDouble();
        Pair<Double, Double> result = calculatePoints(studentId, total);
        Pair<DumyDouble, Double> r = new Pair<>();
        r.setLeft(total);
        if (result.getLeft() == 0) {
            r.setRight(0.0);
            return r;
        } else {
            r.setRight(((double) result.getRight()) / result.getLeft());
            return r;
        }
    }

    public static Pair<DumyDouble, Double> calculateGPAPKF(int studentId) {
        DumyDouble total = new DumyDouble();
        Pair<Double, Double> result = calculatePointsPKF(studentId, total);
        Pair<DumyDouble, Double> r = new Pair<>();
        r.setLeft(total);
        if (result.getLeft() == 0) {
            r.setRight(0.0);
            return r;
        } else {
            r.setRight(((double) result.getRight()) / result.getLeft());
            return r;
        }
    }

    public static Pair<DumyDouble, Double> calculateGPAPKFPeriodic(int studentId, int idStart, int idEnd) {
        DumyDouble total = new DumyDouble();
        Pair<Double, Double> result = calculatePointsPKFPeriodic(studentId, total, idStart, idEnd);
        Pair<DumyDouble, Double> r = new Pair<>();
        r.setLeft(total);
        if (result.getLeft() == 0) {
            r.setRight(0.0);
            return r;
        } else {
            r.setRight(((double) result.getRight()) / result.getLeft());
            return r;
        }
    }

    private static Pair<Double, Double> getUtilGPA(List<Student_Course> courses, DumyDouble total) {
        if (courses.size() == 1) {
            total.setValue(total.getValue() + courses.get(0).getScore() * courses.get(0).getNb_credits());
            return new Pair(courses.get(0).getNb_credits(),
                    calculatePoint(courses.get(0).getGrade().charAt(0), courses.get(0).getNb_credits()));
        } else {
            Student_Course start = theChosen(courses);
            total.setValue(total.getValue() + start.getScore() * start.getNb_credits());
            return new Pair(start.getNb_credits(), calculatePoint(start.getGrade().charAt(0), start.getNb_credits()));
        }
    }

    private static Student_Course theChosen(List<Student_Course> courses) {
        int max_year = 0;
        Student_Course c = null;
        for (Student_Course sc : courses) {
            int c_year = Integer.parseInt(sc.getSemester().substring(sc.getSemester().lastIndexOf(" ") + 1));
            if ((c_year > max_year)) {
                c = sc;
                max_year = c_year;
            } else if (c_year == max_year) {
                if (sc.getSemester().startsWith("Fall")) {
                    c = sc;
                }
            }
            if (sc.getPassing_grade().equalsIgnoreCase("passed")) {
                return sc;
            }
        }
        return c;
    }

    private static double calculatePoint(char score, double credits) {
        switch (score) {
            case 'A':
                return credits * 4;
            case 'B':
                return credits * 3;
            case 'C':
                return credits * 2;
            case 'D':
                return credits * 1;
            default:
                return 0;
        }
    }

    public static boolean connectAdmin(String login, String password) {
        //    DBConnection.writeToConfigFile(login, password, "myurl");
        try (Connection con = DBConnection.connect()) {
            String req = "select * from users where login = '" + login + "' and password = md5('" + password + "');";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            if (result.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static ObservableList<ProgramCourses> getCoursesOfSem(String semName, int sYear, int programId) {
        try (Connection con = DBConnection.getConnection();) {
            ObservableList<ProgramCourses> liste = FXCollections.observableArrayList();
            ResultSet result;
            if (programId == 0) {
                String req = "select distinct pc.id_program as id_program, pc.id_course as id_course, "
                        + " pc.core as core, pc.id_level as id_level, pc.nb_credits as nb_credits, pc.passing_grade as passing_grade "
                        + "from program_course pc, course_offered co "
                        + " where pc.id_course = co.id_course and co.course_year = ? and co.course_sem = ? order by id_level;";

                PreparedStatement stm = con.prepareStatement(req);
                stm.setInt(1, sYear);
                stm.setString(2, semName);
                System.out.println(stm);
                result = stm.executeQuery();
            } else {
                String req = "select distinct pc.id_program as id_program, pc.id_course as id_course, "
                        + " pc.core as core, pc.id_level as id_level, pc.nb_credits as nb_credits, pc.passing_grade as passing_grade "
                        + "from program_course pc, course_offered co "
                        + " where pc.id_course = co.id_course and co.course_year = ? and co.course_sem = ? and pc.id_program = ? order by id_level; ";
                PreparedStatement stm = con.prepareStatement(req);
                stm.setInt(1, sYear);
                stm.setString(2, semName);
                stm.setInt(3, programId);
                System.out.println(stm);
                result = stm.executeQuery();
            }

            int id_course;
            double nb_credits;
            String core;
            int id_level, id_program;
            SchoolLevel level;
            char passing_grade;
            Course course;
            while (result.next()) {
                id_course = result.getInt("id_course");
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
                liste.add(pcourse);
            }
            con.close();
            return liste;

        } catch (SQLException ex) {
            Logger.getLogger(ManageProgramCourses.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<Student> getStudentsSemester(int id_sem) {
        try (Connection con = DBConnection.getConnection();) {
            ObservableList<Student> liste = FXCollections.observableArrayList();
            String req = "select distinct pe.name as studentName, s.studentId as refNum, "
                    + " s.entrance_year as entrance_year, lev.name as studentLevel "
                    + " from person pe, student s, student_level sl, schoollevel lev "
                    + " where pe.id = s.id_person and s.id = sl.id_student and sl.id_level = lev.id and sl.id_semester = " + id_sem + ";";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            String refNum, name, level, entranceYear;
            while (result.next()) {
                refNum = result.getString("refNum");
                name = result.getString("studentName");
                level = result.getString("studentLevel");
                entranceYear = result.getString("entrance_year");
                Student stud = new Student();
                stud.setEntrance_year(entranceYear);
                stud.setName(name);
                stud.setStuID(refNum);
                stud.setStatus(level);
                liste.add(stud);
            }
            con.close();
            return liste;

        } catch (SQLException ex) {
            Logger.getLogger(ManageProgramCourses.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<Student> getStudentsSemester(int id_sem, int id_program) {
        try (Connection con = DBConnection.getConnection();) {
            ObservableList<Student> liste = FXCollections.observableArrayList();
            String req = "select distinct pe.name as studentName, s.studentId as refNum, "
                    + " s.entrance_year as entrance_year, lev.name as studentLevel "
                    + " from person pe, student s, student_level sl, schoollevel lev "
                    + " where pe.id = s.id_person and s.id = sl.id_student and sl.id_level = lev.id "
                    + "and sl.id_semester = " + id_sem + " and s.id_program = " + id_program + ";";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            String refNum, name, level, entranceYear;
            while (result.next()) {
                refNum = result.getString("refNum");
                name = result.getString("studentName");
                level = result.getString("studentLevel");
                entranceYear = result.getString("entrance_year");
                Student stud = new Student();
                stud.setEntrance_year(entranceYear);
                stud.setName(name);
                stud.setStuID(refNum);
                stud.setStatus(level);
                liste.add(stud);
            }
            con.close();
            return liste;

        } catch (SQLException ex) {
            Logger.getLogger(ManageProgramCourses.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<Student> getStudentsSemester(int id_sem, int id_program, int idLevel) {
        try (Connection con = DBConnection.getConnection();) {
            ObservableList<Student> liste = FXCollections.observableArrayList();
            String req = "select distinct pe.name as studentName, s.studentId as refNum, "
                    + " s.entrance_year as entrance_year, lev.name as studentLevel "
                    + " from person pe, student s, student_level sl, schoollevel lev "
                    + " where pe.id = s.id_person and s.id = sl.id_student and sl.id_level = lev.id "
                    + "and sl.id_semester = " + id_sem + " and s.id_program = " + id_program + " and lev.id = " + idLevel + ";";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            String refNum, name, level, entranceYear;
            while (result.next()) {
                refNum = result.getString("refNum");
                name = result.getString("studentName");
                level = result.getString("studentLevel");
                entranceYear = result.getString("entrance_year");
                Student stud = new Student();
                stud.setEntrance_year(entranceYear);
                stud.setName(name);
                stud.setStuID(refNum);
                stud.setStatus(level);
                liste.add(stud);
            }
            con.close();
            return liste;

        } catch (SQLException ex) {
            Logger.getLogger(ManageProgramCourses.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static CRUDSchoolFaculty loadSchoolFacDB() {
        try (Connection con = DBConnection.getConnection();) {
            CRUDSchoolFaculty l = new CRUDSchoolFaculty();
            String req = "select sf.id as sfid, sf.title as sfname, sf.id_head as sfidhead "
                    + "from schoolfaculty sf, staff s "
                    + "where sf.id_head = s.id;";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery(req);
            int id, id_head;
            String name;
            while (result.next()) {
                id = result.getInt("sfid");
                name = result.getString("sfname");
                id_head = result.getInt("sfidhead");
                l.addDep(new SchoolFaculty(id, name, id_head));
            }
            return l;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String getAssistant(int course_offered_id, String sem_name, int sem_year) {
        String output = "";
        try (Connection con = DBConnection.getConnection();) {
            String req = "SELECT DISTINCT f.title as fTitle, f.id as facultyId, pe.name as facultyName "
                    + "FROM faculty f,taugh_by tby , person pe, employee e "
                    + "WHERE f.id_employee = e.id and pe.id = e.id_person and tby.id_course_offered = " + course_offered_id + " and "
                    + "tby.sem_name = '" + sem_name + "' and tby.sem_year = " + sem_year + "  and tby.type_faculty = 'Assistant' and tby.id_faculty = f.id;";
            System.out.println(req);
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();

            if (result.next()) {
                output += result.getString("fTitle") + ". " + result.getString("facultyName");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }

    public static Semester getCurrentSemester() {
        try (Connection con = DBConnection.getConnection();) {
            String req = "select * "
                    + "from semester "
                    + "where s_year = (select max(s_year) from semester);";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery(req);
            int id, sYear;
            String name;
            Semester s = null;
            while (result.next()) {
                id = result.getInt("id");
                name = result.getString("name");
                sYear = result.getInt("s_year");
                s = new Semester(id, name, sYear);
                if (s.getName().equals("Fall")) {
                    return s;
                }
            }
            return s;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void checkAndRegister(int idStudent, int idCourseOffered) {
        try (Connection con = DBConnection.getConnection();) {
            String req = "SELECT id "
                    + "FROM schedule  "
                    + "WHERE id_course_offered =  " + idCourseOffered + ";";
            System.out.println(req);
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            int idSchedule;

            while (result.next()) {
                idSchedule = result.getInt("id");
                String req2 = "insert into student_attendance(id_student, id_schedule) values (?,?);";
                PreparedStatement stm2 = con.prepareStatement(req2);
                stm2.setInt(1, idStudent);
                stm2.setInt(2, idSchedule);
                stm2.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(MyTools.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ObservableList<Student> getStudentForRel(int idSemester, int id_co, int idCourse, int id_program) {
        ObservableList<Student> results = FXCollections.observableArrayList();
        try (Connection con = DBConnection.getConnection();) {
            String req = "select s.id as id, p.name as pname,"
                    + " s.studentId as sId  "
                    + "from student s, person p, course_offered co, student_course sc "
                    + "where p.id = s.id_person and sc.id_course_offered = ? and co.id = sc.id_course_offered"
                    + " and co.id_course = ? and sc.id_student = s.id  and s.id_program = ? and sc.id_semester = ? order by p.name;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, id_co);
            stm.setInt(2, idCourse);
            stm.setInt(3, id_program);
            stm.setInt(4, idSemester);
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
                results.add(s);
            }
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Grade letterGrade(int id_Student, int id_course_offered, int id_semester) {
        Grade g = new Grade();
        try (Connection con = DBConnection.getConnection();) {
            String req = "select score, grade "
                    + "from student_course "
                    + "where id_student = ? and id_course_offered = ? and id_semester = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, id_Student);
            stm.setInt(2, id_course_offered);
            stm.setInt(3, id_semester);
            ResultSet result = stm.executeQuery();
            char letter = 'F';
            double grade = 0;
            while (result.next()) {
                letter = result.getString("score").charAt(0);
                grade = result.getDouble("grade");
                g.setGrade(grade);
                g.setLetterGrade(letter);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
    }

    public static ObservableList<StudentProgress> getProgressions(int id_student) {
        ObservableList<StudentProgress> results = FXCollections.observableArrayList();
        try (Connection con = DBConnection.getConnection();) {
            String req = "select * from student_level where id_student = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, id_student);
            ResultSet result = stm.executeQuery();
            while (result.next()) {

                results.add(new StudentProgress(result.getInt("id_student"),
                        result.getInt("id_level"),
                        result.getInt("id_semester")));
            }
            Collections.sort(results);
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /*
    Removing data that are not any more usefull for the current semester
     */
    public static void closeSemester() {
        try (Connection con = DBConnection.getConnection();) {
            con.setAutoCommit(false);
            String req3 = "delete from student_attendance; ";
            PreparedStatement stm3 = con.prepareStatement(req3);
            stm3.executeUpdate();
            String req1 = "delete from schedule;";
            PreparedStatement stm1 = con.prepareStatement(req1);
            stm1.executeUpdate();
            String req2 = "delete from take_test;";
            PreparedStatement stm2 = con.prepareStatement(req2);
            stm2.executeUpdate();
            String req4 = "delete from timeslot; ";
            PreparedStatement stm4 = con.prepareStatement(req4);
            stm4.executeUpdate();
            con.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean alreadyOpenedForTheSem(int idCourse, int idSemester) {
        try (Connection con = DBConnection.getConnection();) {
            String req = "select * "
                    + "from course_offered "
                    + "where id_course = ? and id_semester = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, idCourse);
            stm.setInt(2, idSemester);
            ResultSet result = stm.executeQuery();
            if (result.next()) {
                return true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
