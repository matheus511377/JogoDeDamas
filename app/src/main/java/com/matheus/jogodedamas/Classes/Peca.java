package com.matheus.jogodedamas.Classes;

import android.widget.ImageView;

import com.matheus.jogodedamas.R;

/**
 * Created by Matheus on 05/04/2017.
 */

public class Peca {

    private String strCor = "";
    private int lngIdImagemPeca;
    private ImageView img;


    Peca(String strCor){
        this.strCor = strCor;

        if (strCor.equals("BRANCO"))
            lngIdImagemPeca = R.drawable.pecabranca;

        else if(strCor.equals("PRETO"))
            lngIdImagemPeca = R.drawable.pecapreta;
        else
            lngIdImagemPeca = 0;
    }

    public String getStrCor() {
        return strCor;
    }

    public int getLngIdImagemPeca() {
        return lngIdImagemPeca;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }
}
