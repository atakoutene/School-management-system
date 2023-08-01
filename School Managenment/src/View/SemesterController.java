/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.Tools;
import Model.Semester;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
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
public class SemesterController implements Initializable {

    @FXML
    private Label titleStage;

    @FXML
    private JFXComboBox<String> semName;

    @FXML
    private JFXTextField semYear;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    private TableView<Semester> tableSemester;

    public void setTableSemester(TableView<Semester> tableSemester) {
        this.tableSemester = tableSemester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    private Semester semester;

    private boolean flag = false;

    private ObservableList<String> loadSemCombo() {
        ArrayList<String> l = new ArrayList<>();
        l.add("Fall");
        l.add("Spring");
        l.add("Summer");
        ObservableList<String> myList = FXCollections.observableArrayList(l);
        return myList;
    }

    @FXML
    void cancelSemester(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveSemester(ActionEvent event) {
        if (flag == false) {
            Semester sem = new Semester();
            sem.setName(semName.getSelectionModel().getSelectedItem());
            sem.setSYear(Integer.parseInt(semYear.getText()));
            sem = Tools.saveSemester(sem);
            tableSemester.getItems().add(sem);
            Collections.sort(tableSemester.getItems());
            tableSemester.refresh();
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        } else {
            semester.setName(semName.getSelectionModel().getSelectedItem());
            semester.setSYear(Integer.parseInt(semYear.getText()));
            Tools.updateSemester(semester);
            ObservableList<Semester> tmp = tableSemester.getItems();
            Semester ff = null;
            int j = 0;
            boolean b = false;
            while (!tmp.isEmpty() && (j < tmp.size()) && !b) {
                ff = tmp.get(j);
                if (ff.getId() == semester.getId()) {
                    b = true;
                }
                j++;
            }
            tmp.remove(ff);
            Semester s = new Semester();
            s.setId(semester.getId());
            s.setName(semester.getName());
            s.setSYear(semester.getSYear());
            tmp.add(s);
            Collections.sort(tmp);
            tableSemester.setItems(tmp);
            tableSemester.refresh();
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
        semName.getItems().addAll(loadSemCombo());
    }

    public void initWindow(Semester sem) {
        semYear.setText(String.valueOf(sem.getSYear()));
        semName.getSelectionModel().select(sem.getName());
        flag = true;
        titleStage.setText("Update Semester Info");
        semester = sem;
    }
}
