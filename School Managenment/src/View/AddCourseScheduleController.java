/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.CRUDSchedule;
import Control.CRUDTimeSlot;
import Control.MyTools;
import Control.Tools;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import jfxtras.labs.scene.control.BigDecimalField;

/**
 * FXML Controller class
 *
 * @author Sokeng Paul (AG)
 */
public class AddCourseScheduleController implements Initializable {

    @FXML
    private JFXDatePicker datefield;

    @FXML
    private BigDecimalField endMinutes;

    @FXML
    private BigDecimalField startMinutes;

    @FXML
    private JFXComboBox<EDay> daybox;

    @FXML
    private JFXComboBox<Integer> startHour;

    @FXML
    private JFXComboBox<Integer> endHour;

    @FXML
    private JFXComboBox<Room> placefield;

    @FXML
    private JFXComboBox<String> ampm1;

    @FXML
    private JFXComboBox<String> ampm2;

    private TableView<Schedule> tableSche;
    private Program program;
    private Semester semester;
    private CourseOffered courseOff;
    private ObservableList<String> listDays;
    private ObservableList<Student_Attendance> attending;
    private int count;
    private final ObservableList<Integer> hours = FXCollections.observableArrayList(01, 02, 03, 04, 05, 06, 07, 0x08, 0x09, 10, 11, 12);
    private final ObservableList<String> ampm = FXCollections.observableArrayList("AM", "PM");

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @FXML
    void getDay(ActionEvent event) {
        String day = datefield.getValue().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
        daybox.getSelectionModel().select(EDay.getValue(day));
    }

    @FXML
    void okButtonAction(ActionEvent event) {
        TimeSlot ts = new TimeSlot();
        Schedule s = new Schedule();
        if (okControl()) {
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
            s.setRoom_name(placefield.getSelectionModel().getSelectedItem());
            s.setId_course_offered(courseOff.getId());
            s.setCourse_date(ts.getDateCourse());
            s.setCourse_day(ts.getDayCourse());
            s.setStartTime(ts.getStartTime());
            s.setEndTime(ts.getEndTime());
            s.setId(ts.getId());
            s.setFacultyName(courseOff.getLecturer());
            if (!MyTools.checkValidityOfSchedule(s, ts, program, semester)) {
                Alert a = new Alert(Alert.AlertType.ERROR, "Another course has been planned around this time in room"
                        + placefield.getSelectionModel().getSelectedItem() + ". Please select another room or change the start and end times");
                a.setHeaderText("Course schedules clash");
                a.show();
            } else {
                CRUDSchedule.insertSchedule(courseOff.getId(), placefield.getSelectionModel().getSelectedItem().getId(), ts.getId(), semester);
            }
            tableSche.setItems(Tools.getSchedules(program, semester, courseOff));
            tableSche.refresh();
            //controller.setTableSche(tableSche);
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            //tableSche.refresh();
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private boolean okControl() {
        if (datefield == null || daybox.getSelectionModel().getSelectedItem() == null || placefield.getSelectionModel().getSelectedItem() == null
                || startHour == null || endHour == null || startMinutes == null || endMinutes == null) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please provide all the information before clicking on OK", ButtonType.OK);
            a.setHeaderText("Protocol Error");
            a.show();
            return false;
        }
        return true;
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        datefield.setConverter(new StringConverter<LocalDate>() {
            private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            @Override
            public String toString(LocalDate localDate) {
                if(localDate == null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if(dateString == null || dateString.trim().isEmpty())
                    return null;
                return LocalDate.parse(dateString,dateTimeFormatter);
            }
        });
        startHour.setItems(hours);
        endHour.setItems(hours);
        startMinutes.setNumber(BigDecimal.ZERO);
        endMinutes.setNumber(BigDecimal.ZERO);
        startMinutes.setMaxValue(BigDecimal.valueOf(59));
        startMinutes.setMinValue(BigDecimal.ZERO);
        endMinutes.setMaxValue(BigDecimal.valueOf(59));
        endMinutes.setMinValue(BigDecimal.ZERO);
        daybox.getItems().addAll(EDay.values());
        placefield.setItems(MyTools.getRooms());
        ampm1.setItems(ampm);
        ampm2.setItems(ampm);
    }

}
