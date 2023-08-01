/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.DBConnection;
import Control.ManageCourse;
import Model.Course;
import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author HERMAN
 */
public class AddModifyCourseController implements Initializable {

    @FXML
    private JFXTextField code;

    @FXML
    private JFXTextField title;

    @FXML
    private JFXTextField hours;

    @FXML
    private TableView<Course> requisites;

    @FXML
    private TableColumn<Course, String> courseCode;

    @FXML
    private TableColumn<Course, String> courseTitle;

    @FXML
    private TextArea description;

    @FXML
    private JFXButton btSave;

    @FXML
    private JFXButton btCancel;

    @FXML
    private Button addRequisite;

    @FXML
    private Button deleteRequisite;

    @FXML
    private Label courseTitre;

    private TableView<Course> tableCourse;

    private ObservableList<Course> currentCourseList = FXCollections.observableArrayList();

    private Course course;

    private boolean flag = false;

    public TableView<Course> getTableCourse() {
        return tableCourse;
    }

    public void setTableCourse(TableView<Course> tableCourse) {
        this.tableCourse = tableCourse;
    }

    public ObservableList<Course> getCurrentCourseList() {
        return currentCourseList;
    }

    public void setCurrentCourseList(ObservableList<Course> currentCourseList) {
        this.currentCourseList = currentCourseList;
    }

    @FXML
    void addPreq(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Courses.fxml"));
            Stage st = new Stage(StageStyle.DECORATED);
            st.setScene(new Scene((Pane) loader.load()));
            CoursesController controller = loader.getController();
            controller.setTable(requisites);
            st.setResizable(false);
            st.show();
        } catch (IOException ex) {
            Logger.getLogger(AddModifyCourseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void cancelCourse(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void deletePreq(ActionEvent event) {
        ObservableList<Course> c = requisites.getSelectionModel().getSelectedItems();
        requisites.getItems().removeAll(c);
        requisites.refresh();
    }

    @FXML
    void saveCourse(ActionEvent event) {
        Connection con = DBConnection.getConnection();
        try {
            if (flag == false) {
                PreparedStatement prestat = con.prepareStatement("select * from course where code = ?;");
                prestat.setString(1, code.getText());
                ResultSet result = prestat.executeQuery();
                int i = 0;
                while (result.next()) {
                    i++;
                }
//                if (i != 0) {
//                    Alert a;
//                    a = new Alert(Alert.AlertType.ERROR, "A course with this code already exists. Please a different code!", ButtonType.OK);
//                    a.setHeaderText("Course existing!");
//                    a.showAndWait();
//                    return;
//                }
                Course c = new Course();
                c.setCode(code.getText());
                c.setTitle(title.getText());
                c.setDescription(description.getText());
                c.setHours(Integer.parseInt(hours.getText()));
                String req1 = "INSERT INTO course(code, title, description, hours) VALUES ("
                        + "'" + c.getCode() + "','" + c.getTitle() + "','" + c.getDescription() + "'," + c.getHours() + "); ";
                PreparedStatement stm1 = con.prepareStatement(req1);
                stm1.executeUpdate(req1, Statement.RETURN_GENERATED_KEYS);
                ResultSet rs = stm1.getGeneratedKeys();
                int id_course = 1;
                if (rs.next()) {
                    id_course = rs.getInt(1);
                }
                c.setId(id_course);
                String req2 = "INSERT INTO course_preq(id_course,id_prereq) VALUES (?,?);";
                PreparedStatement stm2 = con.prepareStatement(req2);
                for (Course cc : requisites.getItems()) {
                    stm2.setInt(1, id_course);
                    stm2.setInt(2, cc.getId());
                    stm2.executeUpdate();
                    c.getPrerequisite().add(cc);
                }
                tableCourse.getItems().add(c);
                FXCollections.sort(tableCourse.getItems());
                tableCourse.refresh();
                currentCourseList.add(c);
                FXCollections.sort(currentCourseList);
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            } else {
                Course c = new Course();
                c.setCode(code.getText());
                c.setTitle(title.getText());
                c.setDescription(description.getText());
                c.setHours(Integer.parseInt(hours.getText()));
                c.setId(this.course.getId());
                ManageCourse.deletePrequisite(course.getId());
                String req2 = "INSERT INTO course_preq(id_course,id_prereq) VALUES (?,?);";
                PreparedStatement stm2 = con.prepareStatement(req2);
                for (Course cc : requisites.getItems()) {
                    stm2.setInt(1, c.getId());
                    stm2.setInt(2, cc.getId());
                    stm2.executeUpdate();
                    c.getPrerequisite().add(cc);
                }
                PreparedStatement stm = con.prepareStatement("update course set code = ?,title = ?"
                        + " , description = ?, hours = ? where id = ?;");
                stm.setString(1, c.getCode());
                stm.setString(2, c.getTitle());
                stm.setString(3, c.getDescription());
                stm.setInt(4, c.getHours());
                stm.setInt(5, c.getId());
                stm.executeUpdate();
                ObservableList<Course> tmp = tableCourse.getItems();
                Course ff = null;
                int j = 0;
                boolean b = false;
                while (!tmp.isEmpty() && (j < tmp.size()) && !b) {
                    ff = tmp.get(j);
                    if (ff.getId() == course.getId()) {
                        b = true;
                    }
                    j++;
                }
                b = false;
                j = 0;
                Course ff2 = null;
                while (!currentCourseList.isEmpty() && (j < currentCourseList.size()) && !b) {
                    ff2 = currentCourseList.get(j);
                    if (ff2.getId() == course.getId()) {
                        b = true;
                    }
                    j++;
                }
                tmp.remove(ff);
                currentCourseList.remove(ff2);
                tmp.add(c);
                Collections.sort(tmp);
                tableCourse.setItems(tmp);
                currentCourseList.add(c);
                Collections.sort(currentCourseList);
                tableCourse.getSelectionModel().select(c);
                tableCourse.refresh();
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        requisites.setPlaceholder(new Label("No content available"));
        requisites.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        courseCode.setCellValueFactory(new PropertyValueFactory<Course, String>("code"));
        courseTitle.setCellValueFactory(new PropertyValueFactory<Course, String>("title"));
    }

    public void initWindow(Course c) {
        code.setText(c.getCode());
        title.setText(c.getTitle());
        description.setText(c.getDescription());
        hours.setText(String.valueOf(c.getHours()));
        requisites.setItems(FXCollections.observableArrayList(ManageCourse.getPrerequisite(c.getId())));
        requisites.refresh();
        this.course = c;
        flag = true;
        courseTitre.setText("Update a Course");
    }

}
