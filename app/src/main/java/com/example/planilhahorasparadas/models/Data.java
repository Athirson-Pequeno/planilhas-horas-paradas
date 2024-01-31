package com.example.planilhahorasparadas.models;

import java.io.Serializable;

public class Data implements Serializable {
    private String dataText;
    private Integer id;
    public static final long  serialVersionUID = 100L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataText() {
        return dataText;
    }

    public void setDataText(String dataText) {
        this.dataText = dataText;
    }
}
