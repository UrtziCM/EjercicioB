/**
 * 
 */
/**
 * 
 */
module EjercicioB {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
	requires javafx.base;
    opens tablaPersona to javafx.graphics, javafx.fxml;
    opens controllers to javafx.fxml, javafx.base;
    opens model to javafx.fxml, javafx.base;
}