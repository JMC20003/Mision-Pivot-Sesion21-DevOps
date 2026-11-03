package com.nttdata.cambioexpress.domain.port;

import com.nttdata.cambioexpress.domain.model.Moneda;
import com.nttdata.cambioexpress.domain.model.TipoCambio;

import java.util.List;
import java.util.Optional;

public interface TipoCambioRepository {
    List<TipoCambio> findAll();

    Optional<TipoCambio> findByMoneda(Moneda moneda);
}
