package com.example.planilhahorasparadas.helpers;

import com.example.planilhahorasparadas.models.Cores;
import com.example.planilhahorasparadas.models.Especificacoes;
import com.example.planilhahorasparadas.models.Paradas;

import java.util.List;

public interface ICoresDAO {
    boolean save(Cores cores);
    boolean update(Cores cores);
    boolean delete(Cores cores);
    List<Especificacoes> getAll();
    List<Especificacoes> getByID(Integer id);
    List<Especificacoes> getByCod(String codigo);
}
