package com.matheus.jogodedamas.Classes;

import java.io.Serializable;

/**
 * Created by Matheus on 25/04/2017.
 */

public class Jogador implements Serializable{
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
