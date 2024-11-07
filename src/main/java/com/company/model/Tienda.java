package com.company.model;

import com.company.model.orden.Orden;
import com.company.model.orden.OrdenDomicilio;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Tienda {
    List<Producto> productos;
    List<Orden> ordenes;

    public Tienda() {
        this.productos = new ArrayList<>();
        this.ordenes = new ArrayList<>();
    }


    public void  guardarProductos(String rutaArchivo) throws IOException, ClassNotFoundException {
        /*
        File archivo = new File(rutaArchivo);
        try (FileOutputStream fos = new FileOutputStream(archivo);
             ObjectOutputStream oos = new ObjectOutputStream(fos)){

            oos.writeObject(productos);


        }*/


        File archivo = new File(rutaArchivo);

        for (Producto producto : productos) {
            try (FileOutputStream fos = new FileOutputStream(archivo);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)){
                oos.writeObject(producto);
            }
        }


    }

    /*2.2 Punto 1 - Un método que importe todos los productos de la tienda desde un archivo serializado.
     Este método recibe como único parámetro la ruta a un archivo que contiene una secuencia serializada de productos.
     Estos productos están uno por uno en el archivo.
     Este método debe arrojar una excepción si encuentra productos con código repetido en el archivo.
     En este caso, la lista de productos debe quedar vacía.
     */
    public void  importarProductos(String rutaArchivo) throws IOException, ClassNotFoundException {
        /*
        File archivo = new File(rutaArchivo);

        try (FileInputStream fos = new FileInputStream(archivo);
             ObjectInputStream oos = new ObjectInputStream(fos)){
            List<Producto> productosDeArchivoBinario =
                (ArrayList<Producto>) oos.readObject();

            if(!tieneProductosConCodigosDuplicados(productosDeArchivoBinario)){
                this.productos = productosDeArchivoBinario;
            } else {
                this.productos = new ArrayList<>();
            }

        }
        */

        File archivo = new File(rutaArchivo);

        try (FileInputStream fis = new FileInputStream(archivo);
             ObjectInputStream ois = new ObjectInputStream(fis)){
            Producto producto = (Producto) ois.readObject();
            productos.add(producto);

        }

        if (tieneProductosConCodigosDuplicadosUsandoSet(productos)) {
            productos.clear();
            throw new IllegalArgumentException("El archivo contiene productos con códigos duplicados");
        }

    }

    private boolean tieneProductosConCodigosDuplicados(List<Producto> productosDeArchivoBinario) {

        for (Producto producto: productosDeArchivoBinario) {
            for (Producto producto2: productosDeArchivoBinario) {
                if(producto.equals(producto2)){
                    return true;
                }
            }
        }

        return false;

    }

    private boolean tieneProductosConCodigosDUplicadosUsandoStreams(List<Producto> productosDeArchivoBinario) {

        return productosDeArchivoBinario.stream()
            .anyMatch(producto ->
                productosDeArchivoBinario.stream()
                    .anyMatch(producto2 -> producto.equals(producto2)));

    }

    private boolean tieneProductosConCodigosDuplicadosUsandoSet(List<Producto> productosDeArchivoBinario) {

        Set<Producto> productosSinDuplicados = new HashSet<>(productosDeArchivoBinario);

        return productosSinDuplicados.size() != productosDeArchivoBinario.size();


    }

    /*2.2 - Punto 5
    * Un método getTotalVentas que retorne la sumatoria de precios totales de todas las órdenes del sistema
    * */
    public int getTotalVentas() {

        int totalVentas = 0;

        for (Orden orden: ordenes) {
            totalVentas += orden.getPrecioTotal();
        }

        return totalVentas;

    }

    public int getTotalVentasUsandoStreams() {

        return ordenes.stream()
            .mapToInt(Orden::getPrecioTotal)
            .sum();

    }

    /*2.2 - Punto 6
    * Un método getIngresoDomicilio que retorne cuánto dinero ha ganado la tienda por los costos
    * de envío de las órdenes a domicilio
    *
    * */
    public int getIngresoDomicilio() {

        int ingresoDomicilio = 0;

        for (Orden orden: ordenes) {
            if(orden instanceof OrdenDomicilio) {
                ingresoDomicilio += ((OrdenDomicilio) orden).getCostoEnvio();
            }
        }

        return ingresoDomicilio;

    }

    public int getIngresosPorTipoDeOrden(TipoOrdenesEnum tipo) {

        Predicate <Orden> filtroPorTipoDeOrden = Orden.getFiltroPorTipoDeOrden(tipo);

        return getIngresosUsandoStreams(filtroPorTipoDeOrden);

    }

    public int getIngresosUsandoStreams(Predicate<Orden> filtroPorTipoDeOrden) {

        return ordenes.stream()
            .filter(filtroPorTipoDeOrden)
            .mapToInt(Orden::getPrecioTotal)
            .sum();

    }

    /*2.2 - Punto 7
    * Un método getMasVendido que retorne el código del producto más vendido
    * (i.e., del que se vendió la mayor cantidad de unidades) en la tienda o null si no hay órdenes registradas
    **/
    public String getMasVendido() {


        String codigoProductoMasVendido = null;
        int cantidadMasVendida = 0;

        for (Orden orden: ordenes) {
            for (String codigoProducto: orden.getCodigosDeProductos()) {
                int cantidadProducto = orden.getCantidadTotalProducto(codigoProducto);
                if(cantidadProducto > cantidadMasVendida) {
                    cantidadMasVendida = cantidadProducto;
                    codigoProductoMasVendido = codigoProducto;
                }
            }
        }

        return codigoProductoMasVendido;

    }

    public String getMasVendidoUsandoMaps() {

        if(ordenes.isEmpty()){
            return null;
        }

        Map<String, Integer> cantidadesPorProducto = new HashMap<>();

        for (Orden orden: ordenes) {
            for (Consumo consumo: orden.getConsumos()) {
                String codigoProducto = consumo.getCodigoProducto();
                int cantidadProducto = consumo.getCantidad();
                int cantidadProductoEnOrden = cantidadesPorProducto.getOrDefault(codigoProducto, 0);
                cantidadesPorProducto.put(codigoProducto, cantidadProductoEnOrden + cantidadProducto);
            }
        }

        return obtenerCodigoProductoMasVendido(cantidadesPorProducto);


    }

    private String obtenerCodigoProductoMasVendido(Map<String, Integer> cantidadesPorProducto) {
        return cantidadesPorProducto.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .get()
            .getKey();
    }

    public String getMasVendidoUsandoMapsYStreams() {

        if(ordenes.isEmpty()){
            return null;
        }

        Map<String, Integer> cantidadesPorProducto = ordenes.stream()
            .flatMap(orden -> orden.getConsumos().stream())
            .map(consumo -> Map.entry(consumo.getCodigoProducto(), consumo.getCantidad()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum));

        return obtenerCodigoProductoMasVendido(cantidadesPorProducto);


    }

    public Producto agregarProducto(String Codigo, String Descripcion) {
        Producto producto = new Producto(Codigo, Descripcion);
        productos.add(producto);
        return producto;
    }

    public Orden agregarOrden(String tipo,
                             int numeroOrden,
                             String direccion) {

        Orden orden = Orden.crearOrden(tipo, numeroOrden, direccion);
        ordenes.add(orden);
        return orden;
    }

    public Consumo agregarConsumoAOrden(int numeroOrden,
                                        Producto producto,
                                        int cantidad,
                                        int valorUnitario) {
        Orden orden = buscarOrden(numeroOrden);
        return orden.agregarConsumo(producto, cantidad, valorUnitario);
    }


    private Orden buscarOrden(int numeroOrden) {
        for (Orden orden : ordenes) {
            if (orden.getNumeroOrden() == numeroOrden) {
                return orden;
            }
        }
        throw new IllegalArgumentException("No existe una orden con el número indicado");
    }

    public void limpiarOrdenes() {
        ordenes.clear();
    }

    public void limpiarProductos() {
        productos.clear();
    }


}
