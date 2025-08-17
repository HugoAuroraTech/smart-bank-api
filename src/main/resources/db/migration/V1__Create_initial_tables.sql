-- Script para a criação das tabelas iniciais do Smart Bank

-- Tabela de Clientes
CREATE TABLE clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- Tabela de Contas
CREATE TABLE contas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_conta VARCHAR(20) NOT NULL UNIQUE,
    agencia VARCHAR(10) NOT NULL,
    saldo NUMERIC(19, 2) NOT NULL,
    cliente_id BIGINT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

-- Tabela de Transações
CREATE TABLE transacoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    valor NUMERIC(19, 2) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    conta_id BIGINT NOT NULL,
    FOREIGN KEY (conta_id) REFERENCES contas(id)
);