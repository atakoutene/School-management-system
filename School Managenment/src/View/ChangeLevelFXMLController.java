/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.CRUDJoinTables;
import Control.Tools;
import Model.SchoolLevel;
import Model.Semester;
import Model.Student;
import Model.StudentProgress;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author metch
 */
public class ChangeLevelFXMLController implements Initializable {
    
    @FXML
    private JFXComboBox<SchoolLevel> levelCombo;

    @FXML
    private JFXComboBox<Semester> semesterCombo;

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXButton cancelButton;
    
    private Student student;
    
    @FXML
    private TableView<StudentProgress> studentProgressTable;

    public void setStudentProgressTable(TableView<StudentProgress> studentProgressTable) {
        this.studentProgressTable = studentProgressTable;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @FXML
    void onCancel(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onSave(ActionEvent event) {
        Semester sem = semesterCombo.getSelectionModel().getSelectedItem();
        SchoolLevel level = levelCombo.getSelectionModel().getSelectedItem();
        if(CRUDJoinTables.searchSchoolLev(student.getId(), level.getId(), sem.getId()) == null){
            CRUDJoinTables.insertOrUpdateSchoolLev(student.getId(), level.getId(), sem.getId());
            if(Tools.getCurrentSemester().equals(sem)){
                 student.setStatus(level.getName());
                 (new UpdateStudentController()).updateStudentLevel(student);
            }
               
        }
        studentProgressTable.setItems(Tools.getProgressions(student.getId()));
        studentProgressTable.refresh();
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void initComp(){
        semesterCombo.setItems(getAllSemesters());
        Semester sem = Tools.getCurrentSemester();
        semesterCombo.getSelectionModel().select(sem);
        SchoolLevel sl = Tools.getSchoolLevel(student.getStatus());
        levelCombo.setItems(getAllLevels());
        levelCombo.getSelectionModel().select(sl);
    }
    
    private ObservableList<Semester> getAllSemesters() {
        ObservableList<Semester> semesters = Tools.getSemesters();
        FXCollections.sort(semesters);
        return semesters;
    }
    
    //Methods applying to all sub_modules
    private ObservableList<SchoolLevel> getAllLevels() {

        ObservableList<SchoolLevel> levels = Tools.getSchoolLevel();
        FXCollections.sort(levels);

        return levels;
    }
}
