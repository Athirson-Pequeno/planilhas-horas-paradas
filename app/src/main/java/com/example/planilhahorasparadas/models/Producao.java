package com.example.planilhahorasparadas.models;

public class Producao {
    private Integer id;
    private Integer idCor;
    private Integer idArtigo;
    private String celula;
    private String codigoCor;
    private String codigoArtigo;
    private String descricaoCor;
    private String descricaoArtigo;
    private Integer quantidadeProduzida;
    private Integer dataId;
    private String horario;
    private Boolean salvo;
    private String tamanho;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCelula() {
        return celula;
    }

    public void setCelula(String celula) {
        this.celula = celula;
    }

    public String getCodigoCor() {
        return codigoCor;
    }

    public void setCodigoCor(String codigoCor) {
        this.codigoCor = codigoCor;
    }

    public String getCodigoArtigo() {
        return codigoArtigo;
    }

    public void setCodigoArtigo(String codigoArtigo) {
        this.codigoArtigo = codigoArtigo;
    }

    public String getDescricaoCor() {
        return descricaoCor;
    }

    public void setDescricaoCor(String descricaoCor) {
        this.descricaoCor = descricaoCor;
    }

    public String getDescricaoArtigo() {
        return descricaoArtigo;
    }

    public void setDescricaoArtigo(String descricaoArtigo) {
        this.descricaoArtigo = descricaoArtigo;
    }

    public Integer getQuantidadeProduzida() {
        return quantidadeProduzida;
    }

    public void setQuantidadeProduzida(Integer quantidadeProduzida) {
        this.quantidadeProduzida = quantidadeProduzida;
    }

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public Boolean getSalvo() {
        return salvo;
    }

    public void setSalvo(Boolean salvo) {
        this.salvo = salvo;
    }

    public void setSalvo(Integer salvo) {
        this.salvo = salvo == 1;
    }

    public Integer getIdCor() {
        return idCor;
    }

    public void setIdCor(Integer idCor) {
        this.idCor = idCor;
    }

    public Integer getIdArtigo() {
        return idArtigo;
    }

    public void setIdArtigo(Integer idArtigo) {
        this.idArtigo = idArtigo;
    }
}
