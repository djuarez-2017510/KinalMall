package org.danieljuarez.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
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
import org.danieljuarez.bean.CuentasPorPagar;
import org.danieljuarez.bean.Proveedores;
import org.danieljuarez.db.Conexion;
import org.danieljuarez.system.Principal;

public class CuentasPorPagarController implements Initializable{
    private enum operaciones{NUEVO, GUARDAR, ACTUALIZAR, NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<CuentasPorPagar> listaCuentasPorPagar;
    private ObservableList<Administracion> listaAdministracion;
    private ObservableList<Proveedores> listaProveedores;
    private DatePicker fechaLimite;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoCuentasPP;
    @FXML private TextField txtNumeroFactura;
    @FXML private TextField txtEstadoPago;
    @FXML private TextField txtValorNetoPago;
    @FXML private ComboBox cmbCodigoAdministracion;
    @FXML private ComboBox cmbCodigoProveedor;
    @FXML private GridPane grpFechaLimite;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;       
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    @FXML private TableView tblCuentasPorPagar;
    @FXML private TableColumn colCodigoCuentasPP;
    @FXML private TableColumn colNumeroFactura;
    @FXML private TableColumn colFechaLimitePago;
    @FXML private TableColumn colEstadoPago; 
    @FXML private TableColumn colValorNetoPago;      
    @FXML private TableColumn colCodigoAdministracion;       
    @FXML private TableColumn colCodigoProveedor;       
            
            
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fechaLimite = new DatePicker(Locale.ENGLISH);
        fechaLimite.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        fechaLimite.getCalendarView().todayButtonTextProperty().set("Today");
        fechaLimite.getCalendarView().setShowWeeks(false);
        grpFechaLimite.add(fechaLimite, 5, 0);
        fechaLimite.getStylesheets().add("/org/danieljuarez/resource/DatePicker.css");
        cargarDatos();
        desactivarControles();
    }

    public void cargarDatos(){
        tblCuentasPorPagar.setItems(getCuentaPorPagar());
        colCodigoCuentasPP.setCellValueFactory(new PropertyValueFactory<CuentasPorPagar,Integer>("codigoCuentasPorPagar"));
        colNumeroFactura.setCellValueFactory(new PropertyValueFactory<CuentasPorPagar,String>("numeroFactura"));
        colFechaLimitePago.setCellValueFactory(new PropertyValueFactory<CuentasPorPagar,Date>("fechaLimitePago"));
        colEstadoPago.setCellValueFactory(new PropertyValueFactory<CuentasPorPagar,String>("estadoPago"));
        colValorNetoPago.setCellValueFactory(new PropertyValueFactory<CuentasPorPagar,Double>("valorNetoPago"));
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<Administracion,Integer>("codigoAdministracion"));
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores,Integer>("codigoProveedor"));
        cmbCodigoAdministracion.setItems(getAdministracion());
        cmbCodigoProveedor.setItems(getProveedores());
    }
    
    public void seleccionarElemento(){
        if(tblCuentasPorPagar.getSelectionModel().getSelectedItem() != null){
        txtCodigoCuentasPP.setText(String.valueOf(((CuentasPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoCuentasPorPagar()));
        txtNumeroFactura.setText(((CuentasPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getNumeroFactura());
        fechaLimite.selectedDateProperty().set(((CuentasPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getFechaLimitePago());
        txtEstadoPago.setText(((CuentasPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getEstadoPago());
        txtValorNetoPago.setText(String.valueOf(((CuentasPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getValorNetoPago()));
        cmbCodigoAdministracion.getSelectionModel().select(buscarAdministracion(((CuentasPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
        cmbCodigoProveedor.getSelectionModel().select(buscarProveedor(((CuentasPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
    }
   }      
   
    
    public ObservableList<CuentasPorPagar>getCuentaPorPagar(){
        ArrayList<CuentasPorPagar> lista = new ArrayList<CuentasPorPagar>();
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCuentasPorPagar}");
                ResultSet resultado = procedimiento.executeQuery();
                while (resultado.next()){
                    lista.add(new CuentasPorPagar(resultado.getInt("codigoCuentasPorPagar"),
                                                   resultado.getString("numeroFactura"),
                                                   resultado.getDate("fechaLimitePago"),
                                                   resultado.getString("estadoPago"),
                                                   resultado.getDouble("valorNetoPago"),
                                                   resultado.getInt("codigoAdministracion"),
                                                   resultado.getInt("codigoProveedor")));
               
                }
            }catch(Exception e){
                e.printStackTrace();
            }
                
        return listaCuentasPorPagar =  FXCollections.observableArrayList(lista);      
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
    
    public ObservableList<Proveedores> getProveedores(){
        ArrayList<Proveedores> lista = new ArrayList<Proveedores>();
            try{
            
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProveedores()}");
                ResultSet resultado = procedimiento.executeQuery();
                while (resultado.next()){
                    lista.add(new Proveedores(resultado.getInt("codigoProveedor"),
                                            resultado.getString("NITProveedor"),
                                            resultado.getString("servicioPrestado"),
                                            resultado.getString("telefonoProveedor"),
                                            resultado.getString("direccionProveedor"),
                                            resultado.getDouble("saldoFavor"),
                                            resultado.getDouble("saldoContra"),
                                            resultado.getInt("codigoAdministracion")));
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        
        
        
        return listaProveedores = FXCollections.observableArrayList(lista);
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
    
   public Proveedores buscarProveedor(int codigoProveedor){
         Proveedores resultado = null;
         try{
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProveedores(?)}");
             procedimiento.setInt(1, codigoProveedor);
             ResultSet registro = procedimiento.executeQuery();
             while(registro.next()){
             
                 resultado = new Proveedores(registro.getInt("codigoProveedor"),
                                                registro.getString("NITProveedor"),
                                                registro.getString("servicioPrestado"),
                                                registro.getString("telefonoProveedor"),
                                                registro.getString("direccionProveedor"),
                                                registro.getDouble("saldoFavor"),
                                                registro.getDouble("saldoContra"),
                                                registro.getInt("codigoAdministracion"));
              
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
       CuentasPorPagar registro = new CuentasPorPagar();
       registro.setNumeroFactura(txtNumeroFactura.getText());
       registro.setFechaLimitePago(fechaLimite.getSelectedDate());
       registro.setEstadoPago(txtEstadoPago.getText());
       registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
       registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
       registro.setCodigoProveedor(((Proveedores)cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCuentasPorPagar(?,?,?,?,?,?)}");
            procedimiento.setString(1, registro.getNumeroFactura());
            procedimiento.setDate(2, new java.sql.Date(registro.getFechaLimitePago().getTime()));
            procedimiento.setString(3, registro.getEstadoPago());
            procedimiento.setDouble(4, registro.getValorNetoPago());
            procedimiento.setInt(5, registro.getCodigoAdministracion());
            procedimiento.setInt(6, registro.getCodigoProveedor());
            procedimiento.execute();
            listaCuentasPorPagar.add(registro);
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
                if (tblCuentasPorPagar.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar el registro?", "Eliminar Cuenta Pagar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BorrarCuentasPorPagar(?)}");
                        procedimiento.setInt(1, ((CuentasPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoCuentasPorPagar());
                        procedimiento.execute();
                        listaCuentasPorPagar.remove(tblCuentasPorPagar.getSelectionModel().getSelectedIndex());
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
                if (tblCuentasPorPagar.getSelectionModel().getSelectedItem() != null){
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
         
         PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCuentasPorPagar(?,?,?,?,?,?,?)}");
         CuentasPorPagar registro = (CuentasPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem();
         registro.setNumeroFactura(txtNumeroFactura.getText());
         registro.setFechaLimitePago(fechaLimite.getSelectedDate());
         registro.setEstadoPago(txtEstadoPago.getText());
         registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
         registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
         registro.setCodigoProveedor(((Proveedores)cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
         procedimiento.setInt(1, registro.getCodigoCuentasPorPagar());
         procedimiento.setString(2, registro.getNumeroFactura());
         procedimiento.setDate(3, new java.sql.Date(registro.getFechaLimitePago().getTime()));
         procedimiento.setString(4, registro.getEstadoPago());
         procedimiento.setDouble(5, registro.getValorNetoPago());
         procedimiento.setInt(6, registro.getCodigoAdministracion());
         procedimiento.setInt(7, registro.getCodigoProveedor());
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
        }
    }
    
   
   
   public void desactivarControles(){
       txtCodigoCuentasPP.setEditable(false);
       txtNumeroFactura.setEditable(false);
       txtEstadoPago.setEditable(false);
       txtValorNetoPago.setEditable(false);
       cmbCodigoAdministracion.setDisable(true);
       cmbCodigoProveedor.setDisable(true);
       fechaLimite.setDisable(true);
   }
   
   public void activarControles(){
       txtCodigoCuentasPP.setEditable(false);
       txtNumeroFactura.setEditable(true);
       txtEstadoPago.setEditable(true);
       txtValorNetoPago.setEditable(true);
       cmbCodigoAdministracion.setDisable(false);
       cmbCodigoProveedor.setDisable(false);
       fechaLimite.setDisable(false);
       
   }
   
   public void limpiarControles(){
       txtCodigoCuentasPP.clear();
       txtNumeroFactura.clear();
       txtEstadoPago.clear();
       txtValorNetoPago.clear();
       cmbCodigoAdministracion.setValue(null);
       cmbCodigoProveedor.setValue(null);
       fechaLimite.setPromptText("");
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
   
    public void ventanaProveedores(){
        escenarioPrincipal.ventanaProveedores();
    }
    
}
