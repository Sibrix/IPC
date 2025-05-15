/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.User;

/**
 * FXML Controller class
 *
 * @author idair
 */
public class RegistrarseController implements Initializable {

    @FXML
    private Label errorcor;
    @FXML
    private Label errorcon;
    @FXML
    private Label errorfecha;
    @FXML
    private Label errorus;
    private TextField campoCorreo;
    @FXML
    private Label correo;
    @FXML
    private TextField campocorreo;
    
    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errorcor.setText("");
    }    

    @FXML
    private void cerrar(ActionEvent event) {
        errorcon.getScene().getWindow().hide();
    }
    
    @FXML
    private void aceptarreg(ActionEvent event) {
        String correo = campoCorreo.getText();
        if (correo == null || correo.trim().isEmpty() ) {
            errorcor.setText("El campo no puede estar vacío");
        } else if (!User.checkEmail(correo)) {
             errorcor.setText("Correo no válido");
        } else {
            errorcor.setText("");
        }
    }
    
}
