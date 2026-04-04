package br.edu.cs.poo.ac.bolsa.dao;

import br.edu.cs.poo.ac.bolsa.entidades.InvestidorEmpresa;

public class DAOInvestidorEmpresa extends DAOGenerico {
    public DAOInvestidorEmpresa() {
        inicializarCadastro(InvestidorEmpresa.class);
    }

    public InvestidorEmpresa buscar(String cnpj) {
        return (InvestidorEmpresa) cadastro.buscar(cnpj);
    }

    public boolean incluir(InvestidorEmpresa ie) {
        if (buscar(ie.getCnpj()) == null) {
            cadastro.incluir(ie, ie.getCnpj());
            return true;
        }
        return false;
    }

    public boolean alterar(InvestidorEmpresa ie) {
        if (buscar(ie.getCnpj()) != null) {
            cadastro.alterar(ie, ie.getCnpj());
            return true;
        }
        return false;
    }

    public boolean excluir(String cnpj) {
        if (buscar(cnpj) != null) {
            cadastro.excluir(cnpj);
            return true;
        }
        return false;
    }
}