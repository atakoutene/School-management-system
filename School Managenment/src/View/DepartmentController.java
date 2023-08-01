/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.LoadStaff;
import Control.Tools;
import Model.Department;
import Model.Staff;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
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
public class DepartmentController implements Initializable {

    @FXML
    private Label titleStage;

    @FXML
    private JFXTextField departmentName;

    @FXML
    private JFXComboBox<Staff> headCombo;

    private TableView<Department> departmentTable;

    private Department department;

    @FXML
    private Button btnSave;

    private boolean flag;

    @FXML
    private Button btnCancel;

    public void setDepartmentTable(TableView<Department> departmentTable) {
        this.departmentTable = departmentTable;
    }

    private ObservableList<Staff> loadStaffCombo() {
        return FXCollections.observableArrayList(LoadStaff.loadStaffFromDB().getList());
    }

    @FXML
    void cancelDepartment(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveDepartment(ActionEvent event) {
        if (flag == false) {
            String name = departmentName.getText();
            Staff head = headCombo.getSelectionModel().getSelectedItem();
            Department dept = new Department();
            dept.setHead(head);
            dept.setName(name);
            dept.setIdHead(head.getId());
            dept.setHeadName(head.toString());
            dept = Tools.saveDepartment(dept);
            departmentTable.getItems().add(dept);
            Collections.sort(departmentTable.getItems());
            departmentTable.refresh();
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        } else {
            String name = departmentName.getText();
            Staff head = headCombo.getSelectionModel().getSelectedItem();
            department.setName(name);
            department.setHead(head);
            department.setHeadName(head.toString());
            department.setIdHead(head.getId());
            Tools.updateDepartment(department);
            ObservableList<Department> tmp = departmentTable.getItems();
            Department ff = null;
            int j = 0;
            boolean b = false;
            while (!tmp.isEmpty() && (j < tmp.size()) && !b) {
                ff = tmp.get(j);
                if (ff.getId() == department.getId()) {
                    b = true;
                }
                j++;
            }
            tmp.remove(ff);
            Department d = new Department();
            d.setId(department.getId());
            d.setHeadName(department.getHeadName());
            d.setIdHead(department.getIdHead());
            d.setHead(department.getHead());
            d.setName(department.getName());
            tmp.add(d);
            Collections.sort(tmp);
            departmentTable.setItems(tmp);
            departmentTable.refresh();
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }

    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        headCombo.setItems(loadStaffCombo());
    }

    public void initWindow(Department dep) {
        Staff s = new Staff();
        for(Staff ss : headCombo.getItems()){
            if(ss.getId() == dep.getHead().getId())
                s = ss;
        }
        headCombo.getSelectionModel().select(s);
        departmentName.setText(dep.getName());
        department = dep;
        flag = true;
        titleStage.setText("Update Department Info");
    }

}
