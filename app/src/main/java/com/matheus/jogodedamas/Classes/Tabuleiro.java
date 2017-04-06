package com.matheus.jogodedamas.Classes;

import android.content.Context;
import android.widget.ImageView;

import com.matheus.jogodedamas.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matheus on 05/04/2017.
 */

public class Tabuleiro {
    private static List<Casa> tabuleiro = new ArrayList<Casa>();
    private List<String> listInicialBranca;
    private List<String> listInicialPreta;
    private List<String> listInicialDemaisCasas;
    private String x = "";
    private String y = "";

    public Tabuleiro(){
        inicializaTabuleiro();
    }

    public List<Casa> getTabuleiro(){
        return tabuleiro;
    }

    public void setTabuleiro(Casa casa){
        tabuleiro.add(casa);
    }
    private void setListInicialBranca(){
        this.listInicialBranca = new ArrayList<String>();
        this.listInicialBranca.add("a1");
        this.listInicialBranca.add("c1");
        this.listInicialBranca.add("e1");
        this.listInicialBranca.add("g1");
        this.listInicialBranca.add("b2");
        this.listInicialBranca.add("d2");
        this.listInicialBranca.add("f2");
        this.listInicialBranca.add("h2");
        this.listInicialBranca.add("a3");
        this.listInicialBranca.add("c3");
        this.listInicialBranca.add("e3");
        this.listInicialBranca.add("g3");
    }

    private void setListInicialPreta(){
        this.listInicialPreta = new ArrayList<String>();
        this.listInicialPreta.add("b6");
        this.listInicialPreta.add("d6");
        this.listInicialPreta.add("f6");
        this.listInicialPreta.add("h6");
        this.listInicialPreta.add("a7");
        this.listInicialPreta.add("c7");
        this.listInicialPreta.add("e7");
        this.listInicialPreta.add("g7");
        this.listInicialPreta.add("b8");
        this.listInicialPreta.add("d8");
        this.listInicialPreta.add("f8");
        this.listInicialPreta.add("h8");
    }

    private void setListInicialDemaisCasas(){
        this.listInicialDemaisCasas = new ArrayList<String>();

        this.listInicialDemaisCasas.add("b4");
        this.listInicialDemaisCasas.add("d4");
        this.listInicialDemaisCasas.add("f4");
        this.listInicialDemaisCasas.add("h4");
        this.listInicialDemaisCasas.add("a5");
        this.listInicialDemaisCasas.add("c5");
        this.listInicialDemaisCasas.add("e5");
        this.listInicialDemaisCasas.add("g5");
    }

    private void inicializaTabuleiro(){
        setListInicialBranca();
        setListInicialPreta();
        setListInicialDemaisCasas();
        Peca peca;
        Casa casa;

        for (String posicao:listInicialBranca){
            peca = new Peca("BRANCO");
            casa = new Casa(posicao,peca);
            setTabuleiro(casa);
        }

        for (String posicao:listInicialPreta){
            peca = new Peca("PRETO");
            casa = new Casa(posicao,peca);
            setTabuleiro(casa);
        }

        for (String posicao:listInicialDemaisCasas){
            peca = new Peca("VAZIO");
            casa = new Casa(posicao,peca);
            setTabuleiro(casa);
        }

    }

    public Boolean getSetCasaSelecionada(String posicao){
        for (Casa item:tabuleiro) {
            if (item.getPosicao().equals(posicao)){
                item.setCasaSelecionada(!item.getCasaSelecionada());
                return !item.getCasaSelecionada();
            }

        }
        return false;
    }


    public void mover(String posicao, ImageView img, Context context){
        if (!(getSetCasaSelecionada(posicao))) {
            img.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            if(this.x.equalsIgnoreCase("")) {
                this.x = posicao;
            }
            else{
                this.y = posicao;
            }
        }
        else{
            img.setBackgroundColor(context.getResources().getColor(R.color.marron));
            if (this.x.equals(posicao)) {
                this.x = "";
            }
            else{
                this.y = "";
            }
        }

        if (!(this.x.equals("")) && !(this.y.equals(""))){
            Casa casa1 = getCasa(this.y);
            Casa casa2 = getCasa(this.x);
            tabuleiro.remove(casa1);
            tabuleiro.remove(casa2);

            img = casa1.getImageView();
            img.setImageResource(casa2.getlngIdImagemPeca());
            img.setBackgroundColor(context.getResources().getColor(R.color.marron));
            casa1.setImageView(img);
            casa1.setLngIdImagemPeca(casa2.getlngIdImagemPeca());
            casa1.setCasaSelecionada(false);
            tabuleiro.add(casa1);



            img = casa2.getImageView();
            img.setImageResource(0);
            img.setBackgroundColor(context.getResources().getColor(R.color.marron));
            casa2.setLngIdImagemPeca(0);
            casa2.setImageView(img);
            casa2.setCasaSelecionada(false);
            tabuleiro.add(casa2);

            this.x = "";
            this.y = "";

        }




    }

    private Casa getCasa(String posicao){
        for (Casa item:tabuleiro) {
            if (item.getPosicao().equals(posicao)){
                return item;
            }
        }
        return null;
    }
    private void atualizaTabuleiro(Casa casa){
        tabuleiro.remove(casa);
        tabuleiro.add(casa);

        }
    }


