package com.nttdata.cambioexpress.domain.model;

import com.nttdata.cambioexpress.domain.exception.ReglaDeNegocioException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TipoCambioTest {
    // TODO RN-2: agregar pruebas unitarias del modelo TipoCambio.
    private final TipoCambio tipoCambio = new TipoCambio(
            Moneda.USD,
            new BigDecimal("3.712"),
            new BigDecimal("3.786"),
            LocalDate.of(2026, 11, 3)
    );

    @Test
    void compraDeDolaresUsaTipoDeVenta() {
        assertEquals(new BigDecimal("378.60"),
                tipoCambio.convertir(Operacion.COMPRA, new BigDecimal("100")));
    }

    @Test
    void ventaDeDolaresUsaTipoDeCompra() {
        assertEquals(new BigDecimal("371.20"),
                tipoCambio.convertir(Operacion.VENTA, new BigDecimal("100")));
    }

    @Test
    void redondeaElResultadoADosDecimales() {
        assertEquals(new BigDecimal("39.94"),
                tipoCambio.convertir(Operacion.COMPRA, new BigDecimal("10.55")));
    }

    @Test
    void rechazaMontoSobreElLimitePorOperacion() {
        ReglaDeNegocioException exception = assertThrows(ReglaDeNegocioException.class,
                () -> tipoCambio.convertir(Operacion.COMPRA, new BigDecimal("10000.01")));

        assertEquals("El monto supera el límite de 10000.00 por operación",
                exception.getMessage());
    }

    @Test
    void rechazaMontoEnCero() {
        ReglaDeNegocioException exception = assertThrows(ReglaDeNegocioException.class,
                () -> tipoCambio.convertir(Operacion.VENTA, BigDecimal.ZERO));

        assertEquals("El monto debe ser mayor que cero", exception.getMessage());
    }

    @Test
    void rechazaMonedaEnFormatoInvalidoAlConstruir() {
        assertThrows(IllegalArgumentException.class,
                () -> new TipoCambio("usd", new BigDecimal("3.712"),
                        new BigDecimal("3.786"), LocalDate.now()));
    }

    @Test
    void rechazaTiposDeCambioInvalidosAlConstruir() {
        assertThrows(IllegalArgumentException.class,
                () -> new TipoCambio(Moneda.USD, BigDecimal.ZERO,
                        new BigDecimal("3.786"), LocalDate.now()));
        assertThrows(IllegalArgumentException.class,
                () -> new TipoCambio(Moneda.USD, new BigDecimal("3.786"),
                        new BigDecimal("3.786"), LocalDate.now()));
        assertThrows(IllegalArgumentException.class,
                () -> new TipoCambio(Moneda.USD, new BigDecimal("3.786"),
                        new BigDecimal("3.712"), LocalDate.now()));
    }
}
