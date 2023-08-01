/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.DBConnection;
import Control.LoadProgram;
import Control.LoadStudent;
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
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Steve Meudje
 */
public class AddStudentController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private void back(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

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
    private Button btnSelectFile;
    private File file = null;

    @FXML
    private JFXTextField photoSelected;

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

    @FXML
    private JFXComboBox<Department> departmentCombo;

    private TableView<Student> table;

    @FXML
    private JFXComboBox<Program> studProgram;

    @FXML
    private JFXTextField birthplace;

    public void setTableStudent(TableView<Student> tableStudent) {
        this.table = tableStudent;
    }

    private ObservableList<Department> loadDepartmentCombo() {

        ObservableList<Department> myList = Tools.getDepartments();
        return myList;
    }

    private ObservableList<Program> loadProgramsCombo() {
        return FXCollections.observableArrayList(LoadProgram.loadProgramFromDB().getList());
    }

    @FXML
    void openFileChooserForSelect(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select an image...");
        //FileChooser.ExtensionFilter exFilter = new FileChooser.ExtensionFilter("PDF files(*.pdf)", "*.pdf");
        //chooser.getExtensionFilters().add(exFilter);
        file = chooser.showOpenDialog(null);
        if (file != null) {
            if (file.length() <= 10000) {
                photoSelected.setText(file.getName());
            } else {
                Alert a;
                a = new Alert(Alert.AlertType.ERROR, "The file selected for image profile is too large. It should be 100 x 100 pixels maximum!", ButtonType.OK);
                a.setTitle("Error!");
                a.setHeaderText("Too large file!");
                a.showAndWait();
                file = null;
            }

        }
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

    private ObservableList<String> loadSemCombo() {
        ArrayList<String> l = new ArrayList<>();
        l.add("Fall");
        l.add("Spring");
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
    public void toSave(ActionEvent event) {
        if (existStudent(refNumber.getText())) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Duplicate reference Number");
            a.setContentText("There is already student with the specified reference number please change it!!!");
            a.showAndWait();
            return;
        }
        try {
            Student s = new Student();
            s.setAddress(decide(address.getText()));
            if (birthday.getValue() != null) {
                s.setBirth(new MyDate(birthday.getValue().getDayOfMonth(), birthday.getValue().getMonthValue(), birthday.getValue().getYear()));
            }
            s.setEmailAddress(email.getText());
            s.setName(decide(name.getText()));
            s.setPhoneNumber(decide(phone.getText()));
            s.setParent_info(decide(parent_info.getText()));
            if (semCombo.getSelectionModel().getSelectedItem() != null) {
                s.setEntrance_year(decide(semCombo.getSelectionModel().getSelectedItem()) + " " + decide(entranceYearTxt.getText()));
            }
            if (male.isSelected()) {
                s.setGender('M');
            } else {
                s.setGender('F');
            }
            if (!"".equals(refNumber.getText())) {
                s.setStuID(decide(refNumber.getText()));
            }
            if (status.getSelectionModel().getSelectedItem() != null) {
                s.setStatus(status.getSelectionModel().getSelectedItem());
            }
            if (departmentCombo.getSelectionModel().getSelectedItem() != null) {
                s.setDep(decide(departmentCombo.getSelectionModel().getSelectedItem().getName()));
            }
            if (studProgram.getSelectionModel().getSelectedItem() != null) {
                s.setProgram_id(studProgram.getSelectionModel().getSelectedItem().getId());
            }
            s.setPlaceOfBirth(decide(birthplace.getText()));
            if (file != null) {
                s.setProfilePhoto(new FileInputStream(file));
                s.setProfileFile(photoSelected.getText());
            }
            Connection con = DBConnection.getConnection();
            try {
                con.setAutoCommit(false);
                if (s.getBirth() != null) {
                    String req1 = "INSERT INTO mydate(jour, mois, annee) VALUES (?,?,?); ";
                    PreparedStatement stm1 = con.prepareStatement(req1);
                    stm1.setInt(1, s.getBirth().getDay());
                    stm1.setInt(2, s.getBirth().getMonth());
                    stm1.setInt(3, s.getBirth().getYear());
                    stm1.executeUpdate();
                }
                String req2 = "INSERT INTO person(name, address, phonenumber,email,gender,id_date_birth,placeofbirth) VALUES (?,?,?,?,?,LAST_INSERT_ID(),?); ";
                PreparedStatement stm2 = con.prepareStatement(req2);
                stm2.setString(1, s.getName());
                stm2.setString(2, s.getAddress());
                if (!"".equals(s.getPhoneNumber())) {
                    stm2.setInt(3, Integer.parseInt(s.getPhoneNumber()));
                } else {
                    stm2.setInt(3, 0);
                }
                stm2.setString(4, s.getEmailAddress());
                stm2.setString(5, String.valueOf(s.getGender()));
                stm2.setString(6, s.getPlaceOfBirth());
                String req3 = "INSERT INTO student(level,departement,id_person,studentId,"
                        + "parent_info,entrance_year, id_program, profilename, profilepicture)"
                        + " VALUES(?,?,LAST_INSERT_ID(),?,?,?,?,?,?);";
                PreparedStatement stm3 = con.prepareStatement(req3);
                stm3.setString(1, s.getStatus());
                stm3.setString(2, s.getDep());
                stm3.setString(3, s.getStuID());
                stm3.setString(4, s.getParent_info());
                stm3.setString(5, s.getEntrance_year());
                stm3.setInt(6, s.getProgram_id());
                if (file == null) {
                    stm3.setString(7, "default.png");
                    // st.getIcons().add(new Image(Lanceur.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
//                    File imgfile = new File("src/Resource/default.png");
//                    FileInputStream fini;
                    Image img = new Image(Lanceur.class.getResourceAsStream("/Resource/default.png"));
                    // fini = new FileInputStream(new Image(Lanceur.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
                    BufferedImage bImage = SwingFXUtils.fromFXImage(img, null);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    ImageIO.write(bImage, "png", outputStream);
                    byte[] res = outputStream.toByteArray();
                    InputStream inputStream = new ByteArrayInputStream(res);
                    stm3.setBinaryStream(8, inputStream);
                } else {
                    stm3.setString(7, s.getProfileFile());
                    stm3.setBinaryStream(8, (InputStream) s.getProfilePhoto());
                }
                String req4 = "INSERT INTO student_department values(LAST_INSERT_ID(),?);";
                PreparedStatement stm4 = con.prepareStatement(req4);
                stm4.setInt(1, departmentCombo.getSelectionModel().getSelectedItem().getId());
                stm2.executeUpdate();
                stm3.executeUpdate();
                stm4.executeUpdate();
                con.commit();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            table.getItems().clear();
            table.setItems(FXCollections.observableArrayList(LoadStudent.loadStudentsFromDB().getList()));

            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        } catch (Exception ex) {
            Logger.getLogger(AddStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        status.getItems().addAll(loadDdataCombo());
        semCombo.getItems().addAll(loadSemCombo());
        departmentCombo.getItems().addAll(loadDepartmentCombo());
        studProgram.getItems().addAll(loadProgramsCombo());
        email.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV && !email.getText().trim().equals("")) { // focus lost
                if(btCancel.isFocused()){
                    return;
                }
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
            if (!newV && !phone.getText().trim().equals("")) { // focus lost
                if (!PatternMatching.isValidPhone(phone.getText())) {
                    if (btCancel.isFocused()) {
                        return;
                    }
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("Invalid phone number");
                    a.setContentText("Please enter a valid phone number before continuing!");
                    a.show();
                    phone.requestFocus();
                }
            }
        });

        name.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV && name.getText().trim().equals("")) { // focus lost
                if (btCancel.isFocused()) {
                    return;
                }
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Student name empty");
                a.setContentText("The student name cannot be an empty field please fill it");
                a.showAndWait();
                name.requestFocus();
            }
        });
    }

    private boolean existStudent(String text) {
        return LoadStudent.loadStudentByRefNumber(text) != null;
    }

}
