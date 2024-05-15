package com.company.model;

import java.io.Serializable;

//Siempre que una clase se vaya a escribir en un archivo binario,
// se debe implementar la interfaz Serializable
public class Producto implements Serializable {

    private String codigo;
    private String descripcion;

    public Producto(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setCodigo(String codigo) {
        if (codigo == null || codigo.isEmpty()) {
            throw new IllegalArgumentException("Codigo no puede ser nulo o vacio");
        }
        this.codigo = codigo;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.isEmpty()) {
            throw new IllegalArgumentException("Descripcion no puede ser nulo o vacio");
        }
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Producto{" +
            "codigo='" + codigo + '\'' +
            ", descripcion='" + descripcion + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Producto) {
            Producto producto = (Producto) obj;
            return this.codigo.equals(producto.getCodigo());
        }
        return false;
    }
}
