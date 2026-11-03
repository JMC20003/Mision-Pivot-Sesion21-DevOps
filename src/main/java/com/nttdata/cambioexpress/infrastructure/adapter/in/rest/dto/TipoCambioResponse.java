package com.nttdata.cambioexpress.infrastructure.adapter.in.rest.dto;

import com.nttdata.cambioexpress.domain.model.Moneda;
import com.nttdata.cambioexpress.domain.model.TipoCambio;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TipoCambioResponse(
        Moneda moneda,
        BigDecimal compra,
        BigDecimal venta,
        LocalDate fecha
) {
    public static TipoCambioResponse from(TipoCambio tipoCambio) {
        return new TipoCambioResponse(
                tipoCambio.moneda(),
                tipoCambio.compra(),
                tipoCambio.venta(),
                tipoCambio.fecha()
        );
    }
}
