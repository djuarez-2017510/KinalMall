package org.danieljuarez.bean;


public class Cargos {
    private int codigoCargo;
    private String nombreCargo;

    public Cargos() {
    }

    public Cargos(int codigoCargo, String nombreCargo) {
        this.codigoCargo = codigoCargo;
        this.nombreCargo = nombreCargo;
    }

    public int getCodigoCargo() {
        return codigoCargo;
    }

    public void setCodigoCargo(int codigoCargo) {
        this.codigoCargo = codigoCargo;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }
    
    public String toString(){
       return getCodigoCargo() + " / " + getNombreCargo();
   
   } 
    
}

