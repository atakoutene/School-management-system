/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.ManageCourse;
import Control.Tools;
import Model.Course;
import Model.CourseComparator;
import Model.ProgramCourses;
import Model.SchoolLevel;
import Model.Semester;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HERMAN
 */
public class Course_ProgramController implements Initializable {

    @FXML
    private Label windowTitle;

    @FXML
    private JFXComboBox<Course> coursesCombo;

    @FXML
    private JFXComboBox<SchoolLevel> levelCombo;

    @FXML
    private JFXTextField nberObCredits;

    @FXML
    private JFXComboBox<String> passingGradeCombo;

    @FXML
    private JFXComboBox<String> isCoreCombo;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    private TableView<ProgramCourses> tableProgramCourse;
    
    ProgramCourses prog_course;
    
    private boolean flag = false;
    
    private Semester sem;

    public void setSem(Semester sem) {
        this.sem = sem;
    }

    public void setTableProgramCourse(TableView<ProgramCourses> tableProgramCourse) {
        this.tableProgramCourse = tableProgramCourse;
    }

    private ObservableList<SchoolLevel> loadLevelCombo() {
        ArrayList<String> l = new ArrayList<>();
        l.add("Preparatory Semester");
        l.add("Freshman I");
        l.add("Freshman II");
        l.add("Sophomore I");
        l.add("Sophomore II");
        l.add("Junior I");
        l.add("Junior II");
        l.add("MBA I");
        l.add("MBA II");
        l.add("PhD");
        ObservableList<SchoolLevel> myList = FXCollections.observableArrayList(Tools.getSchoolLevels());
        return myList;
    }

    private ObservableList<String> loadPassingGradeCombo() {
        ArrayList<String> l = new ArrayList<>();
        l.add("A");
        l.add("B");
        l.add("C");
        ObservableList<String> myList = FXCollections.observableArrayList(l);
        return myList;
    }

    private ObservableList<String> loadIsCoreCombo() {
        ArrayList<String> l = new ArrayList<>();
        l.add("Yes");
        l.add("No");
        ObservableList<String> myList = FXCollections.observableArrayList(l);
        return myList;
    }

    private ObservableList<Course> loadCourseCombo() {
        List<Course> theList = ManageCourse.loadCourseFromDB().getList();
        Collections.sort(theList, new CourseComparator());
        return FXCollections.observableArrayList(theList);
    }

    @FXML
    void cancel(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void save(ActionEvent event) {
        Course c;
        Alert a;
        if ((c = coursesCombo.getSelectionModel().getSelectedItem()) == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a course", ButtonType.OK);
            a.setTitle("Error!");
            a.setHeaderText("No course selected!");
            a.showAndWait();
        } else {
            ProgramCourses pcourse = new ProgramCourses();
            pcourse.setCourse(c);
            pcourse.setCode_course(c.getCode());
            pcourse.setId_course(c.getId());
            pcourse.setCourse_title(c.getTitle());
            pcourse.setLevel(levelCombo.getSelectionModel().getSelectedItem());
            pcourse.setNb_credits(Double.parseDouble(nberObCredits.getText()));
            pcourse.setPassing_grade(passingGradeCombo.getSelectionModel().getSelectedItem().charAt(0));
            pcourse.setCore(isCoreCombo.getSelectionModel().getSelectedItem());
            pcourse.setId_semester(sem.getId());
            if(flag){
                tableProgramCourse.getItems().remove(prog_course);
            }
            tableProgramCourse.getItems().add(pcourse);
            tableProgramCourse.refresh();
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        coursesCombo.setItems(loadCourseCombo());
        levelCombo.setItems(loadLevelCombo());
        passingGradeCombo.setItems(loadPassingGradeCombo());
        isCoreCombo.setItems(loadIsCoreCombo());
    }
    
    public void initWindow(ProgramCourses pc){
        flag = true;
        windowTitle.setText("Update a Course Assign to a Program");
        btnSave.setText("Save");
        coursesCombo.getSelectionModel().select(pc.getCourse());
        levelCombo.getSelectionModel().select(pc.getLevel());
        passingGradeCombo.getSelectionModel().select(String.valueOf(pc.getPassing_grade()));
        isCoreCombo.getSelectionModel().select(pc.getCore());
        nberObCredits.setText(String.valueOf(pc.getNb_credits()));
        prog_course = pc;
    }

}
