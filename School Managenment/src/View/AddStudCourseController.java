/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.CRUDCourse;
import Control.CRUDStudentAttendance;
import Control.CRUDStudentCourse;
import Control.MaxCredits;
import Control.Tools;
import Model.Course;
import Model.SchoolLevel;
import Model.Semester;
import Model.Student;
import Model.Student_Course;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sokeng Paul (AG)
 */
public class AddStudCourseController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label formLabel;
    @FXML
    private TableView<Student_Course> tAvailaible;
    @FXML
    private TableView<Student_Course> tSelected;
    @FXML
    private TableColumn<Student_Course, String> tcode1;
    @FXML
    private TableColumn<Student_Course, String> tcode2;
    @FXML
    private TableColumn<Student_Course, String> tTitle1;
    @FXML
    private TableColumn<Student_Course, String> tTitle2;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;

    private TableView<Student_Course> tStudCourse;

    private Student student;
    private SchoolLevel slvel;
    private Semester sem;

    private ObservableList<Student_Course> programCourses = FXCollections.observableArrayList();
    private ObservableList<Student_Course> yourCourses = FXCollections.observableArrayList();

    public ObservableList<Student_Course> getProgramCourses() {
        return programCourses;
    }

    public void setProgramCourses(ObservableList<Student_Course> programCourses) {
        this.programCourses = programCourses;
    }

    public TableView<Student_Course> gettStudCourse() {
        return tStudCourse;
    }

    public void settStudCourse(TableView<Student_Course> tStudCourse) {
        this.tStudCourse = tStudCourse;
    }

    public ObservableList<Student_Course> getYourCourses() {
        return yourCourses;
    }

    public void setYourCourses(ObservableList<Student_Course> yourCourses) {
        this.yourCourses = yourCourses;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public SchoolLevel getSlvel() {
        return slvel;
    }

    public void setSlvel(SchoolLevel slvel) {
        this.slvel = slvel;
    }

    public Semester getSem() {
        return sem;
    }

    public void setSem(Semester sem) {
        this.sem = sem;
    }

    @FXML
    private void addCourse() {
        if (tAvailaible.getSelectionModel().isEmpty()) {
            return;
        }
        Student_Course sc = tAvailaible.getSelectionModel().getSelectedItem();
        ObservableList<Course> preReq = CRUDCourse.getPrerequisites(sc.getIdCourse());
        if ((preReq != null) && !CRUDStudentCourse.hasValidatedPreReq(sc.getIdStudent(), preReq)) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Pre requisites not validated!");
            a.setContentText("The student has not validated all the prerequisites for this course. He or She cannot take it yet!");
            a.showAndWait();
            return;
        }

        if (Tools.hasPassedCourse(sc.getIdStudent(), sc.getIdCourse())) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Course already validated");
            a.setContentText("The student has  already validated the course, and cannot take it anymore!");
            a.showAndWait();
            return;
        }

        yourCourses.add(sc);
        if (addCourseControl()) {
            tSelected.getItems().add(sc);
            programCourses.remove(sc);
            tAvailaible.getItems().remove(sc);
            tAvailaible.refresh();
            tSelected.refresh();
        } else {
            yourCourses.remove(sc);
        }

    }

    @FXML
    private void dropCourse() {
        if (tSelected.getSelectionModel().isEmpty()) {
            return;
        }
        Student_Course sc = tSelected.getSelectionModel().getSelectedItem();
        tAvailaible.getItems().add(sc);
        yourCourses.remove(sc);
        programCourses.add(sc);
        tSelected.getItems().remove(sc);
        tAvailaible.refresh();
        tSelected.refresh();
    }

    @FXML
    private void save(ActionEvent event) {
        ObservableList<Student_Course> sc = tSelected.getItems();
        ObservableList<Student_Course> registered = Tools.getStudentCoursesTaken(student, slvel, sem);
        ArrayList<Student_Course> toBeDeleted = new ArrayList<>();
        if ((sc != null) && (!sc.isEmpty())) {
            ObservableList<Student_Course> chosen = tSelected.getItems();
            ObservableList<Student_Course> initChosen = FXCollections.observableArrayList();
            Iterator<Student_Course> it = chosen.iterator();
            for (Student_Course scc : registered) {
                try {
                    if (!chosen.contains(scc)) {                       
                        toBeDeleted.add(scc.clone());            
                    }
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(AddStudCourseController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            while (it.hasNext()) {
                try {
                    Student_Course ss = it.next();
                    initChosen.add(ss.clone());
                    if (registered.contains(ss)) {
                        it.remove();
                    }
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(AddStudCourseController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            for (Student_Course c : toBeDeleted) {
                CRUDStudentCourse.deleteStudentCourseSem(student.getId(), c.getIdCourseOffered(), sem.getId());
                CRUDStudentAttendance.deleteAttendancesCourse(student.getId(), c.getIdCourseOffered());
            }
            for (Student_Course c : chosen) {
                CRUDStudentCourse.insertStudentCourseSem(student.getId(), c.getIdCourseOffered(), sem.getId());
                Tools.checkAndRegister(student.getId(), c.getIdCourseOffered());
            }
            tStudCourse.setItems(initChosen);
            tStudCourse.refresh();
        } else if (!registered.isEmpty()) {
            for (Student_Course c : registered) {
                CRUDStudentCourse.deleteStudentCourseSem(student.getId(), c.getIdCourseOffered(), sem.getId());
                CRUDStudentAttendance.deleteAttendancesCourse(student.getId(), c.getIdCourseOffered());
            }
            tStudCourse.getItems().clear();
            tStudCourse.refresh();
        }
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void back(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private boolean addCourseControl() {
        if (CRUDStudentCourse.countCredits(yourCourses) > MaxCredits.getMaxCredits()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "The credit ceiling for this semester is " + MaxCredits.getMaxCredits()
                    + "\nTaking this course will exceed the ceiling (" + CRUDStudentCourse.countCredits(yourCourses) + ")\n"
                    + "Please choose another course within the limits of the remaining credits", ButtonType.CLOSE);
            a.setHeaderText("Credit ceiling reached");
            a.show();
            return false;
        }
        return true;
    }

    private boolean controlBeforeClick() {
        if (tAvailaible.getSelectionModel().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please select a course before clicking this button!", ButtonType.CLOSE);
            a.setHeaderText("Course not selected!");
            a.show();
            return false;
        }
        return true;
    }

    public void initComp() throws CloneNotSupportedException {
        formLabel.setText(student.getName() + "'s Registration Form");
        ObservableList<Student_Course> courses = Tools.getStudentCourses(student, slvel, sem);
        ObservableList<Student_Course> registered = Tools.getStudentCoursesTakenperSemesterLevel(student, sem, slvel);
        tSelected.setItems(registered);
        courses.removeAll(registered);
        tAvailaible.setItems(courses);
        programCourses.addAll(courses);
        yourCourses.addAll(registered);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tAvailaible.setPlaceholder(new Label("No Content Availaible"));
        tSelected.setPlaceholder(new Label("No Content Availaible"));
        if (tcode1 != null) {
            tcode1.setCellValueFactory(new PropertyValueFactory<Student_Course, String>("coursecode"));
        }
        if (tTitle1 != null) {
            tTitle1.setCellValueFactory(new PropertyValueFactory<Student_Course, String>("coursetitle"));
        }
        if (tcode2 != null) {
            tcode2.setCellValueFactory(new PropertyValueFactory<Student_Course, String>("coursecode"));
        }
        if (tTitle2 != null) {
            tTitle2.setCellValueFactory(new PropertyValueFactory<Student_Course, String>("coursetitle"));
        }
        //programCourses.addAll(Tools.getStudentCourses(student, slvel, sem));
    }

}
