package com.company;

import com.company.model.Consumo;
import com.company.model.Producto;
import com.company.model.Tienda;
import com.company.model.TipoOrdenesEnum;
import com.company.model.orden.Orden;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {


        /*Codigo que prueba la tienda*/
        Tienda tienda = new Tienda();

        Producto producto1 = tienda.agregarProducto("001", "Producto 1");
        Producto producto2 = tienda.agregarProducto("002", "Producto 2");
        Producto producto3 = tienda.agregarProducto("003", "Producto 3");
        Producto producto4 = tienda.agregarProducto("004", "Producto 4");
        Producto producto5 = tienda.agregarProducto("005", "Producto 5");
        Producto producto6 = tienda.agregarProducto("006", "Producto 6");
        Producto producto7 = tienda.agregarProducto("007", "Producto 7");
        Producto producto8 = tienda.agregarProducto("008", "Producto 8");
        Producto producto9 = tienda.agregarProducto("009", "Producto 9");
        Producto producto10 = tienda.agregarProducto("010", "Producto 10");

        List<Producto> productosPrueba = List.of(
            producto1,
            producto2,
            producto3,
            producto4,
            producto5,
            producto6,
            producto7,
            producto8,
            producto9,
            producto10
        );

        try {
            tienda.guardarProductos("productos.bin");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error guardando productos: " + e.getMessage());
        }

        tienda.limpiarProductos();
        //Ejemplo de punto 1
        try {
            tienda.importarProductos("productos.txt");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error importando productos: " + e.getMessage());
        }

        String domicilio = "domicilio";
        //Agrega 4 ordenes de domicilio
        Orden ordenDomicilio1 = tienda.agregarOrden(domicilio, 1, "Calle 1");
        Orden ordenDomicilio2 = tienda.agregarOrden(domicilio, 2, "Calle 2");
        Orden ordenDomicilio3 = tienda.agregarOrden(domicilio, 3, "Calle 3");
        Orden ordenDomicilio4 = tienda.agregarOrden(domicilio, 4, "Calle 4");

        String local = "local";
        //Agrega 4 ordenes de local
        Orden ordenLocal1 = tienda.agregarOrden(local, 5, "");
        Orden ordenLocal2 = tienda.agregarOrden(local, 6, "");
        Orden ordenLocal3 = tienda.agregarOrden(local, 7, "");
        Orden ordenLocal4 = tienda.agregarOrden(local, 8, "");

        List<Orden> ordenesPrueba = List.of(
            ordenDomicilio1,
            ordenDomicilio2,
            ordenDomicilio3,
            ordenDomicilio4,
            ordenLocal1,
            ordenLocal2,
            ordenLocal3,
            ordenLocal4
        );

        //Agregar consumos creados aleatoriamente a las ordenes
        List<Consumo> consumos = crearConsumosAleatorios(productosPrueba, ordenesPrueba);

        for (Consumo consumo: consumos) {
            Orden orden = ordenesPrueba.get((int) (Math.random() * ordenesPrueba.size()));
            tienda.agregarConsumoAOrden(orden.getNumeroOrden(),
                consumo.getProducto(),
                consumo.getCantidad(),
                consumo.getValorUnitario());
        }

        System.out.println("Ordenes guardadas en el sistema : ");
        ordenesPrueba.forEach(orden -> System.out.println(orden));
        System.out.println("Ordenes con precio total: ");
        //Prueba de punto 2 y 3, acá no se cumple la asignación de responsabilidades, pero sirve para mostrarlo
        ordenesPrueba.forEach(orden -> System.out.println(orden.getNumeroOrden() + " : " + orden.getPrecioTotal()));

        System.out.println("Calculo total: ");

        //Prueba punto 5
        System.out.println("Total: " + tienda.getTotalVentas());
        System.out.println("Total usando streams y enums: " + tienda.getIngresosPorTipoDeOrden(TipoOrdenesEnum.TODAS));

        //Prueba punto 6
        System.out.println("Ingreso Costos de envio: " + tienda.getIngresoDomicilio());
        System.out.println("Ingreso local: " + tienda.getIngresosPorTipoDeOrden(TipoOrdenesEnum.LOCAL));
        System.out.println("Ingreso Domicilio: " + tienda.getIngresosPorTipoDeOrden(TipoOrdenesEnum.DOMICILIO));
        //Prueba punto 7
        System.out.println("Mas vendido: " + tienda.getMasVendido());



    }

    private static List<Consumo> crearConsumosAleatorios(List<Producto> productosPrueba, List<Orden> ordenesPrueba) {
        //crea 4 consumos aleatorios para cada orden
        List<Consumo> consumos = new ArrayList<>();
        Random random = new Random();

        for (Orden orden: ordenesPrueba) {
            for (int i = 0; i < 4; i++) {
                int cantidad = random.nextInt(1,10);
                int valorUnitario = random.nextInt(1,10) * 10000;
                int indiceProducto = random.nextInt(productosPrueba.size());
                Producto producto = productosPrueba.get(indiceProducto);
                consumos.add(new Consumo(cantidad, valorUnitario, producto));
            }
        }

        return consumos;

    }
}