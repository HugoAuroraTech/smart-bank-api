package com.cavalcante.hugo.smart_bank_api.controller;

import com.cavalcante.hugo.smart_bank_api.dto.ContaResponseDTO;
import com.cavalcante.hugo.smart_bank_api.dto.OperacaoRequestDTO;
import com.cavalcante.hugo.smart_bank_api.dto.TransferenciaRequestDTO;
import com.cavalcante.hugo.smart_bank_api.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService){
        this.contaService = contaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaResponseDTO> buscarConta(@PathVariable Long id){
        ContaResponseDTO responseDTO = contaService.buscarConta(id);

        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/{id}/depositar")
    public ResponseEntity<ContaResponseDTO> depositar(@PathVariable Long id, @Valid @RequestBody OperacaoRequestDTO requestDTO){
        ContaResponseDTO responseDTO = contaService.depositar(id, requestDTO);

        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/{id}/sacar")
    public ResponseEntity<ContaResponseDTO> sacar(@PathVariable Long id, @Valid @RequestBody OperacaoRequestDTO requestDTO){
        ContaResponseDTO responseDTO = contaService.sacar(id, requestDTO);

        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/transferir")
    public ResponseEntity transferir(@Valid @RequestBody TransferenciaRequestDTO transferenciaRequestDTO){
        contaService.transferencia(transferenciaRequestDTO);
        return ResponseEntity.noContent().build();
    }
}
