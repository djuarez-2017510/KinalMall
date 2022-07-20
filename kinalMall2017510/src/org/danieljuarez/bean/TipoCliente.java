
package org.danieljuarez.bean;


public class TipoCliente {
    
    private int codigoTipoCliente;
    private String descripcion;

    public TipoCliente() {
    }

    public TipoCliente(int codigoTipoCliente, String descripcion) {
        this.codigoTipoCliente = codigoTipoCliente;
        this.descripcion = descripcion;
    }

    public int getCodigoTipoCliente() {
        return codigoTipoCliente;
    }

    public void setCodigoTipoCliente(int codigoTipoCliente) {
        this.codigoTipoCliente = codigoTipoCliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String toString(){
       return getCodigoTipoCliente() + " / " +  getDescripcion(); 
   
   } 
    
    
    
}
