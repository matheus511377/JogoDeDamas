package com.matheus.jogodedamas.Classes;

/**
 * Created by Matheus on 24/06/2017.
 */

public class Sala {
    public int numeroDaSala;
    public String nomeJogador1;
    public String nomeJogador2;
    public boolean ocupado = false;

    @Override
    public String toString() {
        return "Sala " + numeroDaSala + " Jogador1: " +
                (nomeJogador1==null?"Aguardando Usuario":nomeJogador1) + " Jogador2: " +
                (nomeJogador2==null?"Aguardando Usuario":nomeJogador2);
    }

}
