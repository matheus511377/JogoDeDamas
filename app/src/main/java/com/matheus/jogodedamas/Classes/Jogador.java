package com.matheus.jogodedamas.Classes;

import java.io.Serializable;

/**
 * Created by Matheus on 25/04/2017.
 */

public class Jogador implements Serializable{
    private String nome;
    private Integer numeroVitorias;
    private Integer numeroDerrotas;

    public Integer getNumeroVitorias() {
        return numeroVitorias;
    }

    public void setNumeroVitorias(Integer numeroVitorias) {
        this.numeroVitorias = numeroVitorias;
    }

    public Integer getNumeroDerrotas() {
        return numeroDerrotas;
    }

    public void setNumeroDerrotas(Integer numeroDerrotas) {
        this.numeroDerrotas = numeroDerrotas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
