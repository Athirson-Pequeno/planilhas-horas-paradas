package com.example.planilhahorasparadas.models;

public class ResponseTableId {

    public String status;
    public String cod;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public ResponseTableId(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
