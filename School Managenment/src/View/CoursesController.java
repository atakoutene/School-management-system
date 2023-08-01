/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.ManageCourse;
import Model.Course;
import Model.CourseComparator;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HERMAN
 */
public class CoursesController implements Initializable {

    @FXML
    private Label titleStage;

    @FXML
    private JFXComboBox<Course> coursesCombo;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    private TableView<Course> table;

    private ObservableList<Course> loadCourseCombo() {
        List<Course> theList = ManageCourse.loadCourseFromDB().getList();
        Collections.sort(theList, new CourseComparator());
        return FXCollections.observableArrayList(theList);
    }

    public void setTable(TableView<Course> table) {
        this.table = table;
    }

    @FXML
    void cancel(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void save(ActionEvent event) {
        table.getItems().add(coursesCombo.getSelectionModel().getSelectedItem());
        table.refresh();
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        coursesCombo.getItems().addAll(loadCourseCombo());
    }

}
