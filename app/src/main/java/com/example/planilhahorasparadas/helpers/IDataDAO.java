package com.example.planilhahorasparadas.helpers;

import com.example.planilhahorasparadas.models.Data;

import java.util.List;

public interface IDataDAO {
    void save(Data data) throws Exception;
    boolean update(Data data);
    boolean delete(Data data);
    List<Data> getAll();
}
