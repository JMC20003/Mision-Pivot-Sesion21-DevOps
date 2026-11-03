package com.nttdata.cambioexpress.infrastructure.adapter.in.rest.dto;

import com.nttdata.cambioexpress.domain.model.Cotizacion;
import com.nttdata.cambioexpress.domain.model.Moneda;
import com.nttdata.cambioexpress.domain.model.Operacion;

import java.math.BigDecimal;

public record CotizacionResponse(
        String id,
        Moneda moneda,
        Operacion operacion,
        BigDecimal monto,
        BigDecimal tipoCambio,
        BigDecimal montoSoles
) {
    public static CotizacionResponse from(Cotizacion cotizacion) {
        return new CotizacionResponse(
                cotizacion.id(),
                cotizacion.moneda(),
                cotizacion.operacion(),
                cotizacion.montoMoneda(),
                cotizacion.tipoCambioAplicado(),
                cotizacion.montoSoles()
        );
    }
}
