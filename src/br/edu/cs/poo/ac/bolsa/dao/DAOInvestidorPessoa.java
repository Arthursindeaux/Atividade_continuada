package br.edu.cs.poo.ac.bolsa.dao;

import br.edu.cs.poo.ac.bolsa.entidades.InvestidorPessoa;

public class DAOInvestidorPessoa extends DAOGenerico {
    public DAOInvestidorPessoa() {
        inicializarCadastro(InvestidorPessoa.class);
    }

    public InvestidorPessoa buscar(String cpf) {
        return (InvestidorPessoa) cadastro.buscar(cpf);
    }

    public boolean incluir(InvestidorPessoa ip) {
        if (buscar(ip.getCpf()) == null) {
            cadastro.incluir(ip, ip.getCpf());
            return true;
        }
        return false;
    }

    public boolean alterar(InvestidorPessoa ip) {
        if (buscar(ip.getCpf()) != null) {
            cadastro.alterar(ip, ip.getCpf());
            return true;
        }
        return false;
    }

    public boolean excluir(String cpf) {
        if (buscar(cpf) != null) {
            cadastro.excluir(cpf);
            return true;
        }
        return false;
    }
}