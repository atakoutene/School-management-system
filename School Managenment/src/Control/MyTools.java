/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.EDay;
import Model.Program;
import Model.Room;
import Model.Schedule;
import Model.Semester;
import Model.Student_Attendance;
import Model.TimeSlot;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Sokeng Paul (AG)
 */
public class MyTools {

    public static TimeSlot getTimeSlot(int timeslotId) {
        Connection con = DBConnection.getConnection();
        TimeSlot t = new TimeSlot();
        try {
            String req = "SELECT * FROM timeslot WHERE id = " + timeslotId + ";";
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            while (result.next()) {
                String start, end, date;
                EDay day;
                start = result.getString("startTime");
                end = result.getString("endTime");
                date = result.getString("dateCourse");
                day = EDay.getValue(result.getString("dayCourse"));
                t = new TimeSlot(timeslotId, start, end, date, day);
            }
            return t;
        } catch (SQLException ex) {
            Logger.getLogger(MyTools.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(MyTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

//    public static void insertTimeSlot(){
//        Connection con = DBConnection.getConnection();
//        try{
//            PreparedStatement stm2 = con.prepareStatement("INSERT INTO timeslot(startTime, endTime, dateCourse, dayCourse)");
//            stm2.setString(2, x);
//            stm2.setString(3, x);
//            stm2.setString(4, x);
//            stm2.setString(5, x);
//        }catch(SQLException ex){
//            ex.printStackTrace();
//        }finally{
//            try {
//                con.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(CRUDSchedule.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
    public static String amOrPm(int hour) {
        if (hour > 12) {
            return "PM";
        } else if (hour < 12) {
            return "AM";
        } else {
            return "PM";
        }
    }

    public static int reverseHour(int hour) {
        if (hour > 12) {
            return (hour - 12);
        } else {
            return hour;
        }
    }

    public static int calculateTime(String ampm, int hour) {
        if (ampm.equals("PM")) {
            switch (hour) {
                case 1:
                    return 13;
                case 2:
                    return 14;
                case 3:
                    return 15;
                case 4:
                    return 16;
                case 5:
                    return 17;
                case 6:
                    return 18;
                case 7:
                    return 19;
                case 8:
                    return 20;
                case 9:
                    return 21;
                case 10:
                    return 22;
                case 11:
                    return 23;
                case 12:
                    return 00;
            }
        } else {
            return hour;
        }
        return -1;
    }

    public static int getTimeSlotID(String start, String end, String day, String date) {
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement stm = con.prepareStatement("SELECT id FROM timeslot "
                    + "WHERE startTime = '" + start + "' and endTime = '" + end + "' and dateCourse = '" + date + "' and dayCourse = '" + day + "';");
            System.out.println("");
            ResultSet result = stm.executeQuery();
            int timeId = 0;
            while (result.next()) {
                timeId = result.getInt("id");
            }
            return timeId;
        } catch (SQLException ex) {
            Logger.getLogger(MyTools.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(MyTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return -1;
    }

    public static ObservableList<Room> getRooms() {
        ObservableList<Room> results = FXCollections.observableArrayList();
        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement stm = con.prepareStatement("SELECT * FROM room");
            ResultSet result = stm.executeQuery();
            int roomID, capacity;
            String name;
            while (result.next()) {
                roomID = result.getInt("id");
                name = result.getString("name");
                capacity = result.getInt("capacity");
                results.add(new Room(roomID, name, capacity));
            }
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(MyTools.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(MyTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static boolean checkValidityOfSchedule(Schedule s, TimeSlot ts, Program p, Semester sem) {
        ObservableList<Schedule> schedules = MyTools.getAllCoursesSchedules(p, sem);
        int i=0, j = 0, ii = 0, jj = 0;
        for (Schedule sc : schedules) {
            i = ts.getStartTime().indexOf(":");
            ii = ts.getEndTime().indexOf(":");
            j = sc.getEndTime().indexOf(":");
            jj = sc.getEndTime().indexOf(":");
            if (s.getRoom_name().equals(sc.getRoom_name())                   
                    && Integer.parseInt(ts.getStartTime().substring(0, i)) >= Integer.parseInt(sc.getStartTime().substring(0, j))
                    && Integer.parseInt(ts.getEndTime().substring(0, ii)) < Integer.parseInt(sc.getEndTime().substring(0, jj))
                    && ts.getId() == sc.getId_time_slot()) {
                return false;
            }
        }
        return true;
    }

    public static ObservableList<Schedule> getAllCoursesSchedules(Program p, Semester sem) {
        Connection con = DBConnection.getConnection();
        ObservableList<Schedule> results = FXCollections.observableArrayList();
        try {
            String req = "SELECT DISTINCT s.id as scheduleId, s.id_course_offered as courseOffId, co.id_course as courseId, c.title as courseTitle, s.id_room as roomId, r.name as roomName, s.id_time_slot as timeSlotId, "
                    + "t.startTime as startTime, t.endTime as endTime, t.dateCourse as courseDate, t.dayCourse as courseDay, f.id as facultyId, pe.name as facultyName "
                    + "FROM faculty f, schedule s, course c, course_offered co, room r, person pe, employee e, timeslot t, taugh_by tby, program p "
                    + "WHERE f.id_employee = e.id and pe.id = e.id_person and c.id = co.id_course and co.id = s.id_course_offered and p.id = tby.id_program and "
                    + " r.id = s.id_room and t.id = s.id_time_slot and f.id = tby.id_faculty and co.id = tby.id_course_offered and "
                    + "tby.id_program = " + p.getId() + " and co.course_year = '" + sem.getSYear() + "' and co.course_sem = '" + sem.getName() + "';";
            System.out.println(req);
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            int idFaculty, idCourseOff, idRoom, idSchedule, idtimeslot,idCourse;
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
                facultyName = result.getString("facultyName");
                courseTitle = result.getString("courseTitle");
                Schedule sche = new Schedule(idSchedule, idCourse,idCourseOff, courseTitle, idRoom, Tools.getRoom(idRoom), idtimeslot, courseDay, courseDate, startTime, endTime, idFaculty, facultyName);
                results.add(sche);
            }
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static ObservableList<Student_Attendance> getAttendanceList(int scheduleId) {
        ObservableList<Student_Attendance> results = FXCollections.observableArrayList();
        Connection con = DBConnection.getConnection();
        try {
            String req = "SELECT DISTINCT sat.id_student as studentId, s.studentId as studentRef, pe.name as student_name, sche.id as scheduleId, sat.presence "
                    + "FROM person pe, student s, course_offered co, student_course sc, schedule sche, student_attendance sat "
                    + "WHERE pe.id = s.id_person and s.id = sat.id_student and sat.id_schedule = sche.id and sc.id_course_offered = sche.id_course_offered and sche.id = "+scheduleId+";";
            System.out.println(req);
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            int idStudent, idSchedule;
            String studName, studRef, presence;
            while (result.next()) {
                idStudent = result.getInt("studentId");
                idSchedule = result.getInt("scheduleId");
                studName = result.getString("student_name");
                studRef = result.getString("studentRef");
                presence = result.getString("presence");
                results.add(new Student_Attendance(idStudent, idSchedule, presence, studName, studRef));
            }
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(MyTools.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(MyTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    public static ObservableList<Student_Attendance> getStudentAttendancesWithDate(int studentId, String fromDate, String toDate) {
        ObservableList<Student_Attendance> results = FXCollections.observableArrayList();
        Connection con = DBConnection.getConnection();
        try {
            String req = "SELECT DISTINCT s.id as studentId, s.studentId as studentRef, pe.name as student_name, sche.id as scheduleId, " 
                    +"t.startTime as startTime, t.endTime as endTime, t.dateCourse, t.dayCourse, sa.presence as studPresence, c.title as courseTitle " 
                    +"FROM person pe, student s, course c, course_offered co, student_course sc, schedule sche, timeslot t,student_attendance sa " 
                    +"WHERE pe.id = s.id_person and c.id = co.id_course and s.id = sc.id_student and s.id = sa.id_student and sche.id = sa.id_schedule " 
                    +"and co.id = sc.id_course_offered and co.id = sche.id_course_offered  "
                    + "and t.id = sche.id_time_slot and s.id = "+studentId+" and t.dateCourse >= '"+fromDate+"' and t.dateCourse <= '"+toDate+"';";
            System.out.println(req);
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            int idSchedule;
            String studName, studRef, presence, date, start, end, title;
            EDay day;
            while (result.next()) {
                idSchedule = result.getInt("scheduleId");
                studName = result.getString("student_name");
                studRef = result.getString("studentRef"); 
                presence = result.getString("studPresence"); 
                day = EDay.getValue(result.getString("dayCourse")); 
                date = result.getString("dateCourse"); 
                start = result.getString("startTime"); 
                end = result.getString("endTime"); 
                title = result.getString("courseTitle"); 
                
                results.add(new Student_Attendance(studentId, idSchedule, presence, studName, studRef, day, date, start, end, title));
            }
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(MyTools.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(MyTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public static ObservableList<Student_Attendance> getStudentAttendancesWithDateAndCourse(int studentId, String fromDate, String toDate, int coId) {
        ObservableList<Student_Attendance> results = FXCollections.observableArrayList();
        Connection con = DBConnection.getConnection();
        try {
            String req = "SELECT DISTINCT s.id as studentId, s.studentId as studentRef, pe.name as student_name, sche.id as scheduleId, " 
                    +"t.startTime as startTime, t.endTime as endTime, t.dateCourse, t.dayCourse, sa.presence as studPresence, c.title as courseTitle " 
                    +"FROM person pe, student s, course c, course_offered co, student_course sc, schedule sche, timeslot t,student_attendance sa " 
                    +"WHERE pe.id = s.id_person and c.id = co.id_course and s.id = sc.id_student and s.id = sa.id_student and sche.id = sa.id_schedule " 
                    +"and co.id = sc.id_course_offered and co.id = sche.id_course_offered  "
                    + "and t.id = sche.id_time_slot and s.id = "+studentId+" and t.dateCourse >= '"+fromDate+"' and t.dateCourse <= '"+toDate+"' and sche.id_course_offered = "+coId+";";
            System.out.println(req);
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            int idSchedule;
            String studName, studRef, presence, date, start, end, title;
            EDay day;
            while (result.next()) {
                idSchedule = result.getInt("scheduleId");
                studName = result.getString("student_name");
                studRef = result.getString("studentRef"); 
                presence = result.getString("studPresence"); 
                day = EDay.getValue(result.getString("dayCourse")); 
                date = result.getString("dateCourse"); 
                start = result.getString("startTime"); 
                end = result.getString("endTime"); 
                title = result.getString("courseTitle"); 
                
                results.add(new Student_Attendance(studentId, idSchedule, presence, studName, studRef, day, date, start, end, title));
            }
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(MyTools.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(MyTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public static ObservableList<Student_Attendance> getStudentAttendances(int studentId) {
        ObservableList<Student_Attendance> results = FXCollections.observableArrayList();
        Connection con = DBConnection.getConnection();
        try {
            String req = "SELECT DISTINCT s.id as studentId, s.studentId as studentRef, pe.name as student_name, sche.id as scheduleId, " 
                    +"t.startTime as startTime, t.endTime as endTime, t.dateCourse, t.dayCourse, sa.presence as studPresence, c.title as courseTitle " 
                    +"FROM person pe, student s, course c, course_offered co, student_course sc, schedule sche, timeslot t,student_attendance sa " 
                    +"WHERE pe.id = s.id_person and c.id = co.id_course and s.id = sc.id_student and s.id = sa.id_student and sche.id = sa.id_schedule " 
                    +"and co.id = sc.id_course_offered and co.id = sche.id_course_offered "
                    + "and t.id = sche.id_time_slot and s.id = "+studentId+" ;";
            System.out.println(req);
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            int idSchedule;
            String studName, studRef, presence, date, start, end, title;
            EDay day;
            while (result.next()) {
                idSchedule = result.getInt("scheduleId");
                studName = result.getString("student_name");
                studRef = result.getString("studentRef"); 
                presence = result.getString("studPresence"); 
                day = EDay.getValue(result.getString("dayCourse")); 
                date = result.getString("dateCourse"); 
                start = result.getString("startTime"); 
                end = result.getString("endTime"); 
                title = result.getString("courseTitle"); 
                
                results.add(new Student_Attendance(studentId, idSchedule, presence, studName, studRef, day, date, start, end, title));
            }
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(MyTools.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(MyTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public static ObservableList<Student_Attendance> getStudentAttendancesWithCourse(int studentId, int coId) {
        ObservableList<Student_Attendance> results = FXCollections.observableArrayList();
        Connection con = DBConnection.getConnection();
        try {
            String req = "SELECT DISTINCT s.id as studentId, s.studentId as studentRef, pe.name as student_name, sche.id as scheduleId, " 
                    +"t.startTime as startTime, t.endTime as endTime, t.dateCourse, t.dayCourse, sa.presence as studPresence, c.title as courseTitle " 
                    +"FROM person pe, student s, course c, course_offered co, student_course sc, schedule sche, timeslot t,student_attendance sa " 
                    +"WHERE pe.id = s.id_person and c.id = co.id_course and s.id = sc.id_student and s.id = sa.id_student and sche.id = sa.id_schedule " 
                    +"and co.id = sc.id_course_offered and co.id = sche.id_course_offered "
                    + "and t.id = sche.id_time_slot and s.id = "+studentId+" and sche.id_course_offered = "+coId+";";
            System.out.println(req);
            PreparedStatement stm = con.prepareStatement(req);
            ResultSet result = stm.executeQuery();
            int idSchedule;
            String studName, studRef, presence, date, start, end, title;
            EDay day;
            while (result.next()) {
                idSchedule = result.getInt("scheduleId");
                studName = result.getString("student_name");
                studRef = result.getString("studentRef"); 
                presence = result.getString("studPresence"); 
                day = EDay.getValue(result.getString("dayCourse")); 
                date = result.getString("dateCourse"); 
                start = result.getString("startTime"); 
                end = result.getString("endTime"); 
                title = result.getString("courseTitle"); 
                
                results.add(new Student_Attendance(studentId, idSchedule, presence, studName, studRef, day, date, start, end, title));
            }
            return results;
        } catch (SQLException ex) {
            Logger.getLogger(MyTools.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(MyTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    
}
