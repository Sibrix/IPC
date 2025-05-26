/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Main;

import CartaNautica.FXMLDocumentController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.NavDAOException;
import model.User;
import static modelos.alerta.mostrarAlerta;
import model.Navigation;
import javafx.stage.Modality;


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
    
    public static User user;
    public static SimpleStringProperty miStringProperty = new SimpleStringProperty("");
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
    private void inicioSesion(ActionEvent event) throws IOException, NavDAOException {
        Navigation nav = Navigation.getInstance();
        if (usuario.getText().isEmpty() || contraseña.getText().isEmpty()){
            mostrarAlerta("Error","Usuario o contraseña vacíos",Alert.AlertType.ERROR,null);
            return;
        }
        if (!User.checkPassword(contraseña.getText()) && !User.checkNickName(usuario.getText())){
          mostrarAlerta("Error","Usuario o contraseña no validos",Alert.AlertType.ERROR,null);
          return;
        }
        else if(!nav.exitsNickName(usuario.getText()) || null == nav.authenticate(usuario.getText(), contraseña.getText())){
           mostrarAlerta("Error","Usuario o contraseña no existen",Alert.AlertType.ERROR,null);
           return;
        }
        else{
            user = nav.authenticate(usuario.getText(), contraseña.getText());
            miStringProperty.set(user.getNickName());
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
    @FXML
    private void registro(MouseEvent event) throws IOException {   
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("registrarse.fxml"));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("INICIO DE SESION");
        stage.setScene(scene);
        stage.show();
        contraseña.getScene().getWindow().hide();
        
    }
    public static void setUser(User usuario){
        user=usuario;
    }
    
}
