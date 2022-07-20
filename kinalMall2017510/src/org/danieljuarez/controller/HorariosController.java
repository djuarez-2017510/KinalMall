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
import org.danieljuarez.bean.Horarios;
import org.danieljuarez.db.Conexion;
import org.danieljuarez.system.Principal;

public class HorariosController implements Initializable{
    private enum operaciones{NUEVO, GUARDAR, ACTUALIZAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<Horarios> listaHorarios;
    private ObservableList<Boolean> listacmb;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoHorario;
    @FXML private TextField txtHorarioEntrada;
    @FXML private TextField txtHorarioSalida;
    @FXML private ComboBox cmbLunes;
    @FXML private ComboBox cmbMartes;
    @FXML private ComboBox cmbMiercoles;
    @FXML private ComboBox cmbJueves;       
    @FXML private ComboBox cmbViernes;       
    @FXML private TableView tblHorarios;         
    @FXML private TableColumn colCodigoHorario;       
    @FXML private TableColumn colHorarioEntrada;       
    @FXML private TableColumn colHorarioSalida;       
    @FXML private TableColumn colLunes;       
    @FXML private TableColumn colMartes;
    @FXML private TableColumn colMiercoles;       
    @FXML private TableColumn colJueves;       
    @FXML private TableColumn colViernes;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
            
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Boolean> listaDatos = new ArrayList<>();
        listaDatos.add(Boolean.TRUE);
        listaDatos.add(Boolean.FALSE);
        listacmb = FXCollections.observableArrayList(listaDatos);
        cmbLunes.setItems(listacmb);
        cmbMartes.setItems(listacmb);
        cmbMiercoles.setItems(listacmb);
        cmbJueves.setItems(listacmb);
        cmbViernes.setItems(listacmb);
        cargarDatos();
        desactivarControles();
    }
    
    public void cargarDatos(){
        tblHorarios.setItems(getHorarios());
        colCodigoHorario.setCellValueFactory(new PropertyValueFactory<Horarios,Integer>("codigoHorario"));
        colHorarioEntrada.setCellValueFactory(new PropertyValueFactory<Horarios,String>("horarioEntrada"));
        colHorarioSalida.setCellValueFactory(new PropertyValueFactory<Horarios,String>("horarioSalida"));
        colLunes.setCellValueFactory(new PropertyValueFactory<Horarios,Boolean>("lunes"));
        colMartes.setCellValueFactory(new PropertyValueFactory<Horarios,Boolean>("martes"));
        colMiercoles.setCellValueFactory(new PropertyValueFactory<Horarios,Boolean>("miercoles"));
        colJueves.setCellValueFactory(new PropertyValueFactory<Horarios,Boolean>("jueves"));
        colViernes.setCellValueFactory(new PropertyValueFactory<Horarios,Boolean>("viernes"));
    }
    
    public void seleccionarElemento(){
        if(tblHorarios.getSelectionModel().getSelectedItem() != null){
        txtCodigoHorario.setText(String.valueOf(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).getCodigoHorario()));
        txtHorarioEntrada.setText(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).getHorarioEntrada());
        txtHorarioSalida.setText(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).getHorarioSalida());
        cmbLunes.getSelectionModel().select(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isLunes());
        cmbMartes.getSelectionModel().select(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isMartes());
        cmbMiercoles.getSelectionModel().select(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isMiercoles());
        cmbJueves.getSelectionModel().select(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isJueves());
        cmbViernes.getSelectionModel().select(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isViernes());
        }
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
        Horarios registro = new Horarios();
        registro.setHorarioEntrada(txtHorarioEntrada.getText());
        registro.setHorarioSalida(txtHorarioSalida.getText());
        registro.setLunes(((Boolean)cmbLunes.getSelectionModel().getSelectedItem()));
        registro.setMartes(((Boolean)cmbMartes.getSelectionModel().getSelectedItem()));
        registro.setMiercoles(((Boolean)cmbMiercoles.getSelectionModel().getSelectedItem()));
        registro.setJueves(((Boolean)cmbJueves.getSelectionModel().getSelectedItem()));
        registro.setViernes(((Boolean)cmbViernes.getSelectionModel().getSelectedItem()));
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarHorarios(?,?,?,?,?,?,?)}");
            procedimiento.setString(1, registro.getHorarioEntrada());
            procedimiento.setString(2, registro.getHorarioSalida());
            procedimiento.setBoolean(3, registro.isLunes());
            procedimiento.setBoolean(4, registro.isMartes());
            procedimiento.setBoolean(5, registro.isMiercoles());
            procedimiento.setBoolean(6, registro.isJueves());
            procedimiento.setBoolean(7, registro.isViernes());
            procedimiento.execute();
            listaHorarios.add(registro);
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
                if (tblHorarios.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar el registro?", "Eliminar Horarios", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BorrarHorarios(?)}");
                        procedimiento.setInt(1, ((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).getCodigoHorario());
                        procedimiento.execute();
                        listaHorarios.remove(tblHorarios.getSelectionModel().getSelectedIndex());
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
                if (tblHorarios.getSelectionModel().getSelectedItem() != null){
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
          PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarHorarios(?,?,?,?,?,?,?,?)}");
          Horarios registro = (Horarios)tblHorarios.getSelectionModel().getSelectedItem();
          registro.setHorarioEntrada(txtHorarioEntrada.getText());
          registro.setHorarioSalida(txtHorarioSalida.getText());
          registro.setLunes(((Boolean)cmbLunes.getSelectionModel().getSelectedItem()));
          registro.setMartes(((Boolean)cmbMartes.getSelectionModel().getSelectedItem()));
          registro.setMiercoles(((Boolean)cmbMiercoles.getSelectionModel().getSelectedItem()));
          registro.setJueves(((Boolean)cmbJueves.getSelectionModel().getSelectedItem()));
          registro.setViernes(((Boolean)cmbViernes.getSelectionModel().getSelectedItem()));
            procedimiento.setInt(1, registro.getCodigoHorario());
            procedimiento.setString(2, registro.getHorarioEntrada());
            procedimiento.setString(3, registro.getHorarioSalida());
            procedimiento.setBoolean(4, registro.isLunes());
            procedimiento.setBoolean(5, registro.isMartes());
            procedimiento.setBoolean(6, registro.isMiercoles());
            procedimiento.setBoolean(7, registro.isJueves());
            procedimiento.setBoolean(8, registro.isViernes());
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
        txtCodigoHorario.setEditable(false);
        txtHorarioEntrada.setEditable(false);
        txtHorarioSalida.setEditable(false);
        cmbLunes.setDisable(true);
        cmbMartes.setDisable(true);
        cmbMiercoles.setDisable(true);
        cmbJueves.setDisable(true);
        cmbViernes.setDisable(true);
    }
    
    public void activarControles(){
        txtCodigoHorario.setEditable(false);
        txtHorarioEntrada.setEditable(true);
        txtHorarioSalida.setEditable(true);
        cmbLunes.setDisable(false);
        cmbMartes.setDisable(false);
        cmbMiercoles.setDisable(false);
        cmbJueves.setDisable(false);
        cmbViernes.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCodigoHorario.clear();
        txtHorarioEntrada.clear();
        txtHorarioSalida.clear();
        cmbLunes.setValue(null);
        cmbMartes.setValue(null);
        cmbMiercoles.setValue(null);
        cmbJueves.setValue(null);
        cmbViernes.setValue(null);
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
    
    public void ventanaAdministracion(){
        escenarioPrincipal.ventanaAdministracion();
    }
    
    public void ventanaTipoCliente(){
        escenarioPrincipal.ventanaTipoCliente();
    }
    
}
