/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.CRUDDepartment;
import Control.CRUDFaculty;
import Control.CRUDProgram;
import Control.CRUDSchedule;
import Control.CRUDSemester;
import Control.CRUDStaff;
import Control.CRUDStudent;
import Control.CRUDStudentCourse;
import Control.CRUDTest;
import Control.CourseOfferedMgnt;
import Control.DBConnection;
import Control.LoadDepartment;
import Control.ManageCourse;
import Control.LoadFaculty;
import Control.LoadStaff;
import Control.LoadStudent;
import Model.Faculty;
import Model.Program;
import Model.Staff;
import Model.Student;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JTextField;
import Control.LoadProgram;
import Control.LoadSemester;
import Control.ManageProgramCourses;
import Control.MaxCredits;
import Control.Tools;
import Model.Course;
import Model.CourseComparator;
import Model.CourseOffered;
import Model.CourseOfferedComparator;
import Model.Department;
import Model.ETestType;
import Model.PositiveNumberException;
import Model.ProgramCourses;
import Model.Room;
import Model.Schedule;
import Model.SchoolFaculty;
import Model.SchoolLevel;
import Model.Semester;
import Model.Student_Course;
import Model.StudentsProgDTO;
import Model.TakeTest;
import Model.Taught_By;
import Utilitaires.reports.FinalResultsReport;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Stream;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.DoubleStringConverter;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Steve Meudje
 */
public class MainPageForAllController implements Initializable {

    //Config Tab
    @FXML
    private TableView<Department> departmentTable;

    @FXML
    private TableColumn<Department, String> deptTableColName;

    @FXML
    private TableColumn<Department, String> deptTableColHead;

    @FXML
    private Button addDepartement;

    @FXML
    private Button editDepartment;

    @FXML
    private Button deleteDepartment;

    @FXML
    private TableView<StudentsProgDTO> departmentStudTable;

    @FXML
    private TableColumn<StudentsProgDTO, String> departmentStudTableName;

    @FXML
    private TableColumn<StudentsProgDTO, String> departmentStudTableRef;

    @FXML
    private TableColumn<StudentsProgDTO, String> departmentStudTableLev;

    @FXML
    private TableColumn<StudentsProgDTO, String> departmentStudTableProg;

    @FXML
    private JFXTextField signingAuthorities;

    @FXML
    private TableColumn<StudentsProgDTO, String> departmentStudTableEnt;

    public MainPageForAllController() {
        this.semchoices = FXCollections.observableArrayList("Spring", "Fall", "Summer");
    }

    @FXML
    void addDepartmentAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Department.fxml"));
            Stage st = new Stage(StageStyle.DECORATED);
            st.setScene(new Scene((Pane) loader.load()));
            st.getIcons().add(new Image(Lanceur.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
            DepartmentController controller = loader.getController();
            controller.setDepartmentTable(departmentTable);
            st.setResizable(false);
            st.show();
        } catch (IOException ex) {
            Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void deleteDepartmentAction(ActionEvent event) {
        Alert a;
        if (departmentTable.getSelectionModel().getSelectedItem() == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a row before clicking on DELETE", ButtonType.OK);
            a.setTitle("Error!");
            a.setHeaderText("No element selected!");
            a.show();
        } else {
            Department ad = departmentTable.getSelectionModel().getSelectedItem();
            a = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wanna Delete that department ?", ButtonType.OK, ButtonType.CANCEL);
            a.setTitle("Error!");
            a.showAndWait();
            if (a.getResult() == ButtonType.CANCEL) {
                a.close();
            } else {
                //table.getItems().remove(table.getSelectionModel().getSelectedItem());
                departmentTable.getItems().remove(departmentTable.getSelectionModel().getSelectedIndex());
                CRUDDepartment listofdeps = new CRUDDepartment(LoadDepartment.loadDepFromDB().getList());
                listofdeps.delete(ad);
                //StoreStudent.storestudents(listofadresses);
            }
        }
    }

    @FXML
    void editDepartmentAction(ActionEvent event) {
        Alert a;
        if (departmentTable.getSelectionModel().getSelectedItem() == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a row before on the Edit Button", ButtonType.OK);
            a.showAndWait();
        } else {
            Department dept = departmentTable.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Department.fxml"));
                Stage st = new Stage(StageStyle.DECORATED);
                st.setScene(new Scene((Pane) loader.load()));
                st.getIcons().add(new Image(Lanceur.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
                DepartmentController controller = loader.getController();
                controller.setDepartmentTable(departmentTable);
                controller.initWindow(dept);
                st.setResizable(false);
                st.show();
            } catch (IOException ex) {
                Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void showStudentsDept(MouseEvent event) {
        Department dept = departmentTable.getSelectionModel().getSelectedItem();
        if (dept != null) {
            departmentStudTable.setItems(Tools.getStudentsDept(dept.getId()));
            departmentStudTable.refresh();
            departmentFaculties.setItems(loadDdataFacultyDepartment(dept.getName()));
            departmentFaculties.refresh();
        }
    }

    @FXML
    private TableView<Semester> tableSemester;

    @FXML
    private TableColumn<Semester, String> tableSemesterColName;

    @FXML
    private TableColumn<Semester, Integer> tableSemesterColYear;

    @FXML
    private Button addSemester;

    @FXML
    private Button editSemester;

    @FXML
    private Button deleteSemester;

    @FXML
    void addSemesterAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Semester.fxml"));
            Stage st = new Stage(StageStyle.DECORATED);
            st.setScene(new Scene((Pane) loader.load()));
            st.getIcons().add(new Image(Lanceur.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
            SemesterController controller = loader.getController();
            controller.setTableSemester(tableSemester);
            st.setResizable(false);
            st.show();
        } catch (IOException ex) {
            Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void deleteSemesterAction(ActionEvent event) {
        Alert a;
        if (tableSemester.getSelectionModel().getSelectedItem() == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a row before clicking on DELETE", ButtonType.OK);
            a.setTitle("Error!");
            a.setHeaderText("No element selected!");
            a.show();
        } else {
            Semester ad = tableSemester.getSelectionModel().getSelectedItem();
            a = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wanna Delete this Semester ?", ButtonType.OK, ButtonType.CANCEL);
            a.setTitle("Error!");
            a.showAndWait();
            if (a.getResult() == ButtonType.CANCEL) {
                a.close();
            } else {
                //table.getItems().remove(table.getSelectionModel().getSelectedItem());
                tableSemester.getItems().remove(tableSemester.getSelectionModel().getSelectedIndex());
                CRUDSemester listofsems = new CRUDSemester(LoadSemester.loadSemFromDB().getList());
                listofsems.delete(ad);
                //StoreStudent.storestudents(listofadresses);
            }
        }
    }

    @FXML
    void editSemesterAction(ActionEvent event) {
        Alert a;
        Semester sem;
        if ((sem = tableSemester.getSelectionModel().getSelectedItem()) == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a row before clicking on the Edit Button", ButtonType.OK);
            a.showAndWait();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Semester.fxml"));
                Stage st = new Stage(StageStyle.DECORATED);
                st.setScene(new Scene((Pane) loader.load()));
                st.getIcons().add(new Image(Lanceur.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
                SemesterController controller = loader.getController();
                controller.setTableSemester(tableSemester);
                controller.initWindow(sem);
                st.setResizable(false);
                st.show();
            } catch (IOException ex) {
                Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //StudentMangement
    @FXML
    JFXTextField searchStudent;
    @FXML
    JFXTextField iDtextStudent;
    @FXML
    JFXTextField nametextStudent;
    @FXML
    JFXTextField statustextStudent;
    @FXML
    JFXTextField deptextStudent;
    @FXML
    JFXTextField teltextStudent;
    @FXML
    JFXTextField emailtextStudent;
    @FXML
    JFXTextField addresstextStudent;
    @FXML
    JFXTextField gendertextStudent;
    @FXML
    JFXTextField birthdaytextStudent;
    @FXML
    private TableView<Student> table;

    @FXML
    private TableColumn<Student, String> nameStudentCol;

    @FXML
    private TableColumn<Student, String> telStudentCol;

    @FXML
    private TableColumn<Student, String> statusStudentCol;

    @FXML
    JFXButton registerStudent;
    @FXML
    JFXButton EditStudent;
    @FXML
    JFXButton deleteStudent;
    @FXML
    JFXButton saveChangesStudent;
    @FXML
    JFXButton cancelchangesStudent;
    @FXML
    private JFXTextField studParentInfo;

    @FXML
    private JFXTextField studEntranceYear;

    @FXML
    private JFXTextField textStudProgram;

    @FXML
    private ImageView profileImageStudent;

    private ObservableList<Student> loadDdata() {
        ArrayList<Student> students = LoadStudent.loadStudentsFromDB().getList();
        if (students == null) {
            return null;
        }
        ObservableList<Student> myList = FXCollections.observableArrayList(students);
        return myList;
    }

    private ObservableList<Student> makelistObservable(CRUDStudent l) {
        if (l == null) {
            return null;
        }
        ObservableList<Student> myList = FXCollections.observableArrayList(l.getList());
        return myList;
    }

    @FXML
    public void searchStudent() {
        searchStudent.getText();
        CRUDStudent list = new CRUDStudent();
        list.setList(LoadStudent.loadStudentsFromDB().searchposibilities(searchStudent.getText()).getList());
        table.setItems(makelistObservable(list));
    }

    @FXML
    public void delete() {
        Alert a;
        if (table.getSelectionModel().getSelectedItem() == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a row before clicking on DELETE", ButtonType.OK);
            a.setTitle("Error!");
            a.setHeaderText("No element selected!");
            a.show();
        } else {
            Student ad = table.getSelectionModel().getSelectedItem();
            a = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wanna Delete that student ?", ButtonType.OK, ButtonType.CANCEL);
            a.setTitle("Error!");
            a.showAndWait();
            if (a.getResult() == ButtonType.CANCEL) {
                a.close();
            } else {
                //table.getItems().remove(table.getSelectionModel().getSelectedItem());
                table.getItems().remove(table.getSelectionModel().getSelectedIndex());
                CRUDStudent listofadresses = new CRUDStudent(LoadStudent.loadStudentsFromDB().getList());
                listofadresses.delete(ad);
                clearStudentForm();
                //StoreStudent.storestudents(listofadresses);
            }
        }
    }

    @FXML
    public void display() {
        Student s = table.getSelectionModel().getSelectedItem();
        if (s != null) {
            iDtextStudent.setText(s.getStuID());
            nametextStudent.setText(s.getName());
            deptextStudent.setText(s.getDep());
            if (s.getGender() == 'M') {
                gendertextStudent.setText("Male");
            } else {
                gendertextStudent.setText("Female");
            }
            teltextStudent.setText(s.getPhoneNumber());
            statustextStudent.setText(s.getStatus());
            addresstextStudent.setText(s.getAddress());
            emailtextStudent.setText(s.getEmailAddress());
            birthdaytextStudent.setText(s.getBirth().toString());
            studParentInfo.setText(s.getParent_info());
            studEntranceYear.setText(s.getEntrance_year());
            textStudProgram.setText(LoadProgram.getProgram(s.getProgram_id()).getTitle());
            s.setProfilePhoto(Tools.loadImageProfiles(s.getId()));
            s.setProfileFile(Tools.loadImageName(s.getId()));
            Image image = new Image(s.getProfilePhoto());
            profileImageStudent.setImage(image);

        }
    }

    @FXML
    public void toADDPage(ActionEvent action) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddStudent.fxml"));
            Stage st = new Stage(StageStyle.DECORATED);
            st.setScene(new Scene((Pane) loader.load()));
            st.getIcons().add(new Image(Lanceur.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
            AddStudentController controller = loader.getController();
            controller.setTableStudent(table);
            st.setResizable(false);
            st.setOnCloseRequest(e -> {
                e.consume();
            });
            st.show();
        } catch (IOException ex) {
            Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void toModifyPage(ActionEvent action) {
        Alert a;
        if (table.getSelectionModel().getSelectedItem() == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a row before clicking this button!", ButtonType.OK);
            a.showAndWait();
        } else {
            Student s = table.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateStudent.fxml"));
                Stage st = new Stage(StageStyle.DECORATED);
                st.setScene(new Scene((Pane) loader.load()));
                st.getIcons().add(new Image(Lanceur.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
                UpdateStudentController controller = loader.<UpdateStudentController>getController();
                s.setProfilePhoto(Tools.loadImageProfiles(s.getId()));
                s.setProfileFile(Tools.loadImageName(s.getId()));
                controller.setStudent(s);
                controller.setTableStudent(table);
                controller.initComp();
                st.setResizable(false);
                st.show();

            } catch (IOException ex) {
                Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void clearStudentForm() {
        iDtextStudent.clear();
        nametextStudent.clear();
        addresstextStudent.clear();
        deptextStudent.clear();
        emailtextStudent.clear();
        teltextStudent.clear();
        statustextStudent.clear();
        gendertextStudent.clear();
        birthdaytextStudent.clear();
        studParentInfo.clear();
        studEntranceYear.clear();
        textStudProgram.clear();
    }

    //StaffManagement
    @FXML
    JFXTextField searchStaff;
    @FXML
    JFXTextField nameStaff;
    @FXML
    JFXTextField adressStaff;
    @FXML
    JFXTextField phonenumberStaff;
    @FXML
    JFXTextField emailStaff;
    @FXML
    JFXTextField officeStaff;
    @FXML
    JFXTextField titleStaff;

    @FXML
    JFXTextField salaryStaff;
    @FXML
    JFXTextField genderStaff;
    @FXML
    JFXTextField datehiredStaff;

    @FXML
    JFXTextField datebirthStaff1;

    @FXML
    JTextField myTitleStaff1;

    //List staff
    ObservableList<Staff> currentStaffList = FXCollections.observableArrayList();

    //List Program
    ObservableList<Program> currentProgramList = FXCollections.observableArrayList();

    //List Course
    ObservableList<Course> currentCourseList = FXCollections.observableArrayList();

    @FXML
    public void searchStaff() {
        tableStaff.setItems(CRUDStaff.searchposibilitie(
                currentStaffList, searchStaff.getText()));
    }

    private ObservableList<Staff> loadDdataStaff() {
        ArrayList<Staff> staffs = LoadStaff.loadStaffFromDB().getList();
        if (staffs.isEmpty()) {
            return null;
        }
        ObservableList<Staff> myList = FXCollections.observableArrayList(staffs);
        return myList;
    }

    @FXML
    private TableView<Staff> tableStaff;

    @FXML
    private TableColumn<Staff, String> nameStaffCol;

    @FXML
    private TableColumn<Staff, String> telStaffCol;

    @FXML
    private TableColumn<Staff, String> staffTitleCol;

    @FXML
    private TableColumn<Staff, String> staffPositionCol;

    @FXML
    JFXButton registerStaff;
    @FXML
    JFXButton EditStaff;
    @FXML
    JFXButton deleteStaff;
    @FXML
    JFXButton saveChangesStaff;
    @FXML
    JFXButton cancelchangesStaff;
    @FXML
    private JFXTextField staffMgntTitle;

    @FXML
    public void displayStaff() {
        Staff s = tableStaff.getSelectionModel().getSelectedItem();
        if (s != null) {
            nameStaff.setText(s.getName());
            if (s.getGender() == 'M') {
                genderStaff.setText("Male");
            } else {
                genderStaff.setText("Female");
            }
            phonenumberStaff.setText(s.getPhoneNumber());
            adressStaff.setText(s.getAddress());
            emailStaff.setText(s.getEmailAddress());
            officeStaff.setText(s.getOffice());
            salaryStaff.setText(Double.toString(s.getSalary()));
            titleStaff.setText(s.getTitle());
            datehiredStaff.setText(s.getDateHired().toString());
            datebirthStaff1.setText(s.getBirth().toString());
            staffMgntTitle.setText(s.getTitre());

        }
    }

    @FXML
    public void toADDPageStaff(ActionEvent action) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddStaff.fxml"));
            Stage st = new Stage(StageStyle.DECORATED);
            st.setScene(new Scene((Pane) loader.load()));
            st.getIcons().add(new Image(Lanceur.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
            AddStaffController controller = loader.<AddStaffController>getController();
            controller.setTable(tableStaff);
            controller.setCurrentStaffList(currentStaffList);
            st.setResizable(false);
            st.show();
        } catch (IOException ex) {
            Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void modifyStaff(ActionEvent action) {
        Alert a;
        if (tableStaff.getSelectionModel().getSelectedItem() == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a row before clicking this button!", ButtonType.OK);
            a.showAndWait();
        } else {
            Staff s = tableStaff.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateStaff.fxml"));
                Stage st = new Stage(StageStyle.DECORATED);
                st.setScene(new Scene((Pane) loader.load()));
                st.getIcons().add(new Image(Lanceur.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
                UpdateStaffController controller = loader.<UpdateStaffController>getController();
                controller.setMyStaff(s);
                controller.setTableStaff(tableStaff);
                controller.setCurrentStaffList(currentStaffList);
                controller.initComp();
                st.setResizable(false);
                st.show();

            } catch (IOException ex) {
                Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    public void deleteStaff() {
        Alert a;
        if (tableStaff.getSelectionModel().getSelectedItem() == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please ble before clicking on DELETE", ButtonType.OK);
            a.setTitle("Error!");
            a.setHeaderText("No element selected!");
            a.show();
        } else {
            Staff ad = tableStaff.getSelectionModel().getSelectedItem();
            a = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wanna Delete that  faculty ?", ButtonType.OK, ButtonType.CANCEL);
            a.setTitle("Error!");
            a.showAndWait();
            if (a.getResult() == ButtonType.CANCEL) {
                a.close();
            } else {

                tableStaff.getItems().remove(tableStaff.getSelectionModel().getSelectedIndex());
                CRUDStaff listOfStaff = new CRUDStaff(LoadStaff.loadStaffFromDB().getList());
                listOfStaff.delete(ad);
                clearStaffForm();
            }
        }
    }

    private void clearStaffForm() {
        // iDtextStudent.clear();
        nameStaff.clear();
        adressStaff.clear();
        officeStaff.clear();
        // officehoursFaculty.clear();
        salaryStaff.clear();
        datehiredStaff.clear();
        datebirthStaff1.clear();
        titleStaff.clear();
        //deptextStudent.clear();
        emailStaff.clear();
        phonenumberStaff.clear();
        //statustextStudent.clear();
        genderStaff.clear();
        // birthdaytextStudent.clear();
        titleStaff.clear();
    }

    //FacultyManagement
    @FXML
    JFXTextField searchFaculty;
    @FXML
    JFXTextField nameFaculty;
    @FXML
    JFXTextField adressFaculty;
    @FXML
    JFXTextField phonenumberFaculty;
    @FXML
    JFXTextField emailFaculty;
    @FXML
    JFXTextField officeFaculty;
    @FXML
    JFXTextField rankFaculty;
    @FXML
    JFXTextField officehoursFaculty;
    @FXML
    JFXTextField salaryFaculty;
    @FXML
    JFXTextField genderFaculty;
    @FXML
    JFXTextField datehiredFaculty;

    @FXML
    JFXTextField datebirthFaculty;

    @FXML
    private JFXTextField facultySpecialty;

    @FXML
    private JFXTextField depText;

    @FXML
    private JFXTextField titleTxfiel;

    @FXML
    public void searchFaculty() {
        searchFaculty.getText();
        CRUDFaculty list = new CRUDFaculty();
        list.setList(LoadFaculty.loadFacultyFromDB().searchposibilities(searchFaculty.getText()).getList());
        tableFaculty.setItems(makelistObservableFaculty(list));
    }

    private ObservableList<Faculty> loadDdataFaculty() {
        ArrayList<Faculty> faculties = LoadFaculty.loadFacultyFromDB().getList();
        if (faculties.isEmpty()) {
            return null;
        }
        ObservableList<Faculty> myList = FXCollections.observableArrayList(faculties);
        return myList;
    }

    private ObservableList<Faculty> loadDdataFacultyDepartment(String deptName) {
        ArrayList<Faculty> faculties = LoadFaculty.loadFacultyFromDBDepartment(deptName).getList();
        if (faculties.isEmpty()) {
            return null;
        }
        ObservableList<Faculty> myList = FXCollections.observableArrayList(faculties);
        return myList;
    }

    private ObservableList<Faculty> makelistObservableFaculty(CRUDFaculty l) {
        if (l == null) {
            return null;
        }
        ObservableList<Faculty> myList = FXCollections.observableArrayList(l.getList());
        return myList;
    }

    @FXML
    private TableView<Faculty> tableFaculty;

    @FXML
    private TableColumn<Faculty, String> nameFacultyCol;

    @FXML
    private TableColumn<Faculty, String> telFacultyCol;

    @FXML
    private TableColumn<Faculty, String> facultyTitleCol;

    @FXML
    JFXButton registerFaculty;
    @FXML
    JFXButton EditFaculty;
    @FXML
    JFXButton deleteFaculty;

    @FXML
    public void displayFaculty() {
        Faculty s = tableFaculty.getSelectionModel().getSelectedItem();
        if (s != null) {
            nameFaculty.setText(s.getName());
            if (s.getGender() == 'M') {
                genderFaculty.setText("Male");
            } else {
                genderFaculty.setText("Female");
            }
            phonenumberFaculty.setText(s.getPhoneNumber());
            adressFaculty.setText(s.getAddress());
            emailFaculty.setText(s.getEmailAddress());
            officeFaculty.setText(s.getOffice());
            officehoursFaculty.setText(Long.toString(s.getOfficehours()));
            salaryFaculty.setText(Double.toString(s.getSalary()));
            rankFaculty.setText(s.getRank());
            datehiredFaculty.setText(s.getDateHired().toString());
            datebirthFaculty.setText(s.getBirth().toString());
            titleTxfiel.setText(s.getTitle());
            depText.setText(s.getDepartement());
            facultySpecialty.setText(s.getSpecialty());
            disableAllFacProps();

        }
    }

    private void disableAllFacProps() {
        genderFaculty.setEditable(false);
        nameFaculty.setEditable(false);
        phonenumberFaculty.setEditable(false);
        adressFaculty.setEditable(false);
        emailFaculty.setEditable(false);
        officeFaculty.setEditable(false);
        officehoursFaculty.setEditable(false);
        salaryFaculty.setEditable(false);
        rankFaculty.setEditable(false);
        datehiredFaculty.setEditable(false);
        datebirthFaculty.setEditable(false);
        titleTxfiel.setEditable(false);
        depText.setEditable(false);
        facultySpecialty.setEditable(false);
    }

    @FXML
    public void toADDPageFaculty(ActionEvent action) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddFaculty.fxml"));
            Stage st = new Stage(StageStyle.DECORATED);
            st.setScene(new Scene((Pane) loader.load()));
            st.getIcons().add(new Image(Lanceur.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
            AddFacultyController controller = loader.<AddFacultyController>getController();
            controller.setTableFaculty(tableFaculty);
            st.setResizable(false);
            st.show();
        } catch (IOException ex) {
            Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void toModifyPageFaculty(ActionEvent action) {
        Alert a;
        if (tableFaculty.getSelectionModel().getSelectedItem() == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a row before clicking this button!", ButtonType.OK);
            a.showAndWait();
        } else {
            Faculty s = tableFaculty.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateFaculty.fxml"));
                Stage st = new Stage(StageStyle.DECORATED);
                st.setScene(new Scene((Pane) loader.load()));
                st.getIcons().add(new Image(Lanceur.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
                UpdateFacultyController controller = loader.<UpdateFacultyController>getController();
                controller.setMyFaculty(s);
                controller.setTableFaculty(tableFaculty);
                controller.initComp();
                st.setResizable(false);
                st.show();

            } catch (IOException ex) {
                Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    public void deleteFaculty() {
        Alert a;
        if (tableFaculty.getSelectionModel().getSelectedItem() == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please ble before clicking on DELETE", ButtonType.OK);
            a.setTitle("Error!");
            a.setHeaderText("No element selected!");
            a.show();
        } else {
            Faculty ad = tableFaculty.getSelectionModel().getSelectedItem();
            a = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wanna Delete that  faculty ?", ButtonType.OK, ButtonType.CANCEL);
            a.setTitle("Error!");
            a.showAndWait();
            if (a.getResult() == ButtonType.CANCEL) {
                a.close();
            } else {
                //table.getItems().remove(table.getSelectionModel().getSelectedItem());
                tableFaculty.getItems().remove(tableFaculty.getSelectionModel().getSelectedIndex());
                CRUDFaculty listoffaculties = new CRUDFaculty(LoadFaculty.loadFacultyFromDB().getList());
                listoffaculties.delete(ad);
                clearFacultyForm();
                //StoreStudent.storestudents(listofadresses);
            }
        }
    }

    private void clearFacultyForm() {
        nameFaculty.clear();
        adressFaculty.clear();
        officeFaculty.clear();
        officehoursFaculty.clear();
        salaryFaculty.clear();
        datehiredFaculty.clear();
        datebirthFaculty.clear();
        rankFaculty.clear();
        //deptextStudent.clear();
        emailFaculty.clear();
        phonenumberFaculty.clear();
        //statustextStudent.clear();
        genderFaculty.clear();
        titleTxfiel.clear();
        depText.clear();
        facultySpecialty.clear();
    }

    @FXML
    private JFXTextField searchProgram;

    @FXML
    private JFXButton addProgram;

    @FXML
    private JFXButton modifyProgram;

    @FXML
    private JFXButton deleteProgram;

    @FXML
    private TableView<Program> tableProgram;

    @FXML
    private TableColumn<Program, String> programTitle;

    @FXML
    private TableView<Course> programCourseDetails;

    @FXML
    private TableColumn<Course, String> courseCodeProgramDetails;

    @FXML
    private TableColumn<Course, String> courseTitleProgramDetails;
    @FXML
    private TableView<ProgramCourses> tableProgramCourse;

    @FXML
    private TableColumn<ProgramCourses, String> codeProgramCourse;

    @FXML
    private TableColumn<ProgramCourses, String> titleProgramCourse;

    @FXML
    private TableColumn<ProgramCourses, String> levelProgramCourse;

    @FXML
    private TableColumn<ProgramCourses, String> gradeProgramCourse;

    @FXML
    private TableColumn<ProgramCourses, Integer> creditsProgramCourse;

    @FXML
    private TableView<Student> programStudentsTable;

    @FXML
    private TableColumn<Student, String> programStudentsTableRef;

    @FXML
    private TableColumn<Student, String> programStudentsTableName;

    @FXML
    private TableColumn<Student, String> programStudentsTableLevel;

    @FXML
    private TableColumn<Student, String> programStudentsTableYear;

    @FXML
    private TableColumn<ProgramCourses, String> coreProgramCourse;

    @FXML
    private JFXTextField nameStudentSearch;

    @FXML
    private JFXComboBox<String> levelStudentSearch;

    @FXML
    private JFXTextField yearStudentSearch;

    @FXML
    private JFXButton searchStudentProgram;

    private final ObservableList<String> semchoices;
    private final ObservableList<String> levelchoices = FXCollections.observableArrayList("Preparatory Semester", "Freshman I",
            "Freshman II", "Sophomore I", "Sophomore II", "Junior I", "Junior II", "MBA I", "MBA II", "PhD");

    @FXML
    private JFXComboBox<Semester> semesterboxProgram;

    @FXML
    void searchStudentsProgram(ActionEvent event) {

    }

    private ObservableList<Program> loadDdataProgram() {
        ArrayList<Program> programs = LoadProgram.loadProgramFromDB().getList();
        if (programs.isEmpty()) {
            return null;
        }
        ObservableList<Program> myList = FXCollections.observableArrayList(programs);
        return myList;
    }

    @FXML
    void toAddProgram(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLProgram.fxml"));
            Stage st = new Stage(StageStyle.DECORATED);
            st.setScene(new Scene((Pane) loader.load()));
            st.getIcons().add(new Image(Lanceur.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
            FXMLProgramController controller = loader.<FXMLProgramController>getController();
            controller.setTableProgram(tableProgram);
            controller.setCurrentProgramList(currentProgramList);
            controller.setSem(semesterboxProgram.getSelectionModel().getSelectedItem());
            st.setResizable(false);
            st.show();
        } catch (IOException ex) {
            Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void toModifyProgram(ActionEvent event) {
        Alert a;
        Program p;
        if ((p = tableProgram.getSelectionModel().getSelectedItem()) == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a row before clicking on Edit!", ButtonType.OK);
            a.setHeaderText("Please select a program");
            a.showAndWait();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLProgram.fxml"));
                Stage st = new Stage(StageStyle.DECORATED);
                st.setScene(new Scene((Pane) loader.load()));
                st.getIcons().add(new Image(Lanceur.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
                FXMLProgramController controller = loader.<FXMLProgramController>getController();
                controller.setTableProgram(tableProgram);
                Semester sem = semesterboxProgram.getSelectionModel().getSelectedItem();
                controller.setCurrentProgramList(currentProgramList);
                controller.setSem(sem);
                controller.initWindow(p);
                st.setResizable(false);
                st.show();
            } catch (IOException ex) {
                Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void deleteProgram(ActionEvent event) {
        Alert a;
        Program p;
        if ((p = tableProgram.getSelectionModel().getSelectedItem()) == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a row before clicking on Delete!", ButtonType.OK);
            a.setHeaderText("Please select a program");
            a.showAndWait();
        } else {
        }
    }

    @FXML
    void researchPrograme(KeyEvent event) {
        tableProgram.setItems(CRUDProgram.searchposibilitie(
                currentProgramList, searchProgram.getText()));
    }

    @FXML
    void displayProgramDetails(MouseEvent event) {
        Alert a;
        Program p;
        if ((p = tableProgram.getSelectionModel().getSelectedItem()) == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a row before clicking on Edit!", ButtonType.OK);
            a.setHeaderText("Please select a program");
            a.showAndWait();
        } else {
            semesterboxProgram.getSelectionModel().select(Tools.currentSemester);
            Semester sem = semesterboxProgram.getSelectionModel().getSelectedItem();
            tableProgramCourse.setItems(FXCollections.observableArrayList(
                    ManageProgramCourses.loadProgramCourses(p, sem.getId())));
            tableProgramCourse.refresh();
            programStudentsTable.setItems(FXCollections.observableArrayList(ManageProgramCourses.programStudent(p.getId())));
            programStudentsTable.refresh();
        }
    }

    @FXML
    void displayProgramCourseSemDetails(ActionEvent event) {
        Alert a;
        Program p;
        if ((p = tableProgram.getSelectionModel().getSelectedItem()) == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a row before selecting a semester!", ButtonType.OK);
            a.setHeaderText("Please select a program");
            a.showAndWait();
        }
        Semester sem = semesterboxProgram.getSelectionModel().getSelectedItem();
        tableProgramCourse.setItems(FXCollections.observableArrayList(
                ManageProgramCourses.loadProgramCourses(p, sem.getId())));
        tableProgramCourse.refresh();
    }

    @FXML
    private JFXTextField searchCourse;

    @FXML
    private JFXButton registerCourse;

    @FXML
    private JFXButton editCourse;

    @FXML
    private JFXButton deleteCourse;

    @FXML
    private TableColumn<Course, String> courseCode;

    @FXML
    private TableColumn<Course, String> courseTitle;

    @FXML
    private TableColumn<Course, String> courseDescription;

    @FXML
    private TableView<Course> courseTable;

    @FXML
    private JFXTextField codeCourse;

    @FXML
    private JFXTextField hoursCourse;

    @FXML
    private JFXTextField titleCourse;

    @FXML
    private TextArea descriptionCourse;

    @FXML
    private TableView<Course> requisitesDetails;

    @FXML
    private TableColumn<Course, String> courseCodeTable;

    @FXML
    private TableColumn<Course, String> courseTitleTable;

    @FXML
    private TitledPane courseOpenedPane;

    @FXML
    private Button addCourseOpened;

    @FXML
    private Button editCourseOffered;

    @FXML
    private Button deleteCourseOpened;

    @FXML
    private TableView<CourseOffered> tableCouresesOpen;

    @FXML
    private TableColumn<CourseOffered, String> semesterCourseOpened;

    @FXML
    private TableColumn<CourseOffered, Integer> yearCourseOpened;

    @FXML
    private JFXComboBox<Semester> semesterCourseStudentBox;

    @FXML
    void editCourseOfferedAction(ActionEvent event) {
        Alert a;
        Course c;
        if ((c = courseTable.getSelectionModel().getSelectedItem()) == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a course!", ButtonType.OK);
            a.setHeaderText("Please select a course");
            a.showAndWait();
        } else {
            try {
                CourseOffered co;
                if ((co = tableCouresesOpen.getSelectionModel().getSelectedItem()) == null) {
                    a = new Alert(Alert.AlertType.ERROR, "Please select an opening for the course!", ButtonType.OK);
                    a.setHeaderText("Please select an opening!");
                    a.showAndWait();
                    return;
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseOpenedGUI.fxml"));
                Stage st = new Stage(StageStyle.DECORATED);
                st.setScene(new Scene((Pane) loader.load()));
                st.getIcons().add(new Image(Lanceur.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
                CourseOpenedGUIController controller = loader.getController();
                controller.setIdCourse(c.getId());
                controller.setTableCouresesOpen(tableCouresesOpen);
                controller.initWindow(co);
                st.setResizable(false);
                st.show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLProgramController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void addCourseOpenedAction(ActionEvent event) {
        Alert a;
        Course c;
        if ((c = courseTable.getSelectionModel().getSelectedItem()) == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a course!", ButtonType.OK);
            a.setHeaderText("Please select a course");
            a.showAndWait();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseOpenedGUI.fxml"));
                Stage st = new Stage(StageStyle.DECORATED);
                st.setScene(new Scene((Pane) loader.load()));
                st.getIcons().add(new Image(Lanceur.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
                CourseOpenedGUIController controller = loader.getController();
                controller.setIdCourse(c.getId());
                controller.setTableCouresesOpen(tableCouresesOpen);
                st.setResizable(false);
                st.show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLProgramController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    void deleteCourseOpenedAction(ActionEvent event) {
        Alert a;
        Course c;
        if ((c = courseTable.getSelectionModel().getSelectedItem()) == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a course!", ButtonType.OK);
            a.setHeaderText("Please select a course");
            a.showAndWait();
        } else {
            CourseOffered cc;
            if ((cc = tableCouresesOpen.getSelectionModel().getSelectedItem()) != null) {
                CourseOfferedMgnt.deleteCourseOpened(cc, "src/Resource/syllabus/" + cc.getSyllabus());
                a = new Alert(Alert.AlertType.ERROR, "Course opening deleted!!!", ButtonType.OK);
                a.setHeaderText("Deletion successful!");
                a.showAndWait();
                tableCouresesOpen.getItems().remove(cc);
                tableCouresesOpen.refresh();
            } else {
                a = new Alert(Alert.AlertType.ERROR, "Please select a course opened!", ButtonType.OK);
                a.setHeaderText("Please select a course");
                a.showAndWait();
            }
        }
    }

    private void displayCourseDetails(Course course) {
        codeCourse.setText(course.getCode());
        titleCourse.setText(course.getTitle());
        hoursCourse.setText(String.valueOf(course.getHours()));
        descriptionCourse.setText(course.getDescription());
        requisitesDetails.setItems(FXCollections.observableArrayList(course.getPrerequisite()));
        requisitesDetails.refresh();
        tableCouresesOpen.setItems(CourseOfferedMgnt.getOpenings(course.getId()));
        tableCouresesOpen.refresh();

    }

    private void displayStudentCourseDetails(Course course, Semester sem) {
        programStudentsTableStudent.setItems(Tools.getStudentCourses(course, sem));
        tableCouresesOpen.refresh();

    }

    @FXML
    void displayStudentCourseDetailsSem() {
        Alert a;
        Course c;
        if ((c = courseTable.getSelectionModel().getSelectedItem()) == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a course!", ButtonType.OK);
            a.setHeaderText("Please select a course");
            a.showAndWait();
        } else {
            Semester sem = semesterCourseStudentBox.getSelectionModel().getSelectedItem();
            programStudentsTableStudent.setItems(Tools.getStudentCourses(c, sem));
        }
    }

    @FXML
    void showCourseDetails() {
        Course c;
        if ((c = courseTable.getSelectionModel().getSelectedItem()) != null) {
            displayCourseDetails(c);
            Semester sem = semesterCourseStudentBox.getSelectionModel().getSelectedItem();
            displayStudentCourseDetails(c, sem);
        }

    }

    @FXML
    void deleteCourse(ActionEvent event) {
        Alert a;
        Course c;
        if ((c = courseTable.getSelectionModel().getSelectedItem()) == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a row before clicking on Edit!", ButtonType.OK);
            a.setHeaderText("Please select a course");
            a.showAndWait();
        } else {
            ManageCourse.deleteCourse(c);
            courseTable.getItems().remove(c);
            courseTable.refresh();
            displayCourseDetails(courseTable.getSelectionModel().getSelectedItem());

        }
    }

    @FXML
    void searchCourse(KeyEvent event) {
        courseTable.setItems(ManageCourse.serachCourse(currentCourseList, searchCourse.getText()));
        courseTable.refresh();
        Collections.sort(courseTable.getItems());
    }

    private ObservableList<Course> loadAllCourse() {
        ArrayList<Course> courses = ManageCourse.loadCourseFromDB().getList();
        if (courses.isEmpty()) {
            return null;
        }
        ObservableList<Course> myList = FXCollections.observableArrayList(courses);
        Collections.sort(myList, new CourseComparator());
        return myList;
    }

    @FXML
    void toADDPageCourse(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddModifyCourse.fxml"));
            Stage st = new Stage(StageStyle.DECORATED);
            st.setScene(new Scene((Pane) loader.load()));
            st.getIcons().add(new Image(Lanceur.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
            AddModifyCourseController controller = loader.getController();
            controller.setTableCourse(courseTable);
            controller.setCurrentCourseList(currentCourseList);
            st.setResizable(false);
            st.show();
        } catch (IOException ex) {
            Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void toModifyCourse(ActionEvent event) {
        Alert a;
        Course c;
        if ((c = courseTable.getSelectionModel().getSelectedItem()) == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a row before clicking on Edit!", ButtonType.OK);
            a.setHeaderText("Please select a course");
            a.showAndWait();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddModifyCourse.fxml"));
                Stage st = new Stage(StageStyle.DECORATED);
                st.setScene(new Scene((Pane) loader.load()));
                st.getIcons().add(new Image(Lanceur.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
                AddModifyCourseController controller = loader.getController();
                controller.setTableCourse(courseTable);
                controller.setCurrentCourseList(currentCourseList);
                controller.initWindow(c);
                st.setResizable(false);
                st.show();
            } catch (IOException ex) {
                Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private TableView<Student_Course> tcoursegrade;
    @FXML
    private TableColumn<Student_Course, String> tstudentname;
    @FXML
    private TableColumn<Student_Course, Double> tstudentgrade;
    @FXML
    private JFXComboBox<Program> programbox;
    @FXML
    private JFXComboBox<CourseOffered> coursebox;
    @FXML
    private JFXComboBox<Semester> semesterbox;
    @FXML
    private JFXButton goButton;
    @FXML
    private JFXButton saveButton;

    private ObservableList<Program> programlist = FXCollections.observableArrayList();
    private ObservableList<Semester> semesterlist = FXCollections.observableArrayList();
    private ObservableList<CourseOffered> courselist = FXCollections.observableArrayList();
    private ObservableList<Student_Course> studentgrades = FXCollections.observableArrayList();

    @FXML
    void editGradeAction(CellEditEvent e) {

        Student_Course sc = (Student_Course) e.getRowValue();
        sc.setScore((Double) e.getNewValue());

        tcoursegrade.refresh();
    }

    private boolean controls() {
        if ((semesterbox.getSelectionModel().getSelectedItem() == null)
                || (programbox.getSelectionModel().getSelectedItem() == null)
                || (coursebox.getSelectionModel().getSelectedItem() == null)) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please chose an item from all fields before clicking on Go", ButtonType.OK);
            a.setHeaderText("Protocol Error");
            a.show();
            return false;
        }
        return true;
    }

    private boolean controlsG() {
        if ((semesterTestBox.getSelectionModel().getSelectedItem() == null)
                || (programTestBox.getSelectionModel().getSelectedItem() == null)
                || (courseTestBox.getSelectionModel().getSelectedItem() == null)
                || (testTypeBox.getSelectionModel().getSelectedItem() == null)
                || (dateEnregTest.getValue() == null) || (dateEnregTest.getValue() == null)) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please chose an item from all fields before clicking on Go", ButtonType.OK);
            a.setHeaderText("One of the mandatory fields not filled!");
            a.show();
            return false;
        }
        return true;
    }

    private boolean controlsG2() {
        if ((semesterbox.getSelectionModel().getSelectedItem() == null)
                || (programbox.getSelectionModel().getSelectedItem() == null)
                || (coursebox.getSelectionModel().getSelectedItem() == null)) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please chose an item from all fields before clicking on Go", ButtonType.OK);
            a.setHeaderText("One of the mandatory fields not filled!");
            a.show();
            return false;
        }
        return true;
    }

    @FXML
    void changeSemesterlist() {
        ObservableList<Semester> thesemesters = Tools.getProgramSemesters(programbox.getSelectionModel().getSelectedItem());
        Iterator<Semester> iter = semesterlist.iterator();
        while (iter.hasNext()) {
            Semester sem = iter.next();
            if (semesterlist.contains(sem)) {
                iter.remove();
            }
        }
        semesterlist.addAll(thesemesters);
        semesterbox.setItems(semesterlist);
        coursebox.getItems().clear();
        tcoursegrade.getItems().clear();
        tcoursegrade.refresh();
    }

    @FXML
    void changeSemesterTestList() {
        ObservableList<Semester> thesemesters = Tools.getProgramSemesters(programTestBox.getSelectionModel().getSelectedItem());
        semesterTestBox.setItems(thesemesters);
    }

    @FXML
    void changeCourselist() {
        if (!(semesterbox.getSelectionModel().getSelectedItem() == null)) {
            ObservableList<CourseOffered> thecourses = Tools.getProgramCourses(programbox.getSelectionModel().getSelectedItem(),
                    semesterbox.getSelectionModel().getSelectedItem());
            Iterator<CourseOffered> iter = courselist.iterator();
            while (iter.hasNext()) {
                CourseOffered co = iter.next();
                if (courselist.contains(co)) {
                    iter.remove();
                }
            }
            courselist.addAll(thecourses);
            Collections.sort(courselist, new CourseOfferedComparator());
            coursebox.setItems(courselist);
        }
    }

    @FXML
    void changeCourseTestList() {
        if (!(semesterTestBox.getSelectionModel().getSelectedItem() == null)) {
            ObservableList<CourseOffered> thecourses = Tools.getProgramCourses(programTestBox.getSelectionModel().getSelectedItem(),
                    semesterTestBox.getSelectionModel().getSelectedItem());
            Collections.sort(thecourses, new CourseOfferedComparator());
            courseTestBox.setItems(thecourses);
        }
    }

    @FXML
    void saveButtonAction() {
        if (tcoursegrade.getSelectionModel().getSelectedItem() == null) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please select a row before clicking on Save", ButtonType.OK);
            a.setHeaderText("Row not selected");
            a.show();
        } else {
            if (tstudentgrade.getCellData(tcoursegrade.getSelectionModel().getSelectedIndex()) < 0.0
                    || tstudentgrade.getCellData(tcoursegrade.getSelectionModel().getSelectedIndex()) > 100.0) {
                Alert a = new Alert(Alert.AlertType.ERROR, "Please insert a grade between 0.0 and 100.0", ButtonType.OK);
                a.setHeaderText("Invalid Grade");
                a.show();
            } else {
                System.out.println(tcoursegrade.getSelectionModel().getSelectedItem().toString());
                for (Student_Course sc : tcoursegrade.getItems()) {
                    CRUDStudentCourse.updateStudentCourseSem(sc, semesterbox.getSelectionModel().getSelectedItem());
                }
                CRUDStudentCourse.updateStudentCourseSem(tcoursegrade.getSelectionModel().getSelectedItem(),
                        semesterbox.getSelectionModel().getSelectedItem());
            }
        }
    }

    @FXML
    void goButtonAction() {
        if (controls()) {
            ObservableList<Student_Course> coursegrades = Tools.getAllStudentGrades(
                    programbox.getSelectionModel().getSelectedItem(),
                    coursebox.getSelectionModel().getSelectedItem(),
                    semesterbox.getSelectionModel().getSelectedItem());
            Iterator<Student_Course> iter = studentgrades.iterator();
            while (iter.hasNext()) {
                Student_Course sc = iter.next();
                if (studentgrades.contains(sc)) {
                    iter.remove();
                }
            }
            studentgrades.addAll(coursegrades);
            tcoursegrade.refresh();
        }
    }

    @FXML
    private JFXButton studPersoSpace;

    @FXML
    void studentSpaceMgnt(ActionEvent event) {
        Alert a;
        if (table.getSelectionModel().getSelectedItem() == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a row before clicking this button!", ButtonType.OK);
            a.showAndWait();
        } else {
            Student s = table.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Student_CourseRegistration.fxml"));
                Stage st = new Stage(StageStyle.DECORATED);
                st.setScene(new Scene((Pane) loader.load()));
                st.getIcons().add(new Image(Lanceur.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
                Student_CourseRegistrationController controller = loader.getController();
                s.setProfilePhoto(Tools.loadImageProfiles(s.getId()));
                s.setProfileFile(Tools.loadImageName(s.getId()));
                controller.setStudent(s);
                controller.setTableStudent(table);
                controller.setSigningAuthorities(signingAuthorities.getText());
                controller.initComp();
                st.setResizable(false);
                st.show();

            } catch (IOException ex) {
                Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private JFXComboBox<Program> programTestBox;

    @FXML
    private JFXComboBox<CourseOffered> courseTestBox;

    @FXML
    private JFXComboBox<Semester> semesterTestBox;

    @FXML
    private JFXButton goButtonTest;

    @FXML
    private JFXComboBox<String> testTypeBox;

    @FXML
    private JFXDatePicker dateEnregTest;

    @FXML
    private TableView<TakeTest> tCourseOtherGrades;

    @FXML
    private TableColumn<TakeTest, String> tOGStudentCode;

    @FXML
    private TableColumn<TakeTest, String> tOGStudent;

    @FXML
    private TableColumn<TakeTest, String> tOGStudentRegistration;

    @FXML
    private TableColumn<TakeTest, Double> tOGStudentGrade;

    @FXML
    private TableColumn<TakeTest, String> letterGradeTest;

    @FXML
    private JFXButton saveTestGrade;

    @FXML
    private JFXDatePicker currentDate;

    @FXML
    private JFXButton goButtonNewEval;

    private boolean isCreationMode = false;

    private ObservableList<String> loadTestTypes() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Quiz");
        list.add("Assignment");
        list.add("Research Work");
        list.add("Test");
        list.add("Midterm");
        list.add("Lab");
        list.add("Final Exam");
        return list;
    }

    @FXML
    void editGradeActionG(CellEditEvent e) {

        TakeTest test = (TakeTest) e.getRowValue();
        test.setScore((Double) e.getNewValue());
        tCourseOtherGrades.getSelectionModel().getSelectedItem().setLetterGrade(Tools.getLettergrade(test.getScore()).charAt(0));
        tCourseOtherGrades.refresh();
    }

    @FXML
    void editCodeAction(CellEditEvent e) {
        TakeTest test = (TakeTest) e.getRowValue();
        test.setAnonymous((String) e.getNewValue());
        tCourseOtherGrades.refresh();
    }

    @FXML
    void goButtonActionG(ActionEvent event) {
        if (controlsG()) {
            Calendar dateEval = Calendar.getInstance();
            Date date = Date.from(dateEnregTest.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            dateEval.setTimeInMillis(date.getTime());
            ETestType type = ETestType.getValue(testTypeBox.getSelectionModel().getSelectedItem());
            ObservableList<TakeTest> coursegrades = Tools.getStudentTestGrades(
                    programTestBox.getSelectionModel().getSelectedItem(),
                    courseTestBox.getSelectionModel().getSelectedItem(),
                    semesterTestBox.getSelectionModel().getSelectedItem(),
                    dateEval,
                    type);
            tCourseOtherGrades.setItems(coursegrades);
            tCourseOtherGrades.refresh();

        }
    }

    @FXML
    void goButtonActionNewEval(ActionEvent event) {
        if (controlsG()) {
            tCourseOtherGrades.getItems().clear();
            ObservableList<Student_Course> coursegrades = Tools.getAllStudentGrades(
                    programTestBox.getSelectionModel().getSelectedItem(),
                    courseTestBox.getSelectionModel().getSelectedItem(),
                    semesterTestBox.getSelectionModel().getSelectedItem());
            coursegrades.stream().map((sc) -> {
                Student s = LoadStudent.loadStudentById(sc.getIdStudent());
                TakeTest test = new TakeTest();
                test.setAnonymous("00000");
                test.setScore(0.0);
                test.setLetterGrade('F');
                test.setStudentname(s.getName());
                test.setRegistration(s.getStuID());
                test.setIdStudent(sc.getIdStudent());
                return test;
            }).forEachOrdered((test) -> {
                tCourseOtherGrades.getItems().add(test);
            });
            tCourseOtherGrades.refresh();
            isCreationMode = true;
        }
    }

    @FXML
    void saveButtonActionTests(ActionEvent event) {
        if (controlsG()) {
            Calendar dateEval = Calendar.getInstance();
            Date date = Date.from(dateEnregTest.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            dateEval.setTimeInMillis(date.getTime());
            if (isCreationMode) {
                ETestType type = ETestType.getValue(testTypeBox.getSelectionModel().getSelectedItem());
                int course_id = courseTestBox.getSelectionModel().getSelectedItem().getId();
                CRUDTest.insertStudetntTestGrades(dateEval, type, course_id, tCourseOtherGrades.getItems());
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Information succesfully saved!", ButtonType.OK);
                a.show();
                isCreationMode = false;
            } else {
                CRUDTest.updateTestGades(tCourseOtherGrades.getItems(), dateEval);
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Information succesfully saved!", ButtonType.OK);
                a.show();
            }
        }
    }

    @FXML
    void printResultAction(ActionEvent event) {
        if (controlsG()) {
            Calendar dateEval = Calendar.getInstance(), current = Calendar.getInstance();
            Date date = Date.from(dateEnregTest.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date datec = Date.from(currentDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            current.setTimeInMillis(datec.getTime());
            dateEval.setTimeInMillis(date.getTime());

            ETestType type = ETestType.getValue(testTypeBox.getSelectionModel().getSelectedItem());
            CourseOffered cc = courseTestBox.getSelectionModel().getSelectedItem();
            try {
                Connection connection = DBConnection.getConnection();
                /* JasperReport is the object
                that holds our compiled jrxml file */
                JasperReport jasperReport;
                Semester semester = semesterTestBox.getSelectionModel().getSelectedItem();

                /* JasperPrint is the object contains
                report after result filling process */
                JasperPrint jasperPrint;
                // jasperParameter is a Hashmap contains the parameters
                // passed from application to the jrxml layout
                HashMap jasperParameter = new HashMap();
                jasperParameter.put("id_course_offered", cc.getId());
                jasperParameter.put("dateOfDay", datec);
                jasperParameter.put("id_semester", semester.getId());
                jasperParameter.put("course_year", semester.getSYear());
                jasperParameter.put("course_sem", semester.getName());
                jasperParameter.put("id", cc.getId());
                jasperParameter.put("test_date", date);
                BufferedImage image = ImageIO.read(getClass().getResource("/Resource/images/logopkf.PNG"));
                jasperParameter.put("imagesDir", image);
                jasperParameter.put("accademicYear", Tools.getAcademicYear(semester));
                int i = cc.getLecturer().lastIndexOf("-");
                String lect = "";
                if (cc.getLecturer().substring(i + 2).equals("Mr No Assistant Assigned")) {
                    lect = cc.getLecturer().substring(0, i);
                } else {
                    lect = cc.getLecturer();
                    lect = lect.replace('-', '/');
                }
                jasperParameter.put("lecturer", lect);

                InputStream stream = getClass().getResource("/Resource/reports/result_report_form.jasper").openStream();

                jasperReport = (JasperReport) JRLoader.loadObject(stream);
                // jrxml compiling process

                // filling report with data from data source
                jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameter, connection);

                // exporting process
                // 1- export to PDF
                JasperViewer.viewReport(jasperPrint, false);
//                DirectoryChooser chooser = new DirectoryChooser();
//
//                chooser.setTitle("Select directory where to save...");
//                File selectedFile = chooser.showDialog(((Node) event.getSource()).getScene().getWindow());
//
//                if (selectedFile != null) {
//                    String pdfPath = selectedFile.getPath() + " eval " + dateEval.get(Calendar.DAY_OF_MONTH) + "-" + dateEval.get(Calendar.MONTH) + ""
//                        + dateEval.get(Calendar.YEAR)
//                        + semester.getName() + " " + semester.getSYear() + ".pdf";
//                    JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath);
//                    Tools.openPDFFile(pdfPath);
//                }

            } catch (JRException | IOException ex) {
                Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void printResultActionFinalResult(ActionEvent event) {
        if (controlsG2()) {
            Date current = new Date();

            CourseOffered cc = coursebox.getSelectionModel().getSelectedItem();
            try {
                Connection connection = DBConnection.getConnection();
                /* JasperReport is the object
                that holds our compiled jrxml file */
                JasperReport jasperReport;
                Semester semester = semesterbox.getSelectionModel().getSelectedItem();

                /* JasperPrint is the object contains
                report after result filling process */
                JasperPrint jasperPrint;
                // jasperParameter is a Hashmap contains the parameters
                // passed from application to the jrxml layout
                HashMap jasperParameter = new HashMap();
                jasperParameter.put("id_course_offered", cc.getId());
                jasperParameter.put("dateOfDay", current);
                jasperParameter.put("id_semester", semester.getId());
                jasperParameter.put("course_year", semester.getSYear());
                jasperParameter.put("course_sem", semester.getName());
                jasperParameter.put("id", cc.getId());
                jasperParameter.put("code", cc.getC().getCode());
                jasperParameter.put("title", cc.getC().getTitle());
                BufferedImage image = ImageIO.read(getClass().getResource("/Resource/images/logopkf.PNG"));
                jasperParameter.put("imagesDir", image);
                jasperParameter.put("accademicYear", Tools.getAcademicYear(semester));
                int i = cc.getLecturer().lastIndexOf("-");
                String lect = "";
                if (cc.getLecturer().substring(i + 2).equals("Mr No Assistant Assigned")) {
                    lect = cc.getLecturer().substring(0, i);
                } else {
                    lect = cc.getLecturer();
                    lect = lect.replace('-', '/');
                }
                jasperParameter.put("lecturer", lect);

                InputStream stream = getClass().getResource("/Resource/reports/result_report_form_final.jasper").openStream();

                jasperReport = (JasperReport) JRLoader.loadObject(stream);
                // jrxml compiling process

                // filling report with data from data source
                jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameter, connection);

                // exporting process
                // 1- export to PDF
                JasperViewer.viewReport(jasperPrint, false);
//                DirectoryChooser chooser = new DirectoryChooser();
//
//                chooser.setTitle("Select directory where to save...");
//                File selectedFile = chooser.showDialog(((Node) event.getSource()).getScene().getWindow());
//
//                if (selectedFile != null) {
//                    String pdfPath = selectedFile.getPath() + " eval " + dateEval.get(Calendar.DAY_OF_MONTH) + "-" + dateEval.get(Calendar.MONTH) + ""
//                        + dateEval.get(Calendar.YEAR)
//                        + semester.getName() + " " + semester.getSYear() + ".pdf";
//                    JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath);
//                    Tools.openPDFFile(pdfPath);
//                }

            } catch (JRException | IOException ex) {
                Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private TableView<Schedule> tableSche;

    @FXML
    private TableColumn<Schedule, String> tableScheCCol;

    @FXML
    private TableColumn<Schedule, String> tableScheDayCol;

    @FXML
    private TableColumn<Schedule, String> tableScheDateCol;

    @FXML
    private TableColumn<Schedule, String> tableScheSTimeCol;

    @FXML
    private TableColumn<Schedule, String> tableScheETimeCol;

    @FXML
    private TableColumn<Schedule, Room> tableSchePlaceCol;

    @FXML
    private TableColumn<Schedule, String> tableScheLecCol;

    @FXML
    private JFXComboBox<Program> programScheBox;

    @FXML
    private JFXComboBox<CourseOffered> courseScheBox;

    @FXML
    private JFXComboBox<Semester> semesterScheBox;

    @FXML
    private JFXDatePicker dateScheTest;

    @FXML
    private JFXButton goButtonSche;

    @FXML
    private Button addSchedule;

    @FXML
    private Button editSchedule;

    @FXML
    private Button deleteSchedule;

    private ObservableList<Taught_By> schedule = FXCollections.observableArrayList();

    public TableView<Schedule> getTableSche() {
        return tableSche;
    }

    public void setTableSche(TableView<Schedule> tableSche) {
        this.tableSche = tableSche;
    }

    @FXML
    void changeSemesterSchedulelist() {
        ObservableList<Semester> thesemesters = Tools.getProgramSemesters(programScheBox.getSelectionModel().getSelectedItem());
        Iterator<Semester> iter = semesterlist.iterator();
        while (iter.hasNext()) {
            Semester sem = iter.next();
            if (semesterlist.contains(sem)) {
                iter.remove();
            }
        }
        semesterlist.addAll(thesemesters);
        semesterScheBox.setItems(semesterlist);
        tableSche.getItems().clear();
        tableSche.refresh();
        courseScheBox.getItems().clear();
    }

    @FXML
    void changeCourseSchedulelist() {
        if (!(semesterScheBox.getSelectionModel().getSelectedItem() == null)) {
            ObservableList<CourseOffered> thecourses = Tools.getProgramCourses(programScheBox.getSelectionModel().getSelectedItem(),
                    semesterScheBox.getSelectionModel().getSelectedItem());
            Iterator<CourseOffered> iter = courselist.iterator();
            while (iter.hasNext()) {
                CourseOffered co = iter.next();
                if (courselist.contains(co)) {
                    iter.remove();
                }
            }
            courselist.addAll(thecourses);
            Collections.sort(courselist, new CourseOfferedComparator());
            courseScheBox.setItems(courselist);
            tableSche.getItems().clear();
            tableSche.refresh();

        }
    }

    @FXML
    void searchSchedules(ActionEvent event) {
        if (programScheBox.getSelectionModel().getSelectedItem() == null || semesterScheBox.getSelectionModel().getSelectedItem() == null) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Select a program and a semester before clicking on this button", ButtonType.OK);
            a.setHeaderText("Protocol Error");
            a.showAndWait();
        } else if (courseScheBox.getSelectionModel().getSelectedItem() == null) {
            tableSche.setItems(Tools.getSchedules(programScheBox.getSelectionModel().getSelectedItem(),
                    semesterScheBox.getSelectionModel().getSelectedItem(), null));
            if (dateScheTest.getValue() != null) {
                tableSche.setItems(Tools.getSchedulesWithDate(programScheBox.getSelectionModel().getSelectedItem(), dateScheTest.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        semesterScheBox.getSelectionModel().getSelectedItem(), null));
            }
        } else if (!(programScheBox.getSelectionModel().getSelectedItem() == null) && !(semesterScheBox.getSelectionModel().getSelectedItem() == null)
                && !(courseScheBox.getSelectionModel().getSelectedItem() == null) && dateScheTest.getValue() == null) {
            tableSche.setItems(Tools.getSchedules(programScheBox.getSelectionModel().getSelectedItem(),
                    semesterScheBox.getSelectionModel().getSelectedItem(), courseScheBox.getSelectionModel().getSelectedItem()));
        } else {
            tableSche.setItems(Tools.getSchedulesWithDate(programScheBox.getSelectionModel().getSelectedItem(), dateScheTest.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    semesterScheBox.getSelectionModel().getSelectedItem(), courseScheBox.getSelectionModel().getSelectedItem()));
        }
    }

    @FXML
    void addSchedules(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCourseSchedule.fxml"));
            Stage st = new Stage(StageStyle.DECORATED);
            st.setScene(new Scene((Pane) loader.load()));
            st.getIcons().add(new Image(Lanceur.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
            AddCourseScheduleController controller = loader.<AddCourseScheduleController>getController();
            controller.setCourseOff(courseScheBox.getSelectionModel().getSelectedItem());
            controller.setProgram(programScheBox.getSelectionModel().getSelectedItem());
            controller.setSemester(semesterScheBox.getSelectionModel().getSelectedItem());
            //controller.setCount(tableSche.getItems().size());
            controller.setTableSche(tableSche);
            st.setResizable(false);
            st.show();

        } catch (IOException ex) {
            Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void modifySchedules(ActionEvent event) {
        Alert a;
        if (tableSche.getSelectionModel().getSelectedItem() == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a row before clicking this button!", ButtonType.OK);
            a.showAndWait();
        } else {
            Schedule sche = tableSche.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyCourseSchedule.fxml"));
                Stage st = new Stage(StageStyle.DECORATED);
                st.setScene(new Scene((Pane) loader.load()));
                st.getIcons().add(new Image(Lanceur.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
                ModifyCourseScheduleController controller = loader.<ModifyCourseScheduleController>getController();
                controller.setSchedule(sche);
                controller.setProgram(programScheBox.getSelectionModel().getSelectedItem());
                controller.setCourseOff(courseScheBox.getSelectionModel().getSelectedItem());
                controller.setSemester(semesterScheBox.getSelectionModel().getSelectedItem());
                controller.setTableSche(tableSche);
                controller.initComp();
                st.setResizable(false);
                st.show();

            } catch (IOException ex) {
                Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void duplicateSchedules(ActionEvent event) {
        Alert a;
        if (tableSche.getSelectionModel().getSelectedItem() == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a row before clicking this button!", ButtonType.OK);
            a.showAndWait();
        } else {
            Schedule sche = tableSche.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyCourseSchedule.fxml"));
                Stage st = new Stage(StageStyle.DECORATED);
                st.setScene(new Scene((Pane) loader.load()));
                st.getIcons().add(new Image(Lanceur.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
                ModifyCourseScheduleController controller = loader.<ModifyCourseScheduleController>getController();
                controller.setSchedule(sche);
                controller.setIsDuplicate(true);
                controller.setProgram(programScheBox.getSelectionModel().getSelectedItem());
                controller.setCourseOff(courseScheBox.getSelectionModel().getSelectedItem());
                controller.setSemester(semesterScheBox.getSelectionModel().getSelectedItem());
                controller.setTableSche(tableSche);
                controller.initComp();
                st.setResizable(false);
                st.show();

            } catch (IOException ex) {
                Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void modifyStudentAttendance(ActionEvent event) {
        Alert a;
        if (tableSche.getSelectionModel().getSelectedItem() == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a row before clicking this button!", ButtonType.OK);
            a.showAndWait();
        } else {
            Schedule sche = tableSche.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EditCourseAttendance.fxml"));
                Stage st = new Stage(StageStyle.DECORATED);
                st.setScene(new Scene((Pane) loader.load()));
                st.getIcons().add(new Image(Lanceur.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
                EditCourseAttendanceController controller = loader.<EditCourseAttendanceController>getController();
                controller.setSchedule(sche);
                controller.setCourseOff(courseScheBox.getSelectionModel().getSelectedItem());
                controller.setSemester(semesterScheBox.getSelectionModel().getSelectedItem());
                controller.initComp();
                st.setResizable(false);
                st.show();

            } catch (IOException ex) {
                Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void deleteSchedules(ActionEvent event) {
        Alert a;
        if (tableSche.getSelectionModel().getSelectedItem() == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a row before clicking this button!", ButtonType.OK);
            a.showAndWait();
        } else {
            Schedule s = tableSche.getSelectionModel().getSelectedItem();
            tableSche.getItems().remove(s);
            CRUDSchedule.deleteSchedule(s.getId(), s.getId_room());
            a = new Alert(Alert.AlertType.INFORMATION, "Deletion successful!", ButtonType.OK);
            a.show();
            tableSche.refresh();
        }
    }

    @FXML
    private TableView<Faculty> departmentFaculties;

    @FXML
    private TableColumn<Faculty, String> departmentFacultiesTitle;

    @FXML
    private TableColumn<Faculty, String> departmentFacultiesName;

    @FXML
    private TableColumn<Faculty, String> departmentFacultiesSpecialty;

    @FXML
    private TableColumn<Faculty, String> departmentFacultiesPhone;

    @FXML
    private TableView<Student_Course> programStudentsTableStudent;

    @FXML
    private TableColumn<Student_Course, String> programStudentsTableRefStudent;

    @FXML
    private TableColumn<Student_Course, String> programStudentsTableNameStudent;

    @FXML
    private TableColumn<Student_Course, String> programStudentsTableLevelStudent;

    @FXML
    private JFXTextField nameStudentSearchCourse;

    @FXML
    private JFXComboBox<Program> semProgramBox;
    @FXML
    private JFXComboBox<SchoolLevel> semProgLevel;
    @FXML
    private JFXButton goSemesterStudent;
    @FXML
    private JFXButton printSemStudent;
    @FXML
    private TableView<Student> semStudTable;
    @FXML
    private TableColumn<Student, String> sRefNumCol;
    @FXML
    private TableColumn<Student, String> sNameCol;

    @FXML
    private TableColumn<Student, String> levelCol;

    @FXML
    private TableColumn<Student, String> entYearCol;
    @FXML
    private JFXComboBox<Program> semProgramBox1;
    @FXML
    private JFXButton printProgramCourseOff;

    @FXML
    private TableView<ProgramCourses> tableProgramCourse1;
    @FXML
    private TableColumn<ProgramCourses, String> codeProgramCourse1;

    @FXML
    private TableColumn<ProgramCourses, String> titleProgramCourse1;

    @FXML
    private TableColumn<ProgramCourses, String> levelProgramCourse1;

    @FXML
    private TableColumn<ProgramCourses, String> gradeProgramCourse1;
    @FXML
    private TableColumn<ProgramCourses, String> coreProgramCourse1;

    @FXML
    private TableColumn<ProgramCourses, Integer> creditsProgramCourse1;

    @FXML
    void changeSemesterProgramlist(ActionEvent event) {
        ObservableList<SchoolLevel> levels = FXCollections.observableArrayList();
        Program p = semProgramBox.getSelectionModel().getSelectedItem();
        if (p != null && !p.getTitle().equals("All")) {
            switch (p.getTitle().charAt(0)) {
                case 'B':
                    levels.add(new SchoolLevel(1, "Preparatory Semester"));
                    levels.add(new SchoolLevel(2, "Freshman I"));
                    levels.add(new SchoolLevel(3, "Freshman II"));
                    levels.add(new SchoolLevel(4, "Sophomore I"));
                    levels.add(new SchoolLevel(5, "Sophomore II"));
                    levels.add(new SchoolLevel(6, "Junior I"));
                    levels.add(new SchoolLevel(7, "Junior II"));
                    break;
                case 'M':
                    if (p.getTitle().equals("Master")) {
                        levels.add(new SchoolLevel(8, "Master I"));
                        levels.add(new SchoolLevel(9, "Master II"));
                    } else {
                        levels.add(new SchoolLevel(10, "MBA I"));
                        levels.add(new SchoolLevel(11, "MBA II"));
                    }
                    break;
                case 'P':
                    levels.add(new SchoolLevel(11, "PHD"));
                    break;
            }
            SchoolLevel sl = new SchoolLevel(0, "All");
            levels.add(sl);
            semProgLevel.setItems(levels);
            semProgLevel.getSelectionModel().select(sl);

        }
    }

    @FXML
    void goButtonActionStudent(ActionEvent event) {
        Alert a;
        Semester s;
        if ((s = tableSemester.getSelectionModel().getSelectedItem()) == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a semester before proceed!", ButtonType.OK);
            a.setHeaderText("Please select a semester");
            a.showAndWait();
        } else {
            Program prog = semProgramBox.getSelectionModel().getSelectedItem();
            SchoolLevel lv = semProgLevel.getSelectionModel().getSelectedItem();

            if (prog == null || prog.getTitle().equals("All")) {
                semStudTable.setItems(Tools.getStudentsSemester(s.getId()));
                semStudTable.refresh();
            } else if (lv == null || lv.getName().equals("All")) {
                semStudTable.setItems(Tools.getStudentsSemester(s.getId(), prog.getId()));
                semStudTable.refresh();
            } else {
                semStudTable.setItems(Tools.getStudentsSemester(s.getId(), prog.getId(), lv.getId()));
                semStudTable.refresh();
            }
        }
    }

    @FXML
    void showSemDetails(MouseEvent event) {
        Semester sem = tableSemester.getSelectionModel().getSelectedItem();
        if (sem != null) {
            semStudTable.setItems(Tools.getStudentsSemester(sem.getId()));
            semStudTable.refresh();
//            tableProgramCourse1.setItems(Tools.getCoursesOfSem(sem.getName(), sem.getSYear(), 0));
//            tableProgramCourse1.refresh();
        }
    }

    @FXML
    void printStudentSemester(ActionEvent event) {
        Alert a;
        Semester s;
        if ((s = tableSemester.getSelectionModel().getSelectedItem()) == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a semester before proceed!", ButtonType.OK);
            a.setHeaderText("Please select a semester");
            a.showAndWait();
        } else {
            Program prog = semProgramBox.getSelectionModel().getSelectedItem();
            SchoolLevel lv = semProgLevel.getSelectionModel().getSelectedItem();

            if (prog == null || prog.getTitle().equals("All")) {
                try {
                    Connection connection = DBConnection.getConnection();
                    /* JasperReport is the object
                    that holds our compiled jrxml file */
                    JasperReport jasperReport;

                    /* JasperPrint is the object contains
                    report after result filling process */
                    JasperPrint jasperPrint;
                    // jasperParameter is a Hashmap contains the parameters
                    // passed from application to the jrxml layout
                    HashMap jasperParameter = new HashMap();
                    jasperParameter.put("sem_year", s.getSYear());
                    jasperParameter.put("sem_name", s.getName());
                    jasperParameter.put("semId", s.getId());
                    BufferedImage image = ImageIO.read(getClass().getResource("/Resource/images/logopkf.PNG"));
                    jasperParameter.put("imagesDir", image);
                    InputStream stream = getClass().getResource("/Resource/reports/ProgramStudentsAll.jasper").openStream();
                    jasperReport = (JasperReport) JRLoader.loadObject(stream);
                    // jrxml compiling process
                    // filling report with data from data source
                    jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameter, connection);

                    // exporting process
                    // 1- export to PDF
                    JasperViewer.viewReport(jasperPrint, false);

                } catch (IOException | JRException ex) {
                    Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (lv == null || lv.getName().equals("All")) {
                try {
                    Connection connection = DBConnection.getConnection();
                    /* JasperReport is the object
                    that holds our compiled jrxml file */
                    JasperReport jasperReport;

                    /* JasperPrint is the object contains
                    report after result filling process */
                    JasperPrint jasperPrint;
                    // jasperParameter is a Hashmap contains the parameters
                    // passed from application to the jrxml layout
                    HashMap jasperParameter = new HashMap();
                    jasperParameter.put("sem_year", s.getSYear());
                    jasperParameter.put("sem_name", s.getName());
                    jasperParameter.put("id_program", prog.getId());
                    jasperParameter.put("programName", prog.getTitle());
                    jasperParameter.put("semId", s.getId());
                    BufferedImage image = ImageIO.read(getClass().getResource("/Resource/images/logopkf.PNG"));
                    jasperParameter.put("imagesDir", image);
                    InputStream stream = getClass().getResource("/Resource/reports/ProgramStudents.jasper").openStream();
                    jasperReport = (JasperReport) JRLoader.loadObject(stream);
                    // jrxml compiling process
                    // filling report with data from data source
                    jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameter, connection);

                    // exporting process
                    // 1- export to PDF
                    JasperViewer.viewReport(jasperPrint, false);

                } catch (IOException | JRException ex) {
                    Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    Connection connection = DBConnection.getConnection();
                    /* JasperReport is the object
                    that holds our compiled jrxml file */
                    JasperReport jasperReport;

                    /* JasperPrint is the object contains
                    report after result filling process */
                    JasperPrint jasperPrint;
                    // jasperParameter is a Hashmap contains the parameters
                    // passed from application to the jrxml layout
                    HashMap jasperParameter = new HashMap();
                    jasperParameter.put("sem_year", s.getSYear());
                    jasperParameter.put("sem_name", s.getName());
                    jasperParameter.put("id_program", prog.getId());
                    jasperParameter.put("programName", prog.getTitle());
                    jasperParameter.put("semId", s.getId());
                    jasperParameter.put("levelId", lv.getId());
                    BufferedImage image = ImageIO.read(getClass().getResource("/Resource/images/logopkf.PNG"));
                    jasperParameter.put("imagesDir", image);
                    InputStream stream = getClass().getResource("/Resource/reports/ProgramStudentsLevel.jasper").openStream();
                    jasperReport = (JasperReport) JRLoader.loadObject(stream);
                    // jrxml compiling process
                    // filling report with data from data source
                    jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameter, connection);

                    // exporting process
                    // 1- export to PDF
                    JasperViewer.viewReport(jasperPrint, false);

                } catch (IOException | JRException ex) {
                    Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @FXML
    void chargeCoursesProgramlist(ActionEvent event) {

        Semester s;
        if ((s = tableSemester.getSelectionModel().getSelectedItem()) == null) {
            Alert a;
            a = new Alert(Alert.AlertType.ERROR, "Please select a semester before proceed!", ButtonType.OK);
            a.setHeaderText("Please select a semester");
            a.showAndWait();
        } else {
            Program prog = semProgramBox1.getSelectionModel().getSelectedItem();
            System.out.println(prog);
            if (prog == null || prog.getTitle().equals("All")) {
                tableProgramCourse1.setItems(Tools.getCoursesOfSem(s.getName(), s.getSYear(), 0));
                tableProgramCourse1.refresh();
            } else {
                tableProgramCourse1.setItems(FXCollections.observableArrayList(
                        ManageProgramCourses.loadProgramCourses(prog, s.getId())));
                tableProgramCourse1.refresh();
            }
        }
    }

    public String decide(String text) {
        if ("".equals(text)) {
            return "";
        }
        return text;
    }

    @FXML
    void printProgramCourseOffGo(ActionEvent event) {
        Semester s;
        if ((s = tableSemester.getSelectionModel().getSelectedItem()) == null) {
            Alert a;
            a = new Alert(Alert.AlertType.ERROR, "Please select a semester before proceed!", ButtonType.OK);
            a.setHeaderText("Please select a semester");
            a.showAndWait();
        } else {
            Program prog = semProgramBox1.getSelectionModel().getSelectedItem();
            if (prog == null) {
                Alert a;
                a = new Alert(Alert.AlertType.ERROR, "Please select a program before proceed!", ButtonType.OK);
                a.setHeaderText("Please select a program");
                a.showAndWait();
            } else {
                try {
                    Connection connection = DBConnection.getConnection();
                    /* JasperReport is the object
                    that holds our compiled jrxml file */
                    JasperReport jasperReport;

                    /* JasperPrint is the object contains
                    report after result filling process */
                    JasperPrint jasperPrint;
                    // jasperParameter is a Hashmap contains the parameters
                    // passed from application to the jrxml layout
                    HashMap jasperParameter = new HashMap();
                    jasperParameter.put("sem_year", s.getSYear());
                    jasperParameter.put("sem_name", s.getName());
                    jasperParameter.put("id_semester", s.getId());
                    jasperParameter.put("id_program", prog.getId());
                    jasperParameter.put("programName", prog.getTitle());
                    BufferedImage image = ImageIO.read(getClass().getResource("/Resource/images/logopkf.PNG"));
                    jasperParameter.put("imagesDir", image);
                    InputStream stream = getClass().getResource("/Resource/reports/ProgramCourse.jasper").openStream();
                    jasperReport = (JasperReport) JRLoader.loadObject(stream);
                    // jrxml compiling process
                    // filling report with data from data source
                    jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParameter, connection);

                    // exporting process
                    // 1- export to PDF
                    JasperViewer.viewReport(jasperPrint, false);

                } catch (IOException | JRException ex) {
                    Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Control for StudentManagement
        if (nameStudentCol != null) {
            nameStudentCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        }
        if (telStudentCol != null) {
            telStudentCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        }
        if (statusStudentCol != null) {
            statusStudentCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        }
        if (table != null) {
            table.setItems(loadDdata());
        }

        // Control for Faculty Management
        if (nameFacultyCol != null) {
            nameFacultyCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        }
        if (telFacultyCol != null) {
            telFacultyCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        }
        facultyTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        semProgramBox.setItems(loadPrograms());

        semProgramBox1.setItems(FXCollections.observableArrayList(LoadProgram.loadProgramFromDB().getList()));

        tableFaculty.setItems(loadDdataFaculty());
        departmentFaculties.setPlaceholder(new Label("No content available"));
        departmentFacultiesTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        departmentFacultiesName.setCellValueFactory(new PropertyValueFactory<>("name"));
        departmentFacultiesSpecialty.setCellValueFactory(new PropertyValueFactory<>("specialty"));
        departmentFacultiesPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        semStudTable.setPlaceholder(new Label("No content available"));
        sRefNumCol.setCellValueFactory(new PropertyValueFactory<>("stuID"));
        sNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        levelCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        entYearCol.setCellValueFactory(new PropertyValueFactory<>("entrance_year"));

        programStudentsTableRefStudent.setCellValueFactory(new PropertyValueFactory<>("studRef"));
        programStudentsTableNameStudent.setCellValueFactory(new PropertyValueFactory<>("studentname"));
        programStudentsTableLevelStudent.setCellValueFactory(new PropertyValueFactory<>("semester"));

        //Control for Staff Management
        if (nameStaffCol != null) {
            nameStaffCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        }
        if (telStaffCol != null) {
            telStaffCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        }
        staffTitleCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        staffPositionCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        if (tableStaff != null) {
            currentStaffList = loadDdataStaff();
            tableStaff.setItems(FXCollections.observableArrayList(new ArrayList<>(currentStaffList)));
        }
        //TableColumn initializers for Programs and Courses
        programTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        courseCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        courseTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        courseDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        courseCodeTable.setCellValueFactory(new PropertyValueFactory<>("code"));
        courseTitleTable.setCellValueFactory(new PropertyValueFactory<>("title"));

        currentProgramList = loadDdataProgram();
        tableProgram.setItems(FXCollections.observableArrayList(new ArrayList<>(currentProgramList)));
        courseTable.setPlaceholder(new Label("No content available"));
        table.setPlaceholder(new Label("No content available"));
        tableStaff.setPlaceholder(new Label("No content available"));
        tableFaculty.setPlaceholder(new Label("No content available"));
        requisitesDetails.setPlaceholder(new Label("No prerequisite for this course"));
        currentCourseList = loadAllCourse();

        tableProgram.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        courseTable.setItems(FXCollections.observableArrayList(new ArrayList<>(currentCourseList)));

        programStudentsTableStudent.setPlaceholder(new Label("No prerequisite for this course"));

        tableSemester.setPlaceholder(new Label("No semester available"));

        departmentTable.setPlaceholder(new Label("No department available"));

        codeProgramCourse.setCellValueFactory(new PropertyValueFactory<>("code_course"));
        titleProgramCourse.setCellValueFactory(new PropertyValueFactory<>("course_title"));
        levelProgramCourse.setCellValueFactory(new PropertyValueFactory<>("level"));
        gradeProgramCourse.setCellValueFactory(new PropertyValueFactory<>("passing_grade"));
        creditsProgramCourse.setCellValueFactory(new PropertyValueFactory<>("nb_credits"));
        coreProgramCourse.setCellValueFactory(new PropertyValueFactory<>("core"));
        tableProgramCourse.setPlaceholder(new Label("No content available"));
        tableCouresesOpen.setPlaceholder(new Label("No content available"));
        departmentStudTable.setPlaceholder(new Label("No content available"));

        tableProgramCourse1.setPlaceholder(new Label("No content available"));
        codeProgramCourse1.setCellValueFactory(new PropertyValueFactory<>("code_course"));
        titleProgramCourse1.setCellValueFactory(new PropertyValueFactory<>("course_title"));
        levelProgramCourse1.setCellValueFactory(new PropertyValueFactory<>("level"));
        gradeProgramCourse1.setCellValueFactory(new PropertyValueFactory<>("passing_grade"));
        creditsProgramCourse1.setCellValueFactory(new PropertyValueFactory<>("nb_credits"));
        coreProgramCourse1.setCellValueFactory(new PropertyValueFactory<>("core"));

        codeCourse.setEditable(false);
        hoursCourse.setEditable(false);
        titleCourse.setEditable(false);
        descriptionCourse.setEditable(false);

        programStudentsTable.setPlaceholder(new Label("No content available"));
        programStudentsTableRef.setCellValueFactory(new PropertyValueFactory<>("stuID"));
        programStudentsTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        programStudentsTableLevel.setCellValueFactory(new PropertyValueFactory<>("status"));
        programStudentsTableYear.setCellValueFactory(new PropertyValueFactory<>("entrance_year"));
        semesterCourseOpened.setCellValueFactory(new PropertyValueFactory<>("course_semester"));
        yearCourseOpened.setCellValueFactory(new PropertyValueFactory<>("course_year"));
        deptTableColHead.setCellValueFactory(new PropertyValueFactory<>("headName"));
        deptTableColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableSemesterColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableSemesterColYear.setCellValueFactory(new PropertyValueFactory<>("sYear"));

        departmentStudTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        departmentStudTableEnt.setCellValueFactory(new PropertyValueFactory<>("entrance_year"));
        departmentStudTableLev.setCellValueFactory(new PropertyValueFactory<>("level"));
        departmentStudTableProg.setCellValueFactory(new PropertyValueFactory<>("program"));
        departmentStudTableRef.setCellValueFactory(new PropertyValueFactory<>("reference"));

        departmentTable.setItems(Tools.getDepartments());

        tableSemester.setItems(Tools.getSemesters());

        idSemesterForJury.setItems(Tools.getSemesters());
        idSemesterForJury.getSelectionModel().select(Tools.getCurrentSemester());
        ObservableList<SchoolFaculty> schoolFaculties = Tools.getSchoolFaculties();
        schoolFaculties.add(0, new SchoolFaculty(0, "All", 0));
        idFacultyForJury.setItems(schoolFaculties);
        idFacultyForJury.getSelectionModel().select(schoolFaculties.get(0));
        ArrayList<Program> schoolProgram = new ArrayList<>(LoadProgram.loadProgramFromDB().getList());
        schoolProgram.add(0, new Program("All", 0, 0, 0));
        ObservableList<Program> schoolPrograms = FXCollections.observableArrayList(schoolProgram);
        idProgramForJury.setItems(schoolPrograms);
        idProgramForJury.getSelectionModel().select(schoolPrograms.get(0));
        ObservableList<SchoolLevel> schoolLevels = Tools.getSchoolLevels();
        schoolLevels.add(0, new SchoolLevel(0, "All"));
        idLevelForJury.setItems(schoolLevels);
        idLevelForJury.getSelectionModel().select(schoolLevels.get(0));

        tableProgram.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        titleTxfiel.setEditable(false);
        nameFaculty.setEditable(false);
        facultySpecialty.setEditable(false);
        depText.setEditable(false);
        adressFaculty.setEditable(false);
        phonenumberFaculty.setEditable(false);
        emailFaculty.setEditable(false);
        officeFaculty.setEditable(false);
        officehoursFaculty.setEditable(false);
        salaryFaculty.setEditable(false);
        genderFaculty.setEditable(false);
        datehiredFaculty.setEditable(false);
        datebirthFaculty.setEditable(false);

        maxNumCredit.setText(String.valueOf(MaxCredits.getMaxCredits()));

        tableFaculty.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Faculty>() {

            @Override
            public void changed(ObservableValue<? extends Faculty> observable, Faculty oldValue, Faculty newValue) {
                Faculty s = newValue;
                if (s != null) {
                    nameFaculty.setText(s.getName());
                    if (s.getGender() == 'M') {
                        genderFaculty.setText("Male");
                    } else {
                        genderFaculty.setText("Female");
                    }
                    phonenumberFaculty.setText(s.getPhoneNumber());
                    adressFaculty.setText(s.getAddress());
                    emailFaculty.setText(s.getEmailAddress());
                    officeFaculty.setText(s.getOffice());
                    officehoursFaculty.setText(Long.toString(s.getOfficehours()));
                    salaryFaculty.setText(Double.toString(s.getSalary()));
                    rankFaculty.setText(s.getRank());
                    if (s.getDateHired() != null) {
                        datehiredFaculty.setText(s.getDateHired().toString());
                    } else {
                        datehiredFaculty.setText("Null");
                    }
                    if (s.getBirth() != null) {
                        datebirthFaculty.setText(s.getBirth().toString());
                    } else {
                        datehiredFaculty.setText("Null");
                    }
                    titleTxfiel.setText(s.getTitle());
                    depText.setText(s.getDepartement());
                    facultySpecialty.setText(s.getSpecialty());
                    disableAllFacProps();
                }
            }
        });

        staffMgntTitle.setEditable(false);
        nameStaff.setEditable(false);
        titleStaff.setEditable(false);
        officeStaff.setEditable(false);
        emailStaff.setEditable(false);
        genderStaff.setEditable(false);
        adressStaff.setEditable(false);
        phonenumberStaff.setEditable(false);
        salaryStaff.setEditable(false);
        datehiredStaff.setEditable(false);
        datebirthStaff1.setEditable(false);

        tableStaff.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Staff>() {

            @Override
            public void changed(ObservableValue<? extends Staff> observable, Staff oldValue, Staff newValue) {
                Staff s = newValue;
                if (s != null) {
                    nameStaff.setText(s.getName());
                    if (s.getGender() == 'M') {
                        genderStaff.setText("Male");
                    } else {
                        genderStaff.setText("Female");
                    }
                    phonenumberStaff.setText(s.getPhoneNumber());
                    adressStaff.setText(s.getAddress());
                    emailStaff.setText(s.getEmailAddress());
                    officeStaff.setText(s.getOffice());
                    // officehoursFaculty.setText(Long.toString(s.getOfficehours()));
                    salaryStaff.setText(Double.toString(s.getSalary()));
                    titleStaff.setText(s.getTitle());
                    datehiredStaff.setText(s.getDateHired().toString());
                    datebirthStaff1.setText(s.getBirth().toString());
                    staffMgntTitle.setText(s.getTitre());
                }
            }
        });

        iDtextStudent.setEditable(false);
        nametextStudent.setEditable(false);
        statustextStudent.setEditable(false);
        deptextStudent.setEditable(false);
        textStudProgram.setEditable(false);
        teltextStudent.setEditable(false);
        emailtextStudent.setEditable(false);
        gendertextStudent.setEditable(false);
        addresstextStudent.setEditable(false);
        birthdaytextStudent.setEditable(false);
        studParentInfo.setEditable(false);
        studEntranceYear.setEditable(false);
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {
            @Override
            public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
                Student s = newValue;
                if (s != null) {
                    iDtextStudent.setText(s.getStuID());
                    nametextStudent.setText(s.getName());
                    deptextStudent.setText(s.getDep());
                    if (s.getGender() == 'M') {
                        gendertextStudent.setText("Male");
                    } else {
                        gendertextStudent.setText("Female");
                    }
                    teltextStudent.setText(decide(s.getPhoneNumber()));
                    statustextStudent.setText(decide(s.getStatus()));
                    addresstextStudent.setText(decide(s.getAddress()));
                    emailtextStudent.setText(decide(s.getEmailAddress()));
                    birthdaytextStudent.setText(decide(s.getBirth().toString()));
                    studParentInfo.setText(decide(s.getParent_info()));
                    studEntranceYear.setText(decide(s.getEntrance_year()));
                    textStudProgram.setText(LoadProgram.getProgram(s.getProgram_id()).getTitle());
                    s.setProfilePhoto(Tools.loadImageProfiles(s.getId()));
                    s.setProfileFile(Tools.loadImageName(s.getId()));
                    Image image = new Image(s.getProfilePhoto());
                    //Image image = new Image(Tools.loadImageProfiles(s.getId()));                    
                    profileImageStudent.setImage(image);

                }
            }
        });
        courseTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Course>() {

            @Override
            public void changed(ObservableValue<? extends Course> observable, Course oldValue, Course newValue) {
                Course c = newValue;

                if (c != null) {
                    Semester sem = semesterCourseStudentBox.getSelectionModel().getSelectedItem();
                    displayCourseDetails(c);
                    displayStudentCourseDetails(c, sem);
                }
            }
        });
        tableProgram.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Program>() {

            @Override
            public void changed(ObservableValue<? extends Program> observable, Program oldValue, Program newValue) {
                Program p = newValue;
                if (p != null) {
                    Semester sem = semesterboxProgram.getSelectionModel().getSelectedItem();
                    tableProgramCourse.setItems(FXCollections.observableArrayList(
                            ManageProgramCourses.loadProgramCourses(p, sem.getId())));
                    programStudentsTable.setItems(FXCollections.observableArrayList(ManageProgramCourses.programStudent(p.getId())));
                }
            }
        });

        departmentTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Department>() {

            @Override
            public void changed(ObservableValue<? extends Department> observable, Department oldValue, Department newValue) {
                Department dept = newValue;
                departmentStudTable.setItems(Tools.getStudentsDept(dept.getId()));
                departmentStudTable.refresh();
                departmentFaculties.setItems(loadDdataFacultyDepartment(dept.getName()));
                departmentFaculties.refresh();

            }
        });
        programlist.addAll(LoadProgram.loadProgramFromDB().getList());
        programbox.setItems(programlist);
        //coursebox.setItems(Tools.getCourses());
        //semesterbox.setItems(Tools.getSemesters());
        tcoursegrade.setEditable(true);
        tcoursegrade.setPlaceholder(new Label("No content availaible"));
        tstudentname.setCellValueFactory(new PropertyValueFactory<>("studentname"));
        tstudentgrade.setCellValueFactory(new PropertyValueFactory<>("score"));
        tstudentgrade.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        tcoursegrade.setItems(studentgrades);
        tCourseOtherGrades.setPlaceholder(new Label("No content availaible"));
        programTestBox.setItems(FXCollections.observableArrayList(LoadProgram.loadProgramFromDB().getList()));
        testTypeBox.setItems(loadTestTypes());
        dateEnregTest.setConverter(Tools.getDateConverter());
        currentDate.setConverter(Tools.getDateConverter());
        Calendar d = Calendar.getInstance();
        d.setTimeInMillis(System.currentTimeMillis());
        tCourseOtherGrades.setEditable(true);
        dateEnregTest.setValue(LocalDate.of(d.get(Calendar.YEAR), d.get(Calendar.MONTH) + 1, d.get(Calendar.DAY_OF_MONTH)));
        currentDate.setValue(LocalDate.of(d.get(Calendar.YEAR), d.get(Calendar.MONTH) + 1, d.get(Calendar.DAY_OF_MONTH)));
        tOGStudent.setCellValueFactory(new PropertyValueFactory<>("studentname"));
        tOGStudentRegistration.setCellValueFactory(new PropertyValueFactory<>("registration"));
        tOGStudentGrade.setCellValueFactory(new PropertyValueFactory<>("score"));
        letterGradeTest.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));
        tOGStudentCode.setCellValueFactory(new PropertyValueFactory<>("anonymous"));
        tOGStudentGrade.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        tOGStudentCode.setCellFactory(TextFieldTableCell.forTableColumn());

        programScheBox.setItems(programlist);
        dateScheTest.setConverter(Tools.getDateConverter());
        tableScheCCol.setCellValueFactory(new PropertyValueFactory<>("course_title"));
        tableSchePlaceCol.setCellValueFactory(new PropertyValueFactory<>("room_name"));
        tableScheDateCol.setCellValueFactory(new PropertyValueFactory<>("course_date"));
        tableScheDayCol.setCellValueFactory((new PropertyValueFactory<>("course_day")));
        tableScheETimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        tableScheSTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        tableScheLecCol.setCellValueFactory(new PropertyValueFactory<>("facultyName"));
        //schedule.addAll(get all Taught bys);
        semesterboxProgram.setItems(FXCollections.observableArrayList(Tools.getSemesters()));
        semesterboxProgram.getSelectionModel().select(Tools.currentSemester);
        semesterCourseStudentBox.setItems(FXCollections.observableArrayList(Tools.getSemesters()));
        semesterCourseStudentBox.getSelectionModel().select(Tools.currentSemester);
    }

    @FXML
    void researchStudentPrograme(KeyEvent event) {
        Alert a;
        Program p;
        if ((p = tableProgram.getSelectionModel().getSelectedItem()) == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a row before clicking on Edit!", ButtonType.OK);
            a.setHeaderText("Please select a program");
            a.showAndWait();
        } else {
            searchStudent.getText();
            ArrayList<Student> list = CRUDStudent.searchposibilities(ManageProgramCourses.programStudent(p.getId()), nameStudentSearch.getText());
            programStudentsTable.setItems(FXCollections.observableArrayList(list));

        }

    }

    @FXML
    void researchStudentRegisteredCourse(KeyEvent event) {
        Alert a;
        Program p;
        if ((p = tableProgram.getSelectionModel().getSelectedItem()) == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a row before clicking on Edit!", ButtonType.OK);
            a.setHeaderText("Please select a program");
            a.showAndWait();
        } else {
            ArrayList<Student> list = CRUDStudent.searchposibilities(ManageProgramCourses.programStudent(p.getId()), nameStudentSearch.getText());
            programStudentsTable.setItems(FXCollections.observableArrayList(list));

        }

    }

    @FXML
    void searchStudentRegisteredCourse(KeyEvent event) {
        Alert a;
        Course c;
        if ((c = courseTable.getSelectionModel().getSelectedItem()) == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a row before clicking on Search!", ButtonType.OK);
            a.setHeaderText("Missing selected element!");
            a.showAndWait();
        } else {
            ObservableList<Student_Course> list = CRUDStudentCourse.searchposibilitie(
                    Tools.getStudentCourses(c), nameStudentSearchCourse.getText());
            programStudentsTableStudent.setItems(list);
            programStudentsTableStudent.refresh();
        }
    }

    private ObservableList<Program> loadPrograms() {
        ArrayList<Program> list = LoadProgram.loadProgramFromDB().getList();
        if (list != null && !list.isEmpty()) {
            Program p = new Program();
            p.setTitle("All");
            p.setId(0);
            list.add(p);
            return FXCollections.observableArrayList(list);
        }
        return null;
    }

    @FXML
    private JFXTextField maxNumCredit;

    @FXML
    private JFXTextField newMaxCredits;

    @FXML
    private JFXButton updateCredits;

    @FXML
    void updateNumberOfCredits(ActionEvent event) {
        double newCredits = 0;
        try {
            newCredits = Double.parseDouble(newMaxCredits.getText());
            if (newCredits < 0) {
                throw new PositiveNumberException("The number of credits should be a positive number!");
            }
            MaxCredits.updateMaxCredits(newCredits);
            maxNumCredit.setText(String.valueOf(newCredits));
            newMaxCredits.clear();
        } catch (NumberFormatException ex) {
            Alert a;
            a = new Alert(Alert.AlertType.ERROR, "What you entered is not a number. Please enter it again.", ButtonType.OK);
            a.setHeaderText("Invalid number format!");
            a.showAndWait();
        } catch (PositiveNumberException e) {
            Alert a;
            a = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            a.setHeaderText("Invalid number!");
            a.showAndWait();
        }
    }

    @FXML
    private JFXComboBox<Semester> idSemesterForJury;

    @FXML
    private JFXComboBox<SchoolFaculty> idFacultyForJury;

    @FXML
    private JFXComboBox<SchoolLevel> idLevelForJury;

    @FXML
    private JFXComboBox<Program> idProgramForJury;

    @FXML
    private JFXButton idGenJury;

    @FXML
    void generateJuryReport(ActionEvent event) {
        SchoolFaculty schoolFac = idFacultyForJury.getSelectionModel().getSelectedItem();
        if (!schoolFac.getTitle().equals("All")) {
            Program prog = idProgramForJury.getSelectionModel().getSelectedItem();
            if (prog.getTitle().equals("All")) {
                try {
                    //specific school faculty, all program of the faculty
                    FinalResultsReport.displayReport(idSemesterForJury.getSelectionModel().getSelectedItem().getId(),
                            schoolFac);
                } catch (JRException ex) {
                    Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                SchoolLevel slev = idLevelForJury.getSelectionModel().getSelectedItem();
                if (slev.getName().equals("All")) {
                    try {
                        //specific school faculty, specific program, all the levels
                        FinalResultsReport.displayReport(idSemesterForJury.getSelectionModel().getSelectedItem().getId(),
                                schoolFac, prog);
                    } catch (JRException ex) {
                        Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    //specific school faculty, specific program, specific level
                    try {
                        //specific school faculty, specific program, all the levels
                        FinalResultsReport.displayReport(idSemesterForJury.getSelectionModel().getSelectedItem().getId(),
                                schoolFac, prog, slev);
                    } catch (JRException ex) {
                        Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        } else {
            //All the school faculties
            try {
                FinalResultsReport.displayReport(idSemesterForJury.getSelectionModel().getSelectedItem().getId());
            } catch (JRException ex) {
                Logger.getLogger(MainPageForAllController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private JFXButton closeSemester;

    @FXML
    void closeSemesterAction(ActionEvent event) {
        Tools.closeSemester();
        Alert a;
        a = new Alert(Alert.AlertType.INFORMATION, "Semester succesfully closed. Thank you!", ButtonType.OK);
        a.setHeaderText("Closing a semeseter");
        a.showAndWait();

    }

    @FXML
    void onFacultySelectedForJuryAction(ActionEvent event) {
        SchoolFaculty faculty = idFacultyForJury.getSelectionModel().getSelectedItem();
        /*
        faculty.getId() == 1 if the selected faculty is science and technology
        faculty.getId() == 2 if the selected faculty is economics and management
        else it is any of the two
         */
        ArrayList<Program> schoolProgram = new ArrayList<>(LoadProgram.loadProgramFromDB().getList());
        ObservableList<Program> schoolPrograms = FXCollections.observableArrayList(schoolProgram);
        switch (faculty.getId()) {
            case 1:
                Iterator<Program> it = schoolPrograms.iterator();
                while (it.hasNext()) {
                    Program elt = it.next();
                    if (elt.getId() != 1 && elt.getId() != 2
                            && elt.getId() != 3 && elt.getId() != 4
                            && elt.getId() != 6) {
                        it.remove();
                    }
                }
                break;
            case 2:
                Iterator<Program> it2 = schoolPrograms.iterator();
                while (it2.hasNext()) {
                    Program elt = it2.next();
                    if (elt.getId() != 5 && elt.getId() != 7) {
                        it2.remove();
                    }
                }
                break;
            default:
                break;
        }
        schoolPrograms.add(0, new Program("All", 0, 0, 0));
        idProgramForJury.setItems(schoolPrograms);
        idProgramForJury.getSelectionModel().select(schoolPrograms.get(0));
    }

    @FXML
    void onProgramSelectedForJuryAction(ActionEvent event) {
        Program program = idProgramForJury.getSelectionModel().getSelectedItem();
        ObservableList<SchoolLevel> schoolLevels = Tools.getSchoolLevels();
        if (program != null) {
            if (program.getId() >= 1 && program.getId() <= 6) {
                Iterator<SchoolLevel> it = schoolLevels.iterator();
                while(it.hasNext()){
                    SchoolLevel elt = it.next();
                    if(elt.getId() > 7)
                        it.remove();
                }
            }
            else{
                Iterator<SchoolLevel> it = schoolLevels.iterator();
                while(it.hasNext()){
                    SchoolLevel elt = it.next();
                    if(elt.getId() < 8)
                        it.remove();
                }
            }
            schoolLevels.add(0, new SchoolLevel(0, "All"));
            idLevelForJury.setItems(schoolLevels);
            idLevelForJury.getSelectionModel().select(schoolLevels.get(0));

        }
    }

}
