/*
 codigoProveedor INT AUTO_INCREMENT NOT NULL,
    NITProveedor VARCHAR(45) NOT NULL,
    servicioPrestado VARCHAR(45) NOT NULL,
    telefonoProveedor VARCHAR(8) NOT NULL,
    direccionProveedor VARCHAR(45) NOT NULL,
    saldoFavor DOUBLE(11,2) NOT NULL,
    saldoContra DOUBLE(11,2) NOT NULL,
    codigoAdministracion INT NOT NULL,
 */
package org.danieljuarez.bean;

public class Proveedores {
    
    public int codigoProveedor;
    public String NITProveedor;
    public String servicioPrestado;
    public String telefonoProveedor;
    public String direccionProveedor;
    public double saldoFavor;
    public double saldoContra;
    public int codigoAdministracion;

    public Proveedores() {
    }

    public Proveedores(int codigoProveedor, String NITProveedor, String servicioPrestado, String telefonoProveedor, String direccionProveedor, double saldoFavor, double saldoContra, int codigoAdministracion) {
        this.codigoProveedor = codigoProveedor;
        this.NITProveedor = NITProveedor;
        this.servicioPrestado = servicioPrestado;
        this.telefonoProveedor = telefonoProveedor;
        this.direccionProveedor = direccionProveedor;
        this.saldoFavor = saldoFavor;
        this.saldoContra = saldoContra;
        this.codigoAdministracion = codigoAdministracion;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public String getNITProveedor() {
        return NITProveedor;
    }

    public void setNITProveedor(String NITProveedor) {
        this.NITProveedor = NITProveedor;
    }

    public String getServicioPrestado() {
        return servicioPrestado;
    }

    public void setServicioPrestado(String servicioPrestado) {
        this.servicioPrestado = servicioPrestado;
    }

    public String getTelefonoProveedor() {
        return telefonoProveedor;
    }

    public void setTelefonoProveedor(String telefonoProveedor) {
        this.telefonoProveedor = telefonoProveedor;
    }

    public String getDireccionProveedor() {
        return direccionProveedor;
    }

    public void setDireccionProveedor(String direccionProveedor) {
        this.direccionProveedor = direccionProveedor;
    }

    public double getSaldoFavor() {
        return saldoFavor;
    }

    public void setSaldoFavor(double saldoFavor) {
        this.saldoFavor = saldoFavor;
    }

    public double getSaldoContra() {
        return saldoContra;
    }

    public void setSaldoContra(double saldoContra) {
        this.saldoContra = saldoContra;
    }

    public int getCodigoAdministracion() {
        return codigoAdministracion;
    }

    public void setCodigoAdministracion(int codigoAdministracion) {
        this.codigoAdministracion = codigoAdministracion;
    }
   
    public String toString(){
        return getCodigoProveedor() + " / " + getNITProveedor();
    }
    
}
