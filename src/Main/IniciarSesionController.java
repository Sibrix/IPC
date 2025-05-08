/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.User;
import static modelos.alerta.mostrarAlerta;

/**
 * FXML Controller class
 *
 * @author danie
 */

public class IniciarSesionController implements Initializable {

    @FXML
    private Label errorcon;
    @FXML
    private Label errorfecha;
    @FXML
    private TextField usuario;
    @FXML
    private Label erroruser;
    @FXML
    private PasswordField contraseña;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cerrar(ActionEvent event) {
        errorcon.getScene().getWindow().hide();
    }


    @FXML
    private void inicioSesion(ActionEvent event) throws IOException {
        if (usuario.getText().isEmpty() || contraseña.getText().isEmpty()){
            mostrarAlerta("Error","Usuario o contraseña vacíos",Alert.AlertType.ERROR,null);
            return;
        }
        if ( !User.checkNickName(usuario.getText())){
          erroruser.setText("Usuario no valido");
        }
        else if( !User.checkPassword(contraseña.getText())){
          errorcon.setText("Contraseña no valida");
        }
        else{
            Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/CartaNautica/FXMLDocument.fxml"));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
        Scene scene = new Scene(root);
        stage.setTitle("CartaNautica");
        stage.setScene(scene);
        stage.show();
        erroruser.getScene().getWindow().hide();
        }
    }

    @FXML
    private void cambioDeUsuario(KeyEvent event) {
        erroruser.setText("");
    }

    @FXML
    private void cambioDeContraseña(KeyEvent event) {
        errorcon.setText("");
    }
    
}
