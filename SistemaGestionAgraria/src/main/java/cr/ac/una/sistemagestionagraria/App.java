package cr.ac.una.sistemagestionagraria;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 1024, 748);
        scene.getStylesheets().add(getClass().getResource("estilos.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Sistema de Gestión Agraria - UNA");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
      
    System.out.println("Intentando conectar a la base de datos...");
    try (java.sql.Connection con = config.ConexionBD.conectar()) {
        if (con != null && !con.isClosed()) {
            System.out.println("=========================================");
            System.out.println("¡CONEXIÓN EXITOSA CON LA BASE DE DATOS!");
            System.out.println("El sistema agrario ya puede guardar datos.");
            System.out.println("=========================================");
        }
    } catch (java.sql.SQLException e) {
        System.out.println("=========================================");
        System.out.println("      ERROR DE CONEXIÓN A MYSQL          ");
        System.out.println("Detalle: " + e.getMessage());
        System.out.println("=========================================");
    }
    
        launch();
    }

}