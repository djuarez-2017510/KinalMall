package org.danieljuarez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
import javax.swing.JOptionPane;
import org.danieljuarez.bean.Administracion;
import org.danieljuarez.bean.Clientes;
import org.danieljuarez.bean.CuentasPorCobrar;
import org.danieljuarez.bean.Locales;
import org.danieljuarez.db.Conexion;
import org.danieljuarez.system.Principal;


public class CuentasPorCobrarController implements Initializable{
    private enum operaciones{NUEVO, GUARDAR, ACTUALIZAR, NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<CuentasPorCobrar> listaCuentasPorCobrar;
    private ObservableList<Administracion> listaAdministracion;
    private ObservableList<Locales> listaLocales;
    private ObservableList<Clientes> listaClientes;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoCuentasCobrar;
    @FXML private TextField txtNoFactura;
    @FXML private TextField txtAnio;
    @FXML private TextField txtMes;
    @FXML private TextField txtValorNetoPago;
    @FXML private TextField txtEstadoPago;
    @FXML private ComboBox cmbCodigoAdmin;        
    @FXML private ComboBox cmbCodigoClientes;        
    @FXML private ComboBox cmbCodigoLocales;
    @FXML private TableView tblCuentasPorCobrar;
    @FXML private TableColumn colCodigoCC;        
    @FXML private TableColumn colNoFactura;        
    @FXML private TableColumn colAnio;        
    @FXML private TableColumn colMes;        
    @FXML private TableColumn colValorNetoPago;        
    @FXML private TableColumn colEstadoPago;
    @FXML private TableColumn colCodigoAdministracion;
    @FXML private TableColumn colCodigoCliente;
    @FXML private TableColumn colCodigoLocal;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;       
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
            
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos(){
    tblCuentasPorCobrar.setItems(getCuentaPorCobrar());
    colCodigoCC.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar,Integer>("codigoCuentaPorCobrar"));
    colNoFactura.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar,String>("numeroFactura"));
    colAnio.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar,Integer>("anio"));
    colMes.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar,Integer>("mes"));
    colValorNetoPago.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar,Double>("valorNetoPago"));
    colEstadoPago.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar,String>("estadoPago"));
    colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<Administracion,Integer>("codigoAdministracion"));
    colCodigoCliente.setCellValueFactory(new PropertyValueFactory<Clientes,Integer>("codigoCliente"));
    colCodigoLocal.setCellValueFactory(new PropertyValueFactory<Locales,Integer>("codigoLocal"));
    cmbCodigoAdmin.setItems(getAdministracion());
    cmbCodigoClientes.setItems(getClientes());
    cmbCodigoLocales.setItems(getLocal());
    }
    
    public void seleccionarElemento(){
        if(tblCuentasPorCobrar.getSelectionModel().getSelectedItem() != null){
        txtCodigoCuentasCobrar.setText(String.valueOf(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoCuentaPorCobrar()));
        txtNoFactura.setText(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getNumeroFactura());
        txtAnio.setText(String.valueOf(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getAnio()));
        txtMes.setText(String.valueOf(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getMes()));
        txtValorNetoPago.setText(String.valueOf(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getValorNetoPago()));
        txtEstadoPago.setText(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getEstadoPago());
        cmbCodigoAdmin.getSelectionModel().select(buscarAdministracion(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
        cmbCodigoClientes.getSelectionModel().select(buscarCliente(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        cmbCodigoLocales.getSelectionModel().select(buscarLocales(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoLocal()));
    }
   }     
    
    
    public ObservableList<CuentasPorCobrar>getCuentaPorCobrar(){
        ArrayList<CuentasPorCobrar> lista = new ArrayList<CuentasPorCobrar>();
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCuentasPorCobrar}");
                ResultSet resultado = procedimiento.executeQuery();
                while (resultado.next()){
                    lista.add(new CuentasPorCobrar(resultado.getInt("codigoCuentaPorCobrar"),
                                                   resultado.getString("numeroFactura"),
                                                   resultado.getInt("anio"),
                                                   resultado.getInt("mes"),
                                                   resultado.getDouble("valorNetoPago"),
                                                   resultado.getString("estadoPago"),
                                                   resultado.getInt("codigoAdministracion"),
                                                   resultado.getInt("codigoCliente"),
                                                   resultado.getInt("codigoLocal")));
               
                }
            }catch(Exception e){
                e.printStackTrace();
            }
                
        return listaCuentasPorCobrar =  FXCollections.observableArrayList(lista);      
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

    public ObservableList<Clientes> getClientes(){
        ArrayList<Clientes> lista = new ArrayList<Clientes>();
            try{
            
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes()}");
                ResultSet resultado = procedimiento.executeQuery();
                while (resultado.next()){
                    lista.add(new Clientes(resultado.getInt("codigoCliente"),
                                            resultado.getString("nombresCliente"),
                                            resultado.getString("apellidosCliente"),
                                            resultado.getString("telefonoCliente"),
                                            resultado.getString("direccionCliente"),
                                            resultado.getString("email"),
                                            resultado.getInt("codigoLocal"),
                                            resultado.getInt("codigoAdministracion"),
                                            resultado.getInt("codigoTipoCliente")));
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        
        
        
        return listaClientes = FXCollections.observableArrayList(lista);
    }  
    
    public Clientes buscarCliente(int codigoCliente){
         Clientes resultado = null;
         try{
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarClientes(?)}");
             procedimiento.setInt(1, codigoCliente);
             ResultSet registro = procedimiento.executeQuery();
             while(registro.next()){
             
                 resultado = new Clientes(registro.getInt("codigoCliente"),
                                            registro.getString("nombresCliente"),
                                            registro.getString("apellidosCliente"),
                                            registro.getString("telefonoCliente"),
                                            registro.getString("direccionCliente"),
                                            registro.getString("email"),
                                            registro.getInt("codigoLocal"),
                                            registro.getInt("codigoAdministracion"),
                                            registro.getInt("codigoTipoCliente"));
             }
         }catch(Exception e){
             e.printStackTrace();
         }
        
         return resultado;
     }
    
     public ObservableList<Locales> getLocal(){
        ArrayList<Locales> lista = new ArrayList<Locales>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarLocales()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Locales(resultado.getInt("codigoLocal"),
                                      resultado.getDouble("saldoFavor"),
                                      resultado.getDouble("saldoContra"),
                                      resultado.getInt("mesesPendientes"),
                                      resultado.getBoolean("disponibilidad"),
                                      resultado.getDouble("valorLocal"),
                                      resultado.getDouble("valorAdministracion")));
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
      
    return listaLocales = FXCollections.observableArrayList(lista);
}
    
   public Locales buscarLocales(int codigoLocal){
         Locales resultado = null;
         try{
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarLocales(?)}");
             procedimiento.setInt(1, codigoLocal);
             ResultSet registro = procedimiento.executeQuery();
             while(registro.next()){
             
                 resultado = new Locales(registro.getInt("codigoLocal"),
                                      registro.getDouble("saldoFavor"),
                                      registro.getDouble("saldoContra"),
                                      registro.getInt("mesesPendientes"),
                                      registro.getBoolean("disponibilidad"),
                                      registro.getDouble("valorLocal"),
                                      registro.getDouble("valorAdministracion"));
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
       CuentasPorCobrar registro = new CuentasPorCobrar();
       registro.setNumeroFactura(txtNoFactura.getText());
       registro.setAnio(Integer.parseInt(txtAnio.getText()));
       registro.setMes(Integer.parseInt(txtMes.getText()));
       registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
       registro.setEstadoPago(txtEstadoPago.getText());
       registro.setCodigoAdministracion(((Administracion)cmbCodigoAdmin.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
       registro.setCodigoLocal(((Locales)cmbCodigoLocales.getSelectionModel().getSelectedItem()).getCodigoLocal());
       registro.setCodigoCliente(((Clientes)cmbCodigoClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCuentasPorCobrar(?,?,?,?,?,?,?,?)}");
            procedimiento.setString(1, registro.getNumeroFactura());
            procedimiento.setInt(2, registro.getAnio());
            procedimiento.setInt(3, registro.getMes());
            procedimiento.setDouble(4, registro.getValorNetoPago());
            procedimiento.setString(5, registro.getEstadoPago());
            procedimiento.setInt(6, registro.getCodigoAdministracion());
            procedimiento.setInt(7, registro.getCodigoLocal());
            procedimiento.setInt(8, registro.getCodigoCliente());
            procedimiento.execute();
            listaCuentasPorCobrar.add(registro);
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
                if (tblCuentasPorCobrar.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar el registro?", "Eliminar Cuenta Cobrar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BorrarCuentasPorCobrar(?)}");
                        procedimiento.setInt(1, ((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoCuentaPorCobrar());
                        procedimiento.execute();
                        listaCuentasPorCobrar.remove(tblCuentasPorCobrar.getSelectionModel().getSelectedIndex());
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
                if (tblCuentasPorCobrar.getSelectionModel().getSelectedItem() != null){
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
         
         PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCuentasPorCobrar(?,?,?,?,?,?,?,?,?)}");
         CuentasPorCobrar registro = (CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem();
         registro.setNumeroFactura(txtNoFactura.getText());
         registro.setAnio(Integer.parseInt(txtAnio.getText()));
         registro.setMes(Integer.parseInt(txtMes.getText()));
         registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
         registro.setEstadoPago(txtEstadoPago.getText());
         registro.setCodigoAdministracion(((Administracion)cmbCodigoAdmin.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
         registro.setCodigoLocal(((Locales)cmbCodigoLocales.getSelectionModel().getSelectedItem()).getCodigoLocal());
         registro.setCodigoCliente(((Clientes)cmbCodigoClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
         procedimiento.setInt(1, registro.getCodigoCuentaPorCobrar());
         procedimiento.setString(2, registro.getNumeroFactura());
         procedimiento.setInt(3, registro.getAnio());
         procedimiento.setInt(4, registro.getMes());
         procedimiento.setDouble(5, registro.getValorNetoPago());
         procedimiento.setString(6, registro.getEstadoPago());
         procedimiento.setInt(7, registro.getCodigoAdministracion());
         procedimiento.setInt(8, registro.getCodigoLocal());
         procedimiento.setInt(9, registro.getCodigoCliente());
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
   txtCodigoCuentasCobrar.setEditable(false);
   txtNoFactura.setEditable(false);
   txtAnio.setEditable(false);
   txtMes.setEditable(false);
   txtValorNetoPago.setEditable(false);
   txtEstadoPago.setEditable(false);
   cmbCodigoAdmin.setDisable(true);       
   cmbCodigoClientes.setDisable(true);        
   cmbCodigoLocales.setDisable(true);
   }
   
   public void activarControles(){
   txtCodigoCuentasCobrar.setEditable(false);
   txtNoFactura.setEditable(true);
   txtAnio.setEditable(true);
   txtMes.setEditable(true);
   txtValorNetoPago.setEditable(true);
   txtEstadoPago.setEditable(true);
   cmbCodigoAdmin.setDisable(false);       
   cmbCodigoClientes.setDisable(false);        
   cmbCodigoLocales.setDisable(false);
   }
   
   public void limpiarControles(){
   txtCodigoCuentasCobrar.clear();
   txtNoFactura.clear();
   txtAnio.clear();
   txtMes.clear();
   txtValorNetoPago.clear();
   txtEstadoPago.clear();
   cmbCodigoAdmin.setValue(null);       
   cmbCodigoClientes.setValue(null);        
   cmbCodigoLocales.setValue(null);
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
   
     public void ventanaLocales(){
        escenarioPrincipal.ventanaLocales();
    }
     
}
