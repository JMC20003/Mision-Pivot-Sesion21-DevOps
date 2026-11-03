package com.nttdata.cambioexpress.application.service;

import com.nttdata.cambioexpress.application.exception.RecursoNoEncontradoException;
import com.nttdata.cambioexpress.domain.model.Cotizacion;
import com.nttdata.cambioexpress.domain.model.Moneda;
import com.nttdata.cambioexpress.domain.model.Operacion;
import com.nttdata.cambioexpress.domain.model.TipoCambio;
import com.nttdata.cambioexpress.domain.port.CotizacionRepository;
import com.nttdata.cambioexpress.domain.port.TipoCambioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CambioServiceTest {
    private TipoCambioRepository tipoCambioRepository;
    private ConcurrentHashMap<UUID, Cotizacion> cotizaciones;
    private CambioService service;

    @BeforeEach
    void setUp() {
        TipoCambio tipoCambio = new TipoCambio(
                Moneda.USD, new BigDecimal("3.712"), new BigDecimal("3.786"), LocalDate.now());
        tipoCambioRepository = new TipoCambioRepository() {
            @Override
            public List<TipoCambio> findAll() {
                return List.of(tipoCambio);
            }

            @Override
            public Optional<TipoCambio> findByMoneda(Moneda moneda) {
                return moneda == tipoCambio.moneda() ? Optional.of(tipoCambio) : Optional.empty();
            }
        };
        cotizaciones = new ConcurrentHashMap<>();
        CotizacionRepository cotizacionRepository = new CotizacionRepository() {
            @Override
            public Cotizacion save(Cotizacion cotizacion) {
                cotizaciones.put(cotizacion.id(), cotizacion);
                return cotizacion;
            }

            @Override
            public Optional<Cotizacion> findById(UUID id) {
                return Optional.ofNullable(cotizaciones.get(id));
            }
        };
        service = new CambioService(tipoCambioRepository, cotizacionRepository);
    }

    @Test
    void compraClienteAplicaTipoVentaCasaYRedondeaSoles() {
        Cotizacion cotizacion = service.cotizar(
                Moneda.USD, Operacion.COMPRA, new BigDecimal("10"));

        assertEquals(new BigDecimal("3.786"), cotizacion.tipoCambioAplicado());
        assertEquals(new BigDecimal("37.86"), cotizacion.montoSoles());
    }

    @Test
    void ventaClienteAplicaTipoCompraCasa() {
        Cotizacion cotizacion = service.cotizar(
                Moneda.USD, Operacion.VENTA, new BigDecimal("10"));

        assertEquals(new BigDecimal("3.712"), cotizacion.tipoCambioAplicado());
        assertEquals(new BigDecimal("37.12"), cotizacion.montoSoles());
    }

    @Test
    void obtenerCotizacionInexistenteLanzaNotFound() {
        assertThrows(RecursoNoEncontradoException.class,
                () -> service.obtenerCotizacion(UUID.randomUUID()));
    }
}
