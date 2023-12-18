package com.example.planilhahorasparadas.models;

import java.util.ArrayList;
import java.util.List;

public class Celulas {

    private String numCel;
    private List<Inutilizado> listaInutilizado = new ArrayList<>();

    public List<Inutilizado> getListaInutilizado() {
        return listaInutilizado;
    }

    public void setListaInutilizado(List<Inutilizado> listaInutilizado) {
        this.listaInutilizado = listaInutilizado;
    }

    public String getNumCel() {
        return numCel;
    }

    public void setNumCel(String numCel) {
        this.numCel = numCel;
    }
}
