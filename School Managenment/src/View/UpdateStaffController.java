/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.DBConnection;
import Control.Tools;
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
import java.time.LocalDate;
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
public class UpdateStaffController implements Initializable {
    
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
    
    @FXML
    private JFXComboBox<String> titleStaffCombo;
    
    private Staff myStaff;
    
    private TableView<Staff> tableStaff;

    //List staff
    ObservableList<Staff> currentStaffList;
    
    public void setMyStaff(Staff myStaff) {
        this.myStaff = myStaff;
    }
    
    public void setTableStaff(TableView<Staff> tableStaff) {
        this.tableStaff = tableStaff;
    }
    
    public void setCurrentStaffList(ObservableList<Staff> currentStaffList) {
        this.currentStaffList = currentStaffList;
    }
    
    public JFXComboBox<String> getTitleStaffCombo() {
        return titleStaffCombo;
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
    
    @FXML
    private void back(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        
    }
    
    public String decide(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        return text;
    }
    
    @FXML
    void toSave(ActionEvent event) {
        try {
            Staff s = new Staff();
            s.setAddress(decide(address.getText()));
            if (birthday.getValue() != null) {
                s.setBirth(new MyDate(birthday.getValue().getDayOfMonth(), birthday.getValue().getMonthValue(), birthday.getValue().getYear()));
            }
            if (datehired.getValue() != null) {
                s.setDateHired(new MyDate(datehired.getValue().getDayOfMonth(), datehired.getValue().getMonthValue(), datehired.getValue().getYear()));
            }
            s.setEmailAddress(decide(email.getText()));
            s.setTitle(decide(title.getText()));
            s.setOffice(decide(office.getText()));
            if (!"".equals(salary.getText())) {
                s.setSalary(Double.parseDouble(salary.getText()));
            } else {
                s.setSalary(0);
            }
            s.setName(decide(name.getText()));
            s.setPhoneNumber(phone.getText());
            s.setTitre(titleStaffCombo.getSelectionModel().getSelectedItem());
            if (male.isSelected()) {
                s.setGender('M');
            } else {
                s.setGender('F');
            }
            s.setId(myStaff.getId());
            updateStaff(s);
            ObservableList<Staff> tmp = tableStaff.getItems();
            Staff ff = null;
            int i = 0;
            boolean b = false;
            while (!tmp.isEmpty() && (i < tmp.size()) && !b) {
                ff = tmp.get(i);
                if (ff.getId() == myStaff.getId()) {
                    b = true;
                }
                i++;
            }
            b = false;
            int j = 0;
            Staff ff2 = null;
            while (!currentStaffList.isEmpty() && (j < currentStaffList.size()) && !b) {
                ff2 = currentStaffList.get(j);
                if (ff2.getId() == myStaff.getId()) {
                    b = true;
                }
                j++;
            }
            tmp.remove(ff);
            currentStaffList.remove(ff2);
            tmp.add(s);
            Collections.sort(tmp);
            currentStaffList.add(s);
            Collections.sort(currentStaffList);
            tableStaff.setItems(tmp);
            tableStaff.getSelectionModel().select(s);
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        } catch (Exception ex) {
            Logger.getLogger(UpdateStaffController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void updateStaff(Staff staff) {
        Connection con = DBConnection.getConnection();
        try {
            String req = "update staff s, employee e, person p, mydate d "
                    + "set s.title = ?, s.titre = ?, e.salary = ?, e.office = ?, p.name = ?,"
                    + "p.address = ?, p.phonenumber = ?, p.email = ?, p.gender =?, d.jour = ?, d.mois = ?, d.annee = ? "
                    + "where s.id = ? and s.id_employee = e.id and e.id_person = p.id and p.id_date_birth = d.id;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setString(1, staff.getTitle());
            stm.setString(2, staff.getTitre());
            stm.setDouble(3, staff.getSalary());
            stm.setString(4, staff.getOffice());
            stm.setString(5, staff.getName());
            stm.setString(6, staff.getAddress());
            stm.setInt(7, Integer.parseInt(staff.getPhoneNumber()));
            stm.setString(8, staff.getEmailAddress());
            stm.setString(9, String.valueOf(staff.getGender()));
            stm.setInt(10, staff.getBirth().getDay());
            stm.setInt(11, staff.getBirth().getMonth());
            stm.setInt(12, staff.getBirth().getYear());
            stm.setInt(13, staff.getId());
            stm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        titleStaffCombo.getItems().addAll(loadTitleStaffCombo());
    }
    
    public void initComp() {
        titleStaffCombo.getSelectionModel().select(myStaff.getTitre());
        name.setText(myStaff.getName());
        office.setText(myStaff.getOffice());
        title.setText(myStaff.getTitle());
        address.setText(myStaff.getAddress());
        email.setText(myStaff.getEmailAddress());
        phone.setText(String.valueOf(myStaff.getPhoneNumber()));
        salary.setText(String.valueOf(myStaff.getSalary()));
        if (myStaff.getGender() == 'M') {
            male.setSelected(true);
            female.setSelected(false);
        } else {
            male.setSelected(false);
            female.setSelected(true);
        }
        birthday.setConverter(Tools.getDateConverter());
        birthday.setValue(LocalDate.of(myStaff.getBirth().getYear(), myStaff.getBirth().getMonth(), myStaff.getBirth().getDay()));
        datehired.setConverter(Tools.getDateConverter());
        datehired.setValue(LocalDate.of(myStaff.getDateHired().getYear(), myStaff.getDateHired().getMonth(), myStaff.getDateHired().getDay()));
    }
    
}
