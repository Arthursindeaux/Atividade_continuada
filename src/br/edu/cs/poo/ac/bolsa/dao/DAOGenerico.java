package br.edu.cs.poo.ac.bolsa.dao;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;

public class DAOGenerico {
    protected CadastroObjetos cadastro;

    protected void inicializarCadastro(Class<?> classe) {
        cadastro = new CadastroObjetos(classe);
    }
}