package com.nttdata.cambioexpress.infrastructure.adapter.out.memory;

import com.nttdata.cambioexpress.domain.model.Moneda;
import com.nttdata.cambioexpress.domain.model.TipoCambio;
import com.nttdata.cambioexpress.domain.port.TipoCambioRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class TipoCambioInMemoryAdapter implements TipoCambioRepository {
    private final List<TipoCambio> tiposCambio = List.of(
            new TipoCambio(
                    Moneda.USD,
                    new BigDecimal("3.712"),
                    new BigDecimal("3.786"),
                    LocalDate.now()
            )
    );

    @Override
    public List<TipoCambio> findAll() {
        return tiposCambio;
    }

    @Override
    public Optional<TipoCambio> findByMoneda(Moneda moneda) {
        return tiposCambio.stream()
                .filter(tipoCambio -> tipoCambio.moneda() == moneda)
                .findFirst();
    }
}
