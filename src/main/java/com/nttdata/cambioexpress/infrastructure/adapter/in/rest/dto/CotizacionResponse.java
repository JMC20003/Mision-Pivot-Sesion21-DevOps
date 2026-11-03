package com.nttdata.cambioexpress.infrastructure.adapter.in.rest.dto;

import com.nttdata.cambioexpress.domain.model.Cotizacion;
import com.nttdata.cambioexpress.domain.model.Moneda;
import com.nttdata.cambioexpress.domain.model.Operacion;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record CotizacionResponse(
        UUID id,
        Moneda moneda,
        Operacion operacion,
        BigDecimal montoMoneda,
        BigDecimal montoSoles,
        BigDecimal tipoCambioAplicado,
        Instant fecha
) {
    public static CotizacionResponse from(Cotizacion cotizacion) {
        return new CotizacionResponse(
                cotizacion.id(),
                cotizacion.moneda(),
                cotizacion.operacion(),
                cotizacion.montoMoneda(),
                cotizacion.montoSoles(),
                cotizacion.tipoCambioAplicado(),
                cotizacion.fecha()
        );
    }
}
