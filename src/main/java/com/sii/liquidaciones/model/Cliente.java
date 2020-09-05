package com.sii.liquidaciones.model;

public class Cliente {
    private String nombre;
    private String apellido;
    private String rut;
    private int edad;
    private String sexo;
    private String direccion;

    public Cliente() {
    }

    public Cliente(String nombre, String apellido, String rut, int edad, String sexo, String direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.rut = rut;
        this.edad = edad;
        this.sexo = sexo;
        this.direccion = direccion;
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
