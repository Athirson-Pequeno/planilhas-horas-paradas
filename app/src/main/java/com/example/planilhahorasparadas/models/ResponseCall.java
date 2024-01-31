package com.example.planilhahorasparadas.models;

public class ResponseCall {

    private String status;

    public ResponseCall(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
