package org.danieljuarez.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.danieljuarez.bean.Administracion;
import org.danieljuarez.bean.Cargos;
import org.danieljuarez.bean.Departamentos;
import org.danieljuarez.bean.Empleados;
import org.danieljuarez.bean.Horarios;
import org.danieljuarez.db.Conexion;
import org.danieljuarez.report.GenerarReporte;
import org.danieljuarez.system.Principal;


public class EmpleadosController implements Initializable{
private enum operaciones{NUEVO, GUARDAR, ACTUALIZAR, NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<Empleados> listaEmpleados;
    private ObservableList<Departamentos> listaDepartamentos;        
    private ObservableList<Cargos> listaCargos;
    private ObservableList<Horarios> listaHorarios;
    private ObservableList<Administracion>listaAdministracion;
    @FXML private DatePicker fechaContratacion;
    @FXML private TextField txtCodigoEmpleado;
    @FXML private TextField txtNombres;
    @FXML private TextField txtApellido;       
    @FXML private TextField txtCorreo;       
    @FXML private TextField txtTelefono;       
    @FXML private TextField txtSueldo;
    @FXML private ComboBox cmbCodigoDepa;
    @FXML private ComboBox cmbCargo;        
    @FXML private ComboBox cmbCodigoHorario;        
    @FXML private ComboBox cmbCodigoAdmin;
    @FXML private GridPane grpFechaContratacion;
    @FXML private TableView tblEmpleados;
    @FXML private TableColumn colCodEmpleado;
    @FXML private TableColumn colNombres;
    @FXML private TableColumn colApellidos;
    @FXML private TableColumn colCorreo;
    @FXML private TableColumn colTelefono;
    @FXML private TableColumn colFechaContra;
    @FXML private TableColumn colSueldo;
    @FXML private TableColumn colCodDepa;
    @FXML private TableColumn colCodCargo;
    @FXML private TableColumn colCodHorario;
    @FXML private TableColumn colCodAdmin;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;        
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
     
     
    @Override public void initialize(URL location, ResourceBundle resources) {
       fechaContratacion = new DatePicker(Locale.ENGLISH);
        fechaContratacion.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        fechaContratacion.getCalendarView().todayButtonTextProperty().set("Today");
        fechaContratacion.getCalendarView().setShowWeeks(false);
        grpFechaContratacion.add(fechaContratacion, 5, 1);
        fechaContratacion.getStylesheets().add("/org/danieljuarez/resource/DatePicker.css");
        cargarDatos();
       desactivarControles();
    }
    
    public void cargarDatos(){
        tblEmpleados.setItems(getEmpleados());
        colCodEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados,Integer>("codigoEmpleado"));
        colNombres.setCellValueFactory(new PropertyValueFactory<Empleados,String>("nombreEmpleado"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<Empleados,Integer>("apellidoEmpleado"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<Empleados,Integer>("correoElectronico"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Empleados,Integer>("telefonoEmpleado"));
        colFechaContra.setCellValueFactory(new PropertyValueFactory<Empleados,Date>("fechaContratacion"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<Empleados,Double>("sueldo"));
        colCodDepa.setCellValueFactory(new PropertyValueFactory<Empleados,Integer>("codigoDepartamento"));
        colCodCargo.setCellValueFactory(new PropertyValueFactory<Empleados,Integer>("codigoCargo"));
        colCodHorario.setCellValueFactory(new PropertyValueFactory<Empleados,Integer>("codigoHorario"));
        colCodAdmin.setCellValueFactory(new PropertyValueFactory<Empleados,Integer>("codigoAdministracion"));
        cmbCodigoDepa.setItems(getDepartamentos());
        cmbCargo.setItems(getCargos());        
        cmbCodigoHorario.setItems(getHorarios());
        cmbCodigoAdmin.setItems(getAdministracion());        
    }
    
     public void seleccionarElemento(){
        if(tblEmpleados.getSelectionModel().getSelectedItem() != null){
        txtCodigoEmpleado.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        txtNombres.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getNombreEmpleado());
        txtApellido.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getApellidoEmpleado());
        txtCorreo.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCorreoElectronico());
        txtTelefono.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getTelefonoEmpleado());
        fechaContratacion.selectedDateProperty().set(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getFechaContratacion());
        txtSueldo.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getSueldo()));
        cmbCodigoDepa.getSelectionModel().select(buscarDepartamentos(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoDepartamento()));
        cmbCargo.getSelectionModel().select(buscarCargo(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoCargo()));
        cmbCodigoHorario.getSelectionModel().select(buscarHorario(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoHorario()));
        cmbCodigoAdmin.getSelectionModel().select(buscarAdministracion(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
        
    }
   }  
    
   public ObservableList<Empleados>getEmpleados(){
        ArrayList<Empleados> lista = new ArrayList<Empleados>();
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleados}");
                ResultSet resultado = procedimiento.executeQuery();
                while (resultado.next()){
                    lista.add(new Empleados(resultado.getInt("codigoEmpleado"),
                                                   resultado.getString("nombreEmpleado"),
                                                   resultado.getString("apellidoEmpleado"), 
                                                   resultado.getString("correoElectronico"),
                                                   resultado.getString("telefonoEmpleado"),
                                                   resultado.getDate("fechaContratacion"),
                                                   resultado.getDouble("sueldo"),
                                                   resultado.getInt("codigoDepartamento"),
                                                   resultado.getInt("codigoCargo"),
                                                   resultado.getInt("codigoHorario"),
                                                   resultado.getInt("codigoAdministracion")));
               
                }
            }catch(Exception e){
                e.printStackTrace();
            }
                
        return listaEmpleados =  FXCollections.observableArrayList(lista);      
    } 
     
     public ObservableList<Departamentos>getDepartamentos(){
     ArrayList<Departamentos> lista = new ArrayList<Departamentos>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDepartamentos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Departamentos(resultado.getInt("codigoDepartamento"),
                                             resultado.getString("nombreDepartamento")));
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaDepartamentos = FXCollections.observableArrayList(lista);
    }
     
     public Departamentos buscarDepartamentos(int codigoDepartamento){
         Departamentos resultado = null;
         try{
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarDepartamentos(?)}");
             procedimiento.setInt(1, codigoDepartamento);
             ResultSet registro = procedimiento.executeQuery();
             while(registro.next()){
             
                 resultado = new Departamentos(registro.getInt("codigoDepartamento"),
                                             registro.getString("nombreDepartamento"));
             }
         }catch(Exception e){
             e.printStackTrace();
         }
        
         return resultado;
     }
     
     public ObservableList<Cargos>getCargos(){
     ArrayList<Cargos> lista = new ArrayList<Cargos>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCargos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Cargos(resultado.getInt("codigoCargo"),
                                             resultado.getString("nombreCargo")));
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaCargos = FXCollections.observableArrayList(lista);
    }
     
     public Cargos buscarCargo(int codigoCargo){
         Cargos resultado = null;
         try{
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarCargo(?)}");
             procedimiento.setInt(1, codigoCargo);
             ResultSet registro = procedimiento.executeQuery();
             while(registro.next()){
             
                 resultado = new Cargos(registro.getInt("codigoCargo"),
                                             registro.getString("nombreCargo"));
             }
         }catch(Exception e){
             e.printStackTrace();
         }
        
         return resultado;
     }
    
     
     public ObservableList<Horarios>getHorarios(){
        ArrayList<Horarios> lista = new ArrayList<Horarios>();
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarHorarios()}");
                ResultSet resultado = procedimiento.executeQuery();
                while (resultado.next()){
                    lista.add(new Horarios(resultado.getInt("codigoHorario"),
                                                   resultado.getString("horarioEntrada"),
                                                   resultado.getString("horarioSalida"),
                                                   resultado.getBoolean("lunes"),
                                                   resultado.getBoolean("martes"),
                                                   resultado.getBoolean("miercoles"),
                                                   resultado.getBoolean("jueves"),        
                                                   resultado.getBoolean("viernes")));
                }
            }catch(Exception e){
                e.printStackTrace();
            }
                
        return listaHorarios =  FXCollections.observableArrayList(lista);      
    } 
     
  public Horarios buscarHorario(int codigoHorario){
         Horarios resultado = null;
         try{
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarHorario(?)}");
             procedimiento.setInt(1, codigoHorario);
             ResultSet registro = procedimiento.executeQuery();
             while(registro.next()){
             
                 resultado = new Horarios(registro.getInt("codigoHorario"),
                                                   registro.getString("horarioEntrada"),
                                                   registro.getString("horarioSalida"),
                                                   registro.getBoolean("lunes"),
                                                   registro.getBoolean("martes"),
                                                   registro.getBoolean("miercoles"),
                                                   registro.getBoolean("jueves"),        
                                                   registro.getBoolean("viernes"));
             }
         }catch(Exception e){
             e.printStackTrace();
         }
        
         return resultado;
     }   
     
  public ObservableList<Administracion> getAdministracion(){
        ArrayList<Administracion> lista = new ArrayList<Administracion>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarAdministracion()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Administracion(resultado.getInt("codigoAdministracion"), 
                                             resultado.getString("direccion"),
                                             resultado.getString("telefono")));
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaAdministracion = FXCollections.observableArrayList(lista);
    }
    
    public Administracion buscarAdministracion(int codigoAdministracion){
         Administracion resultado = null;
         try{
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarAdministracion(?)}");
             procedimiento.setInt(1, codigoAdministracion);
             ResultSet registro = procedimiento.executeQuery();
             while(registro.next()){
             
                 resultado = new Administracion(registro.getInt("codigoAdministracion"),
                                                registro.getString("direccion"),
                                                registro.getString("telefono"));
             }
         }catch(Exception e){
             e.printStackTrace();
         }
        
         return resultado;
     }

     public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                    activarControles();
                    limpiarControles();
                    btnNuevo.setText("Guardar");
                    btnEliminar.setText("Cancelar");
                    btnEditar.setDisable(true);
                    btnReporte.setDisable(true);
                    imgNuevo.setImage(new Image("/org/danieljuarez/images/guardar.png"));
                    imgEliminar.setImage(new Image("/org/danieljuarez/images/cancelar.png"));
                    tipoDeOperacion = operaciones.GUARDAR;
                break;
                
            case GUARDAR:
                    guardar();
                    desactivarControles();
                    limpiarControles();
                    btnNuevo.setText("Nuevo");
                    btnEliminar.setText("Eliminar");
                    btnEditar.setDisable(false);
                    btnReporte.setDisable(false);
                    imgNuevo.setImage(new Image("/org/danieljuarez/images/nuevoLogo.png"));
                    imgEliminar.setImage(new Image("/org/danieljuarez/images/borrarLogo.png"));
                    tipoDeOperacion = operaciones.NINGUNO;
                    cargarDatos();
                break;
        }
    }
   
   
   public void guardar(){
       Empleados registro = new Empleados();
       registro.setNombreEmpleado(txtNombres.getText());
       registro.setApellidoEmpleado(txtApellido.getText());
       registro.setCorreoElectronico(txtCorreo.getText());
       registro.setTelefonoEmpleado(txtTelefono.getText());
       registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
       registro.setFechaContratacion(fechaContratacion.getSelectedDate());
       registro.setCodigoDepartamento(((Departamentos)cmbCodigoDepa.getSelectionModel().getSelectedItem()).getCodigoDepartamento());
       registro.setCodigoCargo(((Cargos)cmbCargo.getSelectionModel().getSelectedItem()).getCodigoCargo());
       registro.setCodigoHorario(((Horarios)cmbCodigoHorario.getSelectionModel().getSelectedItem()).getCodigoHorario());
       registro.setCodigoAdministracion(((Administracion)cmbCodigoAdmin.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmpleados(?,?,?,?,?,?,?,?,?,?)}");
            procedimiento.setString(1, registro.getNombreEmpleado());
            procedimiento.setString(2, registro.getApellidoEmpleado());
            procedimiento.setString(3, registro.getCorreoElectronico());
            procedimiento.setString(4, registro.getTelefonoEmpleado());
            procedimiento.setDate(5, new java.sql.Date(registro.getFechaContratacion().getTime()));
            procedimiento.setDouble(6, registro.getSueldo());
            procedimiento.setInt(7, registro.getCodigoDepartamento());
            procedimiento.setInt(8, registro.getCodigoCargo());
            procedimiento.setInt(9, registro.getCodigoHorario());
            procedimiento.setInt(10, registro.getCodigoAdministracion());
            procedimiento.execute();
            listaEmpleados.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
   }
   
   
   public void eliminar(){
        switch(tipoDeOperacion){
            case GUARDAR:
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                imgNuevo.setImage(new Image("/org/danieljuarez/images/nuevoLogo.png"));
                imgEliminar.setImage(new Image("/org/danieljuarez/images/borrarLogo.png"));
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            default:
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar el registro?", "Eliminar Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BorrarEmpleados(?)}");
                        procedimiento.setInt(1, ((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                        procedimiento.execute();
                        listaEmpleados.remove(tblEmpleados.getSelectionModel().getSelectedIndex());
                        limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento para realizar esta accion");
                }
    
    }
    
    }
  
   
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    imgEditar.setImage(new Image("/org/danieljuarez/images/actualizar.png"));
                    imgReporte.setImage(new Image("/org/danieljuarez/images/cancelar.png"));
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento para realizar esta accion");
                }
                
                break;
                
            case ACTUALIZAR:
                actualizar();
                 btnEditar.setText("Editar");
                 btnReporte.setText("Reporte");
                 imgEditar.setImage(new Image("/org/danieljuarez/images/EditarLogo.png"));
                 imgReporte.setImage(new Image("/org/danieljuarez/images/reporteLogo.png"));
                 btnNuevo.setDisable(false);
                 btnEliminar.setDisable(false);
                 desactivarControles();
                 limpiarControles();
                 tipoDeOperacion = operaciones.NINGUNO;
                 cargarDatos();
                break;
        }

    }
  
    
    
    
    public void actualizar(){
         try{
         
         PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarEmpleados(?,?,?,?,?,?,?,?,?,?,?)}");
         Empleados registro = (Empleados)tblEmpleados.getSelectionModel().getSelectedItem();
         registro.setNombreEmpleado(txtNombres.getText());
         registro.setApellidoEmpleado(txtApellido.getText());
         registro.setCorreoElectronico(txtCorreo.getText());
         registro.setTelefonoEmpleado(txtTelefono.getText());
         registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
         registro.setFechaContratacion(fechaContratacion.getSelectedDate());
         registro.setCodigoDepartamento(((Departamentos)cmbCodigoDepa.getSelectionModel().getSelectedItem()).getCodigoDepartamento());
         registro.setCodigoCargo(((Cargos)cmbCargo.getSelectionModel().getSelectedItem()).getCodigoCargo());
         registro.setCodigoHorario(((Horarios)cmbCodigoHorario.getSelectionModel().getSelectedItem()).getCodigoHorario());
         registro.setCodigoAdministracion(((Administracion)cmbCodigoAdmin.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
         procedimiento.setInt(1, registro.getCodigoEmpleado());
         procedimiento.setString(2, registro.getNombreEmpleado());
         procedimiento.setString(3, registro.getApellidoEmpleado());
         procedimiento.setString(4, registro.getCorreoElectronico());
         procedimiento.setString(5, registro.getTelefonoEmpleado());
         procedimiento.setDate(6, new java.sql.Date(registro.getFechaContratacion().getTime()));
         procedimiento.setDouble(7, registro.getSueldo());
         procedimiento.setInt(8, registro.getCodigoDepartamento());
         procedimiento.setInt(9, registro.getCodigoCargo());
         procedimiento.setInt(10, registro.getCodigoHorario());
         procedimiento.setInt(11, registro.getCodigoAdministracion());
         procedimiento.execute();
         }catch(Exception e){
             e.printStackTrace();
         }
    
    }
   
    public void reporte(){
        switch(tipoDeOperacion){
        
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                imgEditar.setImage(new Image("/org/danieljuarez/images/EditarLogo.png"));
                imgReporte.setImage(new Image("/org/danieljuarez/images/reporteLogo.png"));
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
                
            case NINGUNO:
                imprimirReporte();
            break;
            
        }
    }
    
    
    public void imprimirReporte(){
        if(tblEmpleados.getSelectionModel().getSelectedItem() != null){
            Map parametros = new HashMap();
            int codigEmpleado = ((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado();
            parametros.put("codigEmpleado", codigEmpleado);
            GenerarReporte.mostrarReporte("RepoEmpleado.jasper", "Reporte Empleado", parametros);
        }else{
         JOptionPane.showMessageDialog(null, "Seleccione un registro. ");   
        }
    }

    
    
    public void desactivarControles(){
     txtCodigoEmpleado.setEditable(false);
     txtNombres.setEditable(false);
     txtApellido.setEditable(false);       
     txtCorreo.setEditable(false);       
     txtTelefono.setEditable(false);       
     txtSueldo.setEditable(false);
     cmbCodigoDepa.setDisable(true);
     cmbCargo.setDisable(true);        
     cmbCodigoHorario.setDisable(true);        
     cmbCodigoAdmin.setDisable(true);   
     fechaContratacion.setDisable(true);  
    }
    
    public void activarControles(){
     txtCodigoEmpleado.setEditable(false);
     txtNombres.setEditable(true);
     txtApellido.setEditable(true);       
     txtCorreo.setEditable(true);       
     txtTelefono.setEditable(true);       
     txtSueldo.setEditable(true);
     cmbCodigoDepa.setDisable(false);
     cmbCargo.setDisable(false);        
     cmbCodigoHorario.setDisable(false);        
     cmbCodigoAdmin.setDisable(false);   
     fechaContratacion.setDisable(false);
    }
    
    public void limpiarControles(){
     txtCodigoEmpleado.clear();
     txtNombres.clear();
     txtApellido.clear();       
     txtCorreo.clear();       
     txtTelefono.clear();       
     txtSueldo.clear();
     cmbCodigoDepa.setValue(null);
     cmbCargo.setValue(null);      
     cmbCodigoHorario.setValue(null);        
     cmbCodigoAdmin.setValue(null);   
     fechaContratacion.setPromptText("");  
    }
    
    
  public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
  
    public void ventanaCargos(){
        escenarioPrincipal.ventanaCargos();
    }
  
}
