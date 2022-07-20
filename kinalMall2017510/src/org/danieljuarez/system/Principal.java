package org.danieljuarez.system;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.danieljuarez.controller.AdministracionController;
import org.danieljuarez.controller.CargosController;
import org.danieljuarez.controller.ClientesController;
import org.danieljuarez.controller.CuentasPorCobrarController;
import org.danieljuarez.controller.CuentasPorPagarController;
import org.danieljuarez.controller.DepartamentosController;
import org.danieljuarez.controller.EmpleadosController;
import org.danieljuarez.controller.HorariosController;
import org.danieljuarez.controller.LocalesController;
import org.danieljuarez.controller.LoginController;
import org.danieljuarez.controller.MenuPrincipalController;
import org.danieljuarez.controller.ProgramadorController;
import org.danieljuarez.controller.ProveedoresController;
import org.danieljuarez.controller.TipoClienteController;
import org.danieljuarez.controller.UsuarioController;

/**
 *
 * @author hp18la
 */
public class Principal extends Application {
    private final String PAQUETE_VISTA = "/org/danieljuarez/view/"; //Ruta de las vistas
    private Stage escenarioPrincipal;
    private Scene escena;
    
    @Override
    public void start(Stage escenarioPrincipal) throws IOException {
      this.escenarioPrincipal = escenarioPrincipal;
      this.escenarioPrincipal.setTitle("KinalMall 2021");
       ventanaLogin();
      escenarioPrincipal.show();
      
    }

    public void menuPrincipal(){
        try{
            MenuPrincipalController menu = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml", 520, 400);
            menu.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void ventanaProgramador(){
        try{
        
            ProgramadorController programador = (ProgramadorController)cambiarEscena("ProgramadorView.fxml",600,400);
            programador.setEscenarioPrincipal(this);
        
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
   public void ventanaAdministracion(){
       try{
            AdministracionController admin =(AdministracionController)cambiarEscena("AdministracionView.fxml",800,400);
            admin.setEscenarioPrincipal (this);
       }catch(Exception e){
           e.printStackTrace();
       }
   }
   
   public void ventanaTipoCliente(){
       try{
           TipoClienteController tipoCliente = (TipoClienteController)cambiarEscena("TipoClienteView.fxml",800,400);
             tipoCliente.setEscenarioPrincipal(this);
           }catch(Exception e){
           e.printStackTrace();
       }
  
   }
   
   public void ventanaDepartamentos(){
       try{
           DepartamentosController departamento = (DepartamentosController)cambiarEscena("DepartamentosView.fxml",800,400);
             departamento.setEscenarioPrincipal(this);
           }catch(Exception e){
           e.printStackTrace();
       }
  
   }
   
   public void ventanaLocales(){
       try{
           LocalesController local = (LocalesController)cambiarEscena("LocalesView.fxml",950,450);
            local.setEscenarioPrincipal(this);
       }catch(Exception e){
           e.printStackTrace();
       }
   }
   
   public void ventanaClientes(){
       try{
           ClientesController clientes = (ClientesController)cambiarEscena("ClienteView.fxml",1315,450);
            clientes.setEscenarioPrincipal(this);
       }catch(Exception e){
           e.printStackTrace();
       }
   }
   
   public void ventanaProveedores(){
       try{
           ProveedoresController proveedor = (ProveedoresController)cambiarEscena("ProveedoresView.fxml",1315,450);
           proveedor.setEscenarioPrincipal(this);
       }catch(Exception e){
           e.printStackTrace();
       }
   }
   
   public void ventanaCuentasPorPagar(){
       try{
           CuentasPorPagarController cuentas =(CuentasPorPagarController)cambiarEscena("CuentasPorPagarView.fxml",1124,450); 
           cuentas.setEscenarioPrincipal(this);
       }catch(Exception e){
           e.printStackTrace();
       }
   }
   
   public void ventanaHorarios(){
       try{
           HorariosController horario =(HorariosController)cambiarEscena("HorariosView.fxml",1100,450);
           horario.setEscenarioPrincipal(this);
       }catch(Exception e){
           e.printStackTrace();
       }
   }
   
   public void ventanaUsuario(){
       try{
           UsuarioController usuarioController = (UsuarioController)cambiarEscena("UsuarioView.fxml",660,400);
           usuarioController.setEscenarioPrincipal(this);
       }catch(Exception e){
           e.printStackTrace();
       }
   }
   
   public void ventanaLogin(){
       try{
           LoginController loginController = (LoginController)cambiarEscena("LoginView.fxml",500,400);
           loginController.setEscenarioPrincipal(this);
       }catch(Exception e){
           e.printStackTrace();
       }
   }
   
   public void ventanaCargos(){
       try{
           CargosController cargosController = (CargosController)cambiarEscena("CargosView.fxml",745,400);
           cargosController.setEscenarioPrincipal(this);
       }catch(Exception e){
           e.printStackTrace();
       }
   
   }
   
   public void ventanaCuentasPorCobrar(){
       try{
           CuentasPorCobrarController cuentasPorCobrarController = (CuentasPorCobrarController)cambiarEscena("CuentasPorCobrarView.fxml",1125,450);
           cuentasPorCobrarController.setEscenarioPrincipal(this);
       }catch(Exception e){
           e.printStackTrace();
       }
   
   }
   
   public void ventanaEmpleados(){
       try{
           EmpleadosController empleadosController = (EmpleadosController)cambiarEscena("EmpleadosView.fxml",1240,485);
           empleadosController.setEscenarioPrincipal(this);
       }catch(Exception e ){
           e.printStackTrace();
       }
   
  
   }
   
   
   
   
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws IOException{
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
        escena = new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();
        return resultado;
    }

    
}
