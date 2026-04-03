package br.edu.cs.poo.ac.bolsa.entidades;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Titulo {
    private InvestidorPessoa investidorPessoa;
    private InvestidorEmpresa investidorEmpresa;
    private Ativo ativo;
    private BigDecimal valorInvestido;
    private BigDecimal valorAtual;
    private BigDecimal taxaDiaria;
    private LocalDate dataAplicacao;
    private LocalDate dataVencimento;
    private LocalDate dataUltimoRendimento;
    private StatusTitulo status;

    public Titulo(InvestidorPessoa investidorPessoa, InvestidorEmpresa investidorEmpresa,
                  Ativo ativo, BigDecimal valorInvestido, BigDecimal valorAtual,
                  BigDecimal taxaDiaria, LocalDate dataAplicacao, LocalDate dataVencimento,
                  LocalDate dataUltimoRendimento, StatusTitulo status) {
        this.investidorPessoa = investidorPessoa;
        this.investidorEmpresa = investidorEmpresa;
        this.ativo = ativo;
        this.valorInvestido = valorInvestido;
        this.valorAtual = valorAtual;
        this.taxaDiaria = taxaDiaria;
        this.dataAplicacao = dataAplicacao;
        this.dataVencimento = dataVencimento;
        this.dataUltimoRendimento = dataUltimoRendimento;
        this.status = status;
    }

    public boolean render() {
        LocalDate hoje = LocalDate.now();

        if (status != StatusTitulo.ATIVO) return false;
        if (!hoje.isBefore(dataVencimento)) return false;
        if (!hoje.isAfter(dataAplicacao)) return false;
        if (dataUltimoRendimento != null && !hoje.isAfter(dataUltimoRendimento)) return false;

        long dd;
        if (dataUltimoRendimento == null) {
            dd = ChronoUnit.DAYS.between(dataAplicacao, hoje);
        } else {
            dd = ChronoUnit.DAYS.between(dataUltimoRendimento, hoje);
        }

        if (dd == 0) return false;

        BigDecimal fator = BigDecimal.ONE.add(taxaDiaria.divide(new BigDecimal("100")));
        valorAtual = valorAtual.multiply(fator.pow((int) dd));

        dataUltimoRendimento = hoje;
        return true;
    }

    public String getNumero() {
        String dataFormatada = dataAplicacao.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "0000";

        if (investidorPessoa != null) {
            return "000" + investidorPessoa.getCpf() + ativo.getCodigo() + dataFormatada;
        } else {
            return investidorEmpresa.getCnpj() + ativo.getCodigo() + dataFormatada;
        }
    }

    public InvestidorPessoa getInvestidorPessoa() { return investidorPessoa; }
    public void setInvestidorPessoa(InvestidorPessoa p) { this.investidorPessoa = p; }

    public InvestidorEmpresa getInvestidorEmpresa() { return investidorEmpresa; }
    public void setInvestidorEmpresa(InvestidorEmpresa e) { this.investidorEmpresa = e; }

    public Ativo getAtivo() { return ativo; }
    public void setAtivo(Ativo ativo) { this.ativo = ativo; }

    public BigDecimal getValorInvestido() { return valorInvestido; }
    public void setValorInvestido(BigDecimal v) { this.valorInvestido = v; }

    public BigDecimal getValorAtual() { return valorAtual; }
    public void setValorAtual(BigDecimal v) { this.valorAtual = v; }

    public BigDecimal getTaxaDiaria() { return taxaDiaria; }
    public void setTaxaDiaria(BigDecimal t) { this.taxaDiaria = t; }

    public LocalDate getDataAplicacao() { return dataAplicacao; }
    public void setDataAplicacao(LocalDate d) { this.dataAplicacao = d; }

    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate d) { this.dataVencimento = d; }

    public LocalDate getDataUltimoRendimento() { return dataUltimoRendimento; }
    public void setDataUltimoRendimento(LocalDate d) { this.dataUltimoRendimento = d; }

    public StatusTitulo getStatus() { return status; }
    public void setStatus(StatusTitulo status) { this.status = status; }
}