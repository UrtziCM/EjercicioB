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
		mostrarVentanaEmergente("Agregada nueva entrada", "Se ha añadido una nueva entrada", AlertType.INFORMATION);
    }
    @FXML
    void borrarPersona(ActionEvent event) {
    	if (personaTableView.getSelectionModel().getSelectedItem() != null) {
    		data.remove(personaTableView.getSelectionModel().getSelectedItem());
    		mostrarVentanaEmergente("Borrada entrada", "Se ha borrado la entrada elegida", AlertType.INFORMATION);    		
    	}

    }

    @FXML
    void modificarPersona(ActionEvent event) {
    	if (personaTableView.getSelectionModel().getSelectedItem() != null)
    		ventanaModificar(personaTableView.getSelectionModel().getSelectedIndex());
    }
    
    private void ventanaModificar(int index) {
    	
		GridPane mainFrame = new GridPane();
		Text nombre = new Text("Nombre: ");
		mainFrame.add(nombre, 0, 0);
		TextField modifNombreTxtf = new TextField(nombreTxtf.getText());
		mainFrame.add(modifNombreTxtf, 1, 0);
		
		Text apellido = new Text("Apellido: ");
		mainFrame.add(apellido, 0, 1);
		TextField modifApellidoTxtf = new TextField(apellidosTxtf.getText());
		mainFrame.add(modifApellidoTxtf, 1, 1);
		
		Text edad = new Text("Edad: ");
		mainFrame.add(edad, 0, 2);
		TextField modifEdadTxtf = new TextField(edadTxtf.getText());
		mainFrame.add(modifEdadTxtf, 1, 2);
		
		Button botonAceptar = new Button("Aceptar");
		mainFrame.add(botonAceptar, 0, 3);
		GridPane.setColumnSpan(botonAceptar, GridPane.REMAINING);
		
		
		Stage modifyStage = new Stage();
		botonAceptar.setOnAction(e -> {
			try {				
				data.set(index, new Persona(modifNombreTxtf.getText(),modifApellidoTxtf.getText(),Integer.parseInt(modifEdadTxtf.getText())));
				personaTableView.setItems(data);
				modifyStage.close();
			} catch (NumberFormatException numberFormat) {
				mostrarVentanaEmergente("Edad no es numero", "La edad debe ser un numero", AlertType.ERROR);
				return;
			}
		});
		
		modifyStage.setScene(new Scene(mainFrame));
    	modifyStage.setTitle("Modificar datos");
		modifyStage.initModality(Modality.APPLICATION_MODAL);
		modifyStage.showAndWait();
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
