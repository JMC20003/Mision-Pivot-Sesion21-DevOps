package com.nttdata.cambioexpress.infrastructure.adapter.in.rest.dto;

import com.nttdata.cambioexpress.domain.model.Moneda;
import com.nttdata.cambioexpress.domain.model.Operacion;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CotizacionRequest(
        @NotNull Moneda moneda,
        @NotNull Operacion operacion,
        @NotNull @DecimalMin(value = "0.01") BigDecimal monto
) {
}
