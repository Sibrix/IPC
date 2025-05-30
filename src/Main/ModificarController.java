/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.ResourceBundle;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.NavDAOException;
import model.Navigation;
import model.User;

/**
 * FXML Controller class
 *
 * @author idair
 */
public class ModificarController implements Initializable {

    @FXML
    private Button infous;
    @FXML
    private TextField campoNick;
    @FXML
    private Label mensajeerror;
    @FXML
    private Label correo;
    @FXML
    private Button infous1;
    @FXML
    private TextField campocorreo;
    @FXML
    private Button infous2;
    @FXML
    private PasswordField campoPas;
    @FXML
    private Button infous3;
    @FXML
    private DatePicker campoDate;
    @FXML
    private Label errorus;
    @FXML
    private Label errorcor;
    @FXML
    private Label errorcon;
    @FXML
    private Label errorfecha;
    @FXML
    private Button registrarse;
    @FXML
    private ImageView imagen_avatar;
    @FXML
    private Button avatar;
    private User usuario;

    private Image avatarSeleccionado = null;
    @FXML
    private TextField contraseña_visible;
    @FXML
    private CheckBox visible;
    /**
     * Initializes the controller class.
     */
    @Override
    //yaiza puta
    public void initialize(URL url, ResourceBundle rb){
        usuario = IniciarSesionController.user;
        campoNick.setText(usuario.getNickName());
        campocorreo.setText(usuario.getEmail());
        campoPas.setText(usuario.getPassword());
        campoDate.setValue(usuario.getBirthdate());
        imagen_avatar.setImage(usuario.getAvatar());
    }    

    @FXML
    private void info_us(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    alerta.setTitle("Información sobre el nickname");
    alerta.setHeaderText(null);
    alerta.setContentText("Debe tener entre 6 y 15 caracteres,\n" +
                          "sin espacios. Se permiten guiones o subguiones");
    alerta.showAndWait();
    }

    @FXML
    private void info_cor(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    alerta.setTitle("Información sobre el correo");
    alerta.setHeaderText(null);
    alerta.setContentText("Debe ser un correo válido");
    alerta.showAndWait();
    }

    @FXML
    private void info_con(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    alerta.setTitle("Información sobre el nickname");
    alerta.setHeaderText(null);
    alerta.setContentText("Debe tener entre 8 y 20 caracteres,\n" +
                          "con mayúsculas, minúsculas, dígitos y un carácter especial");
    alerta.showAndWait();
    }

    @FXML
    private void info_date(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    alerta.setTitle("Información sobre el nickname");
    alerta.setHeaderText(null);
    alerta.setContentText("El usuario debe tener mas de 16 años" );
    alerta.showAndWait();
    }

    @FXML
    private void aceptarreg(ActionEvent event) throws IOException, NavDAOException {
        String nick = campoNick.getText().trim();
        String email = campocorreo.getText().trim();
        String password;
        if (visible.isSelected()){
            password = contraseña_visible.getText().trim();
        }else{
            password = campoPas.getText().trim();
        }
        LocalDate birthdate = campoDate.getValue();
        if (nick.isEmpty() || email.isEmpty() || password.isEmpty() || birthdate == null) {
            mensajeerror.setText ("Todos los campos son obligatorios");
            mensajeerror.setVisible(true);
            return;
        }
        if (!User.checkEmail(email)) {
            errorcor.setText("Correo no válido");
            errorcor.setVisible(true);
            return ;
        }
        if (!User.checkPassword(password)) {
            errorcon.setText("Contraseña no válida");
            errorcon.setVisible(true);
            return ;
        }
        if (birthdate.isAfter(LocalDate.now().minusYears(16))) {
            errorfecha.setText("Debes tener al menos 16 años");
            errorfecha.setVisible(true);
            return ;
        }
        usuario.setEmail(email);
        usuario.setBirthdate(birthdate);
        usuario.setPassword(password);
        usuario.setAvatar(imagen_avatar.getImage());
        IniciarSesionController.miStringProperty.set(password);
        campoNick.getScene().getWindow().hide();
    }

    @FXML
    private void cerrar(ActionEvent event) {
        campoNick.getScene().getWindow().hide();
    }

    @FXML
    private void cambio_avatar(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar avatar");
        fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
    );

    File archivo = fileChooser.showOpenDialog(null);

    if (archivo != null) {
        try {
            avatarSeleccionado = new Image(new FileInputStream(archivo));
            imagen_avatar.setImage(avatarSeleccionado);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    }

    @FXML
    private void contraseña_visible(ActionEvent event) {
        if (visible.isSelected()) {
                    contraseña_visible.setText(campoPas.getText());
                    contraseña_visible.setVisible(true);
                    campoPas.setVisible(false);
                } else {
                    campoPas.setText(contraseña_visible.getText());
                    campoPas.setVisible(true);
                    contraseña_visible.setVisible(false);
                }
        

    
}
    }
