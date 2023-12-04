package com.example.planilhahorasparadas.helpers;

import com.example.planilhahorasparadas.models.Data;

import java.util.List;

public interface IDataDAO {
    boolean save(Data data);
    boolean update(Data data);
    boolean delete(Data data);
    List<Data> getAll();
}
