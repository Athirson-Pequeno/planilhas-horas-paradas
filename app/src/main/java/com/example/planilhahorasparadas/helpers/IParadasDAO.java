package com.example.planilhahorasparadas.helpers;

import com.example.planilhahorasparadas.models.Paradas;

import java.util.List;

public interface IParadasDAO {
    boolean save(Paradas parada);
    boolean update(Paradas parada);
    boolean delete(Paradas parada);

    boolean deleteByData(String dataText);

    List<Paradas> getAll();
    List<Paradas> getAllData(String data);
}
