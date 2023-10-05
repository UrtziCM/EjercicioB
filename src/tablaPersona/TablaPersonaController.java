package tablaPersona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import model.Persona;

public class TablaPersonaController {

    @FXML
    private TableColumn<?, ?> nombreColumn;
   
    @FXML
    private TableColumn<?, ?> apellidosColumn;
    
    @FXML
    private TableColumn<?, ?> edadColumn;

    @FXML
    private TextField edadTxtf;

    @FXML
    private TextField apellidosTxtf;

    @FXML
    private TextField nombreTxtf;

    @FXML
    private TableView<Persona> personaTableView;

    @FXML
    private Button agregarBtn;
    
    private ObservableList<Persona> data;
    
    @FXML
    public void initialize() {
    	nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    	apellidosColumn.setCellValueFactory(new PropertyValueFactory<>("apellido"));
    	edadColumn.setCellValueFactory(new PropertyValueFactory<>("edad"));
    	data = FXCollections.observableArrayList();
    	agregarBtn.setOnAction(e -> agregarPersona(e));
    }
    
    @FXML
    void agregarPersona(ActionEvent event) {
    	if (!queFalta().isEmpty()) {
    		Alert alerta = new Alert(AlertType.ERROR);
    		alerta.setTitle("Campos faltantes");
    		alerta.setHeaderText(null);
    		alerta.setContentText(queFalta());
    		alerta.showAndWait();
    		return;
    	}
    	Persona pers = null;
    	try {			
    		pers = new Persona(nombreTxtf.getText(),apellidosTxtf.getText(),Integer.parseInt(edadTxtf.getText()));
		} catch (NumberFormatException e) {
			Alert avisoedad = new Alert(AlertType.INFORMATION);
			avisoedad.setTitle("Numero es NAN");
			avisoedad.setHeaderText(null);
			avisoedad.setContentText("La edad debe ser un número");
			avisoedad.showAndWait();
			return;
		}
		if (data.contains(pers)) {
			Alert entradaRepetida = new Alert(AlertType.INFORMATION);
			entradaRepetida.setTitle("Entrada duplicada");
			entradaRepetida.setHeaderText(null);
			entradaRepetida.setContentText("Ya se ha guardado a esa persona");
			entradaRepetida.showAndWait();
			return;
		}
		data.add(pers);
		personaTableView.setItems(data);
		Alert anadidaPersona = new Alert(AlertType.INFORMATION);
		anadidaPersona.setTitle("Entrada añadida");
		anadidaPersona.setHeaderText(null);
		anadidaPersona.setContentText("Se ha agregado una nueva entrada");
		anadidaPersona.showAndWait();
    }
    
    private String queFalta() {
    	String faltantes="";
    	if (nombreTxtf.getText().isEmpty()) 
    		faltantes += "El campo nombre es obligatorio\n";
    	if (apellidosTxtf.getText().isEmpty())
    		faltantes += "El campo apellidos es obligatorio\n";
    	if (edadTxtf.getText().isEmpty())
			faltantes += "El campo edad es obligatorio\n";
		return faltantes;
    }
    

}
