package com.cavalcante.hugo.smart_bank_api.controller;

import com.cavalcante.hugo.smart_bank_api.dto.ClienteRequestDTO;
import com.cavalcante.hugo.smart_bank_api.dto.ClienteResponseDTO;
import com.cavalcante.hugo.smart_bank_api.dto.ContaRequestDTO;
import com.cavalcante.hugo.smart_bank_api.dto.ContaResponseDTO;
import com.cavalcante.hugo.smart_bank_api.service.ClienteService;
import com.cavalcante.hugo.smart_bank_api.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService clienteService;
    private final ContaService contaService;

    public ClienteController(ClienteService clienteService, ContaService contaService){
        this.clienteService = clienteService;
        this.contaService = contaService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> criarCliente(@Valid @RequestBody ClienteRequestDTO cliente){
        ClienteResponseDTO responseDTO = clienteService.criarCliente(cliente);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarCliente(@Valid @PathVariable Long id){
        ClienteResponseDTO responseDTO = clienteService.buscarCliente(id);

        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/{clienteId}/contas")
    public ResponseEntity<ContaResponseDTO> criarConta(@PathVariable Long clienteId, @Valid @RequestBody ContaRequestDTO requestDTO){
        ContaResponseDTO responseDTO = contaService.criarConta(clienteId, requestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/contas/{id}").buildAndExpand(responseDTO.id()).toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }
}
