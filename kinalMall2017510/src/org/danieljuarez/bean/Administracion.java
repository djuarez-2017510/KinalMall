package org.danieljuarez.bean;


public class Administracion {
    private int codigoAdministracion;
    private String direccion;
    private String telefono;

    public Administracion() {
    }

    public Administracion(int codigoAdministracion, String direccion, String telefono) {
        this.codigoAdministracion = codigoAdministracion;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public int getCodigoAdministracion() {
        return codigoAdministracion;
    }

    public void setCodigoAdministracion(int codigoAdministracion) {
        this.codigoAdministracion = codigoAdministracion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String toString(){
       return getCodigoAdministracion() + " / " + getTelefono();
   
   } 
}
