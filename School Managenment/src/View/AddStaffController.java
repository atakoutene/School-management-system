/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.DBConnection;
import Model.Staff;
import Model.MyDate;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Steve Meudje
 */
public class AddStaffController implements Initializable {
    
    @FXML
    private JFXTextField name;
    
    @FXML
    private JFXTextField address;
    
    @FXML
    private JFXTextField title;
    
    @FXML
    private JFXTextField office;
    
    @FXML
    private JFXTextField phone;
    
    @FXML
    private JFXTextField salary;
    
    @FXML
    private JFXTextField email;
    
    @FXML
    private JFXDatePicker birthday;
    
    @FXML
    private JFXDatePicker datehired;
    
    @FXML
    private JFXRadioButton male;
    
    @FXML
    private ToggleGroup gender;
    
    @FXML
    private JFXRadioButton female;
    
    @FXML
    private JFXButton btSave;
    
    @FXML
    private JFXButton btCancel;
    
    private TableView<Staff> table;
    //List staff
    ObservableList<Staff> currentStaffList;
    
    @FXML
    private JFXComboBox<String> titleStaffCombo;
    
    public void setTable(TableView<Staff> table) {
        this.table = table;
    }
    
    public void setCurrentStaffList(ObservableList<Staff> currentStaffList) {
        this.currentStaffList = currentStaffList;
    }
    
    @FXML
    private void back(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        
    }
    
    private ObservableList<String> loadTitleStaffCombo() {
        ArrayList<String> l = new ArrayList<>();
        l.add("Pr");
        l.add("Dr");
        l.add("Mr");
        l.add("Mrs");
        l.add("Miss");
        ObservableList<String> myList = FXCollections.observableArrayList(l);
        return myList;
    }
    
    public String decide(String text) {
        if (text.isEmpty()) {
            return "";
        }
        return text;
    }
    
    @FXML
    void toSave(ActionEvent event) {
        try {
            Staff s = new Staff();
            s.setAddress(address.getText());
            if (birthday.getValue() != null) {
                s.setBirth(new MyDate(birthday.getValue().getDayOfMonth(), birthday.getValue().getMonthValue(), birthday.getValue().getYear()));
            }
            if (datehired.getValue() != null) {
                s.setDateHired(new MyDate(datehired.getValue().getDayOfMonth(), datehired.getValue().getMonthValue(), datehired.getValue().getYear()));
            }
            s.setEmailAddress(email.getText());
            s.setTitle(title.getText());
            s.setOffice(office.getText());
            //s.setOfficehours(Long.parseLong(officehours.getText()));
            if (!"".equals(salary.getText())) {
                s.setSalary(Double.parseDouble(salary.getText()));
            } else {
                s.setSalary(0);
            }
            s.setName(name.getText());
            s.setPhoneNumber(phone.getText());
            if (titleStaffCombo.getSelectionModel().getSelectedItem() != null) {
                s.setTitre(titleStaffCombo.getSelectionModel().getSelectedItem());
            }
            if (male.isSelected()) {
                s.setGender('M');
            } else {
                s.setGender('F');
            }
            
            Connection con = DBConnection.getConnection();
            
            try {
                int id_date_hired = 1;
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
                String req2 = "INSERT INTO person(name, address, phonenumber,gender,id_date_birth) VALUES (?,?,?,?,LAST_INSERT_ID()); ";
                PreparedStatement stm2 = con.prepareStatement(req2);
                stm2.setString(1, s.getName());
                stm2.setString(2, s.getAddress());
                if (!"".equals(s.getPhoneNumber())) {
                    stm2.setInt(3, Integer.parseInt(s.getPhoneNumber()));
                } else {
                    stm2.setInt(3, 0);
                }
                //stm2.setString(4, s.getEmailAddress());
                stm2.setString(4, String.valueOf(s.getGender()));
                String req3 = "INSERT INTO employee(salary,id_date_hired,id_person,office) VALUES(?,?,LAST_INSERT_ID(),?);";
                PreparedStatement stm3 = con.prepareStatement(req3);
                stm3.setDouble(1, s.getSalary());
                stm3.setInt(2, id_date_hired);
                stm3.setString(3, s.getOffice());
                String req5 = "INSERT INTO staff(title,id_employee,titre) VALUES(?,LAST_INSERT_ID(),?);";
                PreparedStatement stm5 = con.prepareStatement(req5, Statement.RETURN_GENERATED_KEYS);
                stm5.setString(1, s.getTitle());
                stm5.setString(2, s.getTitre());
                stm2.executeUpdate();
                stm3.executeUpdate();
                stm5.executeUpdate();
                ResultSet rst = stm5.getGeneratedKeys();
                int id_last_staff = 1;
                if (rst.next()) {
                    id_last_staff = rst.getInt(1);
                }
                con.commit();
                s.setId(id_last_staff);
            } catch (NumberFormatException | SQLException ex) {
                ex.printStackTrace();
            }
            
            ObservableList<Staff> list = table.getItems();
            list.add(s);
            Collections.sort(list);
            currentStaffList.add(s);
            Collections.sort(currentStaffList);
            table.setItems(list);
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        } catch (Exception ex) {
            Logger.getLogger(AddStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        titleStaffCombo.getItems().addAll(loadTitleStaffCombo());
    }
    
}
