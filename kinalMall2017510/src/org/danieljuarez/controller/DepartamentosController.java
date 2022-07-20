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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.danieljuarez.bean.Departamentos;
import org.danieljuarez.db.Conexion;
import org.danieljuarez.system.Principal;

public class DepartamentosController implements Initializable {
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<Departamentos> listaDepartamentos;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoDepartamento;
    @FXML private TextField txtNombreDepartamento;
    @FXML private TableView tblDepartamentos;
    @FXML private TableColumn colCodigoDepartamento;
    @FXML private TableColumn colNombreDepartamento;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
     public void cargarDatos(){
        tblDepartamentos.setItems(getDepartamentos());
        colCodigoDepartamento.setCellValueFactory(new PropertyValueFactory<Departamentos,Integer>("codigoDepartamento"));
        colNombreDepartamento.setCellValueFactory(new PropertyValueFactory<Departamentos,String>("nombreDepartamento"));
        
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
     
     public void seleccionarElemento(){
         if(tblDepartamentos.getSelectionModel().getSelectedItem() != null){
        txtCodigoDepartamento.setText(String.valueOf(((Departamentos)tblDepartamentos.getSelectionModel().getSelectedItem()).getCodigoDepartamento()));
        txtNombreDepartamento.setText(((Departamentos)tblDepartamentos.getSelectionModel().getSelectedItem()).getNombreDepartamento());
    } 
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
        Departamentos registro = new Departamentos();
        registro.setNombreDepartamento(txtNombreDepartamento.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarDepartamentos(?)}");
            procedimiento.setString(1, registro.getNombreDepartamento());
            procedimiento.execute();
            listaDepartamentos.add(registro);
            
            
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
                if (tblDepartamentos.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar el registro?", "Eliminar Departamentos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BorrarDepartamentos(?)}");
                        procedimiento.setInt(1, ((Departamentos)tblDepartamentos.getSelectionModel().getSelectedItem()).getCodigoDepartamento());
                        procedimiento.execute();
                        listaDepartamentos.remove(tblDepartamentos.getSelectionModel().getSelectedIndex());
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
                if (tblDepartamentos.getSelectionModel().getSelectedItem() != null){
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
         
         PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarDepartamentos(?,?)}");
         Departamentos registro = (Departamentos)tblDepartamentos.getSelectionModel().getSelectedItem();
         registro.setNombreDepartamento(txtNombreDepartamento.getText());
         procedimiento.setInt(1, registro.getCodigoDepartamento());
         procedimiento.setString(2, registro.getNombreDepartamento());
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
        txtCodigoDepartamento.setEditable(false);
        txtNombreDepartamento.setEditable(false);
         
    }
    
    public void activarControles(){
        txtCodigoDepartamento.setEditable(false);
        txtNombreDepartamento.setEditable(true);
          
    }
    
    public void limpiarControles(){
        txtCodigoDepartamento.clear();
        txtNombreDepartamento.clear();
        
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
   
    
}
