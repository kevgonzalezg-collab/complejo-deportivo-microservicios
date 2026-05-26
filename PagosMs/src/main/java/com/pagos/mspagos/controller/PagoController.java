package com.pagos.mspagos.controller;

import com.pagos.mspagos.dto.PagoRequestDTO;
import com.pagos.mspagos.dto.PagoResponseDTO;
import com.pagos.mspagos.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @PostMapping
    public PagoResponseDTO procesarPago(@RequestBody PagoRequestDTO request) {

        return pagoService.procesarPago(request);
    }
}