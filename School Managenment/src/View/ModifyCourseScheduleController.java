/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.CRUDCourse;
import Control.CRUDSchedule;
import Control.CRUDTimeSlot;
import Control.CourseOfferedMgnt;
import Control.MyTools;
import Control.Tools;
import Model.Course;
import Model.CourseOffered;
import Model.EDay;
import Model.Program;
import Model.Room;
import Model.Schedule;
import Model.Semester;
import Model.Student_Attendance;
import Model.TimeSlot;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import jfxtras.labs.scene.control.BigDecimalField;

/**
 * FXML Controller class
 *
 * @author Sokeng Paul (AG)
 */
public class ModifyCourseScheduleController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXDatePicker datefield;

    @FXML
    private JFXTextField codefield;

    @FXML
    private JFXTextField descriptionfield;

    @FXML
    private JFXComboBox<Room> placefield;

    @FXML
    private JFXComboBox<EDay> daybox;

    @FXML
    private JFXComboBox<Integer> startHour;

    @FXML
    private JFXComboBox<Integer> endHour;

    @FXML
    private JFXComboBox<String> ampm1;

    @FXML
    private JFXComboBox<String> ampm2;

    @FXML
    private BigDecimalField startMinutes;

    @FXML
    private BigDecimalField endMinutes;
    
    
    @FXML
    private Label scheduleLabel;

    @FXML
    private TableView<Student_Attendance> tEnrolledStudent;

    @FXML
    private TableColumn<Student_Attendance, String> tStudRef;

    @FXML
    private TableColumn<Student_Attendance, String> tStudName;

    @FXML
    private TableColumn<Student_Attendance, String> tpresence;

    private TableView<Schedule> tableSche;
    private Schedule schedule;
    private Program program;
    private Semester semester;
    private CourseOffered courseOff;
    private final ObservableList<Integer> hours = FXCollections.observableArrayList(01, 02, 03, 04, 05, 06, 07, 0x08, 0x09, 10, 11, 12);
    private final ObservableList<String> ampm = FXCollections.observableArrayList("AM", "PM");

    private boolean isDuplicate = false;

    public boolean isIsDuplicate() {
        return isDuplicate;
    }

    public void setIsDuplicate(boolean isDuplicate) {
        this.isDuplicate = isDuplicate;
    }

    public TableView<Schedule> getTableSche() {
        return tableSche;
    }

    public void setTableSche(TableView<Schedule> tableSche) {
        this.tableSche = tableSche;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public CourseOffered getCourseOff() {
        return courseOff;
    }

    public void setCourseOff(CourseOffered courseOff) {
        this.courseOff = courseOff;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    @FXML
    void getDay(ActionEvent event) {
        String day = datefield.getValue().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
        daybox.getSelectionModel().select(EDay.getValue(day));
    }

    @FXML
    void cancel(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveButtonAction(ActionEvent event) {

        TimeSlot ts = new TimeSlot();
        ts.setDayCourse(daybox.getSelectionModel().getSelectedItem());
        ts.setDateCourse(datefield.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        int start = MyTools.calculateTime(ampm1.getSelectionModel().getSelectedItem(), startHour.getSelectionModel().getSelectedItem());
        int end = MyTools.calculateTime(ampm2.getSelectionModel().getSelectedItem(), endHour.getSelectionModel().getSelectedItem());
        ts.setStartTime(start + ":" + startMinutes.getText());
        ts.setEndTime(end + ":" + endMinutes.getText());
        if (MyTools.getTimeSlotID(ts.getStartTime(), ts.getEndTime(), ts.getDayCourse().getCode(), ts.getDateCourse()) == 0) {
            CRUDTimeSlot.insertTimeSlot(ts);
        }
        ts.setId(MyTools.getTimeSlotID(ts.getStartTime(), ts.getEndTime(), ts.getDayCourse().getCode(), ts.getDateCourse()));
        schedule.setCourse_date(ts.getDateCourse());
        schedule.setCourse_day(ts.getDayCourse());
        schedule.setStartTime(ts.getStartTime());
        schedule.setEndTime(ts.getEndTime());
        schedule.setId_time_slot(ts.getId());
        schedule.setId_faculty(courseOff.getId_lecturer());
        schedule.setId_course_offered(courseOff.getId());
        schedule.setId_room(placefield.getSelectionModel().getSelectedItem().getId());
        if (okControl()) {
            if (!MyTools.checkValidityOfSchedule(schedule, ts, program, semester)) {
                Alert a = new Alert(Alert.AlertType.ERROR, "Another course has been planned at around this time in this room("
                        + " ).Please select another room or change the start and end times");
                a.setHeaderText("Course Schedules Clash");
                a.show();
            } else {
                if (!isDuplicate) {
                    CRUDSchedule.updateSchedule(schedule);
                    tableSche.setItems(Tools.getSchedules(program, semester, courseOff));
                } else {
                    CRUDSchedule.insertSchedule(schedule.getId_course_offered(), schedule.getId_room(), schedule.getId_time_slot(), semester);
                    tableSche.setItems(Tools.getSchedules(program, semester, courseOff));
                }
                tableSche.refresh();
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();

            }

        }

    }

    private boolean okControl() {
        if (datefield == null || daybox.getSelectionModel() == null || placefield.getSelectionModel() == null
                || startHour == null || endHour == null || startMinutes == null || endMinutes == null) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please provide all the information before clicking on OK", ButtonType.OK);
            a.setHeaderText("Protocol Error");
            a.show();
            return false;
        }
        return true;
    }

    public void initComp() {
        if(isDuplicate){
            scheduleLabel.setText("Duplicate a Schedule");
        }
        int i = schedule.getStartTime().indexOf(":");
        int i1 = schedule.getStartTime().lastIndexOf(":");
        int j = schedule.getEndTime().indexOf(":");
        int j1 = schedule.getEndTime().lastIndexOf(":");
        int len1 = schedule.getStartTime().length();
        int len2 = schedule.getEndTime().length();
        int start = Integer.parseInt(schedule.getStartTime().substring(0, i));
        System.out.println("start:" + start);
        int end = Integer.parseInt(schedule.getEndTime().substring(0, j));
        System.out.println("end:" + end);
        CourseOffered co = CourseOfferedMgnt.getCourseOffered(schedule.getId_course_offered());
        Course c = CRUDCourse.getCourse(co.getCourse_id());
        codefield.setText(c.getCode());
        descriptionfield.setText(c.getTitle());
        daybox.setEditable(false);
        daybox.getItems().addAll(EDay.values());
        daybox.getSelectionModel().select(schedule.getCourse_day());
        datefield.setValue(LocalDate.parse(schedule.getCourse_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        startHour.getSelectionModel().select((Integer) MyTools.reverseHour(start));
        endHour.getSelectionModel().select((Integer) MyTools.reverseHour(end));
        startMinutes.setNumber(BigDecimal.valueOf(Double.parseDouble(schedule.getStartTime().substring(i+1, i1))));
        endMinutes.setNumber(BigDecimal.valueOf(Double.parseDouble(schedule.getEndTime().substring(j+1, j1))));
        endMinutes.setText(schedule.getEndTime().substring(j+1, j1));
        ampm1.getSelectionModel().select(MyTools.amOrPm(start));
        ampm2.getSelectionModel().select(MyTools.amOrPm(end));
        placefield.getSelectionModel().select(schedule.getRoom_name());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        datefield.setConverter(new StringConverter<LocalDate>() {
            private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) {
                    return "";
                }
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, dateTimeFormatter);
            }
        });
        startHour.setItems(hours);
        endHour.setItems(hours);
        codefield.setEditable(false);
        descriptionfield.setEditable(false);
        startMinutes.setMaxValue(BigDecimal.valueOf(59));
        startMinutes.setMinValue(BigDecimal.ZERO);
        endMinutes.setMaxValue(BigDecimal.valueOf(59));
        endMinutes.setMinValue(BigDecimal.ZERO);
        placefield.setItems(MyTools.getRooms());
        ampm1.setItems(ampm);
        ampm2.setItems(ampm);

    }

}
