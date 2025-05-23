/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import static java.lang.Math.E;
import static java.lang.StrictMath.E;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import model.Answer;
import model.Problem;

/**
 *
 * @author idair
 */
public class listaproblemasController {

    @FXML
    private ChoiceBox<Answer> answerschoicebox;
    @FXML
    private Button submitbutton;
    
    private Problem problema;
    
    private final String STYLE_NORMAL_CHOICEBOX = "-fx-control-inner-background: white; -fx-text-fill: black;"; 
    private final String STYLE_CORRECT_CHOICEBOX = "-fx-control-inner-background: #c8e6c9; -fx-text-fill: green; -fx-font-weight: bold;"; 
    private final String STYLE_INCORRECT_CHOICEBOX = "-fx-control-inner-background: #ffcdd2; -fx-text-fill: red; -fx-font-weight: bold;";
    @FXML
    private TextArea problemtext;
    
    public void initialize(){
        answerschoicebox.setStyle(STYLE_NORMAL_CHOICEBOX);
        
    }
    
     private void resetChoiceBoxStyle() {
        answerschoicebox.setStyle(STYLE_NORMAL_CHOICEBOX);
    }
     
     public void setProblem(Problem problem) {
        this.problema = problem;
        resetChoiceBoxStyle();

        if (problem == null) {
            problemtext.setText("No se pudo cargar el problema."); 
            answerschoicebox.setVisible(false);
            submitbutton.setDisable(true);
            return;
        }   
        
        problemtext.setText(problem.getText()); 
        answerschoicebox.setVisible(true);
        submitbutton.setDisable(false);

        List<Answer> answers = problem.getAnswers();
        if (answers != null && !answers.isEmpty()) {
            ObservableList<Answer> observableAnswer = FXCollections.observableArrayList(answers);
            answerschoicebox.setItems(observableAnswer);
            answerschoicebox.getSelectionModel().clearSelection(); }
        else {   
            problemtext.setText("Error: El problema no tiene respuestas.");
            if (answerschoicebox.getItems() != null) {
                answerschoicebox.getItems().clear();
            }
            answerschoicebox.setDisable(true);
            submitbutton.setDisable(true);
        }
    }
     
      @FXML
    void handleSubmitAnswer(ActionEvent event) {
        Answer selectedAnswer = answerschoicebox.getSelectionModel().getSelectedItem();

        if (selectedAnswer == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atención");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecciona una respuesta.");
            alert.showAndWait();
            return;
        }

        if (selectedAnswer.getValidity()) {
            answerschoicebox.setStyle(STYLE_CORRECT_CHOICEBOX);
        } else {
            answerschoicebox.setStyle(STYLE_INCORRECT_CHOICEBOX);
        }

        submitbutton.setDisable(true);
        answerschoicebox.setDisable(true); 
    }
    
  
}
        
