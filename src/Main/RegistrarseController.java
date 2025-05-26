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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.NavDAOException;
import model.Navigation;
import model.User;
import static modelos.alerta.mostrarAlerta;

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
    @FXML
    private TextField campoNick;
    @FXML
    private PasswordField campoPas;
    @FXML
    private DatePicker campoDate;
    @FXML
    private Label mensajeerror;
    @FXML
    private ImageView imagen_avatar;
    private Image avatarSeleccionado;
    @FXML
    private Button avatar;
    @FXML
    private Button registrarse;
    @FXML
    private Button infous;
    @FXML
    private Button infous1;
    @FXML
    private Button infous2;
    @FXML
    private Button infous3;
    
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
    private void aceptarreg(ActionEvent event) throws NavDAOException, IOException {
        String nick = campoNick.getText().trim();
        String email = campocorreo.getText().trim();
        String password = campoPas.getText().trim();
        LocalDate birthdate = campoDate.getValue();
        
        if (nick.isEmpty() || email.isEmpty() || password.isEmpty() || birthdate == null) {
            mensajeerror.setText ("Todos los campos son obligatorios");
            mensajeerror.setVisible(true);
            return;
        }
        if (!User.checkNickName(nick)) {
            errorus.setText("Este usuario ya existe");
            errorus.setVisible(true);
            return ;
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
    private void cambio_avatar(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Seleccionar avatar");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
    );

    File archivo = fileChooser.showOpenDialog(null);

    if (archivo != null) {
        try {
            String url =archivo.getPath();
            avatarSeleccionado = new Image(new FileInputStream(url));
            System.out.println(url);
            imagen_avatar.imageProperty().setValue(avatarSeleccionado);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
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
    alerta.setTitle("Información sobre el nickname");
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
    
}