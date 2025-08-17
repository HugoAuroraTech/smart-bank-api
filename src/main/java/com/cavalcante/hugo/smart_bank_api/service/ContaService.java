package com.cavalcante.hugo.smart_bank_api.service;

import com.cavalcante.hugo.smart_bank_api.dto.*;
import com.cavalcante.hugo.smart_bank_api.exceptions.ClienteNaoEncontradoException;
import com.cavalcante.hugo.smart_bank_api.exceptions.ContaNaoEncontradaException;
import com.cavalcante.hugo.smart_bank_api.model.Cliente;
import com.cavalcante.hugo.smart_bank_api.model.Conta;
import com.cavalcante.hugo.smart_bank_api.model.Transacao;
import com.cavalcante.hugo.smart_bank_api.model.enums.TipoTransacao;
import com.cavalcante.hugo.smart_bank_api.repository.ClienteRepositoy;
import com.cavalcante.hugo.smart_bank_api.repository.ContaRepository;
import com.cavalcante.hugo.smart_bank_api.repository.TransacaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final ClienteRepositoy clienteRepositoy;
    private final TransacaoRepository transacaoRepository;

    public ContaService(ContaRepository contaRepository, ClienteRepositoy clienteRepositoy, TransacaoRepository transacaoRepository){
        this.contaRepository = contaRepository;
        this.clienteRepositoy = clienteRepositoy;
        this.transacaoRepository = transacaoRepository;
    }

    @Transactional
    public ContaResponseDTO criarConta(Long clienteId, ContaRequestDTO requestDTO){
        Cliente cliente = clienteRepositoy.findById(clienteId)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado com ID: " + clienteId));

        Conta contaNova = new Conta();
        contaNova.setAgencia(requestDTO.agencia());
        contaNova.setNumeroConta(gerarNumeroDaConta());
        contaNova.setSaldo(BigDecimal.ZERO);
        contaNova.setCliente(cliente);

        Conta contaSalva = contaRepository.save(contaNova);

        return ContaResponseDTO.fromEntity(contaSalva);
    }

    @Transactional(readOnly = true)
    public ContaResponseDTO buscarConta(Long contaId){
        return contaRepository.findById(contaId)
                .map(ContaResponseDTO::fromEntity)
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada com ID: " + contaId));
    }

    @Transactional
    public ContaResponseDTO depositar(Long contaId, OperacaoRequestDTO operacaoRequestDTO){
        Conta contaEncontrada = contaRepository.findById(contaId).orElseThrow(() -> new ContaNaoEncontradaException("Conta não econtrada com ID: " + contaId));

        BigDecimal valorDeposito = operacaoRequestDTO.valor();
        if(valorDeposito.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Valor de deposito inválido");
        }

        BigDecimal novoSaldo = contaEncontrada.getSaldo().add(valorDeposito);
        contaEncontrada.setSaldo(novoSaldo);

        Conta contaSalva = contaRepository.save(contaEncontrada);

        Transacao transacao = new Transacao();

        transacao.setConta(contaSalva);
        transacao.setTipo(TipoTransacao.DEPOSITO);
        transacao.setDataHora(LocalDateTime.now());
        transacao.setValor(valorDeposito);
        transacaoRepository.save(transacao);

        return ContaResponseDTO.fromEntity(contaSalva);
    }

    @Transactional
    public  ContaResponseDTO sacar(Long contaId, OperacaoRequestDTO operacaoRequestDTO){
        Conta contaEncontrada = contaRepository.findById(contaId).orElseThrow(() -> new ContaNaoEncontradaException("Conta não econtrada com ID: " + contaId));

        BigDecimal valorSaque =operacaoRequestDTO.valor();

        if(valorSaque.compareTo(contaEncontrada.getSaldo()) > 0){
            throw new IllegalArgumentException("Valor de saque indisponivel");
        }

        if (valorSaque.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor da transferência deve ser maior que zero");
        }

        BigDecimal novoSaldo = contaEncontrada.getSaldo().subtract(valorSaque);
        contaEncontrada.setSaldo(novoSaldo);

        Conta contaSalva = contaRepository.save(contaEncontrada);

        Transacao transacao = new Transacao();

        transacao.setConta(contaSalva);
        transacao.setTipo(TipoTransacao.SAQUE);
        transacao.setDataHora(LocalDateTime.now());
        transacao.setValor(valorSaque);
        transacaoRepository.save(transacao);

        return ContaResponseDTO.fromEntity(contaSalva);
    }

    @Transactional
    public void transferencia(TransferenciaRequestDTO transferenciaRequestDTO){
        Conta contaOrigem = contaRepository.findById(transferenciaRequestDTO.idContaOrigem())
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta não econtrada com ID: " + transferenciaRequestDTO.idContaOrigem()));

        Conta contaDestino = contaRepository.findById(transferenciaRequestDTO.idContaDestino())
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta não econtrada com ID: " + transferenciaRequestDTO.idContaDestino()));

        if (transferenciaRequestDTO.idContaOrigem().equals(transferenciaRequestDTO.idContaDestino())) {
            throw new IllegalArgumentException("Conta de origem não pode ser igual à conta de destino");
        }

        BigDecimal valorTransferencia = transferenciaRequestDTO.valor();

        if (valorTransferencia.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor da transferência deve ser maior que zero");
        }



        if(valorTransferencia.compareTo(contaOrigem.getSaldo()) > 0){
            throw new IllegalArgumentException("Valor de transferência indisponivel");
        }



        BigDecimal novoSaldoOrigem = contaOrigem.getSaldo().subtract(valorTransferencia);
        contaOrigem.setSaldo(novoSaldoOrigem);

        BigDecimal novoSaldoDestino = contaDestino.getSaldo().add(valorTransferencia);
        contaDestino.setSaldo(novoSaldoDestino);

        contaRepository.saveAll(Arrays.asList(contaOrigem, contaDestino));

        List<Transacao> transacoes = criarTransacoesTransferencia(contaOrigem, contaDestino, valorTransferencia);
        transacaoRepository.saveAll(transacoes);

    }

    @Transactional(readOnly = true)
    public Page<TransacaoResponseDTO> listarTransacoesPorConta(Long contaId, Pageable pageable){
        if (!contaRepository.existsById(contaId)) {
            throw new ContaNaoEncontradaException("Conta não encontrada com o ID: " + contaId);
        }

        // 2. Buscar as transações paginadas no repositório
        Page<Transacao> paginaDeTransacoes = transacaoRepository.findByContaId(contaId, pageable);

        // 3. Converter a página de Entidades (Transacao) para uma página de DTOs (TransacaoResponseDTO)
        return paginaDeTransacoes.map(TransacaoResponseDTO::fromEntity);
    }

    private String gerarNumeroDaConta(){

        return UUID.randomUUID().toString().substring(0, 8);
    }

    private Transacao criarTransacao(Conta conta, TipoTransacao tipo, BigDecimal valor) {
        Transacao transacao = new Transacao();
        transacao.setConta(conta);
        transacao.setTipo(tipo);
        transacao.setValor(valor);
        transacao.setDataHora(LocalDateTime.now());
        return transacao;
    }

    private List<Transacao> criarTransacoesTransferencia(Conta contaOrigem, Conta contaDestino, BigDecimal valor) {
        Transacao transacaoOrigem = criarTransacao(contaOrigem, TipoTransacao.TRANSFERENCIA_ENVIADA, valor);
        Transacao transacaoDestino = criarTransacao(contaDestino, TipoTransacao.TRANSFERENCIA_RECEBIDA, valor);

        return Arrays.asList(transacaoOrigem, transacaoDestino);
    }

}
