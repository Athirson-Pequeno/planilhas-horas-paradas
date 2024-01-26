package com.example.planilhahorasparadas.models;

public class Paradas {
    public Paradas(String celula, Integer horaI, Integer horaF, String cod) {
        this.celula = celula;
        this.horaI = horaI;
        this.horaF = horaF;
        this.cod = cod;
    }

    public Paradas() {
    }

    private Integer id;
    private String celula;
    private Integer horaI;
    private Integer horaF;
    private String obs;
    private String cod;
    private Integer dataId;
    private String horario;
    private Boolean salvo = false;

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getCelula() {
        return celula;
    }

    public void setCelula(String celula) {
        this.celula = celula;
    }

    public Integer getHoraI() {
        return horaI;
    }

    public void setHoraI(Integer horaI) {
        this.horaI = horaI;
    }

    public Integer getHoraF() {
        return horaF;
    }

    public void setHoraF(Integer horaF) {
        this.horaF = horaF;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
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
}
