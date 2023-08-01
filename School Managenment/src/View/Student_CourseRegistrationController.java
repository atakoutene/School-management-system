/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.DBConnection;
import Control.MyTools;
import Control.Pair;
import Control.Tools;
import Model.CourseOffered;
import Model.DumyDouble;
import Model.EDay;
import Model.SchoolLevel;
import Model.Semester;
import Model.Student;
import Model.EStudentLevel;
import Model.StudentProgress;
import Model.Student_Attendance;
import Model.Student_Course;
import Model.TestGradeDTO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
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
public class Student_CourseRegistrationController implements Initializable {

    @FXML
    private ImageView imView;
    @FXML
    private JFXComboBox<Semester> semesterbox;
    @FXML
    private JFXComboBox<SchoolLevel> levelbox;
    @FXML
    private JFXButton goButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private JFXButton transButton;
    @FXML
    private Button courseManagementButton;
    @FXML
    private Label studentLabel;
    @FXML
    private Label programLabel;
    @FXML
    private Label departmentLabel;
    @FXML
    private Label levelLabel;
    @FXML
    private TableView<Student_Course> tStudCourse;
    @FXML
    private TableColumn<Student_Course, String> tcode;
    @FXML
    private TableColumn<Student_Course, String> ttitle;
    @FXML
    private TableColumn<Student_Course, Float> tgrade;
    @FXML
    private TableColumn<Student_Course, String> tlettergrade;
    @FXML
    private TableColumn<Student_Course, String> tpass;
    @FXML
    private TableColumn<Student_Course, Double> tcredits;
    @FXML
    private JFXButton reForm;

    @FXML
    private JFXComboBox<CourseOffered> studentCourses;

    private TableView<Student> tableStud;
    private Student student;
    private ObservableList<Student_Course> currentStudCourseList;
    //private ObservableList<Course> courseAvailaible=FXCollections.observableArrayList(Tools.getCourses());
    private String photoPath;

    @FXML
    private JFXComboBox<SchoolLevel> levelGradeBox;

    @FXML
    private JFXComboBox<Semester> semesterGradeBox;

    @FXML
    private JFXButton goSearchGrades;

    @FXML
    private TableView<TestGradeDTO> SGradeTable;

    @FXML
    private TableColumn<TestGradeDTO, String> SGradeTableCClol;

    @FXML
    private TableColumn<TestGradeDTO, String> SGradeTableETCol;

    @FXML
    private TableColumn<TestGradeDTO, Double> SGradeTableGradCol;

    @FXML
    private TableColumn<TestGradeDTO, String> SGradeTableLGCol;

    @FXML
    private DatePicker startDateField;

    @FXML
    private DatePicker endDateField;

    @FXML
    private JFXButton searchAttendButton;

    @FXML
    private TableView<Student_Attendance> tStudAttendance;

    @FXML
    private TableColumn<Student_Attendance, EDay> tday;

    @FXML
    private TableColumn<Student_Attendance, String> tdate;

    @FXML
    private TableColumn<Student_Attendance, String> tCourseTitle;

    @FXML
    private TableColumn<Student_Attendance, String> tstart;

    @FXML
    private TableColumn<Student_Attendance, String> tend;

    @FXML
    private TableColumn<Student_Attendance, String> tpresence;

    @FXML
    private JFXComboBox<Semester> semAttendBox;

    @FXML
    private JFXComboBox<SchoolLevel> levelAttendBox;

    @FXML
    private JFXButton updateLevel;
    
    @FXML
    private TableView<StudentProgress> studentProgressTable;

    @FXML
    private TableColumn<StudentProgress, String> studentProgressSem;

    @FXML
    private TableColumn<StudentProgress, String> studentProgressLevel;
    
    @FXML
    private JFXComboBox<Semester> startSem;

    @FXML
    private JFXComboBox<Semester> endSem;

    @FXML
    private JFXButton transPeriodic;
    
    private String signingAuthorities;

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setTableStudent(TableView<Student> tableStudent) {
        this.tableStud = tableStudent;
    }

    public void setCurrentStudList(ObservableList<Student_Course> currentStudList) {
        this.currentStudCourseList = currentStudList;
    }

    public void setSigningAuthorities(String signingAuthorities) {
        this.signingAuthorities = signingAuthorities;
    }

    @FXML
    public void back(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

    @FXML
    public void loadStudentCoursesCombo() {
        studentCourses.setItems(Tools.studCourseSem(student, semAttendBox.getSelectionModel().getSelectedItem()));
        CourseOffered co = new CourseOffered();
        co.setId(-1);
        studentCourses.getItems().add(co);
        studentCourses.setCellFactory(lv -> new ListCell<CourseOffered>() {
            @Override
            protected void updateItem(CourseOffered item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null || item.getId() == -1)
                        ? ""
                        : item.toString());
            }

        });
        studentCourses.getSelectionModel().select(co);

    }

    //Courses Tab
    @FXML
    public void searchStudentCourse(ActionEvent event) {
        if (controlBeforeClick()) {
            tStudCourse.setItems(Tools.getStudentCoursesTaken(
                    student,
                    levelbox.getSelectionModel().getSelectedItem(),
                    semesterbox.getSelectionModel().getSelectedItem()));
            tStudCourse.refresh();
        }
    }

    @FXML
    void printRegForm(ActionEvent event) {
        if (semesterbox.getSelectionModel().isEmpty()) {
            Alert a = new Alert(AlertType.ERROR, "Please select a semester!", ButtonType.CLOSE);
            a.setHeaderText("Semester Not Selected!");
            a.show();
        } else {
            try {
                Connection connection = DBConnection.getConnection();
                /* JasperReport is the object
                that holds our compiled jrxml file */
                JasperReport jasperReport;
                Semester semester = semesterbox.getSelectionModel().getSelectedItem();
                SchoolLevel level = levelbox.getSelectionModel().getSelectedItem();

                BufferedImage image = ImageIO.read(getClass().getResource("/Resource/images/logopkf.PNG"));

                /* JasperPrint is the object contains
                report after result filling process */
                JasperPrint jasperPrint;
                // jasperParameter is a Hashmap contains the parameters
                // passed from application to the jrxml layout
                HashMap jasperParameter = new HashMap();
                jasperParameter.put("id_program", student.getProgram_id());
                jasperParameter.put("sem_year", semester.getSYear());
                jasperParameter.put("sem_name", semester.getName());
                jasperParameter.put("id_level", level.getId());
                jasperParameter.put("id_student", student.getId());
                jasperParameter.put("imagesDir", image);

                InputStream stream = getClass().getResource("/Resource/reports/simple_report.jrxml").openStream();

                jasperReport = JasperCompileManager.compileReport(stream);
                // jrxml compiling process

                // filling report with data from data source
                jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameter, connection);

                // exporting process
                // 1- export to PDF
                JasperViewer.viewReport(jasperPrint, false);
//                DirectoryChooser chooser = new DirectoryChooser();
//
//                chooser.setTitle("Select directory where to save...");
//                File selectedFile = chooser.showDialog(((Node) event.getSource()).getScene().getWindow());
//
//                if (selectedFile != null) {
//                    System.out.println(selectedFile.getPath());
//                    JasperExportManager.exportReportToPdfFile(jasperPrint, selectedFile.getPath() + "/" + student.getName() + "-"
//                        + semester.getName() + " " + semester.getSYear() + ".pdf");
//                    Tools.openPDFFile(selectedFile.getPath()+ "/" + student.getName() + "-"
//                        + semester.getName() + " " + semester.getSYear() + ".pdf");
//                }

            } catch (JRException | IOException ex) {
                Logger.getLogger(Student_CourseRegistrationController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    //Grades Tab
    @FXML
    void searchStudentCourseGrades(ActionEvent event) {
        ObservableList<Student_Course> sCourses = Tools.getStudentCoursesTaken(
                student,
                levelGradeBox.getSelectionModel().getSelectedItem(),
                semesterGradeBox.getSelectionModel().getSelectedItem());
        SGradeTable.setItems(Tools.getStudentTestGrades(sCourses, semesterGradeBox.getSelectionModel().getSelectedItem().getId()));
        SGradeTable.refresh();
    }

    @FXML
    void printAttendanceForm(ActionEvent event) {
        if (semAttendBox.getSelectionModel().getSelectedItem() == null
                || levelAttendBox.getSelectionModel().getSelectedItem() == null) {
            Alert a = new Alert(AlertType.ERROR, "Please fill all fields before trying to print!", ButtonType.CLOSE);
            a.setHeaderText("Protocol Error");
            a.show();
        } else if ((startDateField.getValue() == null && !(endDateField.getValue() == null))
                || (!(startDateField.getValue() == null) && endDateField.getValue() == null)) {
            Alert a = new Alert(AlertType.ERROR, "Please provide both the start and the end date before trying to print!", ButtonType.CLOSE);
            a.setHeaderText("Protocol Error");
            a.show();
        } else {
            if (endDateField.getValue() == null && startDateField.getValue() == null) {
                if (studentCourses.getSelectionModel().getSelectedItem().getId() == -1) {
                    HashMap jasperParams = new HashMap();
                    jasperParams.put("studentId", student.getId());
                    jasperParams.put("studentLevel", levelAttendBox.getSelectionModel().getSelectedItem().getName());
                    jasperParams.put("studentSemester", (semAttendBox.getSelectionModel().getSelectedItem().getName() + " "
                            + semAttendBox.getSelectionModel().getSelectedItem().getSYear()));
                    jasperParams.put("attendancePeriod", "Not Specified (All)");
                    printAttendanceRecord(jasperParams, 0);
                } else {
                    tStudAttendance.setItems(MyTools.getStudentAttendancesWithCourse(student.getId(),
                            studentCourses.getSelectionModel().getSelectedItem().getId()));
                    HashMap jasperParams = new HashMap();
                    jasperParams.put("studentId", student.getId());
                    jasperParams.put("studentLevel", levelAttendBox.getSelectionModel().getSelectedItem().getName());
                    jasperParams.put("studentSemester", (semAttendBox.getSelectionModel().getSelectedItem().getName() + " "
                            + semAttendBox.getSelectionModel().getSelectedItem().getSYear()));
                    jasperParams.put("coId", studentCourses.getSelectionModel().getSelectedItem().getId());
                    jasperParams.put("attendancePeriod", "Not Specified (All)");
                    printAttendanceRecord(jasperParams, 1);
                }
            } else {
                if (studentCourses.getSelectionModel().getSelectedItem().getId() == -1) {
                    tStudAttendance.setItems(MyTools.getStudentAttendancesWithDate(student.getId(), startDateField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), endDateField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                    HashMap jasperParams = new HashMap();
                    jasperParams.put("studentId", student.getId());
                    jasperParams.put("studentLevel", levelAttendBox.getSelectionModel().getSelectedItem().getName());
                    jasperParams.put("studentSemester", (semAttendBox.getSelectionModel().getSelectedItem().getName() + " "
                            + semAttendBox.getSelectionModel().getSelectedItem().getSYear()));
                    String period = "From: " + startDateField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                            + " To: " + endDateField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    jasperParams.put("attendancePeriod", period);
                    Date start = Date.from(startDateField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    Date end = Date.from(endDateField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    jasperParams.put("startDate", start);
                    jasperParams.put("endDate", end);
                    printAttendanceRecord(jasperParams, 2);
                } else {
                    tStudAttendance.setItems(MyTools.getStudentAttendancesWithDateAndCourse(student.getId(), startDateField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            endDateField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            studentCourses.getSelectionModel().getSelectedItem().getId()));
                    HashMap jasperParams = new HashMap();
                    jasperParams.put("studentId", student.getId());
                    jasperParams.put("studentLevel", levelAttendBox.getSelectionModel().getSelectedItem().getName());
                    jasperParams.put("studentSemester", (semAttendBox.getSelectionModel().getSelectedItem().getName() + " "
                            + semAttendBox.getSelectionModel().getSelectedItem().getSYear()));
                    jasperParams.put("coId", studentCourses.getSelectionModel().getSelectedItem().getId());
                    String period = "From: " + startDateField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                            + " To: " + endDateField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    jasperParams.put("attendancePeriod", period);
                    Date start = Date.from(startDateField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    Date end = Date.from(endDateField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    jasperParams.put("startDate", start);
                    jasperParams.put("endDate", end);
                    printAttendanceRecord(jasperParams, 3);
                }
            }
        }

    }

    //Attendance Tab
    @FXML
    void searchStudentAttendance(ActionEvent event) {
        if (semAttendBox.getSelectionModel().getSelectedItem() == null
                || levelAttendBox.getSelectionModel().getSelectedItem() == null) {
            Alert a = new Alert(AlertType.ERROR, "Please fill all fields before clicking on OK", ButtonType.CLOSE);
            a.setHeaderText("Protocol Error");
            a.show();
        } else if ((startDateField.getValue() == null && !(endDateField.getValue() == null))
                || (!(startDateField.getValue() == null) && endDateField.getValue() == null)) {
            Alert a = new Alert(AlertType.ERROR, "Please provide both the start and the end date before clicking on OK", ButtonType.CLOSE);
            a.setHeaderText("Protocol Error");
            a.show();
        } else {
            if (endDateField.getValue() == null && startDateField.getValue() == null) {
                if (studentCourses.getSelectionModel().getSelectedItem().getId() == -1) {
                    tStudAttendance.setItems(MyTools.getStudentAttendances(student.getId()));
                } else {
                    tStudAttendance.setItems(MyTools.getStudentAttendancesWithCourse(student.getId(),
                            studentCourses.getSelectionModel().getSelectedItem().getId()));
                }
            } else {
                if (studentCourses.getSelectionModel().getSelectedItem().getId() == -1) {
                    tStudAttendance.setItems(MyTools.getStudentAttendancesWithDate(student.getId(), startDateField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), endDateField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                } else {
                    tStudAttendance.setItems(MyTools.getStudentAttendancesWithDateAndCourse(student.getId(), startDateField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            endDateField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            studentCourses.getSelectionModel().getSelectedItem().getId()));
                }
            }
        }

    }

    @FXML
    void studentCourseRegistration(ActionEvent event) throws CloneNotSupportedException {
        if (controlBeforeClick()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddStudCourse.fxml"));
                Stage st = new Stage(StageStyle.DECORATED);
                st.setScene(new Scene((Pane) loader.load()));
                AddStudCourseController controller = loader.getController();
                controller.setStudent(student);
                controller.setSem(semesterbox.getSelectionModel().getSelectedItem());
                controller.setSlvel(levelbox.getSelectionModel().getSelectedItem());
                controller.initComp();
                controller.settStudCourse(tStudCourse);
                st.getIcons().add(new Image(Lanceur.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
                st.setResizable(false);
                st.show();

            } catch (IOException ex) {
                Logger.getLogger(Student_CourseRegistrationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void initComp() {
        tStudCourse.setItems(currentStudCourseList);
        semesterbox.setItems(getAllSemesters());
        semesterGradeBox.setItems(getAllSemesters());
        semAttendBox.setItems(getAllSemesters());
        levelbox.setItems(getAllLevels());
        levelGradeBox.setItems(getAllLevels());
        levelAttendBox.setItems(getAllLevels());
//        String substring1 = student.getEntrance_year().substring(0, student.getEntrance_year().indexOf(" "));
//        int substring2 = Integer.parseInt(student.getEntrance_year().substring(student.getEntrance_year().indexOf(" ") + 1, student.getEntrance_year().length()));    
//        Semester sem = Tools.getSemesterFromList(substring1, substring2, semesterbox.getItems());
        Semester sem = Tools.getCurrentSemester();
        SchoolLevel sl = Tools.getSchoolLevel(student.getStatus());
        studentLabel.setText(student.getName());
        departmentLabel.setText(student.getDep());
        programLabel.setText(Tools.getProgram(student.getId()).get(0).getProgram());
        levelLabel.setText(student.getStatus());
        levelbox.getSelectionModel().select(sl);
        levelGradeBox.getSelectionModel().select(sl);
        currentStudCourseList = Tools.getStudentCoursesTaken(student, sl, sem);
        semesterbox.getSelectionModel().select(sem);
        semesterGradeBox.getSelectionModel().select(sem);
        photoPath = student.getProfileFile();
        Image image = new Image(student.getProfilePhoto());
        imView.setImage(image);
        studentProgressTable.setItems(Tools.getProgressions(student.getId()));
        studentProgressTable.refresh();

    }

    private boolean controlBeforeClick() {
        if (levelbox.getSelectionModel().isEmpty() || semesterbox.getSelectionModel().isEmpty()) {
            Alert a = new Alert(AlertType.ERROR, "Please select a semester and a level!", ButtonType.CLOSE);
            a.setHeaderText("Level and Semester not validated!");
            a.show();
            return false;
        } else if (EStudentLevel.getValue(levelbox.getSelectionModel().getSelectedItem().getName()).ordinal()
                > EStudentLevel.getValue(student.getStatus()).ordinal()) {
            Alert a = new Alert(AlertType.ERROR, "Select a level less than or equal to the current level of the student!", ButtonType.CLOSE);
            a.setHeaderText("Level Higher than the student level!");
            a.show();
            return false;
        }
        return true;
    }

    //Transcript Generator
    @FXML
    void generateTranstript(ActionEvent event) {
        try {
            Connection connection = DBConnection.getConnection();
            /* JasperReport is the object
                that holds our compiled jrxml file */
            JasperReport jasperReport;
            /* JasperPrint is the object contains
                report after result filling process */
            JasperPrint jasperPrint;
            // jasperParameter is a Hashmap contains the parameters
            // passed from application to the jrxml layout
            HashMap jasperParameter = new HashMap();

            Pair<DumyDouble, Double> gpa = Tools.calculateGPA(student.getId());
            Pair<Double, Double> pointsCredits = Tools.calculatePoints(student.getId(), new DumyDouble());
            jasperParameter.put("studentId", student.getId());
            jasperParameter.put("totalCredits", pointsCredits.getLeft());
            jasperParameter.put("totalPoints", pointsCredits.getRight());
            jasperParameter.put("signedby", signingAuthorities);
            System.out.println("The total number of credits is: " + pointsCredits.getLeft());
            if (pointsCredits.getLeft() == 0) {
                jasperParameter.put("avgMarks", 0.0d);
            } else {
                jasperParameter.put("avgMarks", gpa.getLeft().getValue() / pointsCredits.getLeft());
            }
            jasperParameter.put("gpa", gpa.getRight());

            InputStream stream = getClass().getResource("/Resource/reports/Transcript_form.jasper").openStream();
            //JasperDesign design = JRXmlLoader.load(stream);
            //design.setBottomMargin(55);
            jasperReport = (JasperReport) JRLoader.loadObject(stream);
////            
            // filling report with data from data source
            jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameter, connection);
            // jrxml compiling process
            // exporting process
            // 1- export to PDF

            JasperViewer.viewReport(jasperPrint, false);

//            DirectoryChooser chooser = new DirectoryChooser();
//
//            chooser.setTitle("Select a syllabus file...");
//            File selectedFile = chooser.showDialog(((Node) event.getSource()).getScene().getWindow());
//            if (selectedFile != null) {
//                System.out.println(selectedFile.getPath());
//                JasperExportManager.exportReportToPdfFile(jasperPrint, selectedFile.getPath() + "/" + student.getName() + ".pdf");
//                Tools.openPDFFile(selectedFile.getPath() + "/" + student.getName() + ".pdf");
//            }
//            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath);
//            Tools.openPDFFile(pdfPath);
        } catch (JRException | IOException ex) {
            Logger.getLogger(Student_CourseRegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Transcript Generator for PKF
    @FXML
    void generateTranstriptPKF(ActionEvent event) {
        try {
            Connection connection = DBConnection.getConnection();
            /* JasperReport is the object
                that holds our compiled jrxml file */
            JasperReport jasperReport;
            /* JasperPrint is the object contains
                report after result filling process */
            JasperPrint jasperPrint;
            // jasperParameter is a Hashmap contains the parameters
            // passed from application to the jrxml layout
            HashMap jasperParameter = new HashMap();

            Pair<DumyDouble, Double> gpa = Tools.calculateGPAPKF(student.getId());
            Pair<Double, Double> pointsCredits = Tools.calculatePointsPKF(student.getId(), new DumyDouble());
            jasperParameter.put("studentId", student.getId());
            jasperParameter.put("totalCredits", pointsCredits.getLeft());
            jasperParameter.put("totalPoints", pointsCredits.getRight());
            jasperParameter.put("signedby", signingAuthorities);
            System.out.println("The total number of credits is: " + pointsCredits.getLeft());
            if (pointsCredits.getLeft() == 0) {
                jasperParameter.put("avgMarks", 0.0d);
            } else {
                jasperParameter.put("avgMarks", gpa.getLeft().getValue() / pointsCredits.getLeft());
            }
            jasperParameter.put("gpa", gpa.getRight());

            InputStream stream = getClass().getResource("/Resource/reports/Transcript_form_PKF.jasper").openStream();
            //JasperDesign design = JRXmlLoader.load(stream);
            //design.setBottomMargin(55);
            jasperReport = (JasperReport) JRLoader.loadObject(stream);
////            
            // filling report with data from data source
            jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameter, connection);
            // jrxml compiling process
            // exporting process
            // 1- export to PDF

            JasperViewer.viewReport(jasperPrint, false);

//            DirectoryChooser chooser = new DirectoryChooser();
//
//            chooser.setTitle("Select a syllabus file...");
//            File selectedFile = chooser.showDialog(((Node) event.getSource()).getScene().getWindow());
//            if (selectedFile != null) {
//                System.out.println(selectedFile.getPath());
//                JasperExportManager.exportReportToPdfFile(jasperPrint, selectedFile.getPath() + "/" + student.getName() + ".pdf");
//                Tools.openPDFFile(selectedFile.getPath() + "/" + student.getName() + ".pdf");
//            }
//            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath);
//            Tools.openPDFFile(pdfPath);
        } catch (JRException | IOException ex) {
            Logger.getLogger(Student_CourseRegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void generatePeriodicalTranstript(ActionEvent event) {
        Semester start = startSem.getSelectionModel().getSelectedItem();
        Semester end = endSem.getSelectionModel().getSelectedItem();
        try {
            Connection connection = DBConnection.getConnection();
            /* JasperReport is the object
                that holds our compiled jrxml file */
            JasperReport jasperReport;
            /* JasperPrint is the object contains
                report after result filling process */
            JasperPrint jasperPrint;
            // jasperParameter is a Hashmap contains the parameters
            // passed from application to the jrxml layout
            HashMap jasperParameter = new HashMap();

            Pair<DumyDouble, Double> gpa = Tools.calculateGPAPKFPeriodic(student.getId(), start.getId(), end.getId());
            Pair<Double, Double> pointsCredits = Tools.calculatePointsPKFPeriodic(student.getId(), new DumyDouble(), start.getId(), end.getId());
            jasperParameter.put("studentId", student.getId());
            jasperParameter.put("totalCredits", pointsCredits.getLeft());
            jasperParameter.put("totalPoints", pointsCredits.getRight());
            jasperParameter.put("idStartSemester", start.getId());
            jasperParameter.put("idEndSemester", end.getId());
            jasperParameter.put("signedby", signingAuthorities);
            if (pointsCredits.getLeft() == 0) {
                jasperParameter.put("avgMarks", 0.0d);
            } else {
                jasperParameter.put("avgMarks", gpa.getLeft().getValue() / pointsCredits.getLeft());
            }
            jasperParameter.put("gpa", gpa.getRight());

            InputStream stream = getClass().getResource("/Resource/reports/Transcript_form_periodic.jasper").openStream();
            //JasperDesign design = JRXmlLoader.load(stream);
            //design.setBottomMargin(55);
            jasperReport = (JasperReport) JRLoader.loadObject(stream);
////            
            // filling report with data from data source
            jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameter, connection);
            // jrxml compiling process
            // exporting process
            // 1- export to PDF

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | IOException ex) {
            Logger.getLogger(Student_CourseRegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Methods applying to all sub_modules
    private ObservableList<SchoolLevel> getAllLevels() {

        ObservableList<SchoolLevel> levels = Tools.getSchoolLevel();
        Iterator<SchoolLevel> iter = levels.iterator();
        while (iter.hasNext()) {
            SchoolLevel sl = iter.next();
            if (EStudentLevel.getValue(sl.getName()).ordinal()
                    > EStudentLevel.getValue(student.getStatus()).ordinal()) {
                iter.remove();
            }
        }
        FXCollections.sort(levels);

        return levels;
    }

    private ObservableList<Semester> getAllSemesters() {
        ObservableList<Semester> semesters = Tools.getSemesters();
        FXCollections.sort(semesters);
        return semesters;
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startSem.setItems(getAllSemesters());
        endSem.setItems(getAllSemesters());
        tStudCourse.setEditable(true);
        if (tcode != null) {
            tcode.setCellValueFactory(new PropertyValueFactory<>("coursecode"));
//            tcode.setCellFactory(TextFieldTableCell.forTableColumn());
        }
        if (ttitle != null) {
            ttitle.setCellValueFactory(new PropertyValueFactory<>("coursetitle"));
        }
        if (tgrade != null) {
            tgrade.setCellValueFactory(new PropertyValueFactory<>("score"));
        }
        if (tlettergrade != null) {
            tlettergrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        }
        if (tpass != null) {
            tpass.setCellValueFactory(new PropertyValueFactory<>("passing_grade"));
        }
        if (tcredits != null) {
            tcredits.setCellValueFactory(new PropertyValueFactory<>("nb_credits"));
        }

        SGradeTable.setPlaceholder(new Label("No content availaible"));
        SGradeTableCClol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        SGradeTableETCol.setCellValueFactory(new PropertyValueFactory<>("testType"));
        SGradeTableGradCol.setCellValueFactory(new PropertyValueFactory<>("grade"));
        SGradeTableLGCol.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));

        tStudAttendance.setEditable(true);
        tday.setCellValueFactory(new PropertyValueFactory<>("course_day"));
        tdate.setCellValueFactory(new PropertyValueFactory<>("course_date"));
        tCourseTitle.setCellValueFactory(new PropertyValueFactory<>("course_title"));
        tstart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        tend.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        tpresence.setCellValueFactory(new PropertyValueFactory<>("presence"));
        startDateField.setConverter(Tools.getDateConverter());
        endDateField.setConverter(Tools.getDateConverter());
        
        studentProgressTable.setPlaceholder(new Label("No content availaible"));
        studentProgressSem.setCellValueFactory(new PropertyValueFactory<>("semester"));
        studentProgressLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
    }

    private void printAttendanceRecord(HashMap jasperParameter, int option) {
        try {
            Connection connection = DBConnection.getConnection();
            /* JasperReport is the object
                that holds our compiled jrxml file */
            JasperReport jasperReport;
            /* JasperPrint is the object contains
                report after result filling process */
            JasperPrint jasperPrint;
            InputStream stream = null;
            switch (option) {
                case 0:
                    stream = getClass().getResource("/Resource/reports/Attendance_record.jasper").openStream();
                    break;
                case 1:
                    stream = getClass().getResource("/Resource/reports/Attendance_record2.jasper").openStream();
                    break;
                case 2:
                    stream = getClass().getResource("/Resource/reports/Attendance_record4.jasper").openStream();
                    break;
                case 3:
                    stream = getClass().getResource("/Resource/reports/Attendance_record3.jasper").openStream();
                    break;
                default:
                    stream = getClass().getResource("/Resource/reports/Attendance_record.jasper").openStream();
            }

            jasperReport = (JasperReport) JRLoader.loadObject(stream);
            jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameter, connection);
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException | IOException ex) {
            Logger.getLogger(Student_CourseRegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void changeStudentLevel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangeLevelFXML.fxml"));
            Stage st = new Stage(StageStyle.DECORATED);
            st.setScene(new Scene((Pane) loader.load()));
            st.getIcons().add(new Image(Lanceur.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
            ChangeLevelFXMLController controller = loader.getController();
            controller.setStudent(student);
            controller.initComp();
            controller.setStudentProgressTable(studentProgressTable);
            st.setResizable(false);
            st.showAndWait();
            SchoolLevel sl = Tools.getSchoolLevel(student.getStatus());
            levelbox.getSelectionModel().select(sl);
            levelGradeBox.getSelectionModel().select(sl);
        } catch (IOException ex) {
            Logger.getLogger(Student_CourseRegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
