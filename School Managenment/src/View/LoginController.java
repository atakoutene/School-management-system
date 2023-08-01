/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.Tools;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author HERMAN
 */
public class LoginController implements Initializable {

    @FXML
    private Label studentLabel;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXTextField login;

    @FXML
    private JFXButton loginBut;

    @FXML
    private JFXButton reset;

    @FXML
    void loginAction(ActionEvent event) {
        if (login.getText().equals("admin") && password.getText().equals("admin2022")) {
            try {
                FXMLLoader loader;
                loader = new FXMLLoader(getClass().getResource("DatabaseConfiguration.fxml"));
                Locale.setDefault(Locale.ENGLISH);
                Stage st = new Stage(StageStyle.DECORATED);
                st.setScene(new Scene((Pane) loader.load()));
                st.getIcons().add(new Image(LoginController.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
                st.setResizable(false);
                st.show();
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (Tools.connectAdmin(login.getText(), password.getText())) {
                try {
                    FXMLLoader loader;
                    loader = new FXMLLoader(getClass().getResource("MainPageForAll.fxml"));
                    Tools.currentSemester = Tools.getCurrentSemester();
                    Locale.setDefault(Locale.ENGLISH);
                    Stage st = new Stage(StageStyle.DECORATED);
                    st.setScene(new Scene((Pane) loader.load()));
                    st.getIcons().add(new Image(LoginController.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
                    st.setResizable(false);
                    st.show();
                    Node source = (Node) event.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();

                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Invalid credentials");
                a.setContentText("The login and or the password is invalid. Please check and enter again!");
                a.showAndWait();
            }
        }

    }

    @FXML
    void resetAction(ActionEvent event) {
        login.setText("");
        password.setText("");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
