package com.example.planilhahorasparadas.helpers;

import com.example.planilhahorasparadas.models.Artigos;
import com.example.planilhahorasparadas.models.Especificacoes;
import com.example.planilhahorasparadas.models.Paradas;

import java.util.List;

public interface IArtigosDAO {
    boolean save(Paradas parada);
    boolean update(Paradas parada);
    boolean delete(Paradas parada);
    List<Especificacoes> getAll();
}
