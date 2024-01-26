package com.example.planilhahorasparadas.helpers;

import com.example.planilhahorasparadas.models.Paradas;

import java.util.List;

public interface IParadasDAO {
    boolean save(Paradas parada);
    boolean update(Paradas parada);
    boolean delete(Paradas parada);
    List<Paradas> getAll();
    List<Paradas> getAllData(Integer data);
    List<Paradas> buscarPorDataCelHorario(Integer dataID, String celula, String horarioSpinner);
    List<Paradas> buscarNaoSalvos();
}
