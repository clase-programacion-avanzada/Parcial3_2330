package com.company.model.ordenes;


public class OrdenDomicilio extends Orden {

    private static final double COSTO_POR_KILOMETRO = 3000;
    private String direccion;

    /*2.2 - Punto 4:
    * Un contructor por parámetros para la clase
    * */
    public OrdenDomicilio(int numeroOrden, String direccion) {
        super(numeroOrden);
        this.direccion = direccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public double getDistanciaTotalEnKilometros() {

        return getNumeroOrden() ;
    }

    /*2.2 - Punto 3:
    * Sobreescribir el método getPrecioTotal definido en la clase Orden agregando costos de envío de $3.000
    *   por cada kilómetro (o fracción) de distancia entre la tienda y la dirección de entrega.
    *   Esta distancia la puede calcular con el método getDistanciaTotal
    *   (puede asumir que este método ya está implementado y funciona correctamente)
     */
    @Override
    public int getPrecioTotal() {

        return super.getPrecioTotal() + getCostoEnvio();
    }

    public int getCostoEnvio() {
        return (int) (COSTO_POR_KILOMETRO * getDistanciaTotalEnKilometros());
    }



    @Override
    public String toString() {
        //Creates a string with the contents of the stream separated by comma (", ") in encounter order.
        // and enclosed in brackets using join.
        String consumos = this.consumos.stream()
            .map(consumo -> consumo.toString())
            .reduce("", (subtotal, element) -> subtotal + "\n   " + element);

        return "OrdenDomicilio {" +
            "numeroOrden : " + getNumeroOrden() +
            ",\ndireccion : '" + direccion + '\'' +
            ",\ndistanciaTotalEnKilometros : " + getDistanciaTotalEnKilometros() +
            ",\nprecioTotal : " + getPrecioTotal() +
            ",\nconsumos :  " + "[ " + consumos + "] " +
            "\n}";

    }


}
