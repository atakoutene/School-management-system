/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.CRUDCourse;
import Control.CRUDStudentAttendance;
import Control.DBConnection;
import Control.MyTools;
import Control.Tools;
import Model.CourseOffered;
import Model.EDay;
import Model.Room;
import Model.Schedule;
import Model.Semester;
import Model.Student_Attendance;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import jfxtras.labs.scene.control.BigDecimalField;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Sokeng Paul (AG)
 */
public class EditCourseAttendanceController implements Initializable {

    @FXML
    private JFXDatePicker datefield;

    @FXML
    private JFXComboBox<EDay> daybox;

    @FXML
    private JFXButton searchScheduleButton;

    @FXML
    private JFXButton searchScheduleButton1;

    @FXML
    private JFXTextField codefield;

    @FXML
    private JFXTextField descriptionfield;

    @FXML
    private TableView<Student_Attendance> tEnrolledStudent;

    @FXML
    private TableColumn<Student_Attendance, String> tStudRef;

    @FXML
    private TableColumn<Student_Attendance, String> tStudName;

    @FXML
    private TableColumn<Student_Attendance, StringProperty> tpresence;

    @FXML
    private BigDecimalField startMinutes;

    @FXML
    private BigDecimalField endMinutes;

    @FXML
    private JFXComboBox<Integer> startHour;

    @FXML
    private JFXComboBox<Integer> endHour;

    @FXML
    private JFXComboBox<String> ampm1;

    @FXML
    private JFXComboBox<String> ampm2;

    @FXML
    private JFXComboBox<Room> placefield;

    @FXML
    private JFXButton printAttendance;

    private Schedule schedule;
    private CourseOffered courseOff;
    private Semester semester;
    private final ObservableList<Integer> hours = FXCollections.observableArrayList(01, 02, 03, 04, 05, 06, 07, 0x08, 0x09, 10, 11, 12);
    private final ObservableList<String> ampm = FXCollections.observableArrayList("AM", "PM");

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
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

    @FXML
    void cancel(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void getDay(ActionEvent event) {
        String day = datefield.getValue().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
        daybox.getSelectionModel().select(EDay.getValue(day));
    }

    @FXML
    void saveButtonAction(ActionEvent event) {
        ObservableList<Student_Attendance> attend = tEnrolledStudent.getItems();
        Iterator<Student_Attendance> iter = attend.iterator();
        while (iter.hasNext()) {
            Student_Attendance satt = iter.next();
            CRUDStudentAttendance.insertAttendances(satt);
        }
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /*
    @FXML
    void commitChoice(ActionEvent event2){
            tpresence.setOnEditCommit((CellEditEvent<Student_Attendance, String> event) -> {
            TablePosition<Student_Attendance, String> pos = event.getTablePosition();

            String new_presence = event.getNewValue();

            int row = pos.getRow();
            Student_Attendance satt = event.getTableView().getItems().get(row);

            satt.setPresence(new_presence);
        });
        tEnrolledStudent.refresh();
    }*/
    public void initComp() {
        int start = Integer.parseInt(schedule.getStartTime().substring(0, 2));
        System.out.println("start:" + start);
        int end = Integer.parseInt(schedule.getEndTime().substring(0, 2));
        System.out.println("end:" + end);
        codefield.setText(CRUDCourse.getCourse(schedule.getId_course()).getCode());
        descriptionfield.setText(CRUDCourse.getCourse(schedule.getId_course()).getTitle());
        daybox.getItems().addAll(EDay.values());
        daybox.getSelectionModel().select(schedule.getCourse_day());
        daybox.setDisable(true);
        daybox.setStyle("-fx-opacity: 1;");
        datefield.setValue(LocalDate.parse(schedule.getCourse_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        datefield.setDisable(true);
        datefield.setStyle("-fx-opacity: 1;");
        startHour.getSelectionModel().select((Integer) MyTools.reverseHour(start));
        startHour.setDisable(true);
        startHour.setStyle("-fx-opacity: 1;");
        endHour.getSelectionModel().select((Integer) MyTools.reverseHour(end));
        endHour.setDisable(true);
        endHour.setStyle("-fx-opacity: 1;");
        startMinutes.setNumber(BigDecimal.valueOf(Double.parseDouble(schedule.getStartTime().substring(3, 5))));
        startMinutes.setDisable(true);
        startMinutes.setStyle("-fx-opacity: 1;");
        endMinutes.setNumber(BigDecimal.valueOf(Double.parseDouble(schedule.getEndTime().substring(3, 5))));
        endMinutes.setDisable(true);
        endMinutes.setStyle("-fx-opacity: 1;");
        ampm1.getSelectionModel().select(MyTools.amOrPm(start));
        ampm1.setDisable(true);
        ampm1.setStyle("-fx-opacity: 1;");
        ampm2.getSelectionModel().select(MyTools.amOrPm(end));
        ampm2.setDisable(true);
        ampm2.setStyle("-fx-opacity: 1;");
        placefield.getSelectionModel().select(schedule.getRoom_name());
        placefield.setDisable(true);
        placefield.setStyle("-fx-opacity: 1;");

        tEnrolledStudent.setItems(MyTools.getAttendanceList(schedule.getId()));
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startHour.setItems(hours);
        endHour.setItems(hours);
        startHour.setEditable(false);
        endHour.setEditable(false);
        codefield.setEditable(false);
        descriptionfield.setEditable(false);
        datefield.setEditable(false);
        placefield.setItems(MyTools.getRooms());
        placefield.setEditable(false);
        ampm1.setItems(ampm);
        ampm2.setItems(ampm);
        ampm1.setEditable(false);
        ampm2.setEditable(false);
        tStudName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        tStudRef.setCellValueFactory(new PropertyValueFactory<>("studentRef"));

        ObservableList liste = FXCollections.observableArrayList(null, "Present", "Absent");

        tpresence.setCellValueFactory(i -> {
            final StringProperty value = i.getValue().presenceProperty();
            return Bindings.createObjectBinding(() -> value);
        }
        );

        //tpresence.setCellFactory(ComboBoxTableCell.forTableColumn(liste));
        tpresence.setCellFactory(col -> {
            TableCell<Student_Attendance, StringProperty> c = new TableCell<>();
            final ComboBox<String> combobox = new ComboBox<>(liste);
            c.itemProperty().addListener((observable, oldValue, newValue) -> {
                if (oldValue != null) {
                    combobox.valueProperty().unbindBidirectional(oldValue);
                }
                if (newValue != null) {
                    combobox.valueProperty().bindBidirectional(newValue);
                }
            });
            c.graphicProperty().bind(Bindings.when(c.emptyProperty()).then((Node) null).otherwise(combobox));
            return c;
        });
    }

    @FXML
    void printAttendanceAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getConnection();
            int start = Integer.parseInt(schedule.getStartTime().substring(0, 2));
            int end = Integer.parseInt(schedule.getEndTime().substring(0, 2));
            /* JasperReport is the object
            that holds our compiled jrxml file */
            JasperReport jasperReport;
            /* JasperPrint is the object contains
            report after result filling process */
            JasperPrint jasperPrint;
            // jasperParameter is a Hashmap contains the parameters
            // passed from application to the jrxml layout
            HashMap jasperParameter = new HashMap();
            jasperParameter.put("lecturer", schedule.getFacultyName());
            jasperParameter.put("course", codefield.getText() + " - " + descriptionfield.getText());
            jasperParameter.put("day", daybox.getSelectionModel().getSelectedItem().toString());
            datefield.setConverter(Tools.getDateConverter());
            jasperParameter.put("date", datefield.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            jasperParameter.put("startTime", startHour.getSelectionModel().getSelectedItem() + " : "
                    + schedule.getStartTime().substring(3, 5) + MyTools.amOrPm(start));
            jasperParameter.put("endTime", endHour.getSelectionModel().getSelectedItem() + " : "
                    + schedule.getEndTime().substring(3, 5) + MyTools.amOrPm(end));
            jasperParameter.put("place", schedule.getRoom_name().getName());
            jasperParameter.put("scheduleId", schedule.getId());
            jasperParameter.put("accademicYear", Tools.getAcademicYear(semester));
            BufferedImage image = ImageIO.read(getClass().getResource("/Resource/reports/logopkf.PNG"));
            jasperParameter.put("imagesDir", image);
            System.out.println(jasperParameter);
            // jrxml compiling process
            InputStream stream = getClass().getResource("/Resource/reports/attendance_report_form.jasper").openStream();

            //jasperReport = JasperCompileManager.compileReport(stream);
            jasperReport = (JasperReport) JRLoader.loadObject(stream);
            // jrxml compiling process

            // filling report with data from data source
            jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameter, connection);

            // exporting process
            // 1- export to PDF
            JasperViewer.viewReport(jasperPrint, false);
//            DirectoryChooser chooser = new DirectoryChooser();
//
//            chooser.setTitle("Select directory where to save...");
//            File selectedFile = chooser.showDialog(((Node) event.getSource()).getScene().getWindow());
//
//            if (selectedFile != null) {
//                String pdfPath = selectedFile.getPath() + datefield.getValue().toString() + codefield.getText() + ".pdf";
//                JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath);
//                Tools.openPDFFile(pdfPath);
//            }

        } catch (JRException | IOException ex) {
            Logger.getLogger(EditCourseAttendanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
