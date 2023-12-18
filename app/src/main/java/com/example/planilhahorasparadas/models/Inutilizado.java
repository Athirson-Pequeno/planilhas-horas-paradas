package com.example.planilhahorasparadas.models;

public class Inutilizado {
    private String pincaD;
    private String pincaE;
    private String furacao;
    private String corte;
    private String codArtigo;
    private String nomeArtigo;
    private String numeracao;
    private String codCor;
    private String nomeCor;
    public Inutilizado() {
    }

    public Inutilizado(String pincaD, String pincaE, String furacao, String corte) {
        this.pincaD = pincaD;
        this.pincaE = pincaE;
        this.furacao = furacao;
        this.corte = corte;
    }

    public String getCodArtigo() {
        return codArtigo;
    }

    public void setCodArtigo(String codArtigo) {
        this.codArtigo = codArtigo;
    }

    public String getNomeArtigo() {
        return nomeArtigo;
    }

    public void setNomeArtigo(String nomeArtigo) {
        this.nomeArtigo = nomeArtigo;
    }

    public String getNumeracao() {
        return numeracao;
    }

    public void setNumeracao(String numeracao) {
        this.numeracao = numeracao;
    }

    public String getCodCor() {
        return codCor;
    }

    public void setCodCor(String codCor) {
        this.codCor = codCor;
    }

    public String getNomeCor() {
        return nomeCor;
    }

    public void setNomeCor(String nomeCor) {
        this.nomeCor = nomeCor;
    }

    public String getPincaD() {
        return pincaD;
    }

    public void setPincaD(String pincaD) {
        this.pincaD = pincaD;
    }

    public String getPincaE() {
        return pincaE;
    }

    public void setPincaE(String pincaE) {
        this.pincaE = pincaE;
    }

    public String getFuracao() {
        return furacao;
    }

    public void setFuracao(String furacao) {
        this.furacao = furacao;
    }

    public String getCorte() {
        return corte;
    }

    public void setCorte(String corte) {
        this.corte = corte;
    }
}
