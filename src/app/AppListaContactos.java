package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AppListaContactos extends Application {
	
	private final FileChooser explorador = new FileChooser();
	private String rutaFoto;
	private String nombreFoto;
	private ObservableList<String> listaTelefonos;
	private ObservableList<Contacto> listaContactos;
	private ListView<String> lsvTelefonos;
	private ListView<Contacto> lsvContactos;
	private TextField txfNombres,txfApellidos,txfDireccion,txfEmail,txfTelefono;
	private CheckBox chkWhatsapp;
	private boolean validacionesOK;
	private ImageView imagen;
	private File archivoCSV;
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		//Creación del root
		AnchorPane root = new AnchorPane();
		
		//VBox de todo lo perteneciente a la lista de contactos.
		VBox vbxContactos = new VBox(5);
		Label lblContactos = new Label("Listado de Contactos:");
		this.lsvContactos = new ListView<>();
		lsvContactos.setMinSize(250, 270);
		lsvContactos.setPrefSize(250, 270);
		lsvContactos.setMaxSize(400, Double.MAX_VALUE);
		
		VBox.setVgrow(lsvContactos, Priority.ALWAYS);
		//Botones de opciones
		Button btnInfo = new Button("+Info");
		btnInfo.setPrefSize(70, 25);
		Button btnGrabar = new Button("Grabar");
		btnGrabar.setPrefSize(70, 25);
		Button btnEliminar = new Button("Eliminar");
		btnEliminar.setPrefSize(70, 25);
		//HBox Botones de opciones de la lista contactos.
		HBox hbxBtnContactos = new HBox(btnInfo,btnGrabar,btnEliminar);
		hbxBtnContactos.setSpacing(20d);
		//Se agrega al VBox. Se da un tamaño horizontal máximo para conservar estética en la lista.
		vbxContactos.getChildren().addAll(lblContactos,lsvContactos,hbxBtnContactos);
		vbxContactos.setMaxSize(400, Double.MAX_VALUE);
		
		
		//Sección Nombres y Apellidos
		Label lblNombres = new Label("Nombres:");
		this.txfNombres = new TextField();
		Label lblApellidos = new Label("Apellidos:");
		this.txfApellidos = new TextField();
		
		//Sección Dirección e Email y LblTelefono.
		Label lblDireccion = new Label("Dirección:");
		this.txfDireccion = new TextField();
		Label lblEmail = new Label("Email:");
		this. txfEmail = new TextField();
		Label lblTelefono = new Label("Telefonos:");
		
		//Sección Teléfonos		
		//Parte Izquierda
		this.txfTelefono = new TextField();
		this.lsvTelefonos = new ListView<>();
			
		//Parte Derecha
		this.chkWhatsapp = new CheckBox("Whatsapp");
		Button btnAddTelf = new Button("Adicionar");
		btnAddTelf.setMaxSize(Double.MAX_VALUE,25);
		Button btnDelTelf = new Button("Eliminar");
		btnDelTelf.setMaxSize(Double.MAX_VALUE,25);
		VBox vbxBtnTelf = new VBox(btnAddTelf,btnDelTelf);
		vbxBtnTelf.setSpacing(5);
		
		//Sección botones Registrar y Salir
		Button btnRegistrar = new Button("Registrar");
		btnRegistrar.setPrefSize(70, 25);
		Button btnSalir = new Button("Salir");
		btnSalir.setPrefSize(70, 25);
		
		//Creación del formulario
		GridPane grpFormulario = new GridPane();
		
		//Añadir sección de Nombres y Apellidos
		grpFormulario.add(lblNombres, 0, 0);
		grpFormulario.add(this.txfNombres, 0, 1);
		grpFormulario.add(lblApellidos, 0, 2);
		grpFormulario.add(this.txfApellidos, 0, 3);
		//Añadir sección de Dirección e Email y LblTelefono
		grpFormulario.add(lblDireccion, 0, 4);
		grpFormulario.add(this.txfDireccion, 0, 5,2,1);
		grpFormulario.add(lblEmail, 0, 6);
		grpFormulario.add(txfEmail, 0, 7,2,1);
		grpFormulario.add(lblTelefono, 0, 8);
		
		//Añadir sección de Teléfonos
		grpFormulario.add(this.txfTelefono, 0, 9);
		grpFormulario.add(this.lsvTelefonos, 0, 10,1,2);
		
		grpFormulario.add(this.chkWhatsapp, 1, 9);
		grpFormulario.add(vbxBtnTelf, 1, 10,1,2);		
		
		//Añadir botones de Registrar y Salir
		grpFormulario.add(btnRegistrar, 0, 12);
		grpFormulario.add(btnSalir, 1, 12);		
		GridPane.setHalignment(btnSalir, HPos.RIGHT);
		
		//Constraints Formulario		
		ColumnConstraints reglaCol1 = new ColumnConstraints(100,250,1300);
		reglaCol1.setHgrow(Priority.ALWAYS);
		grpFormulario.getColumnConstraints().add(reglaCol1);
		ColumnConstraints reglaCol2 = new ColumnConstraints(50,125,125);
		reglaCol2.setHgrow(Priority.ALWAYS);
		grpFormulario.getColumnConstraints().add(reglaCol2);
		
		RowConstraints reglaRowNormal = new RowConstraints(20,25,35);
		RowConstraints reglaRowTelf = new RowConstraints(25,25,300);
		RowConstraints reglaRowUltima = new RowConstraints(35,35,35);
		reglaRowNormal.setVgrow(Priority.ALWAYS);
		reglaRowTelf.setVgrow(Priority.ALWAYS);
		reglaRowUltima.setVgrow(Priority.ALWAYS);
		grpFormulario.getRowConstraints().addAll(reglaRowNormal,reglaRowNormal,reglaRowNormal,
				reglaRowNormal,reglaRowNormal,reglaRowNormal,reglaRowNormal,reglaRowNormal,
				reglaRowNormal,reglaRowNormal,reglaRowTelf,reglaRowTelf,reglaRowUltima);
		
		//Espaciado del formulario
		grpFormulario.setVgap(9d);
		grpFormulario.setHgap(10d);
		
		//Creación del HBox main que sostiene la lista de contactos 
		//y el formulario
		HBox hbxMain = new HBox(vbxContactos,grpFormulario);
		root.getChildren().addAll(hbxMain);
		hbxMain.setSpacing(30d);
		HBox.setHgrow(grpFormulario, Priority.ALWAYS);
		HBox.setHgrow(vbxContactos, Priority.ALWAYS);
		
		
		//Ubicación del HBox según el AnchorPane
		AnchorPane.setLeftAnchor(hbxMain, 25d);
		AnchorPane.setRightAnchor(hbxMain, 25d);
		AnchorPane.setTopAnchor(hbxMain, 20d);
		AnchorPane.setBottomAnchor(hbxMain, 20d);
		
		//Se carga la imagen por Default	
		//Se guarda el archivo por defecto si no se selecciona imagen.
		this.nombreFoto = "default.png";
		this.rutaFoto = "/fotos/default.png";
		this.imagen = new ImageView(new Image(this.rutaFoto));		
		//Se asignan dimensiones estáticas
		this.imagen.setFitWidth(112);
		this.imagen.setFitHeight(145);
		AnchorPane.setRightAnchor(this.imagen, 30d);	
		AnchorPane.setTopAnchor(this.imagen, 5d);
		root.getChildren().addAll(this.imagen);
		
		
		//Funcionalidad		
		
		//Selección de la fotografía		
		imagen.setOnMouseClicked(event ->{			
			configurarExploradorArchivos();
			try {
				//Se obtiene el archivo seleccionado
				File fotografia = explorador.showOpenDialog(primaryStage);
				//Si el archivo existe, muestrelo en el ImageView
				if(fotografia.exists()) {
					//Se asigna el nombre de la foto ingresada con la variable a guardar en el objeto Contacto				
					this.nombreFoto = fotografia.getName();	
					this.rutaFoto = "/fotos/" + this.nombreFoto;		
					imagen.setImage(new Image(rutaFoto));
				}
			}catch(Exception e) {
				mostrarAlerta(AlertType.INFORMATION, "Selección de imagen", "Se cerró el explorador de archivos", null);
			}
		});
		
		
		//Manejo de la lista teléfonos
		List<String> listaTelefonosAux = new ArrayList<>();
		this.listaTelefonos = FXCollections.observableList(listaTelefonosAux);
		this.lsvTelefonos.setItems(this.listaTelefonos);
		
		btnAddTelf.setOnAction(event ->{		
			String telfIngresado = this.txfTelefono.getText();
			//Si se presiona el botón añadir y no se ingresaron teléfonos, no se debe agregar nada a la lista.
			if(telfIngresado.equals(""))
				return;
			if(chkWhatsapp.isSelected())
				telfIngresado = telfIngresado + " (W)";
			this.listaTelefonos.add(telfIngresado);			
		});
		
		btnDelTelf.setOnAction(event ->{
			if(this.lsvTelefonos.getSelectionModel().getSelectedIndex()>=0) {
			this.listaTelefonos.remove(this.lsvTelefonos.getSelectionModel().getSelectedIndex());
			}
		});
		
		
		//Registro
		List<Contacto> listaContactosAux = new ArrayList<>();
		this.listaContactos = FXCollections.observableList(listaContactosAux);
		this.lsvContactos.setItems(this.listaContactos);		
		
		//Se referencia el archivo después de crear la lista porque sino, a priori no existe y 
		//no se puede cargar.
		this.archivoCSV = new File("archivoCSV.csv");
		//Si el archivo ya existe, cargarlo.
		//El archivo se generará y se sobrescribirá cuando se presione el botón Grabar.
		if(this.archivoCSV.exists()) {
			cargarCSV();
		}
		
		
		btnRegistrar.setOnAction(event ->{			
			//Validación del email. Si no cumple con la condición, se rompe el evento.
			if(!(txfEmail.getText().contains("@"))) {				
				mostrarAlerta(AlertType.ERROR, "ERROR Email", "Email incorrecto", "El email debe al menos el caracter @");
				return;
			}
			
			//Validación de contacto existente en la lista.
			validacionesOK=true;
			this.listaContactos.forEach(contacto->{
				if(txfNombres.getText().equals(contacto.getNombres()) && 
						txfApellidos.getText().equals(contacto.getApellidos())) {
					mostrarAlerta(AlertType.ERROR, "ERROR Contacto duplicado", "Contacto duplicado", "El contacto con ese nombre ya existe");
					validacionesOK=false;					
				}
			});		
			
			if(!validacionesOK) {				
				return;
			}
			
			List<String> telefonos = new ArrayList<>();			
			this.listaTelefonos.forEach(telefono ->{
				telefonos.add(telefono);
			});
			
			Contacto nuevoContacto = new Contacto(txfNombres.getText(),txfApellidos.getText(),
					this.nombreFoto,txfDireccion.getText(),txfEmail.getText(),telefonos);
			
			this.listaContactos.add(nuevoContacto);
			//Resetear campos
			limpiarCampos();
			this.imagen.setImage(new Image("/fotos/default.png"));
			
			//Seleccionar el contacto ingresado en la lista de contactos.			
			this.lsvContactos.getSelectionModel().clearAndSelect(this.listaContactos.size()-1);
			this.lsvContactos.requestFocus();
		});	
		
		
		//Visualización de datos		
		btnInfo.setOnAction(event ->{			
			if(this.lsvContactos.getSelectionModel().getSelectedItem()==null) {
				if(this.listaContactos.isEmpty()) {
					mostrarAlerta(AlertType.ERROR, "ERROR Contacto seleccionado", "La lista de contactos está vacía", null);
					return;
				}else {
					mostrarAlerta(AlertType.ERROR, "ERROR Contacto no seleccionado", "Contacto no seleccionado", "No se ha seleccionado un contacto para visualizar");
					return;
				}
			}
			
			Contacto contactoSel = this.lsvContactos.getSelectionModel().getSelectedItem();
			
			//Se muestran los datos
			this.txfNombres.setText(contactoSel.getNombres());
			this.txfApellidos.setText(contactoSel.getApellidos());
			this.txfDireccion.setText(contactoSel.getDireccion());
			this.txfEmail.setText(contactoSel.getEmail());
			
			this.listaTelefonos = FXCollections.observableList(contactoSel.getTelefonos());
			this.lsvTelefonos.setItems(this.listaTelefonos);
						
			this.rutaFoto = "/fotos/" + contactoSel.getFotografia();
			this.imagen.setImage(new Image(this.rutaFoto));
		});
		
		//Eliminar contacto
		btnEliminar.setOnAction(event -> borrarContacto());		
		
		
		//Grabar archivo CSV
		btnGrabar.setOnAction(event -> grabarCSV());
		
		btnSalir.setOnAction(event ->primaryStage.close());		
		
		//Creación de la escena y el Stage
		Scene scene = new Scene(root,750,430);
		root.setId("root");
		scene.getStylesheets().add(getClass().getResource("/style/estilos.css").toExternalForm());		
		primaryStage.setTitle("Aplicación de Lista de Contactos V-1.0");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void grabarCSV() {
		try {
			//Se crea un flujo de escritura en el que se sobrescribe el archivo.
			FileWriter escritor = new FileWriter(this.archivoCSV);
			
			//Se recorre la lista de Contactos actual y se genera una linea con sus valores.
			this.listaContactos.forEach(contacto ->{
				StringBuilder lineaContacto = new StringBuilder();
				//Se agregan los 5 primeros valores de cada Contacto a la linea.
				lineaContacto.append(contacto.getNombres() + "," + contacto.getApellidos() + "," +
						contacto.getFotografia() + "," + contacto.getDireccion() + "," + contacto.getEmail());
				
				//Si se registraron teléfonos, la lista de contactos no debe estar vacía.
				if(!(contacto.getTelefonos().isEmpty())) {
					//Se obtienen los teléfonos del contacto y se los añade a la línea como valores.
					contacto.getTelefonos().forEach(telefono ->{
						lineaContacto.append("," + telefono.toString());
					});
				}
				
				//Se añade la linea al flujo de escritura
				try {
					escritor.append(lineaContacto.toString() + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}				
			});
			
			//Cerrar el flujo
			escritor.flush();
			escritor.close();
			
		}catch(Exception e) {
			e.printStackTrace();			
		}
	}
	
	public void cargarCSV() {
		String linea;
		Contacto contactoLeido;
		try {
			//Crear un Buffered Reader referenciando el nombre del archivo.
			BufferedReader lector = new BufferedReader(new FileReader(this.archivoCSV));
			//Se lee la linea del buffer
			linea = lector.readLine();
			//Si la linea leída no fue nula
			while(linea!=null) {
				//Se guarda todos los valores separados por comas en un arreglo de cadenas.
				String[] lineaContacto = linea.split(",");
				//Se guarda el tamaño del anterior arreglo en una variable.
				int lineaContactoSize = lineaContacto.length;
				
				//Obtener los primeros 5 datos en variables.
				String nombres,apellidos,fotografia,direccion,email;
				nombres = lineaContacto[0];
				apellidos = lineaContacto[1];
				fotografia = lineaContacto[2];
				direccion = lineaContacto[3];
				email = lineaContacto[4];
				
				//Dado que los registros de Contacto se guardan acorde al constructor de dicha clase,
				//siempre se tendrán valores(así sean "") de los 5 primeros atributos de un Contacto,
				//y los demás valores de la linea serán los números de teléfono ingresados.
				
				//Crear una lista de teléfonos.
				List<String> telefonos = new ArrayList<>();
				
				//Si hay más de 5 valores, significa que se agregaron teléfonos.				
				if(lineaContactoSize>4) {
					//Se agregan los valores a la lista de teléfonos creada.
					for(int i=5;i<lineaContactoSize;i++) {
						telefonos.add(lineaContacto[i]);
					}
				}
				
				//Se crea el contacto con los datos leídos.
				contactoLeido = new Contacto(nombres, apellidos, fotografia, direccion, email, telefonos);
				
				//Se agrega el contacto leído a la lista de contactos del programa.
				this.listaContactos.add(contactoLeido);
								
				//Leer la siguiente linea.
				linea = lector.readLine();				
			}
			
			//Cerrar el flujo
			lector.close();
		}catch(Exception e) {
			e.toString();
		}
	}
	
	public void configurarExploradorArchivos() {
		//Titulo de la ventana
		this.explorador.setTitle("Selección de fotografía de contacto");
		
		//Se crea un archivo para que se referencie la carpeta Fotos como directorio inicial
		File directorioFotos = new File(System.getProperty("user.dir"), "/src/fotos");	
		this.explorador.setInitialDirectory(directorioFotos);
		
		//Se agregan los filtros para que la imagen sea únicamente JPG o PNG.
		this.explorador.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Archivos de imagen", "*.*"),
				new FileChooser.ExtensionFilter("JPG", "*.jpg*"),
				new FileChooser.ExtensionFilter("PNG", "*.png*")					
				);
	}
	
	public void borrarContacto() {

		int idx = this.lsvContactos.getSelectionModel().getSelectedIndex();
			
		if(idx < 0) {
			if(this.listaContactos.isEmpty()) {
					mostrarAlerta(AlertType.ERROR, "ERROR Contacto seleccionado", "La lista de contactos está vacía", null);
					return;
			}else {
				mostrarAlerta(AlertType.ERROR, "ERROR Contacto no seleccionado", "Contacto no seleccionado", "No se ha seleccionado un contacto para eliminar.");
				return;
			}
		}
		
		//Se elimina el contacto seleccionado.
		this.listaContactos.remove(idx);
						
		//Se setea el index de selección del contacto anterior al eliminado en la lista
		idx = idx-1;
		
		//Si la lista queda vacía, no puede quedar negativa.
		if(idx < 0) {
			idx = 0;
		}
		//Se selecciona el indice recalculado
		this.lsvContactos.getSelectionModel().clearAndSelect(idx);
		this.lsvContactos.requestFocus();
		
		//Se resetean los campos después del borrado
		this.limpiarCampos();
		this.imagen.setImage(new Image("/fotos/default.png"));
	}
	
	public void limpiarCampos() {
		this.txfNombres.clear();
		this.txfApellidos.clear();
		this.txfDireccion.clear();
		this.txfEmail.clear();
		this.txfTelefono.clear();
		this.chkWhatsapp.setSelected(false);
		this.lsvTelefonos.getItems().clear();	
	}
	
	public void mostrarAlerta(AlertType tipoAlerta,String tituloVentana,String tituloMensaje,String mensaje) {
		Alert alerta = new Alert(tipoAlerta);
		alerta.setTitle(tituloVentana);
		alerta.setHeaderText(tituloMensaje);
		alerta.setContentText(mensaje);
		alerta.showAndWait();		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
