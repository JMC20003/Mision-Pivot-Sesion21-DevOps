package com.nttdata.cambioexpress.domain.port;

import com.nttdata.cambioexpress.domain.model.Cotizacion;

import java.util.Optional;
import java.util.UUID;

public interface CotizacionRepository {
    Cotizacion save(Cotizacion cotizacion);

    Optional<Cotizacion> findById(UUID id);
}
