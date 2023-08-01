/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Objects;

/**
 *
 * @author Sokeng Paul (AG)
 */
public class Schedule {

    private int id;
    private int id_course_offered;
    private int id_course;
    private String course_title;
    private int id_room;
    private Room room_name;
    private int id_time_slot;
    private EDay course_day;
    private String course_date;
    private String startTime;
    private String endTime;
    private int id_faculty;
    private String facultyName;

    public Schedule() {
    }

    public Schedule(int id, int id_course, int id_course_offered, int id_room, int id_time_slot) {
        this.id = id;
        this.id_course = id_course;
        this.id_course_offered = id_course_offered;
        this.id_room = id_room;
        this.id_time_slot = id_time_slot;
    }

    public Schedule(int id, int id_course, int id_course_offered, String course_title, int id_room, Room room_name, int id_time_slot, EDay course_day, String course_date, String startTime, String endTime, int id_faculty, String facultyName) {
        this.id = id;
        this.id_course = id_course;
        this.id_course_offered = id_course_offered;
        this.course_title = course_title;
        this.id_room = id_room;
        this.room_name = room_name;
        this.id_time_slot = id_time_slot;
        this.course_day = course_day;
        this.course_date = course_date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.id_faculty = id_faculty;
        this.facultyName = facultyName;
    }

    public int getId_course() {
        return id_course;
    }

    public void setId_course(int id_course) {
        this.id_course = id_course;
    }

    public int getId_faculty() {
        return id_faculty;
    }

    public void setId_faculty(int id_faculty) {
        this.id_faculty = id_faculty;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getCourse_title() {
        return course_title;
    }

    public void setCourse_title(String course_title) {
        this.course_title = course_title;
    }

    public Room getRoom_name() {
        return room_name;
    }

    public void setRoom_name(Room room_name) {
        this.room_name = room_name;
    }

    public EDay getCourse_day() {
        return course_day;
    }

    public void setCourse_day(EDay course_day) {
        this.course_day = course_day;
    }

    public String getCourse_date() {
        return course_date;
    }

    public void setCourse_date(String course_date) {
        this.course_date = course_date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_course_offered() {
        return id_course_offered;
    }

    public void setId_course_offered(int id_course_offered) {
        this.id_course_offered = id_course_offered;
    }

    public int getId_room() {
        return id_room;
    }

    public void setId_room(int id_room) {
        this.id_room = id_room;
    }

    public int getId_time_slot() {
        return id_time_slot;
    }

    public void setId_time_slot(int id_time_slot) {
        this.id_time_slot = id_time_slot;
    }

    @Override
    public String toString() {
        return "Schedule{" + "id=" + id + ", id_course_offered=" + id_course_offered + ", id_room=" + id_room + ", id_time_slot=" + id_time_slot + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.id;
        hash = 37 * hash + this.id_course_offered;
        hash = 37 * hash + Objects.hashCode(this.course_title);
        hash = 37 * hash + this.id_room;
        hash = 37 * hash + Objects.hashCode(this.room_name);
        hash = 37 * hash + this.id_time_slot;
        hash = 37 * hash + Objects.hashCode(this.course_day);
        hash = 37 * hash + Objects.hashCode(this.course_date);
        hash = 37 * hash + Objects.hashCode(this.startTime);
        hash = 37 * hash + Objects.hashCode(this.endTime);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Schedule other = (Schedule) obj;
        return this.id == other.id;
    }

}
