/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.CourseOfferedMgnt;
import Control.LoadFaculty;
import Control.ManageProgramCourses;
import Control.Tools;
import Model.CourseOffered;
import Model.Faculty;
import Model.ProgramCourses;
import Model.Semester;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HERMAN
 */
public class CourseOpenedGUIController implements Initializable {

    @FXML
    private AnchorPane selectFileButton;

    @FXML
    private Label openCourseTitle;

    @FXML
    private JFXComboBox<Semester> semesterOpened;

    @FXML
    private JFXTextField yearOpened;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    private TableView<CourseOffered> tableCouresesOpen;

    private int idCourse;

    private File file;

    private boolean flag = false;

    private CourseOffered myCO;

    private int oldLect, oldAssist, courseY;

    private String courseSem;

    //private 
    @FXML
    private JFXComboBox<Faculty> lecturer;

    @FXML
    private JFXComboBox<Faculty> assistant;

    public void setIdCourse(int id) {
        this.idCourse = id;
    }

    public void setTableCouresesOpen(TableView<CourseOffered> tableCouresesOpen) {
        this.tableCouresesOpen = tableCouresesOpen;
    }

    @FXML
    void cancel(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void save(ActionEvent event) {
        if (flag) {
            //Update
            Semester sem = semesterOpened.getSelectionModel().getSelectedItem();
            Set<ProgramCourses> programsCourses = ManageProgramCourses.loadProgramCourses(idCourse, sem.getId());
            for (ProgramCourses pc : programsCourses) {
                if (pc.getId_course() == myCO.getCourse_id()) {
                    String semester = semesterOpened.getSelectionModel().getSelectedItem().toString();
                    myCO.setCourse_semester(semester.substring(0, semester.indexOf(" ")));
                    myCO.setCourse_year(Integer.parseInt(semester.substring(semester.indexOf(" ") + 1, semester.length())));
                    myCO.setId_assistant(assistant.getSelectionModel().getSelectedItem().getId());
                    myCO.setId_lecturer(lecturer.getSelectionModel().getSelectedItem().getId());
                    myCO.setLecturer(lecturer.getSelectionModel().getSelectedItem().toString()
                            + " - " + assistant.getSelectionModel().getSelectedItem().toString());

                    CourseOfferedMgnt.updateCourseOffered(myCO, oldLect, oldAssist, courseSem, courseY, pc.getId_program(), sem.getId());
                    int i = 0;
                    for (i = 0; i < tableCouresesOpen.getItems().size(); i++) {
                        if (tableCouresesOpen.getItems().get(i).getId() == myCO.getId()) {
                            tableCouresesOpen.getItems().get(i).setCourse_semester(myCO.getCourse_semester());
                            tableCouresesOpen.getItems().get(i).setCourse_year(myCO.getCourse_year());
                            tableCouresesOpen.getItems().get(i).setSyllabus(myCO.getSyllabus());
                            tableCouresesOpen.getItems().get(i).setLecturer(myCO.getLecturer());
                        }
                    }
                }
            }

            tableCouresesOpen.refresh();
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            return;
        }

        CourseOffered course = new CourseOffered();
        Semester sem = semesterOpened.getSelectionModel().getSelectedItem();
        if(Tools.alreadyOpenedForTheSem(idCourse, sem.getId())){
            Alert a;
            a = new Alert(Alert.AlertType.ERROR, "This course has already been opened this semester!", ButtonType.OK);
            a.setHeaderText("Course already  opened");
            a.showAndWait();
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            return;
        }
        course.setCourse_id(idCourse);
        String semester = semesterOpened.getSelectionModel().getSelectedItem().toString();
        course.setCourse_semester(semester.substring(0, semester.indexOf(" ")));
        course.setCourse_year(Integer.parseInt(semester.substring(semester.indexOf(" ") + 1, semester.length())));
        int posDB = CourseOfferedMgnt.saveCourseOffered(course, semesterOpened.getSelectionModel().getSelectedItem());
        if (posDB > 0) {
            Set<ProgramCourses> programsCourses = ManageProgramCourses.loadProgramCourses(idCourse, sem.getId());
            for (ProgramCourses pc : programsCourses) {
                if (pc.getId_course() == idCourse) {
                    Faculty lect = lecturer.getSelectionModel().getSelectedItem();
                    Faculty assist = assistant.getSelectionModel().getSelectedItem();
                    Tools.insertDumpTaugh(posDB, pc.getId_program(), lect.getId(), "Lecturer", course.getCourse_semester(), course.getCourse_year());
                    Tools.insertDumpTaugh(posDB, pc.getId_program(), assist.getId(), "Assistant", course.getCourse_semester(), course.getCourse_year());
                    String teach = lect.getName();
                    teach += " - " + assist.getName();
                    course.setId(posDB);
                    course.setLecturer(teach);
                    course.setId_assistant(assist.getId());
                    course.setId_lecturer(lect.getId());
                }
            }

            tableCouresesOpen.getItems().add(course);
            tableCouresesOpen.refresh();
        } else {
            Alert a;
            a = new Alert(Alert.AlertType.ERROR, "Problem during the opening of the course...", ButtonType.OK);
            a.setHeaderText("Course not opened");
            a.showAndWait();
        }

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        semesterOpened.setItems(Tools.getSemesters());
        lecturer.setItems(FXCollections.observableList(LoadFaculty.loadFacultyFromDB2().getList()));
        selectDefault(lecturer);
        assistant.setItems(FXCollections.observableList(LoadFaculty.loadFacultyFromDB2().getList()));
        selectDefault2(assistant);
    }

    private void selectDefault(JFXComboBox<Faculty> teacher) {
        for (Faculty f : teacher.getItems()) {
            if (f.getName().equals("No Lecturer Assigned")) {
                teacher.getSelectionModel().select(f);
            }
        }
    }

    private void selectDefault2(JFXComboBox<Faculty> assistant) {
        for (Faculty f : assistant.getItems()) {
            if (f.getName().equals("No Assistant Assigned")) {
                assistant.getSelectionModel().select(f);
            }
        }
    }

    public void initWindow(CourseOffered co) {
        flag = true;
        Semester sem = Tools.findSemester(co.getCourse_semester(), co.getCourse_year());
        semesterOpened.getSelectionModel().select(sem);
        courseY = co.getCourse_year();
        courseSem = co.getCourse_semester();
        oldLect = co.getId_lecturer();
        oldAssist = co.getId_assistant();
        semesterOpened.setDisable(true);
        int i = 0;
        ObservableList<Faculty> lecturers = lecturer.getItems();
        while (i < lecturers.size() && lecturers.get(i).getId() != co.getId_lecturer()) {
            i++;
        }
        if (i < lecturers.size()) {
            lecturer.getSelectionModel().select(lecturers.get(i));
            i = 0;
            ObservableList<Faculty> assist = assistant.getItems();
            while (i < assist.size() && assist.get(i).getId() != co.getId_assistant()) {
                i++;
            }
            assistant.getSelectionModel().select(assist.get(i));

            myCO = co;
        }
    }

}
