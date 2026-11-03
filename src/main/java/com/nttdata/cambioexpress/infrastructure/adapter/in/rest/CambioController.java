package com.nttdata.cambioexpress.infrastructure.adapter.in.rest;

import com.nttdata.cambioexpress.application.exception.RecursoNoEncontradoException;
import com.nttdata.cambioexpress.application.service.CambioService;
import com.nttdata.cambioexpress.domain.model.Moneda;
import com.nttdata.cambioexpress.infrastructure.adapter.in.rest.dto.CotizacionRequest;
import com.nttdata.cambioexpress.infrastructure.adapter.in.rest.dto.CotizacionResponse;
import com.nttdata.cambioexpress.infrastructure.adapter.in.rest.dto.TipoCambioResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class CambioController {
    private final CambioService cambioService;

    public CambioController(CambioService cambioService) {
        this.cambioService = cambioService;
    }

    @GetMapping("/tipos-cambio")
    public List<TipoCambioResponse> listarTiposCambio() {
        return cambioService.listarTiposCambio().stream()
                .map(TipoCambioResponse::from)
                .toList();
    }

    @GetMapping("/tipos-cambio/{moneda}")
    public TipoCambioResponse obtenerTipoCambio(@PathVariable String moneda) {
        return TipoCambioResponse.from(cambioService.obtenerTipoCambio(parseMoneda(moneda)));
    }

    @PostMapping("/cotizaciones")
    @ResponseStatus(HttpStatus.CREATED)
    public CotizacionResponse cotizar(@Valid @RequestBody CotizacionRequest request) {
        return CotizacionResponse.from(
                cambioService.cotizar(request.moneda(), request.operacion(), request.monto()));
    }

    @GetMapping("/cotizaciones/{id}")
    public CotizacionResponse obtenerCotizacion(@PathVariable UUID id) {
        return CotizacionResponse.from(cambioService.obtenerCotizacion(id));
    }

    private Moneda parseMoneda(String moneda) {
        try {
            return Moneda.valueOf(moneda.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new RecursoNoEncontradoException("No existe tipo de cambio para la moneda " + moneda);
        }
    }
}
