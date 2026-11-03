package com.nttdata.cambioexpress.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public record TipoCambio(Moneda moneda, BigDecimal compra, BigDecimal venta, LocalDate fecha) {

    public TipoCambio {
        Objects.requireNonNull(moneda, "La moneda es obligatoria");
        Objects.requireNonNull(compra, "El tipo de compra es obligatorio");
        Objects.requireNonNull(venta, "El tipo de venta es obligatorio");
        Objects.requireNonNull(fecha, "La fecha es obligatoria");
        if (compra.signum() <= 0 || venta.signum() <= 0) {
            throw new IllegalArgumentException("Los tipos de cambio deben ser positivos");
        }
    }
}
