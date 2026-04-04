package br.com.contadora.contadora_api.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/venda")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VendaUsuario {

    private final br.com.contadora.contadora_api.service.VendaService service;

    public VendaUsuario(br.com.contadora.contadora_api.service.VendaService service) {
        this.service = service;
    }

    @PostMapping
    public org.springframework.http.ResponseEntity<br.com.contadora.contadora_api.dto.VendaDTO> realizarVenda(@jakarta.validation.Valid @org.springframework.web.bind.annotation.RequestBody br.com.contadora.contadora_api.dto.VendaDTO vendaDTO) {
        var vendaSalva = service.realizarVenda(vendaDTO);
        java.net.URI uri = org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(vendaSalva.id()).toUri();
        return org.springframework.http.ResponseEntity.created(uri).body(vendaSalva);
    }

    @GetMapping
    public org.springframework.http.ResponseEntity<java.util.List<br.com.contadora.contadora_api.dto.VendaDTO>> listarHistoricoGeral() {
        return org.springframework.http.ResponseEntity.ok(service.listarHisotricoGeral());
    }

    @GetMapping("/hoje")
    public org.springframework.http.ResponseEntity<java.util.List<br.com.contadora.contadora_api.dto.VendaDTO>> listarVendaDeHoje() {
        return org.springframework.http.ResponseEntity.ok(service.listarVendaDeHoje());
    }

    @GetMapping("/cliente/{clienteId}")
    public org.springframework.http.ResponseEntity<java.util.List<br.com.contadora.contadora_api.dto.VendaDTO>> buscarVendasPorCliente(@org.springframework.web.bind.annotation.PathVariable Long clienteId) {
        return org.springframework.http.ResponseEntity.ok(service.buscarVendasPorCliente(clienteId));
    }

    @GetMapping("/lucro-hoje")
    public org.springframework.http.ResponseEntity<java.math.BigDecimal> calcularLucroDoDia() {
        return org.springframework.http.ResponseEntity.ok(service.calcularLucroDoDia());
    }
}
