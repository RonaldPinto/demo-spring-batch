package com.sii.liquidaciones.model;

import javax.swing.text.DateFormatter;
import java.util.Date;

public class FacturasConsolidadaModel {
    private String nombre;
    private String apellido;
    private String rut;
    private Date fecha;
    private int monto;
    private int numFactura;

    public FacturasConsolidadaModel() {

    }

    public FacturasConsolidadaModel(String nombre, String apellido, String rut, Date fecha, int monto, int numFactura) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.rut = rut;
        this.fecha = fecha;
        this.monto = monto;
        this.numFactura = numFactura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public int getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(int numFactura) {
        this.numFactura = numFactura;
    }
}
