package com.nttdata.cambioexpress.domain.model;

import com.nttdata.cambioexpress.domain.exception.ReglaDeNegocioException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

public record TipoCambio(Moneda moneda, BigDecimal compra, BigDecimal venta, LocalDate fecha) {
    private static final BigDecimal MONTO_MAXIMO = new BigDecimal("10000.00");
    private static final int ESCALA_MONETARIA = 2;

    public TipoCambio(String moneda, BigDecimal compra, BigDecimal venta, LocalDate fecha) {
        this(parseMoneda(moneda), compra, venta, fecha);
    }

    public TipoCambio {
        Objects.requireNonNull(moneda, "La moneda es obligatoria");
        Objects.requireNonNull(compra, "El tipo de compra es obligatorio");
        Objects.requireNonNull(venta, "El tipo de venta es obligatorio");
        Objects.requireNonNull(fecha, "La fecha es obligatoria");
        if (compra.signum() <= 0 || venta.compareTo(compra) <= 0) {
            throw new IllegalArgumentException(
                    "El tipo de venta debe ser mayor que el tipo de compra y ambos deben ser positivos");
        }
    }

    public BigDecimal convertir(Operacion operacion, BigDecimal monto) {
        if (monto == null || monto.signum() <= 0) {
            throw new ReglaDeNegocioException("El monto debe ser mayor que cero");
        }
        if (monto.compareTo(MONTO_MAXIMO) > 0) {
            throw new ReglaDeNegocioException(
                    "El monto supera el límite de 10000.00 por operación");
        }
        if (operacion == null) {
            throw new IllegalArgumentException("La operación es obligatoria");
        }

        BigDecimal tasaAplicada = operacion == Operacion.COMPRA ? venta : compra;
        return monto.multiply(tasaAplicada)
                .setScale(ESCALA_MONETARIA, RoundingMode.HALF_UP);
    }

    private static Moneda parseMoneda(String moneda) {
        try {
            return Moneda.valueOf(Objects.requireNonNull(moneda, "La moneda es obligatoria"));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("La moneda no es válida: " + moneda, exception);
        }
    }
}
