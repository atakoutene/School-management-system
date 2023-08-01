/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.util.Locale;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Steve Meudje
 */
public class Lanceur extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Locale.setDefault(Locale.ENGLISH);
//        Parent root = FXMLLoader.load(getClass().getResource("MainPageForAll.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Lanceur.class.getResourceAsStream("/Resource/images/logopkf.PNG")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
