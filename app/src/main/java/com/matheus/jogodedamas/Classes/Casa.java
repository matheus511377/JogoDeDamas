package com.matheus.jogodedamas.Classes;

import android.media.Image;
import android.widget.ImageView;



/**
 * Created by Matheus on 05/04/2017.
 */

public class Casa {
    private String strPosicao = "";
    private Boolean casaSelecionada = false;
    private Peca peca;

    public Peca getPeca() {
        return peca;
    }

    Casa(String strPosicao, Peca peca){
        this.strPosicao = strPosicao;
        this.peca = peca;
    }

    public void setDama(Boolean dama){
        peca.setDama(dama);
    }

    public Boolean getDama(){
        return peca.getDama();
    }
    public String getPosicao(){
        return this.strPosicao;
    }
    public int getlngIdImagemPeca(){
        if(!(peca == null)){
            return peca.getLngIdImagemPeca();
        }
        return 0;
    }

    public Boolean getCasaSelecionada() {
        return casaSelecionada;
    }

    public void setCasaSelecionada(Boolean casaSelecionada) {
        this.casaSelecionada = casaSelecionada;
    }

    public ImageView getImageView(){
        return peca.getImg();
    }
    public void setImageView(ImageView img){
        peca.setImg(img);
    }

    public void setLngIdImagemPeca(int i){
        peca.setLngIdImagemPeca(i);
    }
    public void setStrCor(String strCor) {
        peca.setStrCor(strCor);
    }
    public String getStrCor(){
        return peca.getStrCor();
    }




}
