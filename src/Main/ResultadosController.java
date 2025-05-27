/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Main;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Session;
import model.User;

/**
 * FXML Controller class
 *
 * @author idair
 */
public class ResultadosController implements Initializable {

    @FXML
    private DatePicker datePicker;
    @FXML
    private TableView<Session> resultadosTable;
    @FXML
    private TableColumn<Session, LocalDate> fechaColumn;
    @FXML
    private TableColumn<Session, Integer> aciertosColumn;
    @FXML
    private TableColumn<Session, Integer> erroresColumn;
    
    private ObservableList<Session> sesiones;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("timeStamp"));
        aciertosColumn.setCellValueFactory(new PropertyValueFactory<>("hits"));
        erroresColumn.setCellValueFactory(new PropertyValueFactory<>("faults"));
        
        User user = IniciarSesionController.user;
        sesiones = FXCollections.observableArrayList(user.getSessions());

        resultadosTable.setItems(sesiones);
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> filtrarSesionesPorFecha(newValue));



    }   
    
    private void filtrarSesionesPorFecha(LocalDate fecha) {
        List<Session> sesionesFiltradas = sesiones.stream()
                .filter(session -> !session.getTimeStamp().toLocalDate().isBefore(fecha))
                .collect(Collectors.toList());
        resultadosTable.setItems(FXCollections.observableArrayList(sesionesFiltradas));
    }

    
}
