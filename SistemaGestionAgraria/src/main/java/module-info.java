module cr.ac.una.sistemagestionagraria {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    
    opens model to javafx.base, javafx.fxml;
    opens data to javafx.base, javafx.fxml;
    opens cr.ac.una.sistemagestionagraria to javafx.fxml;
    exports cr.ac.una.sistemagestionagraria;
}
