package com.nttdata.cambioexpress.infrastructure.adapter.out.memory;

import com.nttdata.cambioexpress.domain.model.Cotizacion;
import com.nttdata.cambioexpress.domain.port.CotizacionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class CotizacionInMemoryAdapter implements CotizacionRepository {
    private final ConcurrentMap<String, Cotizacion> cotizaciones = new ConcurrentHashMap<>();

    @Override
    public Cotizacion save(Cotizacion cotizacion) {
        cotizaciones.put(cotizacion.id(), cotizacion);
        return cotizacion;
    }

    @Override
    public Optional<Cotizacion> findById(String id) {
        return Optional.ofNullable(cotizaciones.get(id));
    }
}
