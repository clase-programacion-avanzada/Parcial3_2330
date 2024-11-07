package com.company.model.orden;

import com.company.model.Consumo;
import com.company.model.Producto;
import com.company.model.TipoOrdenesEnum;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class Orden {

    private int numeroOrden;

    protected List<Consumo> consumos;

    public Orden(int numeroOrden) {
        this.numeroOrden = numeroOrden;
        this.consumos = new ArrayList<>();
    }

    public static Predicate<Orden> getFiltroPorTipoDeOrden(TipoOrdenesEnum tipo) {
        return switch (tipo) {
            case LOCAL -> orden -> orden instanceof OrdenLocal;

            case DOMICILIO -> orden -> orden instanceof OrdenDomicilio;

            default -> orden -> true;
        };
    }

    public int getNumeroOrden() {
        return numeroOrden;
    }

    public List<Consumo> getConsumos() {
        return new ArrayList<>(consumos);
    }

    public int getPrecioTotal() {
        int precioTotal = 0;
        for (Consumo consumo : consumos) {
            precioTotal += consumo.getValorTotal();
        }
        return precioTotal;
    }

    public int getPrecioTotalUsandoStreams() {
        return consumos.stream()
            .reduce(0, (subtotal, consumo) -> subtotal + consumo.getValorTotal(), Integer::sum);
    }


    public List<String> getCodigosDeProductos() {
        return consumos.stream()
            .map(Consumo::getCodigoProducto)
            .toList();
    }

    public int getCantidadDeProducto(String codigoProducto) {
        return consumos.stream()
            .filter(consumo -> consumo.getCodigoProducto().equals(codigoProducto))
            .mapToInt(Consumo::getCantidad)
            .sum();
    }

    public List<Producto> getProductos() {
        return consumos.stream()
            .map(Consumo::getProducto)
            .toList();
    }

    public int getCantidadTotalProducto(String codigo) {
        int total = 0;

        for (Consumo consumo : consumos) {
            if (consumo.getCodigoProducto().equals(codigo)) {
                total+=consumo.getCantidad();
            }
        }

        return total;

        /*return consumos.stream()
            .filter(consumo -> consumo.getCodigoProducto().equals(codigo))
            .mapToInt(Consumo::getCantidad)
            .sum();*/
    }

    public Consumo agregarConsumo(Producto producto, int cantidad, int valorUnitario) {
        Consumo consumo = new Consumo(cantidad, valorUnitario, producto);
        consumos.add(consumo);
        return consumo;
    }

    public static Orden crearOrden(String tipo, int numeroOrden,
                                   String direccion) {
        return switch (tipo) {
            case "local" -> new OrdenLocal(numeroOrden);

            case "domicilio" -> new OrdenDomicilio(numeroOrden, direccion);

            default -> throw new IllegalArgumentException("Tipo de orden inválido");
        };
    }


    @Override
    public String toString() {
        //Print as Json
        return "{" +
            "\"numeroOrden\":" + numeroOrden +
            ", \"consumos\":" + consumos +
            '}';

    }
}
