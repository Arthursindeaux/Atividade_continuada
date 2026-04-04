package br.edu.cs.poo.ac.bolsa.negocio;

import br.edu.cs.poo.ac.bolsa.dao.DAOInvestidorEmpresa;
import br.edu.cs.poo.ac.bolsa.dao.DAOInvestidorPessoa;
import br.edu.cs.poo.ac.bolsa.entidades.Contatos;
import br.edu.cs.poo.ac.bolsa.entidades.Endereco;
import br.edu.cs.poo.ac.bolsa.entidades.InvestidorEmpresa;
import br.edu.cs.poo.ac.bolsa.entidades.InvestidorPessoa;
import br.edu.cs.poo.ac.bolsa.util.MensagensValidacao;

public class InvestidorMediator {

    private DAOInvestidorEmpresa daoEmpresa = new DAOInvestidorEmpresa();
    private DAOInvestidorPessoa daoPessoa = new DAOInvestidorPessoa();

    // ── validações compartilhadas ──────────────────────────────

    private MensagensValidacao validarEndereco(Endereco e) {
        MensagensValidacao msgs = new MensagensValidacao();
        if (e == null) { msgs.adicionar("Endereço é obrigatório."); return msgs; }
        if (vazio(e.getLogradouro())) msgs.adicionar("Logradouro é obrigatório.");
        if (vazio(e.getNumero()))     msgs.adicionar("Número é obrigatório.");
        if (vazio(e.getCidade()))     msgs.adicionar("Cidade é obrigatório.");
        if (vazio(e.getEstado()))     msgs.adicionar("Estado é obrigatório.");
        if (vazio(e.getPais()))       msgs.adicionar("País é obrigatório.");
        return msgs;
    }

    private MensagensValidacao validarContatos(Contatos c, boolean isPJ) {
        MensagensValidacao msgs = new MensagensValidacao();
        if (c == null) { msgs.adicionar("Contatos são obrigatórios."); return msgs; }

        if (!vazio(c.getEmail()) && !c.getEmail().contains("@"))
            msgs.adicionar("E-mail inválido.");

        boolean temTelefone = !vazio(c.getTelefoneFixo())
                           || !vazio(c.getTelefoneCelular())
                           || !vazio(c.getNumeroWhatsApp());
        if (!temTelefone)
            msgs.adicionar("Pelo menos um telefone deve ser informado.");

        if (!vazio(c.getTelefoneFixo()) && !c.getTelefoneFixo().matches("\\d+"))
            msgs.adicionar("Telefone fixo deve conter apenas números.");
        if (!vazio(c.getTelefoneCelular()) && !c.getTelefoneCelular().matches("\\d+"))
            msgs.adicionar("Telefone celular deve conter apenas números.");
        if (!vazio(c.getNumeroWhatsApp()) && !c.getNumeroWhatsApp().matches("\\d+"))
            msgs.adicionar("Número WhatsApp deve conter apenas números.");

        if (isPJ && vazio(c.getNomeParaContato()))
            msgs.adicionar("Nome para contato é obrigatório para pessoa jurídica.");

        return msgs;
    }

    private boolean vazio(String s) {
        return s == null || s.trim().isEmpty();
    }

    // ── empresa ───────────────────────────────────────────────

    public MensagensValidacao incluirInvestidorEmpresa(InvestidorEmpresa ie) {
        MensagensValidacao msgs = new MensagensValidacao();
        msgs.adicionar(validarEndereco(ie.getEndereco()));
        msgs.adicionar(validarContatos(ie.getContatos(), true));
        if (!msgs.estaVazio()) return msgs;
        if (!daoEmpresa.incluir(ie))
            msgs.adicionar("Investidor Empresa já existente.");
        return msgs;
    }

    public MensagensValidacao alterarInvestidorEmpresa(InvestidorEmpresa ie) {
        MensagensValidacao msgs = new MensagensValidacao();
        msgs.adicionar(validarEndereco(ie.getEndereco()));
        msgs.adicionar(validarContatos(ie.getContatos(), true));
        if (!msgs.estaVazio()) return msgs;
        if (!daoEmpresa.alterar(ie))
            msgs.adicionar("Investidor Empresa não existente.");
        return msgs;
    }

    public MensagensValidacao excluirInvestidorEmpresa(String cnpj) {
        MensagensValidacao msgs = new MensagensValidacao();
        if (!daoEmpresa.excluir(cnpj))
            msgs.adicionar("Investidor Empresa não existente.");
        return msgs;
    }

    public InvestidorEmpresa buscarInvestidorEmpresa(String cnpj) {
        if (vazio(cnpj) || cnpj.length() != 14) return null;
        return daoEmpresa.buscar(cnpj);
    }

    // ── pessoa ────────────────────────────────────────────────

    public MensagensValidacao incluirInvestidorPessoa(InvestidorPessoa ip) {
        MensagensValidacao msgs = new MensagensValidacao();
        msgs.adicionar(validarEndereco(ip.getEndereco()));
        msgs.adicionar(validarContatos(ip.getContatos(), false));
        if (!msgs.estaVazio()) return msgs;
        if (!daoPessoa.incluir(ip))
            msgs.adicionar("Investidor Pessoa já existente.");
        return msgs;
    }

    public MensagensValidacao alterarInvestidorPessoa(InvestidorPessoa ip) {
        MensagensValidacao msgs = new MensagensValidacao();
        msgs.adicionar(validarEndereco(ip.getEndereco()));
        msgs.adicionar(validarContatos(ip.getContatos(), false));
        if (!msgs.estaVazio()) return msgs;
        if (!daoPessoa.alterar(ip))
            msgs.adicionar("Investidor Pessoa não existente.");
        return msgs;
    }

    public MensagensValidacao excluirInvestidorPessoa(String cpf) {
        MensagensValidacao msgs = new MensagensValidacao();
        if (!daoPessoa.excluir(cpf))
            msgs.adicionar("Investidor Pessoa não existente.");
        return msgs;
    }

    public InvestidorPessoa buscarInvestidorPessoa(String cpf) {
        if (vazio(cpf) || cpf.length() != 11) return null;
        return daoPessoa.buscar(cpf);
    }
}