/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Main;

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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    private Image avatarSeleccionado = null;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        User usuario = IniciarSesionController.user;
        campoNick.setText(usuario.getNickName());
        campocorreo.setText(usuario.getEmail());
        campoPas.setText(usuario.getPassword());
        campoDate.setValue(usuario.getBirthdate());
    }    

    @FXML
    private void info_us(ActionEvent event) {
    }

    @FXML
    private void info_cor(ActionEvent event) {
    }

    @FXML
    private void info_con(ActionEvent event) {
    }

    @FXML
    private void info_date(ActionEvent event) {
    }

    @FXML
    private void aceptarreg(ActionEvent event) throws IOException, NavDAOException {
        String nick = campoNick.getText().trim();
        String email = campocorreo.getText().trim();
        String password = campoPas.getText().trim();
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
        
          User nuevo = Navigation.getInstance().registerUser(nick, email, password, avatarSeleccionado, birthdate);
         if (nuevo != null) {
             mensajeerror.setText("Usuario registrado correctamente");     
             try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("iniciarSesion.fxml"));
                Parent root = loader.load();

                 Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                 stage.setScene(new Scene(root));
                 stage.show();
                    } catch (IOException e) {
                      e.printStackTrace();
                         mensajeerror.setText("No se pudo cargar la pantalla de inicio de sesión.");
                            }
         } else {
             mensajeerror.setText("Error al registrar usuario");
         }
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
            Scene scene = new Scene(root);
            stage.setTitle("REGISTRADO");
            stage.setScene(scene);
            stage.show();
            stage.close();
    }

    @FXML
    private void cerrar(ActionEvent event) {
    }

    @FXML
    private void cambio_avatar(ActionEvent event) {
    }

    @FXML
    private void contraseña_visible(ActionEvent event) {
    }
    
}
