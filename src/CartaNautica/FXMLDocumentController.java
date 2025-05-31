/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CartaNautica;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import CartaNautica.Poi;
import Main.IniciarSesionController;
import Main.Main;
import Main.listaproblemasController;
import java.awt.Color;
import static java.awt.Color.GREEN;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import model.NavDAOException;
import model.Navigation;
import model.Problem;
import model.User;

/**
 *
 * @author jsoler
 */
public class FXMLDocumentController implements Initializable {
    private static Scene scene;

    //=======================================
    // hashmap para guardar los puntos de interes POI
    private final HashMap<String, Poi> hm = new HashMap<>();
    private ObservableList<Poi> data;
    // ======================================
    // la variable zoomGroup se utiliza para dar soporte al zoom
    // el escalado se realiza sobre este nodo, al escalar el Group no mueve sus nodos
    private Group zoomGroup;
    @FXML
    private ScrollPane map_scrollpane;
    @FXML
    private Slider zoom_slider;
    @FXML
    private SplitPane splitPane;
    @FXML
    private Label mousePosition;
    @FXML
    private MenuButton usuarioMenuButton;
    @FXML
    private MenuItem menuListaProblemas;
    @FXML
    private MenuItem menuProblemaAleatorio;
    
    private List<Problem> problemas;
    @FXML
    private Button iniciarSesion;
    
    private User usuarioactivo;
    @FXML
    private MenuButton seleccionar_problema;
    @FXML
    private Button mostrar_resultados;
    @FXML
    private ImageView foto_usuario;
    @FXML
    private Pane drawingPane;
    
    private String activeTool = "";
    @FXML
    private ToggleButton btnLinea;
    @FXML
    private ToggleButton btnArc;
    @FXML
    private ToggleButton btnText;
    @FXML
    private ToggleButton btnPunt;
    @FXML
    private ToggleButton btnGoma;
    private Point2D centroCompas = null;
    private Arc arcoTemporal = null;
    private boolean compasActivo = false;
    private Point2D inicioLinea = null;
    private Line lineaTemporal = null;
    private Line lineaCompas = null;
    private ImageView reglaView = null;
    private ImageView transportadorView = null;
    @FXML
    private ColorPicker colorpunto;
    @FXML
    private ToggleButton btnRegla;
    @FXML
    private ToggleButton btnTransportador;
    
    @FXML
    void zoomIn(ActionEvent event) {
        //================================================
        // el incremento del zoom dependerá de los parametros del 
        // slider y del resultado esperado
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal += 0.1);
    }

    @FXML
    void zoomOut(ActionEvent event) {
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal + -0.1);
    }
    
    // esta funcion es invocada al cambiar el value del slider zoom_slider
    private void zoom(double scaleValue) {
        //===================================================
        //guardamos los valores del scroll antes del escalado
        double scrollH = map_scrollpane.getHvalue();
        double scrollV = map_scrollpane.getVvalue();
        //===================================================
        // escalamos el zoomGroup en X e Y con el valor de entrada
        zoomGroup.setScaleX(scaleValue);
        zoomGroup.setScaleY(scaleValue);
        //===================================================
        // recuperamos el valor del scroll antes del escalado
        map_scrollpane.setHvalue(scrollH);
        map_scrollpane.setVvalue(scrollV);
    }

   /* void listClicked(MouseEvent event) {
        Poi itemSelected = map_listview.getSelectionModel().getSelectedItem();

        // Animación del scroll hasta la mousePosistion del item seleccionado
        double mapWidth = zoomGroup.getBoundsInLocal().getWidth();
        double mapHeight = zoomGroup.getBoundsInLocal().getHeight();
        double scrollH = itemSelected.getPosition().getX() / mapWidth;
        double scrollV = itemSelected.getPosition().getY() / mapHeight;
        final Timeline timeline = new Timeline();
        final KeyValue kv1 = new KeyValue(map_scrollpane.hvalueProperty(), scrollH);
        final KeyValue kv2 = new KeyValue(map_scrollpane.vvalueProperty(), scrollV);
        final KeyFrame kf = new KeyFrame(Duration.millis(500), kv1, kv2);
        timeline.getKeyFrames().add(kf);
        timeline.play();

        // movemos el objto map_pin hasta la mousePosistion del POI
//        double pinW = map_pin.getBoundsInLocal().getWidth();
//        double pinH = map_pin.getBoundsInLocal().getHeight();
        map_pin.setLayoutX(itemSelected.getPosition().getX());
        map_pin.setLayoutY(itemSelected.getPosition().getY());
        pin_info.setText(itemSelected.getDescription());
        map_pin.setVisible(true);
    }
*/
    /*
    private void initData() {
        data=map_listview.getItems();
        data.add(new Poi("1F", "Edificion del DSIC", 275, 250));
        data.add( new Poi("Agora", "Agora", 575, 350));
        data.add( new Poi("Pista", "Pista de atletismo y campo de futbol", 950, 350));
    }
*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        drawingPane.setMouseTransparent(false);
        drawingPane.setPickOnBounds(true);
        
        // TODO
        //==========================================================
        // inicializamos el slider y enlazamos con el zoom
        zoom_slider.setMin(0.5);
        zoom_slider.setMax(1.5);
        zoom_slider.setValue(1.0);
        zoom_slider.valueProperty().addListener((o, oldVal, newVal) -> zoom((Double) newVal));

        //=========================================================================
        //Envuelva el contenido de scrollpane en un grupo para que 
        //ScrollPane vuelva a calcular las barras de desplazamiento tras el escalado
        Group contentGroup = new Group();
        zoomGroup = new Group();
        contentGroup.getChildren().add(zoomGroup);
        zoomGroup.getChildren().add(map_scrollpane.getContent());
        map_scrollpane.setContent(contentGroup);
        
        
        iniciarSesion.setVisible(IniciarSesionController.miStringProperty.get().isEmpty());
        usuarioMenuButton.setDisable(true);
        seleccionar_problema.setDisable(true);
        mostrar_resultados.setDisable(true);
       IniciarSesionController.miStringProperty.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                iniciarSesion.setVisible(newValue == null || newValue.isEmpty());
                usuarioMenuButton.disableProperty().bind(iniciarSesion.visibleProperty());
                seleccionar_problema.disableProperty().bind(iniciarSesion.visibleProperty());
                mostrar_resultados.disableProperty().bind(iniciarSesion.visibleProperty());
                usuarioMenuButton.setText(IniciarSesionController.user.getNickName());
                foto_usuario.setImage(IniciarSesionController.user.getAvatar());
            }
        });
       
       
       
       drawingPane.setOnMouseClicked(event -> {
           switch(activeTool) {
               case "Punt" :
                   marcarPunt(event.getX(), event.getY());
                   break;
               case "Text":
                    escriureText(event.getX(), event.getY());
                    break;
               case "Goma":
                   borrarObjecte(event.getX(), event.getY());
                   break;
            
           }
       });
       
       
       
       
       
    drawingPane.setOnMousePressed(event -> {
    if ("Arc".equals(activeTool)) {
       centroCompas = new Point2D(event.getX(), event.getY());

    arcoTemporal = new Arc();
    arcoTemporal.setCenterX(centroCompas.getX());
    arcoTemporal.setCenterY(centroCompas.getY());
    arcoTemporal.setRadiusX(0);
    arcoTemporal.setRadiusY(0);
    arcoTemporal.setStartAngle(0);
    arcoTemporal.setLength(180);
    arcoTemporal.setType(ArcType.OPEN);
    arcoTemporal.setStroke(colorpunto.getValue());
    arcoTemporal.setStrokeWidth(2);
    arcoTemporal.setFill(null);

    drawingPane.getChildren().add(arcoTemporal);

    // Punto en el centro
    Circle puntoCentro = new Circle(centroCompas.getX(), centroCompas.getY(), 5, colorpunto.getValue());
    drawingPane.getChildren().add(puntoCentro);
    }else if ("Linea".equals(activeTool)){
        inicioLinea = new Point2D(event.getX(), event.getY());
        lineaTemporal = new Line();
        lineaTemporal.setStartX(inicioLinea.getX());
        lineaTemporal.setStartY(inicioLinea.getY());
        lineaTemporal.setEndX(inicioLinea.getX());
        lineaTemporal.setEndY(inicioLinea.getY());
        lineaTemporal.setStroke(colorpunto.getValue());
        lineaTemporal.setStrokeWidth(2);
        drawingPane.getChildren().add(lineaTemporal);
         Circle puntoInicial = new Circle(inicioLinea.getX(), inicioLinea.getY(), 5, colorpunto.getValue());
         drawingPane.getChildren().add(puntoInicial);
    
    }
});

 
       
       
       
       drawingPane.setOnMouseDragged(event -> {
        System.out.println("Dragged con activeTool = " + activeTool);
    if ("Arc".equals(activeTool) && arcoTemporal != null && centroCompas != null) {
         double dx = event.getX() - centroCompas.getX();
    double dy = event.getY() - centroCompas.getY();

    double radio = Math.sqrt(dx * dx + dy * dy);
    double angle = Math.toDegrees(Math.atan2(dy, dx));
    if (angle < 0) angle += 360;

    arcoTemporal.setCenterX(centroCompas.getX());
    arcoTemporal.setCenterY(centroCompas.getY());
    arcoTemporal.setRadiusX(radio);
    arcoTemporal.setRadiusY(radio);
    arcoTemporal.setStartAngle(-angle - 90); // Centrado respecto al ratón
    arcoTemporal.setLength(180);

    // Línea guía
    lineaCompas.setStartX(centroCompas.getX());
    lineaCompas.setStartY(centroCompas.getY());
    lineaCompas.setEndX(event.getX());
    lineaCompas.setEndY(event.getY());
    
    }else if ("Linea".equals(activeTool) && lineaTemporal != null) {
        lineaTemporal.setEndX(event.getX());
        lineaTemporal.setEndY(event.getY());
    }
    });

    
       
       
       
       
       
       
       
       drawingPane.setOnMouseReleased(event -> {
        if ("Arc".equals(activeTool)) {
    // Limpiar temporal
         Circle puntoCentro = new Circle(event.getX(), event.getY(), 5, colorpunto.getValue());
        drawingPane.getChildren().add(puntoCentro);
        arcoTemporal = null;
        centroCompas = null;

    if (lineaCompas != null) {
        drawingPane.getChildren().remove(lineaCompas);
        lineaCompas = null;
    }
        }else if ("Linea".equals(activeTool)) {
            if (lineaTemporal != null) {
            // Al finalizar, también añadimos un punto donde termina la línea
           Circle c = new Circle(lineaTemporal.getEndX(), lineaTemporal.getEndY(), 5, colorpunto.getValue());
            drawingPane.getChildren().add(c);
        }
        lineaTemporal = null;
        inicioLinea = null;
        }
        });
       
       
      
    }
    private void marcarPunt(double x, double y){
         
        Circle c = new Circle(x, y, 5, new javafx.scene.paint.Color(
        colorpunto.getValue().getRed(),
        colorpunto.getValue().getGreen(),
        colorpunto.getValue().getBlue(),
        1.0));
    drawingPane.getChildren().add(c);
    }
     private Point2D primerPunt = null;
     
    
    private void escriureText(double x, double y) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Anotar text");
        dialog.setHeaderText("Escriu el text:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(text -> {
            Text t = new Text(x, y, text);
            t.setFill(javafx.scene.paint.Color.BLACK);
            t.setStyle("-fx-font-size: 14px;");
            drawingPane.getChildren().add(t);
        });
    }
    
      

    @FXML
    private void showPosition(MouseEvent event) {
        mousePosition.setText("sceneX: " + (int) event.getSceneX() + ", sceneY: " + (int) event.getSceneY() + "\n"
                + "         X: " + (int) event.getX() + ",          Y: " + (int) event.getY());
    }

    private void closeApp(ActionEvent event) {
        ((Stage) zoom_slider.getScene().getWindow()).close();
    }

    private void about(ActionEvent event) {
        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        // Acceder al Stage del Dialog y cambiar el icono
        Stage dialogStage = (Stage) mensaje.getDialogPane().getScene().getWindow();
        dialogStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
        mensaje.setTitle("Acerca de");
        mensaje.setHeaderText("IPC - 2025");
        mensaje.showAndWait();
    }

    @FXML
    private void addPoi(MouseEvent event) {

        if (event.isControlDown()) {
            Dialog<Poi> poiDialog = new Dialog<>();
            poiDialog.setTitle("Nuevo POI");
            poiDialog.setHeaderText("Introduce un nuevo POI");
            // Acceder al Stage del Dialog y cambiar el icono
            Stage dialogStage = (Stage) poiDialog.getDialogPane().getScene().getWindow();
            dialogStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));

            ButtonType okButton = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
            poiDialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

            TextField nameField = new TextField();
            nameField.setPromptText("Nombre del POI");

            TextArea descArea = new TextArea();
            descArea.setPromptText("Descripción...");
            descArea.setWrapText(true);
            descArea.setPrefRowCount(5);

            VBox vbox = new VBox(10, new Label("Nombre:"), nameField, new Label("Descripción:"), descArea);
            poiDialog.getDialogPane().setContent(vbox);

            poiDialog.setResultConverter(dialogButton -> {
                if (dialogButton == okButton) {
                    return new Poi(nameField.getText().trim(), descArea.getText().trim(), 0, 0);
                }
                return null;
            });
            Optional<Poi> result = poiDialog.showAndWait();

            if(result.isPresent()) {
                Point2D localPoint = zoomGroup.sceneToLocal(event.getSceneX(), event.getSceneY());
                Poi poi=result.get();
                poi.setPosition(localPoint);
               // map_listview.getItems().add(poi);
            }
        }
    }

    @FXML
    private void irmod(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/Main/modificar_perfil.fxml"));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
        Scene scene = new Scene(root);
        stage.setTitle("INICIO DE SESION");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void cerses(ActionEvent event) throws IOException {
       IniciarSesionController.user = null;
       IniciarSesionController.miStringProperty =new SimpleStringProperty(""); 
       Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
        Scene scene = new Scene(root);
        stage.setTitle("Carta Nautica");
        stage.setScene(scene);
        stage.show();
        mostrar_resultados.getScene().getWindow().hide();
        
       
    }

    @FXML
    private void mostrarListaProblemas(ActionEvent event) throws IOException, NavDAOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/listaproblemas.fxml"));
        Parent root = loader.load();
        listaproblemasController controller = loader.getController();
        List<Problem> problemas = Navigation.getInstance().getProblems();
        controller.setProblemas(problemas, false); 
        Scene scene = new Scene(root); 
        Stage stage = new Stage();
        stage.setTitle("lista problemas");
        stage.setScene(scene);
        
        stage.setOnCloseRequest(e -> {
            e.consume(); 
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("¿Seguro que quieres cerrar?");
            alert.setContentText("Si no pulsas 'Finalizar sesión', los resultados no se guardarán.");

            ButtonType finalizar = new ButtonType("Finalizar sesión");
            ButtonType cerrar = new ButtonType("Cerrar sin guardar");
            ButtonType cancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(finalizar, cerrar, cancelar);
                
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.isPresent()) {
                if (result.get() == finalizar) {
                    controller.finalizar_sesion(null); 
                    stage.close();
                } else if (result.get() == cerrar) {
                           stage.close(); 
                }
            }
        });
        
        stage.show();
       
    }

    @FXML
    private void mostrarProblemaAleatorio(ActionEvent event) throws IOException, NavDAOException {
       
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/listaproblemas.fxml"));   
        Parent root = loader.load();
        listaproblemasController controller = loader.getController();
        List<Problem> problemas = Navigation.getInstance().getProblems();
        Problem aleatorio = problemas.get(new Random().nextInt(problemas.size()));
        controller.setProblemas(List.of(aleatorio), true); 
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("PROBLEMA ALEATORIO");
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> {
            e.consume(); 
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("¿Seguro que quieres cerrar?");
            alert.setContentText("Si no pulsas 'Finalizar sesión', los resultados no se guardarán.");

            ButtonType finalizar = new ButtonType("Finalizar sesión");
            ButtonType cerrar = new ButtonType("Cerrar sin guardar");
            ButtonType cancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(finalizar, cerrar, cancelar);
                
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.isPresent()) {
                if (result.get() == finalizar) {
                    controller.finalizar_sesion(null); 
                    stage.close();
                } else if (result.get() == cerrar) {
                           stage.close(); 
                }
            }
        });
        stage.show();
        //stage.close(); 
        } 

    @FXML
    private void iniciar_sesion(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/Main/iniciarSesion.fxml"));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("INICIO DE SESION");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void mostrarres(ActionEvent event) throws IOException {       
       Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/Main/resultados.fxml"));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/logo.png")));
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("VER RESULTADOS");
        stage.setScene(scene);
        stage.show(); 

    }
    

    @FXML
    private void handleLinea(ActionEvent event) {
        activeTool = "Linea";
        map_scrollpane.setPannable(false);
        actualizarVisibilidadColorPicker();
        colorpunto.setVisible(true);
    }

    @FXML
    private void handleArc(ActionEvent event) {
        activeTool = "Arc";
        map_scrollpane.setPannable(false);
        actualizarVisibilidadColorPicker();
        colorpunto.setVisible(true);
    }

    @FXML
    private void handleText(ActionEvent event) {
        activeTool = "Text";
        map_scrollpane.setPannable(true);
        actualizarVisibilidadColorPicker();
        colorpunto.setVisible(false);
    }

    @FXML
    private void handlePunto(ActionEvent event) {
         activeTool = "Punt";
         map_scrollpane.setPannable(true);
         actualizarVisibilidadColorPicker();
         colorpunto.setVisible(true);
    }
    
     @FXML
    private void handleGoma(ActionEvent event) {
        activeTool = "Goma";
        map_scrollpane.setPannable(true);
        colorpunto.setVisible(false);
        
        
    }
    
    private void borrarObjecte(double x, double y) {
    Node objecteABorrar = null;

    for (Node node : drawingPane.getChildren()) {
        if (node instanceof Line) {
            Line line = (Line) node;
            if (estaSobreLinea(line, x, y)) {
                objecteABorrar = line;
                break;
            }
        } else if (node instanceof Arc) {
            Arc arc = (Arc) node;
            if (arc.getBoundsInParent().contains(x, y)) {
                objecteABorrar = arc;
                break;
            }
        } else if (node instanceof Text) {
            Text text = (Text) node;
            if (text.getBoundsInParent().contains(x, y)) {
                objecteABorrar = text;
                break;
            }
        }else if (node instanceof Circle) {
            Circle circle = (Circle) node;

            // Comprobar si el punto (x, y) está dentro del círculo (con margen)
            Bounds bounds = circle.getBoundsInParent();
            if (bounds.contains(x, y)) {
                objecteABorrar = circle;
                break;
            }
            
        }
        // Si tienes Arc u otras figuras, puedes añadir más casos aquí.
    }

    if (objecteABorrar != null) {
        drawingPane.getChildren().remove(objecteABorrar);
    }
}
    private boolean estaSobreLinea(Line line, double x, double y) {
    double tolerance = 5.0; // rango de clic cercano a la línea

    // Obtener puntos de la línea
    double x1 = line.getStartX();
    double y1 = line.getStartY();
    double x2 = line.getEndX();
    double y2 = line.getEndY();

    // Distancia del punto al segmento de línea
    double distance = distanciaPuntALinea(x, y, x1, y1, x2, y2);
    return distance <= tolerance;
}

private double distanciaPuntALinea(double px, double py, double x1, double y1, double x2, double y2) {
    double dx = x2 - x1;
    double dy = y2 - y1;

    if (dx == 0 && dy == 0) {
        // La línea es un punto
        dx = px - x1;
        dy = py - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }

    double t = ((px - x1) * dx + (py - y1) * dy) / (dx * dx + dy * dy);

    t = Math.max(0, Math.min(1, t));

    double projX = x1 + t * dx;
    double projY = y1 + t * dy;

    dx = px - projX;
    dy = py - projY;

    return Math.sqrt(dx * dx + dy * dy);
}

    @FXML
    private void borrarTodo(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, "Quieres borrarlo todo?", ButtonType.YES, ButtonType.NO);
        alerta.setTitle("Confirmació");
        Optional<ButtonType> resultat = alerta.showAndWait();

        if (resultat.isPresent() && resultat.get() == ButtonType.YES) {
            drawingPane.getChildren().removeIf(node -> !(node instanceof ImageView));
            primerPunt = null;
        }

    }
    
   private ImageView reglaImageView;
    @FXML
    private void handleRegla(ActionEvent event) {
     if (btnRegla.isSelected()) {
        if (reglaImageView == null) {
            reglaImageView = crearHerramientaVisual("regla.png", 300, 300);
            drawingPane.getChildren().add(reglaImageView);
        }
    } else {
        if (reglaImageView != null) {
            drawingPane.getChildren().remove(reglaImageView);
            reglaImageView = null;
        }
    }
}
private ImageView transportadorImageView;
    @FXML
    private void handleTransportador(ActionEvent event) {
       if (btnTransportador.isSelected()) {
        if (transportadorImageView == null) {
            transportadorImageView = crearHerramientaVisual("transportador.png", 200, 200);
            drawingPane.getChildren().add(transportadorImageView);
        }
    } else {
        if (transportadorImageView != null) {
            drawingPane.getChildren().remove(transportadorImageView);
            transportadorImageView = null;
        }
    }
    }
    
    
   private ImageView crearHerramientaVisual(String nombreImagen, double x, double y) {
    Image img = new Image(getClass().getResourceAsStream("/resources/" + nombreImagen));
    ImageView herramienta = new ImageView(img);
    if (nombreImagen.equals("transportador.png")){
    herramienta.setFitWidth(300);
    }else{
     herramienta.setFitWidth(600);
    }
    herramienta.setPreserveRatio(true);
    herramienta.setLayoutX(x);
    herramienta.setLayoutY(y);

    final Delta dragDelta = new Delta();
    herramienta.setOnMousePressed(event -> {
        dragDelta.x = event.getX();
        dragDelta.y = event.getY();
        herramienta.toFront();
        map_scrollpane.setPannable(false);
    });

    herramienta.setOnMouseDragged(event -> {
        herramienta.setLayoutX(event.getSceneX() - dragDelta.x);
        herramienta.setLayoutY(event.getSceneY() - dragDelta.y);
    });

    herramienta.setOnMouseReleased(event -> {
        map_scrollpane.setPannable(true);
    });

    herramienta.setOnScroll(event -> {
        if (event.isShiftDown()) {
            herramienta.setRotate(herramienta.getRotate() + event.getDeltaY() / 10);
        }
    });

    return herramienta;
}

    private static class Delta {
    double x, y;
}
    private void actualizarVisibilidadColorPicker() {
    boolean visible = btnPunt.isSelected() || btnLinea.isSelected() || btnArc.isSelected();
    colorpunto.setDisable(!visible);
}
}

                
    



