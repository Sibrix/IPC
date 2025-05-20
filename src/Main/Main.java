/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author jose
 */
public class Main extends Application {
    private static Scene scene;
    
  public static void setRoot(Parent root) {
        scene.setRoot(root);
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new  FXMLLoader(getClass().getResource("/CartaNautica/FXMLDocument.fxml"));

        Parent  root = loader.load();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Bienvenido a CashControl");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
