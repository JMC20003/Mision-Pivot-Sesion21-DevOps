package com.nttdata.cambioexpress.domain.port;

import com.nttdata.cambioexpress.domain.model.Cotizacion;

import java.util.Optional;

public interface CotizacionRepository {
    Cotizacion save(Cotizacion cotizacion);

    Optional<Cotizacion> findById(String id);
}
