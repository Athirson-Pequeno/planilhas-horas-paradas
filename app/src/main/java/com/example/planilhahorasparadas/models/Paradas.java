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
    public String celula;
    public Integer horaI;
    public Integer id;

    public String data;
    public Integer horaF;
    public String cod;
    public String obs;
    public Integer horario;
    public String user;

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

    public Integer getHorario() {
        return horario;
    }

    public void setHorario(Integer horario) {
        this.horario = horario;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }



    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
