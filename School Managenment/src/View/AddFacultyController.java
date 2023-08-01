/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.DBConnection;
import Control.LoadFaculty;
import Control.Tools;
import Model.Department;
import Model.Faculty;
import Model.MyDate;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Steve Meudje
 */
public class AddFacultyController implements Initializable {

    @FXML
    JFXTextField name;
    @FXML
    JFXTextField office;
    @FXML
    JFXTextField rank;
    @FXML
    JFXTextField officehours;
    @FXML
    JFXTextField salary;
    @FXML
    JFXTextField address;
    @FXML
    JFXTextField email;
    @FXML
    JFXRadioButton male;
    @FXML
    JFXRadioButton female;
    @FXML
    JFXDatePicker birthday;
    @FXML
    JFXDatePicker datehired;
    @FXML
    JFXTextField phone;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    @FXML
    private JFXComboBox<String> titleCombo;

    @FXML
    private JFXTextField facultySpecialty;

    @FXML
    private JFXComboBox<Department> facultyCombo;

    private TableView<Faculty> tableFaculty;

    public void setTableFaculty(TableView<Faculty> tableFaculty) {
        this.tableFaculty = tableFaculty;
    }

    @FXML
    private void back(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

    private ObservableList<Department> loadFacultyCombo() {
        ObservableList<Department> myList = Tools.getDepartments();
        return myList;
    }

    private ObservableList<String> loadTitleCombo() {
        ArrayList<String> l = new ArrayList<>();
        l.add("Pr");
        l.add("Dr");
        l.add("Mr");
        l.add("Mrs");
        l.add("Miss");
        ObservableList<String> myList = FXCollections.observableArrayList(l);
        return myList;
    }

    @FXML
    public void toSave(ActionEvent event) {
        try {
            Faculty s = new Faculty();
            s.setAddress(address.getText());
            if (birthday.getValue() != null) {
                s.setBirth(new MyDate(birthday.getValue().getDayOfMonth(), birthday.getValue().getMonthValue(), birthday.getValue().getYear()));
            }
            if (datehired.getValue() != null) {
                s.setDateHired(new MyDate(datehired.getValue().getDayOfMonth(), datehired.getValue().getMonthValue(), datehired.getValue().getYear()));
            }
            if (!"".equals(email.getText())) {
                s.setEmailAddress(email.getText());
            }
            if (!"".equals(rank.getText())) {
                s.setRank(rank.getText());
            }
            if (!"".equals(office.getText())) {
                s.setOffice(office.getText());
            }
            if (!"".equals(officehours.getText())) {
                s.setOfficehours(Long.parseLong(officehours.getText()));
            }
            if (!"".equals(salary.getText())) {
                s.setSalary(Double.parseDouble(salary.getText()));
            }
            if (name.getText() != null) {
                s.setName(name.getText());
            }
            if (phone.getText() != null) {
                s.setPhoneNumber(phone.getText());
            }
            if (titleCombo.getSelectionModel().getSelectedItem() != null) {
                s.setTitle(titleCombo.getSelectionModel().getSelectedItem());
            }
            if (facultyCombo.getSelectionModel().getSelectedItem() != null) {
                s.setDepartement(facultyCombo.getSelectionModel().getSelectedItem().getName());
            }
            if (facultySpecialty.getText() != null) {
                s.setSpecialty(facultySpecialty.getText());
            }
            if (male.isSelected()) {
                s.setGender('M');
            } else {
                s.setGender('F');
            }

            Connection con = DBConnection.getConnection();
            int id_date_hired = 1;

            try {
                if (s.getDateHired() != null) {
                    String req4 = "INSERT INTO mydate(jour, mois, annee) VALUES (" + s.getDateHired().getDay() + "," + s.getDateHired().getMonth() + "," + s.getDateHired().getYear() + "); ";
                    Statement stm4 = con.createStatement();
                    int numero = stm4.executeUpdate(req4, Statement.RETURN_GENERATED_KEYS);
                    ResultSet rs = stm4.getGeneratedKeys();
                    if (rs.next()) {
                        id_date_hired = rs.getInt(1);
                    }
                }
                con.setAutoCommit(false);
                if (s.getBirth() != null) {
                    String req1 = "INSERT INTO mydate(jour, mois, annee) VALUES (?,?,?); ";
                    PreparedStatement stm1 = con.prepareStatement(req1);
                    stm1.setInt(1, s.getBirth().getDay());
                    stm1.setInt(2, s.getBirth().getMonth());
                    stm1.setInt(3, s.getBirth().getYear());
                    stm1.executeUpdate();
                }
                String req2 = "INSERT INTO person(name, address, phonenumber,email,gender,id_date_birth) VALUES (?,?,?,?,?,LAST_INSERT_ID()); ";
                PreparedStatement stm2 = con.prepareStatement(req2);
                stm2.setString(1, s.getName());
                stm2.setString(2, s.getAddress());
                if (!s.getPhoneNumber().equals("")) {
                    stm2.setInt(3, Integer.parseInt(s.getPhoneNumber()));
                }
                else
                    stm2.setInt(3, 0);
                stm2.setString(4, s.getEmailAddress());
                stm2.setString(5, String.valueOf(s.getGender()));
                String req3 = "INSERT INTO employee(salary,id_date_hired,id_person,office) VALUES(?,?,LAST_INSERT_ID(),?);";
                PreparedStatement stm3 = con.prepareStatement(req3);
                stm3.setDouble(1, s.getSalary());
                stm3.setInt(2, id_date_hired);
                stm3.setString(3, s.getOffice());
                String req5 = "INSERT INTO faculty(officehours,position,id_employee,title,departement,specialty) VALUES(?,?,LAST_INSERT_ID(),?,?,?);";
                PreparedStatement stm5 = con.prepareStatement(req5);
                stm5.setInt(1, (int) s.getOfficehours());
                stm5.setString(2, s.getRank());
                stm5.setString(3, s.getTitle());
                stm5.setString(4, s.getDepartement());
                stm5.setString(5, s.getSpecialty());
                stm2.executeUpdate();
                stm3.executeUpdate();
                stm5.executeUpdate();
                con.commit();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            tableFaculty.getItems().clear();
            tableFaculty.setItems(FXCollections.observableArrayList(LoadFaculty.loadFacultyFromDB().getList()));
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        } catch (Exception ex) {
            Logger.getLogger(AddFacultyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        titleCombo.getItems().addAll(loadTitleCombo());
        facultyCombo.getItems().addAll(loadFacultyCombo());
    }

}
