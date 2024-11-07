package com.company.model;

public class Consumo {

    private int cantidad;
    private int valorUnitario;
    private Producto producto;

    public Consumo(int cantidad, int valorUnitario, Producto producto) {
        this.cantidad = cantidad;
        this.valorUnitario = valorUnitario;
        this.producto = producto;
    }



    public int getCantidad() {
        return cantidad;
    }

    public int getValorUnitario() {
        return valorUnitario;
    }

    public String getCodigoProducto() {
        return producto.getCodigo();
    }

    public Producto getProducto() {
        return producto;
    }

    public int getValorTotal() {
        return cantidad * valorUnitario;
    }

    @Override
    public String toString() {
        return "Consumo {" +
            "cantidad=" + cantidad +
            ", valorUnitario=" + valorUnitario +
            ", producto=" + producto +
            '}';
    }
}
