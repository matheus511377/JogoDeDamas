package com.matheus.jogodedamas.Classes;

/**
 * Created by Matheus on 05/04/2017.
 */

public class Casa {
    private String strPosicao = "";
    private Peca peca;

    Casa(String strPosicao, Peca peca){
        this.strPosicao = strPosicao;
        this.peca = peca;
    }

}
