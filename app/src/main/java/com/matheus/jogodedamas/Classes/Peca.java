package com.matheus.jogodedamas.Classes;

import android.widget.ImageView;

import com.matheus.jogodedamas.R;

/**
 * Created by Matheus on 05/04/2017.
 */

public class Peca {

    private String strCor = "";
    private ImageView imgPeca;

    Peca(String strCor){
        this.strCor = strCor;
        if (strCor.equals("BRANCO"))
            imgPeca.setImageResource(R.drawable.pecabranca);

        else
            imgPeca.setImageResource(R.drawable.pecapreta);
    }


}
