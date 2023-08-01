/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.CRUDDepartment;
import Control.DBConnection;
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
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Steve Meudje
 */
public class UpdateFacultyController implements Initializable {

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

    private Faculty myFaculty;


    public void setTableFaculty(TableView<Faculty> tableFaculty) {
        this.tableFaculty = tableFaculty;
    }

    public void setMyFaculty(Faculty fac) {
        this.myFaculty = fac;
    }

    @FXML
    private void back(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

    private ObservableList<Department> loadDepartmentCombo() {
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
            if(!"".equals(address.getText()))
                s.setAddress(address.getText());
            else
                s.setAddress("");
            if(birthday.getValue() != null)
                s.setBirth(new MyDate(birthday.getValue().getDayOfMonth(), birthday.getValue().getMonthValue(), birthday.getValue().getYear()));
            if(datehired.getValue() != null)
                s.setDateHired(new MyDate(datehired.getValue().getDayOfMonth(), datehired.getValue().getMonthValue(), datehired.getValue().getYear()));
            if(!"".equals(email.getText()))
                s.setEmailAddress(email.getText());
            else
                s.setEmailAddress("");
            if(!"".equals(rank.getText()))
                s.setRank(rank.getText());
            else
                s.setRank("");
            if(!"".equals(office.getText()))
                s.setOffice(office.getText());
            else
                s.setOffice("");
            if(!"".equals(officehours.getText()))
                s.setOfficehours(Long.parseLong(officehours.getText()));
            else
                s.setOfficehours(0);
            if(!"".equals(salary.getText()))
                s.setSalary(Double.parseDouble(salary.getText()));
            else
                s.setSalary(0);
            if(!"".equals(name.getText()))
                s.setName(name.getText());
            else
                s.setName("");
            if(!"".equals(phone.getText()))
                s.setPhoneNumber(phone.getText());
            else
                s.setPhoneNumber("");
            if(titleCombo.getSelectionModel().getSelectedItem() != null)
                s.setTitle(titleCombo.getSelectionModel().getSelectedItem());
            if(facultyCombo.getSelectionModel().getSelectedItem() != null)
                s.setDepartement(facultyCombo.getSelectionModel().getSelectedItem().getName());
            if(!"".equals(facultySpecialty.getText()))
                s.setSpecialty(facultySpecialty.getText());
            s.setId(myFaculty.getId());
            if (male.isSelected()) {
                s.setGender('M');
            } else {
                s.setGender('F');
            }
            updateFaculty(s);
            ObservableList<Faculty> tmp = FXCollections.observableArrayList(new ArrayList<Faculty>(tableFaculty.getItems()));
            Faculty ff = null;
            int i = 0;
            boolean b = false;
            while (!tmp.isEmpty() && (i < tmp.size()) && !b) {
                ff = tmp.get(i);
                if (ff.getId() == myFaculty.getId()) {
                    b = true;
                }
                i++;
            }
            tmp.remove(ff);
            tableFaculty.getItems().clear();
            tmp.add(s);
            Collections.sort(tmp);
            tableFaculty.setItems(tmp);
            tableFaculty.getSelectionModel().select(s);
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        } catch (Exception ex) {
            Logger.getLogger(UpdateFacultyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateFaculty(Faculty faculty) {
        Connection con = DBConnection.getConnection();
        try {
            String req = "update faculty f, employee e, person p, mydate d "
                    + "set f.officehours = ?, f.position = ?, f.title = ?, f.departement = ?, f.specialty = ?, "
                    + "e.salary = ?, e.office = ?, p.name = ?,"
                    + "p.address = ?, p.phonenumber = ?, p.email = ?, p.gender =?, d.jour = ?, d.mois = ?, d.annee = ? "
                    + "where f.id = ? and f.id_employee = e.id and e.id_person = p.id and p.id_date_birth = d.id;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setInt(1, (int) faculty.getOfficehours());
            stm.setString(2, faculty.getRank());
            stm.setString(3, faculty.getTitle());
            stm.setString(4, faculty.getDepartement());
            stm.setString(5, faculty.getSpecialty());
            stm.setDouble(6, faculty.getSalary());
            stm.setString(7, faculty.getOffice());
            stm.setString(8, faculty.getName());
            stm.setString(9, faculty.getAddress());
            stm.setInt(10, Integer.parseInt(faculty.getPhoneNumber()));
            stm.setString(11, faculty.getEmailAddress());
            stm.setString(12, String.valueOf(faculty.getGender()));
            if(faculty.getBirth() != null){
                stm.setInt(13, faculty.getBirth().getDay());
                stm.setInt(14, faculty.getBirth().getMonth());
                stm.setInt(15, faculty.getBirth().getYear());
            }
            else{
                stm.setInt(13, 1);
                stm.setInt(14, 1);
                stm.setInt(15, 2099);
            }
            stm.setInt(16, faculty.getId());
            stm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        titleCombo.getItems().addAll(loadTitleCombo());
        facultyCombo.getItems().addAll(loadDepartmentCombo());

    }

    
    public void initComp() {
        titleCombo.getSelectionModel().select(myFaculty.getTitle());
        facultyCombo.getSelectionModel().select(CRUDDepartment.findByName(myFaculty.getDepartement()));
        name.setText(myFaculty.getName());
        office.setText(myFaculty.getOffice());
        rank.setText(myFaculty.getRank());
        officehours.setText(String.valueOf(myFaculty.getOfficehours()));
        address.setText(myFaculty.getAddress());
        email.setText(myFaculty.getEmailAddress());
        phone.setText(String.valueOf(myFaculty.getPhoneNumber()));
        salary.setText(String.valueOf(myFaculty.getSalary()));
        facultySpecialty.setText(myFaculty.getSpecialty());
        if (myFaculty.getGender() == 'M') {
            male.setSelected(true);
            female.setSelected(false);
        } else {
            male.setSelected(false);
            female.setSelected(true);
        }
        birthday.setConverter(Tools.getDateConverter());
        birthday.setValue(LocalDate.of(myFaculty.getBirth().getYear(), myFaculty.getBirth().getMonth(), myFaculty.getBirth().getDay()));
        datehired.setConverter(Tools.getDateConverter());
        datehired.setValue(LocalDate.of(myFaculty.getDateHired().getYear(), myFaculty.getDateHired().getMonth(), myFaculty.getDateHired().getDay()));
    }

}
