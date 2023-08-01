/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.DBConnection;
import Control.LoadDepartment;
import Control.ManageProgramCourses;
import Control.Tools;
import Model.Department;
import Model.Program;
import Model.ProgramCourses;
import Model.SchoolFaculty;
import Model.SchoolLevel;
import Model.Semester;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author HERMA
 */
public class FXMLProgramController implements Initializable {

    @FXML
    private JFXTextField programTitle;

    @FXML
    private Button btnSave;

    @FXML
    private Label titleStage;

    private TableView<Program> tableProgram;

    private ObservableList<Program> currentProgramList = FXCollections.observableArrayList();

    private Program prog;

    @FXML
    private TableView<ProgramCourses> tableProgramCourse;

    @FXML
    private TableColumn<ProgramCourses, String> codeProgramCourse;

    @FXML
    private TableColumn<ProgramCourses, String> titleProgramCourse;

    @FXML
    private TableColumn<ProgramCourses, SchoolLevel> levelProgramCourse;

    @FXML
    private TableColumn<ProgramCourses, String> gradeProgramCourse;

    @FXML
    private TableColumn<ProgramCourses, Integer> creditsProgramCourse;

    @FXML
    private TableColumn<ProgramCourses, String> coreProgramCourse;

    @FXML
    private Button addCourseProgram;

    @FXML
    private Button deleteCourseProgram;

    @FXML
    private Button editCourseProgram;

    @FXML
    private Button btnCancel;
    
    @FXML
    private JFXComboBox<Department>departments;
    
    @FXML
    private JFXComboBox<SchoolFaculty>schoolfac;

    private boolean flag = false;
    
    private Semester sem;

    public void setSem(Semester sem) {
        this.sem = sem;
    }
    
    

    @FXML
    void addCourseProgram(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Course_Program.fxml"));
            Stage st = new Stage(StageStyle.DECORATED);
            st.setScene(new Scene((Pane) loader.load()));
            Course_ProgramController controller = loader.getController();
            controller.setTableProgramCourse(tableProgramCourse);
            controller.setSem(sem);
            st.setResizable(false);
            st.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLProgramController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void deleteCourseProgram(ActionEvent event) {
        ProgramCourses pc;
        Alert a;
        if ((pc = tableProgramCourse.getSelectionModel().getSelectedItem()) == null) {
            a = new Alert(Alert.AlertType.ERROR, "Please select a course", ButtonType.OK);
            a.setTitle("Error!");
            a.setHeaderText("No course selected!");
            a.showAndWait();
        } else {
            tableProgramCourse.getItems().remove(pc);
            tableProgramCourse.refresh();
        }
    }

    public void setTableProgram(TableView<Program> tableProgram) {
        this.tableProgram = tableProgram;
    }

    public void setCurrentProgramList(ObservableList<Program> currentProgramList) {
        this.currentProgramList = currentProgramList;
    }

    @FXML
    public void saveProgram(ActionEvent event) {
        Connection con = DBConnection.getConnection();
        if (flag == false) {
            try {
                PreparedStatement prestat = con.prepareStatement("select * from program where title = ?;");
                prestat.setString(1, programTitle.getText());
                ResultSet result = prestat.executeQuery();
                int i = 0;
                while (result.next()) {
                    i++;
                }
                if (i != 0) {
                    Alert a;
                    a = new Alert(Alert.AlertType.ERROR, "A program with this title already exists. Please enter another title!", ButtonType.OK);
                    a.setHeaderText("Program existing!");
                    a.showAndWait();
                    return;
                }
                Program p = new Program();
                p.setTitle(programTitle.getText());
                Statement stm = con.createStatement();
                stm.executeUpdate("insert into program(title,id_department,id_schoolfaculty) values ('" + programTitle.getText() + "'"
                        + ","+departments.getSelectionModel().getSelectedItem().getId()+","
                                +schoolfac.getSelectionModel().getSelectedItem().getId()+");", Statement.RETURN_GENERATED_KEYS);
                ResultSet rs = stm.getGeneratedKeys();
                int id_program = 1;
                if (rs.next()) {
                    id_program = rs.getInt(1);
                }
                PreparedStatement stmInsertCourse = con.prepareStatement("insert into program_course values "
                        + "(?,?,?,?,?,?,?)");
                for(ProgramCourses pc: tableProgramCourse.getItems()){
                    pc.setId_program(id_program);
                    pc.setProgram_title(programTitle.getText());
                    stmInsertCourse.setInt(1, id_program);
                    stmInsertCourse.setInt(2, pc.getId_course());
                    stmInsertCourse.setString(3, pc.getCore());
                    stmInsertCourse.setInt(4, pc.getLevel().getId());
                    stmInsertCourse.setDouble(5, pc.getNb_credits());
                    stmInsertCourse.setString(6, String.valueOf(pc.getPassing_grade()));
                    stmInsertCourse.setInt(7, pc.getId_semester());
                    stmInsertCourse.executeUpdate();
                    p.getProgramCourses().add(pc);
                }
                p.setId(id_program);
                ObservableList<Program> list = tableProgram.getItems();
                list.add(p);
                currentProgramList.add(p);
                Collections.sort(currentProgramList);
                Collections.sort(list);
                tableProgram.setItems(list);
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            } catch (SQLException ex) {
                Logger.getLogger(FXMLProgramController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLProgramController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            try {
                Program p = new Program();
                p.setTitle(programTitle.getText());
                p.setId(prog.getId());
                p.setId_department(prog.getId_department());
                p.setId_schoolfaculty(prog.getId_schoolfaculty());
                
                PreparedStatement stm = con.prepareStatement("update program set title = ?, id_department = ?, id_schoolfaculty = ? "
                        + "where id = ?;");
                stm.setString(1, programTitle.getText());
                stm.setInt(2, departments.getSelectionModel().getSelectedItem().getId());
                stm.setInt(3, schoolfac.getSelectionModel().getSelectedItem().getId());
                stm.setInt(4, prog.getId());
                stm.executeUpdate();
                
                
                //Remove existing references for program_courses
                ManageProgramCourses.deleteProgramCourses(prog.getId(),sem.getId());
                
                //Insert the new courses         
                PreparedStatement stmInsertCourse = con.prepareStatement("insert into program_course values "
                        + "(?,?,?,?,?,?,?)");
                for(ProgramCourses pc: tableProgramCourse.getItems()){
                    pc.setId_program(prog.getId());
                    pc.setProgram_title(programTitle.getText());
                    stmInsertCourse.setInt(1, prog.getId());
                    stmInsertCourse.setInt(2, pc.getId_course());
                    stmInsertCourse.setString(3, pc.getCore());
                    stmInsertCourse.setInt(4, pc.getLevel().getId());
                    stmInsertCourse.setDouble(5, pc.getNb_credits());
                    stmInsertCourse.setString(6, String.valueOf(pc.getPassing_grade()));
                    stmInsertCourse.setInt(7, pc.getId_semester());
                    stmInsertCourse.executeUpdate();
                    p.getProgramCourses().add(pc);
                }
                
                ObservableList<Program> tmp = tableProgram.getItems();
                Program ff = null;
                int j = 0;
                boolean b = false;
                while (!tmp.isEmpty() && (j < tmp.size()) && !b) {
                    ff = tmp.get(j);
                    if (ff.getId() == prog.getId()) {
                        b = true;
                    }
                    j++;
                }
                b = false;
                j = 0;
                Program ff2 = null;
                while (!currentProgramList.isEmpty() && (j < currentProgramList.size()) && !b) {
                    ff2 = currentProgramList.get(j);
                    if (ff2.getId() == prog.getId()) {
                        b = true;
                    }
                    j++;
                }

                tmp.remove(ff);
                currentProgramList.remove(ff2);
                tmp.add(p);
                Collections.sort(tmp);
                tableProgram.setItems(tmp);
                currentProgramList.add(p);
                Collections.sort(currentProgramList);
                tableProgram.getSelectionModel().select(p);
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            } catch (SQLException ex) {
                Logger.getLogger(FXMLProgramController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLProgramController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    @FXML
    void editCourseProgram(ActionEvent event) {
        try {
            ProgramCourses pc;
            Alert a;
            if ((pc = tableProgramCourse.getSelectionModel().getSelectedItem()) == null) {
                a = new Alert(Alert.AlertType.ERROR, "Please select a course", ButtonType.OK);
                a.setTitle("Error!");
                a.setHeaderText("No course selected!");
                a.showAndWait();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Course_Program.fxml"));
                Stage st = new Stage(StageStyle.DECORATED);
                st.setScene(new Scene((Pane) loader.load()));
                Course_ProgramController controller = loader.getController();
                controller.setTableProgramCourse(tableProgramCourse);
                controller.initWindow(pc);
                controller.setSem(sem);
                st.setResizable(false);
                st.show();
            }
        } catch (IOException ex) {
            Logger.getLogger(FXMLProgramController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void cancelProgram(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        codeProgramCourse.setCellValueFactory(new PropertyValueFactory<ProgramCourses, String>("code_course"));
        titleProgramCourse.setCellValueFactory(new PropertyValueFactory<ProgramCourses, String>("course_title"));
        levelProgramCourse.setCellValueFactory(new PropertyValueFactory<ProgramCourses, SchoolLevel>("level"));
        gradeProgramCourse.setCellValueFactory(new PropertyValueFactory<ProgramCourses, String>("passing_grade"));
        creditsProgramCourse.setCellValueFactory(new PropertyValueFactory<ProgramCourses, Integer>("nb_credits"));
        coreProgramCourse.setCellValueFactory(new PropertyValueFactory<ProgramCourses, String>("core"));
        tableProgramCourse.setPlaceholder(new Label("No content available"));
        departments.setItems(FXCollections.observableArrayList(LoadDepartment.loadDepFromDB().getList()));
        schoolfac.setItems(FXCollections.observableArrayList(Tools.loadSchoolFacDB().getList()));
    }

    public void initWindow(Program p) {
        flag = true;
        titleStage.setText("Update a Program");
        programTitle.setText(p.getTitle());
        prog = new Program(p.getTitle(), p.getId(),p.getId_department(),p.getId_schoolfaculty());
        prog.setProgramCourses(ManageProgramCourses.loadProgramCourses(p, sem.getId()));
        for(Department d : departments.getItems()){
            if(d.getId() == p.getId_department())
                departments.getSelectionModel().select(d);
        }
        for(SchoolFaculty f : schoolfac.getItems()){
            if(f.getId() == p.getId_schoolfaculty())
                schoolfac.getSelectionModel().select(f);
        }
        tableProgramCourse.setItems(FXCollections.observableArrayList(
                ManageProgramCourses.loadProgramCourses(p, sem.getId())));
        tableProgramCourse.refresh();
    }
}
