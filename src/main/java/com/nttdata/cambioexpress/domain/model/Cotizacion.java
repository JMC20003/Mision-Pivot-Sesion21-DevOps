package com.nttdata.cambioexpress.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public record Cotizacion(
        String id,
        Moneda moneda,
        Operacion operacion,
        BigDecimal montoMoneda,
        BigDecimal montoSoles,
        BigDecimal tipoCambioAplicado,
        Instant fecha
) {
    public Cotizacion {
        Objects.requireNonNull(id, "El identificador es obligatorio");
        Objects.requireNonNull(moneda, "La moneda es obligatoria");
        Objects.requireNonNull(operacion, "La operación es obligatoria");
        Objects.requireNonNull(montoMoneda, "El monto es obligatorio");
        Objects.requireNonNull(montoSoles, "El monto en soles es obligatorio");
        Objects.requireNonNull(tipoCambioAplicado, "El tipo de cambio aplicado es obligatorio");
        Objects.requireNonNull(fecha, "La fecha es obligatoria");
    }
}
