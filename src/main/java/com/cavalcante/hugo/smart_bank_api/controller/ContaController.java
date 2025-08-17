package com.cavalcante.hugo.smart_bank_api.controller;

import com.cavalcante.hugo.smart_bank_api.dto.ContaResponseDTO;
import com.cavalcante.hugo.smart_bank_api.dto.OperacaoRequestDTO;
import com.cavalcante.hugo.smart_bank_api.dto.TransacaoResponseDTO;
import com.cavalcante.hugo.smart_bank_api.dto.TransferenciaRequestDTO;
import com.cavalcante.hugo.smart_bank_api.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/{id}/transacoes")
    public ResponseEntity<Page<TransacaoResponseDTO>> listarTransacoes(
            @PathVariable Long id,
            @PageableDefault(size = 10, sort = "dataHora", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<TransacaoResponseDTO> paginaDeTransacoes = contaService.listarTransacoesPorConta(id, pageable);
        return ResponseEntity.ok(paginaDeTransacoes);
    }
}
