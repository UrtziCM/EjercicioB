package tablaPersona;
import java.util.Stack;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Persona;

public class TablaPersonaController {

    @FXML
    private TableColumn<Persona, String> nombreColumn;
   
    @FXML
    private TableColumn<Persona, String> apellidosColumn;
    
    @FXML
    private TableColumn<Persona, Integer> edadColumn;

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
    
    @FXML
    private Button deleteButton;
    
    @FXML
    private Button modifyButton;

    
    private ObservableList<Persona> data;
    
    @FXML
    public void initialize() {
    	nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    	apellidosColumn.setCellValueFactory(new PropertyValueFactory<>("apellido"));
    	edadColumn.setCellValueFactory(new PropertyValueFactory<>("edad"));
    	data = FXCollections.observableArrayList();
    	agregarBtn.setOnAction(e -> agregarPersona(e));
    	deleteButton.setOnAction(e -> borrarPersona(e));
    	personaTableView.setOnMouseClicked(e -> {
    		if (personaTableView.getSelectionModel().getSelectedItem() == null)
    			return;
    		Persona thisPerson = personaTableView.getSelectionModel().getSelectedItem();
    		nombreTxtf.setText(thisPerson.getNombre());
    		apellidosTxtf.setText(thisPerson.getApellido());
    		edadTxtf.setText(thisPerson.getEdad()+"");
    	});
    }
    
    @FXML
    void agregarPersona(ActionEvent event) {
    	if (!queFalta().isEmpty()) {
    		mostrarVentanaEmergente("Campos faltantes",queFalta(),AlertType.ERROR);
    		return;
    	}
    	Persona pers = null;
    	try {			
    		pers = new Persona(nombreTxtf.getText(),apellidosTxtf.getText(),Integer.parseInt(edadTxtf.getText()));
		} catch (NumberFormatException e) {
			mostrarVentanaEmergente("Numero es NAN", "La edad debe ser un número", AlertType.INFORMATION);
			return;
		}
		if (data.contains(pers)) {
			mostrarVentanaEmergente("Entrada duplicada", "Ya se ha guardado a esa persona", AlertType.INFORMATION);
			return;
		}
		data.add(pers);
		personaTableView.setItems(data);
		clearTextfields();
		mostrarVentanaEmergente("Agregada nueva entrada", "Se ha añadido una nueva entrada", AlertType.INFORMATION);
    }

	private void clearTextfields() {
		nombreTxtf.setText("");
		apellidosTxtf.setText("");
		edadTxtf.setText("");
	}
    @FXML
    void borrarPersona(ActionEvent event) {
    	if (personaTableView.getSelectionModel().getSelectedItem() != null) {
    		data.remove(personaTableView.getSelectionModel().getSelectedItem());
    		clearTextfields();
    		mostrarVentanaEmergente("Borrada entrada", "Se ha borrado la entrada elegida", AlertType.INFORMATION);    		
    	}

    }
    

    @FXML
    void modificarPersona(ActionEvent event) {
    	if (personaTableView.getSelectionModel().getSelectedItem() != null) {
    		if (comprobarModificacion(personaTableView.getSelectionModel().getSelectedIndex())) {    			
    			mostrarVentanaEmergente("Modificada una entrada", "Se ha modificado una entrada con éxito", AlertType.INFORMATION);
    			clearTextfields();
    		}
    	}
    }
    
    private boolean comprobarModificacion(int index) {
		try {				
			data.set(index, new Persona(nombreTxtf.getText(),apellidosTxtf.getText(),Integer.parseInt(edadTxtf.getText())));
			personaTableView.setItems(data);
		} catch (NumberFormatException numberFormat) {
			mostrarVentanaEmergente("Edad no es numero", "La edad debe ser un numero", AlertType.ERROR);
			return false;
		}
		return true;
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
    
    private static void mostrarVentanaEmergente(String titulo,String content, AlertType tipo) {
    	Alert anadidaPersona = new Alert(tipo);
		anadidaPersona.setTitle(titulo);
		anadidaPersona.setHeaderText(null);
		anadidaPersona.setContentText(content);
		anadidaPersona.showAndWait();
    }
    

}
