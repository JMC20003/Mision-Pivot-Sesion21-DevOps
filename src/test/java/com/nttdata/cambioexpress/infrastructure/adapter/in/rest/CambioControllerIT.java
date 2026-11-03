package com.nttdata.cambioexpress.infrastructure.adapter.in.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CambioControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void listaTipoCambioUsdDelDia() throws Exception {
        mockMvc.perform(get("/api/tipos-cambio"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].moneda", is("USD")))
                .andExpect(jsonPath("$[0].compra", is(3.712)))
                .andExpect(jsonPath("$[0].venta", is(3.786)));
    }

    @Test
    void monedaNoSoportadaDevuelveNotFound() throws Exception {
        mockMvc.perform(get("/api/tipos-cambio/EUR"))
                .andExpect(status().isNotFound());
    }

    @Test
    void creaCotizacionYPermiteConsultarla() throws Exception {
        String respuesta = mockMvc.perform(post("/api/cotizaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"moneda":"USD","operacion":"COMPRA","monto":10}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.montoSoles", is(37.86)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String id = respuesta.replaceAll("(?s).*\"id\":\"([^\"]+)\".*", "$1");

        mockMvc.perform(get("/api/cotizaciones/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.montoSoles", is(37.86)));
    }

    @Test
    void montoNoPositivoDevuelveBadRequest() throws Exception {
        mockMvc.perform(post("/api/cotizaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"moneda":"USD","operacion":"COMPRA","monto":0}
                                """))
                .andExpect(status().isBadRequest());
    }
}
