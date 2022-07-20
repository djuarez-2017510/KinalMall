/*
 codigoDepartamento INT AUTO_INCREMENT NOT NULL,
    nombreDepartamento VARCHAR(45) NOT NULL,
    PRIMARY KEY PK_codigoDepartamento (codigoDepartamento)
 */
package org.danieljuarez.bean;

public class Departamentos {
    private int codigoDepartamento;
    private String nombreDepartamento;

    public Departamentos() {
    }

    public Departamentos(int codigoDepartamento, String nombreDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
        this.nombreDepartamento = nombreDepartamento;
    }

    public int getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(int codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }
   
      public String toString(){
       return getCodigoDepartamento() + " / " + getNombreDepartamento();
   
   } 
    
}
