package com.sii.liquidaciones.model;

import java.util.Date;

public class FacturasEntradaModel {
    private String nombre;
    private String apellido;
    private String rut;
    private Date fecha;
    private int monto;
    private int factDesde;
    private int factHasta;

    public FacturasEntradaModel() {

    }

    public FacturasEntradaModel(String nombre, String apellido, String rut, Date fecha, int monto, int factDesde, int factHasta) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.rut = rut;
        this.fecha = fecha;
        this.monto = monto;
        this.factDesde = factDesde;
        this.factHasta = factHasta;
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

    public int getFactDesde() {
        return factDesde;
    }

    public void setFactDesde(int factDesde) {
        this.factDesde = factDesde;
    }

    public int getFactHasta() {
        return factHasta;
    }

    public void setFactHasta(int factHasta) {
        this.factHasta = factHasta;
    }
}
