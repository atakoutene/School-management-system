/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.DBConnection;
import Control.LoadProgram;
import Control.PatternMatching;
import Control.Tools;
import Model.Department;
import Model.MyDate;
import Model.Program;
import Model.Student;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Steve Meudje
 */
public class UpdateStudentController implements Initializable {

    @FXML
    JFXTextField name;
    @FXML
    JFXTextField department;
    @FXML
    JFXTextField address;
    @FXML
    JFXTextField email;
    @FXML
    JFXComboBox<String> status;
    @FXML
    JFXRadioButton male;
    @FXML
    JFXRadioButton female;
    @FXML
    JFXDatePicker birthday;
    @FXML
    JFXTextField phone;

    @FXML
    private JFXTextField parent_info;

    @FXML
    private JFXTextField entranceYearTxt;

    @FXML
    private JFXComboBox<String> semCombo;

    @FXML
    private JFXTextField refNumber;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    private Student student;

    private TableView<Student> table;

    @FXML
    private JFXComboBox<Department> departmentCombo;

    @FXML
    private JFXComboBox<Program> studProgram;

    @FXML
    private Button btnSelectFile;
    private InputStream file = null;

    private File files;

    @FXML
    private JFXTextField photoSelected;

    @FXML
    private JFXTextField placeOfBirth;

    public void setStudent(Student student) {
        this.student = student;
    }

    private ObservableList<Program> loadProgramsCombo() {
        return FXCollections.observableArrayList(LoadProgram.loadProgramFromDB().getList());
    }

    public void setTableStudent(TableView<Student> tableStudent) {
        this.table = tableStudent;
    }

    @FXML
    private void back(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

    private ObservableList<String> loadDdataCombo() {
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
        ObservableList<String> myList = FXCollections.observableArrayList(l);
        return myList;
    }

    private ObservableList<Department> loadDepartmentCombo() {

        ObservableList<Department> myList = Tools.getDepartments();
        return myList;
    }

    private ObservableList<String> loadSemCombo() {

        ArrayList<String> l = new ArrayList<>();
        l.add("Fall");
        l.add("Spring");
        ObservableList<String> myList = FXCollections.observableArrayList(l);
        return myList;
    }

    @FXML
    public void toSave(ActionEvent event) {
        try {
            Student s = new Student();
            s.setAddress(address.getText());
            s.setBirth(new MyDate(birthday.getValue().getDayOfMonth(), birthday.getValue().getMonthValue(), birthday.getValue().getYear()));
            s.setEmailAddress(email.getText());
            s.setName(name.getText());
            s.setPhoneNumber(phone.getText());
            s.setParent_info(parent_info.getText());
            s.setEntrance_year(semCombo.getSelectionModel().getSelectedItem() + " " + entranceYearTxt.getText());
            s.setProgram_id(studProgram.getSelectionModel().getSelectedItem().getId());
            if (male.isSelected()) {
                s.setGender('M');
            } else {
                s.setGender('F');
            }
            s.setStuID(refNumber.getText());
            s.setStatus(status.getSelectionModel().getSelectedItem());
            s.setId(student.getId());
            s.setDep(departmentCombo.getSelectionModel().getSelectedItem().getName());
            s.setPlaceOfBirth(placeOfBirth.getText());
            if (files != null) {
                s.setProfilePhoto(new FileInputStream(files));
                s.setProfileFile(photoSelected.getText());
            }
            s.setProfileFile(photoSelected.getText());
            updateStudent(s);
            ObservableList<Student> tmp = FXCollections.observableArrayList(new ArrayList<Student>(table.getItems()));
            Student ff = null;
            int i = 0;
            boolean b = false;
            while (!tmp.isEmpty() && (i < tmp.size()) && !b) {
                ff = tmp.get(i);
                if (ff.getId() == student.getId()) {
                    b = true;
                }
                i++;
            }
            tmp.remove(ff);
            table.getItems().clear();
            tmp.add(s);
            Collections.sort(tmp);
            table.setItems(tmp);
            table.getSelectionModel().select(s);
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();

        } catch (Exception ex) {
            Logger.getLogger(UpdateStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateStudent(Student stud) {
        Connection con = DBConnection.getConnection();
        try {
            String req = "update student s, person p, mydate d "
                    + "set s.studentId = ?, s.level = ?, s.departement = ?, s.parent_info = ?, s.entrance_year = ?, s.id_program = ?, p.name = ?,"
                    + "p.address = ?, p.phonenumber = ?, p.email = ?, p.gender =?, d.jour = ?, d.mois = ?, d.annee = ?, "
                    + " s.profilename = ?, s.profilepicture = ?, p.placeofbirth = ? "
                    + "where s.id = ? and s.id_person = p.id and p.id_date_birth = d.id;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setString(1, stud.getStuID());
            stm.setString(2, stud.getStatus());
            stm.setString(3, stud.getDep());
            stm.setString(4, stud.getParent_info());
            stm.setString(5, stud.getEntrance_year());
            stm.setInt(6, stud.getProgram_id());
            stm.setString(7, stud.getName());
            stm.setString(8, stud.getAddress());
            stm.setInt(9, Integer.parseInt(stud.getPhoneNumber()));
            stm.setString(10, stud.getEmailAddress());
            stm.setString(11, String.valueOf(stud.getGender()));
            stm.setInt(12, stud.getBirth().getDay());
            stm.setInt(13, stud.getBirth().getMonth());
            stm.setInt(14, stud.getBirth().getYear());
            if (files == null) {
                stm.setString(15, stud.getProfileFile());
                stm.setBinaryStream(16, file);
            } else {
                stm.setString(15, stud.getProfileFile());
                stm.setBinaryStream(16, (InputStream) stud.getProfilePhoto());
            }
            stm.setString(17, stud.getPlaceOfBirth());
            stm.setInt(18, stud.getId());
            stm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UpdateStudentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateStudentLevel(Student stud) {
        Connection con = DBConnection.getConnection();
        try {
            String req = "update student set level = ? where id = ?;";
            PreparedStatement stm = con.prepareStatement(req);
            stm.setString(1, stud.getStatus());
            stm.setInt(2, stud.getId());
            stm.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UpdateStudentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void openFileChooserForSelect(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select an image...");
        //FileChooser.ExtensionFilter exFilter = new FileChooser.ExtensionFilter("PDF files(*.pdf)", "*.pdf");
        //chooser.getExtensionFilters().add(exFilter);
        files = chooser.showOpenDialog(null);
        if (files != null) {
            if (files.length() <= 10000) {
                photoSelected.setText(files.getName());
            } else {
                Alert a;
                a = new Alert(Alert.AlertType.ERROR, "The file selected for image profile is too large. It should be 100 x 100 pixels maximum!", ButtonType.OK);
                a.setTitle("Error!");
                a.setHeaderText("Too large file!");
                a.showAndWait();
                files = null;
            }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        status.getItems().addAll(loadDdataCombo());
        status.getItems().add("");
        semCombo.getItems().addAll(loadSemCombo());
        semCombo.getItems().add("");
        departmentCombo.getItems().addAll(loadDepartmentCombo());
        departmentCombo.getItems().add(null);
        studProgram.getItems().addAll(loadProgramsCombo());
        studProgram.getItems().add(null);
        email.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV && !email.getText().equals("")) { // focus lost
                if (!PatternMatching.isEmail(email.getText())) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("Invalid email");
                    a.setContentText("Please enter a valid email before continuing!");
                    a.show();
                    email.requestFocus();
                }
            }
        });
        phone.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV && !phone.getText().equals("")) { // focus lost
                if (!PatternMatching.isValidPhone(phone.getText())) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("Invalid phone number");
                    a.setContentText("Please enter a valid phone number before continuing!");
                    a.show();
                    phone.requestFocus();
                }
            }
        });
    }

    public void initComp() {
        refNumber.setText(student.getStuID());
        //iDtextStudent.setEditable(false);
        name.setText(student.getName());
        if (!"".equals(student.getStatus())) {
            status.getSelectionModel().select(student.getStatus());
        }
        departmentCombo.getSelectionModel().select(Tools.getDepartmentByName(student.getDep()));
        for (Program pr : studProgram.getItems()) {
            if (pr.getId() == student.getProgram_id()) {
                studProgram.getSelectionModel().select(pr);
                break;
            }
        }
        if (student.getGender() == 'M') {
            male.setSelected(true);
            female.setSelected(false);
        } else {
            male.setSelected(false);
            female.setSelected(true);
        }
        phone.setText(student.getPhoneNumber());
        status.getSelectionModel().select(student.getStatus());
        address.setText(student.getAddress());
        email.setText(student.getEmailAddress());
        birthday.setConverter(Tools.getDateConverter());
        if (!"".equals(student.getBirth())) {
            birthday.setValue(LocalDate.of(student.getBirth().getYear(), student.getBirth().getMonth(), student.getBirth().getDay()));
        }
        parent_info.setText(student.getParent_info());
        if (student.getEntrance_year() != null) {
            semCombo.getSelectionModel().select(student.getEntrance_year().substring(0, student.getEntrance_year().indexOf(" ")));
            entranceYearTxt.setText(student.getEntrance_year().substring(student.getEntrance_year().lastIndexOf(" ") + 1));
        } else {
            semCombo.getSelectionModel().select("");
            entranceYearTxt.setText("");
        }
        photoSelected.setText(student.getProfileFile());
        file = student.getProfilePhoto();
        placeOfBirth.setText(student.getPlaceOfBirth());
    }
}
