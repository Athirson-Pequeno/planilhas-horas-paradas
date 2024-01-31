package com.example.planilhahorasparadas.helpers;

import com.example.planilhahorasparadas.models.Producao;

import java.util.List;

public interface IProducaoDAO {
    boolean save(Producao producao);
    boolean update(Producao producao);
    boolean delete(Producao producao);
    List<Producao> getAll();
    List<Producao> buscarPorDataCelHorario(Integer dataID, String celula, String horarioSpinner);

    List<Producao> buscarPorDataCel(Integer dataID, String celula);

    List<Producao> buscarNaoSalvos();
}
