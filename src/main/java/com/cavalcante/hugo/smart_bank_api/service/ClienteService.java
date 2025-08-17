package com.cavalcante.hugo.smart_bank_api.service;

import com.cavalcante.hugo.smart_bank_api.dto.ClienteRequestDTO;
import com.cavalcante.hugo.smart_bank_api.dto.ClienteResponseDTO;
import com.cavalcante.hugo.smart_bank_api.exceptions.ClienteNaoEncontradoException;
import com.cavalcante.hugo.smart_bank_api.model.Cliente;
import com.cavalcante.hugo.smart_bank_api.repository.ClienteRepositoy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class ClienteService {

    private final ClienteRepositoy clienteRepositoy;

    public ClienteService(ClienteRepositoy clienteRepositoy){
        this.clienteRepositoy = clienteRepositoy;
    }

    @Transactional
    public ClienteResponseDTO criarCliente(ClienteRequestDTO cliente){

        if(clienteRepositoy.findByCpf(cliente.cpf()).isPresent()){
            throw new IllegalArgumentException("CPF já cadastrado");
        }
        if(clienteRepositoy.findByEmail(cliente.email()).isPresent()){
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Cliente clienteNovo = new Cliente();
        clienteNovo.setNome(cliente.nome());
        clienteNovo.setCpf(cliente.cpf());
        clienteNovo.setEmail(cliente.email());
        Cliente clienteSalvo = clienteRepositoy.save(clienteNovo);
        return ClienteResponseDTO.fromEntity(clienteSalvo);
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarCliente(Long id){
        return clienteRepositoy.findById(id)
                .map(ClienteResponseDTO::fromEntity)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado com ID: " + id));
    }

}
