package com.company.model.ordenes;

public class OrdenLocal extends Orden {

    private static final int PORCENTAJE_MAXIMO = 15;
    private static final int PRECIO_PROMOCION = 50000;

    public OrdenLocal(int numeroOrden) {
        super(numeroOrden);
    }

    /*2.2 Punto 2:
    * Sobreescribir el método getPrecioTotal definido en la clase Orden agregando un descuento progresivo de 1%
    por cada 50.000 pesos en compras. El descuento máximo es de 15%
     */
    @Override
    public int getPrecioTotal() {

        int precioTotal = super.getPrecioTotal();

        int porcentajeDescuentoInicial = precioTotal / PRECIO_PROMOCION;

        int porcentajeDeDescuento = porcentajeDescuentoInicial > PORCENTAJE_MAXIMO
            ? PORCENTAJE_MAXIMO
            : porcentajeDescuentoInicial;

        return precioTotal * (1 - porcentajeDeDescuento / 100);

    }

    @Override
    public String toString() {
        //Creates a string with the contents of the stream separated by comma (", ") in encounter order.
        // and enclosed in brackets using join.
        String consumos = this.consumos.stream()
            .map(consumo -> consumo.toString())
            .reduce("", (subtotal, element) -> subtotal + "\n   " + element);

        return "OrdenLocal {" +
            "numeroOrden : " + getNumeroOrden() +
            ",\nprecioTotal : " + getPrecioTotal() +
            ",\nconsumos :  " + "[ " + consumos + "] " +
            "\n}";

    }


}
