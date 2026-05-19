package cr.ac.una.sistemagestionagraria;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import java.time.LocalDate;
import model.*;
import data.*;
import java.util.List;
import javafx.scene.control.Alert;

public class PrimaryController {
    
    //Parcela
    @FXML
    private ComboBox<String> cbParcelaEstado;
    @FXML 
    private TextField txtParcelaUbicacion;
    @FXML
    private TextField txtParcelaCodigo;
    @FXML
    private TextField txtParcelaNombre;
    @FXML
    private TextField txtParcelaArea;
    @FXML
    private ComboBox<String> cbParcelaSuelo;
    @FXML
    private TableView<Parcela> tvParcelas;
    
    //Cultivo
    @FXML
    private TextField txtCultivoCodigo;
    @FXML
    private TextField txtCultivoNombre;
    @FXML
    private TextField txtCultivoVariedad;
    @FXML
    private TextField txtCultivoDatoEspecifico;
    @FXML
    private DatePicker dpCultivoFecha;
    @FXML
    private Label lblDatoEspecifico;
    @FXML
    private TableView<Cultivo> tvCultivos;
    @FXML
    private RadioButton rbAnual;
    @FXML
    private RadioButton rbPerenne;
    
    //Responsable
    @FXML
    private TextField txtRespId;
    @FXML
    private TextField txtRespNombre;
    @FXML
    private TextField txtRespCorreo;
    @FXML
    private TextField txtRespTelefono;
    @FXML
    private TextField txtRespDatoEspecifico;
    @FXML
    private RadioButton rbProductor;
    @FXML
    private RadioButton rbTecnico;
    @FXML
    private Label lblRespEspecifico;
    @FXML
    private TableView<Responsable> tvResponsables;
    
    //Labor y Reportes
    @FXML
    private TextField txtLaborCosto;
    @FXML
    private TextField txtLaborDescripcion;
    @FXML
    private DatePicker dpLaborFecha;
    @FXML
    private ComboBox<Parcela> cbLaborParcela;
    @FXML
    private ComboBox<Cultivo> cbLaborCultivo;
    @FXML
    private ComboBox<Responsable> cbLaborResponsable;
    @FXML
    private ComboBox<String> cbLaborTipo;
    @FXML
    private TableView<LaborAgricola> tvLabores;
    
    // Parcelas
    private ParcelaDAO parcelaDAO = new ParcelaDAO();
    private ObservableList<Parcela> listaParcelas = FXCollections.observableArrayList();
    
    //Cultivos
    private CultivoDAO cultivoDAO = new CultivoDAO();
    private ObservableList<Cultivo> listaCultivos = FXCollections.observableArrayList();
    
    //Responsable
    private ResponsableDAO responsableDAO = new ResponsableDAO();
    private ObservableList<Responsable> listaResponsables = FXCollections.observableArrayList();
    
    //Labor
    private LaborAgricolaDAO laborDAO = new LaborAgricolaDAO();
    private ObservableList<LaborAgricola> listaLabores = FXCollections.observableArrayList();

    public void initialize() {
        
        //Tabla Parcelas
        cbParcelaSuelo.setItems(FXCollections.observableArrayList("Arcilloso", "Arenoso", "Limoso", "Franco"));
        cbParcelaEstado.setItems(FXCollections.observableArrayList("Disponible", "En producción", "En descanso"));
        
        if(tvParcelas.getColumns().size() >= 6) {
            tvParcelas.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("codigo"));
            tvParcelas.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tvParcelas.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("ubicacion"));
            tvParcelas.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("area"));
            tvParcelas.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("tipoSuelo"));
            tvParcelas.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("estado"));
        }

        actualizarTablaParcelas();
    
        
        //Tabla Cultivos
        if (tvCultivos.getColumns().size() >= 5) {
            tvCultivos.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("codigo"));
            tvCultivos.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tvCultivos.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("variedad"));
            tvCultivos.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("fechaSiembra"));
            TableColumn<Cultivo, String> colDesc = (TableColumn<Cultivo, String>) tvCultivos.getColumns().get(4);
            colDesc.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().obtenerDescripcion()));
            // Llama al método polimórfico obtenerDescripcion()
        }
        actualizarTablaCultivos();

        //Tabla Responsables
        if (tvResponsables.getColumns().size() >= 5) {
            tvResponsables.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("identificacion"));
            tvResponsables.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tvResponsables.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("correo"));
            tvResponsables.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("telefono"));
            TableColumn<Responsable, String> colPerf = (TableColumn<Responsable, String>) tvResponsables.getColumns().get(4);
            colPerf.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().obtenerPerfil())); 
        // Llama al método polimórfico obtenerPerfil()
        }
        actualizarTablaResponsables();
        
        //Tabla Labor
        
        cbLaborTipo.setItems(FXCollections.observableArrayList("Siembra", "Fertilización", "Riego", "Control de plagas", "Cosecha", "Mantenimiento", "Otro"));

    
        cargarComboBoxesLabores();

    
        if (tvLabores.getColumns().size() >= 8) {
            tvLabores.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("codigoLabor"));
        
        
            TableColumn<LaborAgricola, String> colP = (TableColumn<LaborAgricola, String>) tvLabores.getColumns().get(1);
            colP.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getParcela().getNombre()));
        
            TableColumn<LaborAgricola, String> colC = (TableColumn<LaborAgricola, String>) tvLabores.getColumns().get(2);
            colC.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCultivo().getNombre()));
        
            TableColumn<LaborAgricola, String> colR = (TableColumn<LaborAgricola, String>) tvLabores.getColumns().get(3);
            colR.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getResponsable().getNombre()));
        
            tvLabores.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("tipoLabor"));
            tvLabores.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("costo"));
            tvLabores.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("fechaLabor"));
            tvLabores.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        }
        actualizarTablaLabores();
        
        
    }
    
    //Parcelas

    private void actualizarTablaParcelas() {
        listaParcelas.clear();
        listaParcelas.addAll(parcelaDAO.listar());
        tvParcelas.setItems(listaParcelas);
    }
    
    //Cultivos
    private void actualizarTablaCultivos() {
        listaCultivos.clear();
        listaCultivos.addAll(cultivoDAO.listar());
        tvCultivos.setItems(listaCultivos);
    }
    
    //Responsables
    private void actualizarTablaResponsables() {
        listaResponsables.clear();
        listaResponsables.addAll(responsableDAO.listar());
        tvResponsables.setItems(listaResponsables);
    }
    
    //Labor
    private void cargarComboBoxesLabores() {
    cbLaborParcela.setItems(FXCollections.observableArrayList(parcelaDAO.listar()));
    cbLaborCultivo.setItems(FXCollections.observableArrayList(cultivoDAO.listar()));
    cbLaborResponsable.setItems(FXCollections.observableArrayList(responsableDAO.listar()));
    }
    
    private void actualizarTablaLabores() {
    listaLabores.clear();
    listaLabores.addAll(laborDAO.listar());
    tvLabores.setItems(listaLabores);
    }
    
    
    
    //Parcelas
    @FXML
    private void guardarParcela() {
        // Validar campos vacíos
        if (txtParcelaCodigo.getText().isEmpty() || txtParcelaNombre.getText().isEmpty() || 
            txtParcelaUbicacion.getText().isEmpty() || txtParcelaArea.getText().isEmpty() ||
            cbParcelaSuelo.getValue() == null || cbParcelaEstado.getValue() == null) {
            
            mostrarAlerta("Campos vacíos", "Por favor complete todos los datos de la parcela.", Alert.AlertType.WARNING);
            return;
        }

        try {
            String codigo = txtParcelaCodigo.getText();
            String nombre = txtParcelaNombre.getText();
            String ubicacion = txtParcelaUbicacion.getText();
            double area = Double.parseDouble(txtParcelaArea.getText());
            String tipoSuelo = cbParcelaSuelo.getValue();
            String estado = cbParcelaEstado.getValue();

            if (area <= 0) {
                mostrarAlerta("Error de Validación", "El área de la parcela debe ser mayor a cero.", Alert.AlertType.ERROR);
                return;
            }
            
            Parcela nuevaParcela = new Parcela(codigo, nombre, ubicacion, area, tipoSuelo, estado);
            
            if (parcelaDAO.insertar(nuevaParcela)) {
                mostrarAlerta("Éxito", "Parcela guardada correctamente.", Alert.AlertType.INFORMATION);
                limpiarParcela();
                actualizarTablaParcelas();
            } else {
                mostrarAlerta("Error", "No se pudo guardar la parcela. Verifique si el código ya existe.", Alert.AlertType.ERROR);
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("Error de formato", "El campo Área debe recibir un valor numérico.", Alert.AlertType.ERROR);
        }
        
        cargarComboBoxesLabores();


    }

    @FXML
    private void limpiarParcela() {
        txtParcelaCodigo.clear();
        txtParcelaNombre.clear();
        txtParcelaUbicacion.clear();
        txtParcelaArea.clear();
        cbParcelaSuelo.setValue(null);
        cbParcelaEstado.setValue(null);
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();

    }
    
    // Cultivos 
    @FXML
    private void guardarCultivo() {
        if (txtCultivoCodigo.getText().isEmpty() || txtCultivoNombre.getText().isEmpty() ||
        txtCultivoVariedad.getText().isEmpty() || dpCultivoFecha.getValue() == null ||
        txtCultivoDatoEspecifico.getText().isEmpty()) {
        
        mostrarAlerta("Campos vacíos", "Por favor complete todos los campos del cultivo.", Alert.AlertType.WARNING);
        return;
    }

    try {
        String codigo = txtCultivoCodigo.getText();
        String nombre = txtCultivoNombre.getText();
        String variedad = txtCultivoVariedad.getText();
        LocalDate fecha = dpCultivoFecha.getValue();
        int valorEspecifico = Integer.parseInt(txtCultivoDatoEspecifico.getText());

        if (valorEspecifico <= 0) {
            mostrarAlerta("Error", "El valor numérico específico debe ser mayor a cero.", Alert.AlertType.ERROR);
            return;
        }

        Cultivo nuevoCultivo;
        if (rbAnual.isSelected()) {
            nuevoCultivo = new CultivoAnual(codigo, nombre, variedad, fecha, valorEspecifico);
        } else {
            nuevoCultivo = new CultivoPerenne(codigo, nombre, variedad, fecha, valorEspecifico);
        }

        if (cultivoDAO.insertar(nuevoCultivo)) {
            mostrarAlerta("Éxito", "Cultivo registrado correctamente.", Alert.AlertType.INFORMATION);
            limpiarCultivo();
            actualizarTablaCultivos();
        } else {
            mostrarAlerta("Error", "No se pudo guardar el cultivo. Verifique el código.", Alert.AlertType.ERROR);
        }

    } catch (NumberFormatException e) {
        mostrarAlerta("Error de formato", "El campo específico debe ser un número entero.", Alert.AlertType.ERROR);
    }
    
    cargarComboBoxesLabores();

    }

    @FXML
    private void seleccionarTipoCultivo() {
        if (rbAnual.isSelected()) {
        lblDatoEspecifico.setText("Duración del ciclo (días):");
        txtCultivoDatoEspecifico.setPromptText("Ej: 90");
        } 
        
        else if (rbPerenne.isSelected()) {
        lblDatoEspecifico.setText("Años prod. estimados:");
        txtCultivoDatoEspecifico.setPromptText("Ej: 5");
        }
    }
    
    @FXML
    private void limpiarCultivo() {
        txtCultivoCodigo.clear();
        txtCultivoNombre.clear();
        txtCultivoVariedad.clear();
        dpCultivoFecha.setValue(null);
        txtCultivoDatoEspecifico.clear();
    }

    @FXML
    private void seleccionarTipoResponsable() {
        if (rbProductor.isSelected()) {
        lblRespEspecifico.setText("Nombre de la Finca:");
        txtRespDatoEspecifico.setPromptText("Ej: Finca El Sol");
    } else if (rbTecnico.isSelected()) {
        lblRespEspecifico.setText("Especialidad:");
        txtRespDatoEspecifico.setPromptText("Ej: Agronomía");
    }
    }

    @FXML
    private void guardarResponsable() {
        if (txtRespId.getText().isEmpty() || txtRespNombre.getText().isEmpty() ||
        txtRespCorreo.getText().isEmpty() || txtRespTelefono.getText().isEmpty() ||
        txtRespDatoEspecifico.getText().isEmpty()) {
        
        mostrarAlerta("Campos vacíos", "Por favor complete todos los datos del responsable.", Alert.AlertType.WARNING);
        return;
    }

    String id = txtRespId.getText();
    String nombre = txtRespNombre.getText();
    String correo = txtRespCorreo.getText();
    String telefono = txtRespTelefono.getText();
    String datoEspecifico = txtRespDatoEspecifico.getText();

    Responsable nuevoResponsable;
    if (rbProductor.isSelected()) {
        nuevoResponsable = new Productor(id, nombre, correo, telefono, datoEspecifico);
    } 
    else {
        nuevoResponsable = new TecnicoAgricola(id, nombre, correo, telefono, datoEspecifico);
    }

    if (responsableDAO.insertar(nuevoResponsable)) {
        mostrarAlerta("Éxito", "Responsable registrado correctamente.", Alert.AlertType.INFORMATION);
        limpiarResponsable();
        actualizarTablaResponsables();
        } 
    else {
        mostrarAlerta("Error", "No se pudo registrar el responsable. Verifique la identificación.", Alert.AlertType.ERROR);
        }
    
    cargarComboBoxesLabores();
    }
    
    @FXML
    private void limpiarResponsable() {
        txtRespId.clear();
        txtRespNombre.clear();
        txtRespCorreo.clear();
        txtRespTelefono.clear();
        txtRespDatoEspecifico.clear();
    }
    
    
    //Labor
    @FXML
    private void guardarLabor() {
        if (cbLaborParcela.getValue() == null || cbLaborCultivo.getValue() == null ||
        cbLaborResponsable.getValue() == null || cbLaborTipo.getValue() == null ||
        dpLaborFecha.getValue() == null || txtLaborCosto.getText().isEmpty()) {
        
        mostrarAlerta("Campos vacíos", "Por favor complete todos los campos para registrar la labor.", Alert.AlertType.WARNING);
        return;
    }

    try {
        Parcela parcela = cbLaborParcela.getValue();
        Cultivo cultivo = cbLaborCultivo.getValue();
        Responsable responsable = cbLaborResponsable.getValue();
        String tipo = cbLaborTipo.getValue();
        LocalDate fecha = dpLaborFecha.getValue();
        double costo = Double.parseDouble(txtLaborCosto.getText());
        String desc = txtLaborDescripcion.getText();


        if (costo < 0) {
            mostrarAlerta("Error de Validación", "El costo estimado de la labor no puede ser negativo.", Alert.AlertType.ERROR);
            return;
        }

        
        LaborAgricola nuevaLabor = new LaborAgricola(parcela, cultivo, responsable, fecha, tipo, desc, costo);

        if (laborDAO.insertar(nuevaLabor)) {
            mostrarAlerta("Éxito", "Labor agrícola registrada correctamente.", Alert.AlertType.INFORMATION);
            limpiarLabor();
            actualizarTablaLabores();
        } else {
            mostrarAlerta("Error", "No se pudo registrar la labor en la base de datos.", Alert.AlertType.ERROR);
        }

    } catch (NumberFormatException e) {
        mostrarAlerta("Error de formato", "El campo Costo debe ser un número válido.", Alert.AlertType.ERROR);
    }
    }
    
    //Reporte
    @FXML
    private void generarInformeText() {
        List<LaborAgricola> labores = laborDAO.listar();
    if (labores.isEmpty()) {
        mostrarAlerta("Sin datos", "No hay labores registradas para generar el reporte.", Alert.AlertType.WARNING);
        return;
    }

    // Ruta del archivo plano solicitado en el paquete de reportes o raíz
    String nombreArchivo = "reporte_labores_agricolas.txt";
    
    try (java.io.PrintWriter escritor = new java.io.PrintWriter(new java.io.FileWriter(nombreArchivo))) {
        escritor.println("=================================================================================");
        escritor.println("                     UNIVERSIDAD NACIONAL - PROGRAMACIÓN II                      ");
        escritor.println("                      INFORME GENERAL DE LABORES AGRÍCOLAS                       ");
        escritor.println("=================================================================================");
        escritor.println(String.format("%-10s %-12s %-15s %-15s %-15s %-10s", "CÓDIGO", "FECHA", "PARCELA", "CULTIVO", "RESPONSABLE", "COSTO"));
        escritor.println("---------------------------------------------------------------------------------");

        for (LaborAgricola l : labores) {
            escritor.println(String.format("%-10d %-12s %-15s %-15s %-15s $%-10.2f",
                l.getCodigoLabor(),
                l.getFechaLabor().toString(),
                l.getParcela().getNombre(),
                l.getCultivo().getNombre(),
                l.getResponsable().getNombre(),
                l.getCosto()
            ));
        }
        escritor.println("=================================================================================");
        escritor.flush();
        
        mostrarAlerta("Informe Generado", "El reporte se exportó con éxito con el nombre:\n" + nombreArchivo, Alert.AlertType.INFORMATION);

    } catch (java.io.IOException e) {
        mostrarAlerta("Error de archivo", "No se pudo escribir el archivo de reporte: " + e.getMessage(), Alert.AlertType.ERROR);
    }
    }

    @FXML
    private void limpiarLabor() {
        cbLaborParcela.setValue(null);
        cbLaborCultivo.setValue(null);
        cbLaborResponsable.setValue(null);
        cbLaborTipo.setValue(null);
        dpLaborFecha.setValue(null);
        txtLaborCosto.clear();
        txtLaborDescripcion.clear();
    }

    

    
}
