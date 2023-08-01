/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Sokeng Paul (AG)
 */
public class PresenceController implements Initializable {
    
    @FXML
    private AnchorPane presencepane;
    @FXML
    private JFXCheckBox presencebox;
    @FXML
    private JFXTextField remarkfield;

    public AnchorPane getPresencepane() {
        return presencepane;
    }

    public void setPresencepane(AnchorPane presencepane) {
        this.presencepane = presencepane;
    }

    public JFXCheckBox getPresencebox() {
        return presencebox;
    }

    public void setPresencebox(JFXCheckBox presencebox) {
        this.presencebox = presencebox;
    }

    public JFXTextField getRemarkfield() {
        return remarkfield;
    }

    public void setRemarkfield(JFXTextField remarkfield) {
        this.remarkfield = remarkfield;
    }
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
