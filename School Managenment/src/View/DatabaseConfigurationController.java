/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.DBConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.DriverManager;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author CNC
 */
public class DatabaseConfigurationController implements Initializable {

    @FXML
    private Label studentLabel;

    @FXML
    private JFXTextField login;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXTextField url;

    @FXML
    private JFXButton saveBut;

    @FXML
    private JFXButton reset;

    @FXML
    private JFXButton bactoLogin;

    @FXML
    private JFXButton cancel;

    @FXML
    void onCancel(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onLogin(ActionEvent event) throws Exception {
        onCancel(event);
        Stage stage = new Stage();
        Locale.setDefault(Locale.ENGLISH);
//        Parent root = FXMLLoader.load(getClass().getResource("MainPageForAll.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        Scene scene = new Scene(root);

        stage.getIcons().add(new Image(DatabaseConfigurationController.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void onReset(ActionEvent event) {
        login.setText("");
        password.setText("");
        url.setText("");
    }

    @FXML
    void onSave(ActionEvent event) throws Exception {
        //Save the new configuration
        DBConnection.writeToConfigFile(login.getText(),
                password.getText(), url.getText());
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Database Config.");
        a.setHeaderText("The database configuration informations have been succesfully saved!");
        a.showAndWait();
        //Close the admin login page
        onCancel(event);
        //return to the classic login page
        onLogin(event);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL urll, ResourceBundle rb) {
        InputStream stream = null;
        try {
            stream = new FileInputStream("config.properties");
            Properties prop = new Properties();

            if (stream == null) {
                System.out.println("Sorry, unable to find config.properties");
            }

            //load a properties file from class path, inside static method
            prop.load(stream);
            login.setText(prop.getProperty("db.user"));
            password.setText(prop.getProperty("db.password"));
            url.setText(prop.getProperty("db.url"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
