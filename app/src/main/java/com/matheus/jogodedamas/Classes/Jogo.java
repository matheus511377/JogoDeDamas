package com.matheus.jogodedamas.Classes;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.matheus.jogodedamas.MainActivity;
import com.matheus.jogodedamas.PlacarActivity;
import com.matheus.jogodedamas.R;
import com.matheus.jogodedamas.adapterLances;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Matheus on 05/04/2017.
 */

public class Jogo {
    private List<Casa> tabuleiro = new ArrayList<Casa>();
    private List<String> listInicialBranca;
    private List<String> listInicialPreta;
    private List<String> listInicialDemaisCasas;
    private List<Lance> listLance = new ArrayList<>();
    private Context contexto;
    private adapterLances adapter;
    private int sala = 0;
    private String x = "";
    private String y = "";
    private Casa casa1;
    private Casa casa2;
    Jogador jogador1;
    Jogador jogador2;
    String lanceAnterior="";
    private boolean blnBrancasJogam = true;
    FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRefBranco;
    final DatabaseReference myRefPreto;


    public Jogo(final Context contexto, final Jogador jogador1, final Jogador jogador2, adapterLances adapter, List<Lance> lances, final int sala) {
        this.contexto = contexto;
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.listLance = lances;
        this.adapter = adapter;
        this.sala = sala;

        inicializaTabuleiro();
        myRefBranco = database.getReference("jogo/"+sala+"/branco");
        myRefPreto = database.getReference("jogo/"+sala+"/preto");
        if(sala>0){
            mAuth = FirebaseAuth.getInstance();
            currentUser = mAuth.getCurrentUser();
            if(jogador2.getNome().equals(currentUser.getEmail().toString())){

            }

            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    if(sala==0){
                        return;
                    }
                    String post = (String) dataSnapshot.getValue();

                    if(!jogador1.getNome().equals(currentUser.getEmail().toString()) ) {
                        if (post!=null){
                            if(post.length()==0){
                                return;
                            }
                            if (lanceAnterior.equals(post.toString().substring(0,3) + "-" + post.toString().substring(4,7))){
                                return;
                            }
                            if(verificaSeComePeca( post.toString().substring(0,3),post.toString().substring(4,7))){

                            }
                            //copiei la de cima mas poderia ser diferente
                            Casa casa1 = getCasa( post.toString().substring(4,7));
                            Casa casa2 = getCasa( post.toString().substring(0,3));
                            lanceAnterior = post.toString().substring(0,3) + "-" + post.toString().substring(4,7);

                            tabuleiro.remove(casa1);
                            tabuleiro.remove(casa2);
                            ImageView img;
                            img = casa1.getImageView();
                            img.setImageResource(casa2.getlngIdImagemPeca());

                            if (viraDama(casa1.getPosicao())) {
                                if (blnBrancasJogam) {
                                    img.setImageResource(R.drawable.damabranca);
                                    casa1.setLngIdImagemPeca(R.drawable.damabranca);
                                } else {
                                    img.setImageResource(R.drawable.damapreta);
                                    casa1.setLngIdImagemPeca(R.drawable.damapreta);
                                }
                                casa1.setDama(true);
                            } else {
                                casa1.setDama(casa2.getDama());
                                casa1.setLngIdImagemPeca(casa2.getlngIdImagemPeca());
                            }

                            img.setBackgroundColor(contexto.getResources().getColor(R.color.marron));
                            casa1.setImageView(img);
                            casa1.setStrCor(casa2.getStrCor());
                            casa1.setCasaSelecionada(false);
                            tabuleiro.add(casa1);
                            //Verifica e seta se vira dama


                            img = casa2.getImageView();
                            img.setImageResource(0);
                            img.setBackgroundColor(contexto.getResources().getColor(R.color.marron));
                            casa2.setStrCor("");
                            casa2.setLngIdImagemPeca(0);
                            casa2.setImageView(img);
                            casa2.setCasaSelecionada(false);
                            tabuleiro.add(casa2);

                            Lance lance = new Lance();
                            lance.setPeca(casa2.getPeca());
                            lance.setLance(getLance(casa1.getPosicao()) + "-" + getLance( post.toString().substring(4,7)));
                            listLance.add(lance);


                            blnBrancasJogam = !blnBrancasJogam;
                            //fim

                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w("T", "loadPost:onCancelled", databaseError.toException());
                    // ...
                }
            };
            myRefBranco.addValueEventListener(postListener);


            ValueEventListener postListener2 = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    if(sala==0){
                        return;
                    }
                    String post = (String) dataSnapshot.getValue();

                    if(!jogador2.getNome().equals(currentUser.getEmail().toString()) ) {
                        if (post!=null ){
                            if(post.length()==0){
                                return;
                            }
                            if (lanceAnterior.equals(post.toString().substring(0,3) + "-" + post.toString().substring(4,7))){
                                return;
                            }
                            lanceAnterior = post.toString().substring(0,3) + "-" + post.toString().substring(4,7);
                            if(verificaSeComePeca( post.toString().substring(0,3),post.toString().substring(4,7))){

                            }
                            //copiei la de cima mas poderia ser diferente
                            Casa casa1 = getCasa( post.toString().substring(4,7));
                            Casa casa2 = getCasa( post.toString().substring(0,3));

                            tabuleiro.remove(casa1);
                            tabuleiro.remove(casa2);
                            ImageView img;
                            img = casa1.getImageView();
                            img.setImageResource(casa2.getlngIdImagemPeca());

                            if (viraDama(casa1.getPosicao())) {
                                if (blnBrancasJogam) {
                                    img.setImageResource(R.drawable.damabranca);
                                    casa1.setLngIdImagemPeca(R.drawable.damabranca);
                                } else {
                                    img.setImageResource(R.drawable.damapreta);
                                    casa1.setLngIdImagemPeca(R.drawable.damapreta);
                                }
                                casa1.setDama(true);
                            } else {
                                casa1.setDama(casa2.getDama());
                                casa1.setLngIdImagemPeca(casa2.getlngIdImagemPeca());
                            }

                            img.setBackgroundColor(contexto.getResources().getColor(R.color.marron));
                            casa1.setImageView(img);
                            casa1.setStrCor(casa2.getStrCor());
                            casa1.setCasaSelecionada(false);
                            tabuleiro.add(casa1);
                            //Verifica e seta se vira dama


                            img = casa2.getImageView();
                            img.setImageResource(0);
                            img.setBackgroundColor(contexto.getResources().getColor(R.color.marron));
                            casa2.setStrCor("");
                            casa2.setLngIdImagemPeca(0);
                            casa2.setImageView(img);
                            casa2.setCasaSelecionada(false);
                            tabuleiro.add(casa2);

                            Lance lance = new Lance();
                            lance.setPeca(casa2.getPeca());
                            lance.setLance(getLance(casa2.getPosicao()) + "-" + getLance( post.toString().substring(4,7)));
                            listLance.add(lance);
                            // adapter.notifyDataSetChanged();

                            blnBrancasJogam = !blnBrancasJogam;
                            //fim

                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w("T", "loadPost:onCancelled", databaseError.toException());
                    // ...
                }
            };
            myRefPreto.addValueEventListener(postListener2);

        }
    }

    public List<Casa> getTabuleiro() {
        return tabuleiro;
    }

    private void setTabuleiro(Casa casa) {
        tabuleiro.add(casa);
    }

    private void setListInicialBranca() {
        this.listInicialBranca = new ArrayList<String>();
        this.listInicialBranca.add("p11");
        this.listInicialBranca.add("p31");
        this.listInicialBranca.add("p51");
        this.listInicialBranca.add("p71");
        this.listInicialBranca.add("p22");
        this.listInicialBranca.add("p42");
        this.listInicialBranca.add("p62");
        this.listInicialBranca.add("p82");
        this.listInicialBranca.add("p13");
        this.listInicialBranca.add("p33");
        this.listInicialBranca.add("p53");
        this.listInicialBranca.add("p73");
    }

    private void setListInicialPreta() {
        this.listInicialPreta = new ArrayList<String>();
        this.listInicialPreta.add("p26");
        this.listInicialPreta.add("p46");
        this.listInicialPreta.add("p66");
        this.listInicialPreta.add("p86");
        this.listInicialPreta.add("p17");
        this.listInicialPreta.add("p37");
        this.listInicialPreta.add("p57");
        this.listInicialPreta.add("p77");
        this.listInicialPreta.add("p28");
        this.listInicialPreta.add("p48");
        this.listInicialPreta.add("p68");
        this.listInicialPreta.add("p88");
    }

    private void setListInicialDemaisCasas() {
        this.listInicialDemaisCasas = new ArrayList<String>();

        this.listInicialDemaisCasas.add("p24");
        this.listInicialDemaisCasas.add("p44");
        this.listInicialDemaisCasas.add("p64");
        this.listInicialDemaisCasas.add("p84");
        this.listInicialDemaisCasas.add("p15");
        this.listInicialDemaisCasas.add("p35");
        this.listInicialDemaisCasas.add("p55");
        this.listInicialDemaisCasas.add("p75");
    }

    private void inicializaTabuleiro() {
        setListInicialBranca();
        setListInicialPreta();
        setListInicialDemaisCasas();

        Peca peca;
        Casa casa;

        for (String posicao : listInicialBranca) {
            peca = new Peca("BRANCO");
            casa = new Casa(posicao, peca);
            setTabuleiro(casa);
        }

        for (String posicao : listInicialPreta) {
            peca = new Peca("PRETO");
            casa = new Casa(posicao, peca);
            setTabuleiro(casa);
        }

        for (String posicao : listInicialDemaisCasas) {
            peca = new Peca("");
            casa = new Casa(posicao, peca);
            setTabuleiro(casa);
        }

    }

    public Boolean getSetCasaSelecionada(String posicao) {
        boolean retorno = false;
        for (Casa item : tabuleiro) {
            if (item.getPosicao().equals(posicao)) {
                item.setCasaSelecionada(!item.getCasaSelecionada());
                retorno = !item.getCasaSelecionada();
                break;

            }

        }
        return retorno;
    }

    private boolean podeSelecionarACasa(String strPosicao, Boolean casaVazia) {
        int i = 0;


        if (casaVazia) {
            if ((getCasa(strPosicao).getStrCor().equals(""))) {
                i = 1;
            }
        } else {
            casa1 = getCasa(strPosicao);

            if (!(casa1.getStrCor().equals("")) && (blnBrancasJogam == true ? casa1.getStrCor().equals("BRANCO") : casa1.getStrCor().equals("PRETO"))) {
                i = 1;
            }
        }

        if (i == 0) {
            //Toast.makeText(contexto, "Jogada invalida (podeSelecionarACasa)", Toast.LENGTH_SHORT).show();
            //img.setBackgroundColor(contexto.getResources().getColor(R.color.marron));
            return false;
        }
        return true;
    }

    private Boolean verificaSeComePeca(String posicaoX, String posicaoY) {
        List<Casa> listCasas = new ArrayList<Casa>();
        Boolean podeComerPeca = true;
        Boolean damaComendo = false;
        Boolean primeiraCasaVazia = false;
        Boolean impar = true;
        String strX1 = "" + posicaoX.charAt(1);
        String strY1 = "" + posicaoX.charAt(2);
        String strX2 = "" + posicaoY.charAt(1);
        String strY2 = "" + posicaoY.charAt(2);

        int x1 = Integer.parseInt(strX1);
        int y1 = Integer.parseInt(strY1);
        int x2 = Integer.parseInt(strX2);
        int y2 = Integer.parseInt(strY2);

        if (Math.abs(y1 - y2) <= 1) {
            return true;
        }

        Casa c, c2;

        for (int i = 1; i <= Math.abs(y1 - y2); i++) {
            if (x1 < x2) {
                if (y1 < y2) {
                    c = getCasa("p" + (x1 + i) + "" + (y1 + i));
                }
                else {
                    c = getCasa("p" + (x1 + i) + "" + (y1 - i));
                }
            }
            else {
                if (y1 < y2) {
                    c = getCasa("p" + (x1 - i) + "" + (y1 + i));
                }
                else {
                    c = getCasa("p" + (x1 - i) + "" + (y1 - i));
                }

            }
            listCasas.add(c);
        }
        c2 = getCasa(posicaoX);


        for (Casa casa : listCasas) {
            //Primira Precisa ter a peca
            //Segunda não pode ter a peca

            //se for dama
            if(c2.getDama()){
               if (!casa.getStrCor().equals("")){
                   if(!(casa.getStrCor().equals(c2.getStrCor()))){
                       damaComendo = true;
                   }
                   else{
                       podeComerPeca = false;
                       break;
                   }

               }

               if(damaComendo){
                   if (impar) {
                       impar = false;
                       if ((casa.getStrCor().equals("")) || (casa.getStrCor().equals(c2.getStrCor()))) {
                           primeiraCasaVazia = true;
                       }
                       else{
                           if(primeiraCasaVazia){
                               primeiraCasaVazia = false;
                           }
                       }
                   } else {
                       impar = true;
                       if (!(casa.getStrCor().equals(""))) {
                           podeComerPeca = false;
                       }
                       else {
                           primeiraCasaVazia = true;
                       }

                   }
                   if(primeiraCasaVazia = false){
                       podeComerPeca = false;
                       break;
                   }
               }
            }
            else {

                if (impar) {
                    impar = false;
                    if ((casa.getStrCor().equals("")) || (casa.getStrCor().equals(c2.getStrCor()))) {

                        podeComerPeca = false;
                        break;

                    }
                } else {
                    impar = true;
                    if (!(casa.getStrCor().equals(""))) {
                        podeComerPeca = false;
                        break;
                    }

                }
            }
        }


        if (podeComerPeca) {
            for (Casa casa : listCasas) {
                tabuleiro.remove(casa);
                ImageView img = casa.getImageView();
                img.setImageResource(0);
                casa.setImageView(img);
                casa.setStrCor("");
                tabuleiro.add(casa);
            }


        }
        else {
            return false;

        }
        return true;

    }

    private Boolean validaPosicao(String posicaoX, String posicaoY) {
        List<String> posicoesPossiveis = getPosicoesPosiveis(posicaoX);

        Boolean posicaoValida = false;
        for (String strPosicao : posicoesPossiveis) {
            if ((strPosicao.equals(posicaoY))) {
                posicaoValida = true;
            }
        }

        if (!posicaoValida) {
            //Toast.makeText(contexto, "Jogada invalida(validaPosicao)", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public Boolean viraDama(String strPosicao){
        String strX1 = "" + strPosicao.charAt(2);

        if (((Integer.parseInt(strX1) == 8) && blnBrancasJogam) ||((Integer.parseInt(strX1) == 1) && !blnBrancasJogam) ){

            return true;
        }
        return false;
    }
    public void mover(String posicao, ImageView img) {
        if (!(getSetCasaSelecionada(posicao))) {

            if (this.x.equals("")) {
                if (podeSelecionarACasa(posicao, false)) {
                    this.x = posicao;
                    img.setBackgroundColor(contexto.getResources().getColor(R.color.colorPrimary));
                }
            } else {
                if (podeSelecionarACasa(posicao, true)) {
                    if (validaPosicao(this.x, posicao)) {
                        if (verificaSeComePeca(this.x, posicao)) {
                            this.y = posicao;
                            img.setBackgroundColor(contexto.getResources().getColor(R.color.colorPrimary));
                        }

                    }
                }
            }
        } else {
            img.setBackgroundColor(contexto.getResources().getColor(R.color.marron));
            if (this.x.equals(posicao)) {
                this.x = "";
            } else {
                this.y = "";
            }
        }

        if (!(this.x.equals("")) && !(this.y.equals(""))) {
            Casa casa1 = getCasa(this.y);
            Casa casa2 = getCasa(this.x);

            //Lance
            Lance lance = new Lance();
            lance.setPeca(casa2.getPeca());
            lance.setLance(getLance(this.x) + "-" + getLance(this.y));
            listLance.add(lance);

            if(sala >0){
                if(jogador1.getNome().equals(currentUser.getEmail().toString()) ) {
                    //Brancas
                    myRefBranco.setValue(this.x + "-" + this.y);

                }
                else{
                    myRefPreto.setValue(this.x + "-" + this.y);
                }
            }


            tabuleiro.remove(casa1);
            tabuleiro.remove(casa2);

            img = casa1.getImageView();
            img.setImageResource(casa2.getlngIdImagemPeca());

            if (viraDama(casa1.getPosicao())){
                if (blnBrancasJogam) {
                    img.setImageResource(R.drawable.damabranca);
                    casa1.setLngIdImagemPeca(R.drawable.damabranca);
                }
                else{
                    img.setImageResource(R.drawable.damapreta);
                    casa1.setLngIdImagemPeca(R.drawable.damapreta);
                }
                casa1.setDama(true);
            }
            else{
                casa1.setDama(casa2.getDama());
                casa1.setLngIdImagemPeca(casa2.getlngIdImagemPeca());
            }

            img.setBackgroundColor(contexto.getResources().getColor(R.color.marron));
            casa1.setImageView(img);
            casa1.setStrCor(casa2.getStrCor());
            casa1.setCasaSelecionada(false);
            tabuleiro.add(casa1);
            //Verifica e seta se vira dama



            img = casa2.getImageView();
            img.setImageResource(0);
            img.setBackgroundColor(contexto.getResources().getColor(R.color.marron));
            casa2.setStrCor("");
            casa2.setLngIdImagemPeca(0);
            casa2.setImageView(img);
            casa2.setCasaSelecionada(false);
            tabuleiro.add(casa2);

            this.x = "";
            this.y = "";
            blnBrancasJogam = !blnBrancasJogam;

        }
        //online
        if(sala >0){

        }
        else {
            if ((!blnBrancasJogam) && (jogador2.getNome().equals("CPU"))) {
                //Seleciona uma peca preta (sorteada)
                Boolean blnMoveu = false;
                for (Casa casa : tabuleiro) {
                    if (casa.getStrCor().equals("PRETO")) {
                        List<String> posicoesPossiveis = getPosicoesPosiveis(casa.getPosicao());
                        for (String strposicao : posicoesPossiveis) {
                            if (podeSelecionarACasa(strposicao, true)) {
                                if (validaPosicao(casa.getPosicao(), strposicao)) {
                                    if (verificaSeComePeca(casa.getPosicao(), strposicao)) {
                                        blnMoveu = true;

                                        //copiei la de cima mas poderia ser diferente
                                        Casa casa1 = getCasa(strposicao);
                                        Casa casa2 = getCasa(casa.getPosicao());

                                        tabuleiro.remove(casa1);
                                        tabuleiro.remove(casa2);

                                        img = casa1.getImageView();
                                        img.setImageResource(casa2.getlngIdImagemPeca());

                                        if (viraDama(casa1.getPosicao())) {
                                            if (blnBrancasJogam) {
                                                img.setImageResource(R.drawable.damabranca);
                                                casa1.setLngIdImagemPeca(R.drawable.damabranca);
                                            } else {
                                                img.setImageResource(R.drawable.damapreta);
                                                casa1.setLngIdImagemPeca(R.drawable.damapreta);
                                            }
                                            casa1.setDama(true);
                                        } else {
                                            casa1.setDama(casa2.getDama());
                                            casa1.setLngIdImagemPeca(casa2.getlngIdImagemPeca());
                                        }

                                        img.setBackgroundColor(contexto.getResources().getColor(R.color.marron));
                                        casa1.setImageView(img);
                                        casa1.setStrCor(casa2.getStrCor());
                                        casa1.setCasaSelecionada(false);
                                        tabuleiro.add(casa1);
                                        //Verifica e seta se vira dama


                                        img = casa2.getImageView();
                                        img.setImageResource(0);
                                        img.setBackgroundColor(contexto.getResources().getColor(R.color.marron));
                                        casa2.setStrCor("");
                                        casa2.setLngIdImagemPeca(0);
                                        casa2.setImageView(img);
                                        casa2.setCasaSelecionada(false);
                                        tabuleiro.add(casa2);

                                        Lance lance = new Lance();
                                        lance.setPeca(casa2.getPeca());
                                        lance.setLance(getLance(casa.getPosicao()) + "-" + getLance(strposicao));
                                        listLance.add(lance);

                                        this.x = "";
                                        this.y = "";
                                        blnBrancasJogam = !blnBrancasJogam;
                                        //fim

                                        break;
                                    }
                                }
                            }
                        }
                        if (blnMoveu) {
                            break;
                        }
                    }

                }
            }
        }
    }

    private Casa getCasa(String posicao) {
        for (Casa item : tabuleiro) {
            if (item.getPosicao().equals(posicao)) {
                return item;
            }
        }
        return null;
    }

    private List<String> getPosicoesPosiveis(String posicao) {
        List<String> posicoesPossiveis = new ArrayList<String>();
        casa1 = getCasa(posicao);

        String strX = "" + posicao.charAt(1);
        String strY = "" + posicao.charAt(2);

        int x = Integer.parseInt(strX);
        int y = Integer.parseInt(strY);

        int xAxiliar = x;
        int yAxiliar = y;

        //Testar diagonal direita cima (VISAO DAS BRANCAS)

        while ((!(xAxiliar == 8)) && (!(yAxiliar == 8))) {
            xAxiliar++;
            yAxiliar++;
            //pretas não podem andar para traz
            if (!(casa1.getStrCor().equals("PRETO") && (Math.abs(yAxiliar - y) == 1) && !casa1.getDama())) {
                posicoesPossiveis.add("p" + (xAxiliar) + "" + (yAxiliar));
            }
        }

        xAxiliar = x;
        yAxiliar = y;

        //Testar Diagonal esquerda cima (VISAO DAS BRANCAS)
        while ((!(xAxiliar == 1)) && (!(yAxiliar == 8))) {
            xAxiliar--;
            yAxiliar++;
            //pretas não podem andar para traz
            if (!(casa1.getStrCor().equals("PRETO") && (Math.abs(yAxiliar - y) == 1) && !casa1.getDama())) {
                posicoesPossiveis.add("p" + (xAxiliar) + "" + (yAxiliar));
            }
        }

        xAxiliar = x;
        yAxiliar = y;

        //Testar Diagonal direita baixo (VISAO DAS BRANCAS)
        while ((!(xAxiliar == 8)) && (!(yAxiliar == 1))) {
            xAxiliar++;
            yAxiliar--;
            //Brancas não podem andar para traz
            if (!(casa1.getStrCor().equals("BRANCO") && (Math.abs(yAxiliar - y) == 1) && !casa1.getDama()) ) {
                posicoesPossiveis.add("p" + (xAxiliar) + "" + (yAxiliar));
            }
        }

        xAxiliar = x;
        yAxiliar = y;

        //Testar Diagonal esquerda baixo (VISAO DAS BRANCAS)
        while ((!(xAxiliar == 1)) && (!(yAxiliar == 1))) {
            xAxiliar--;
            yAxiliar--;
            //Brancas não podem andar para traz
            if (!(casa1.getStrCor().equals("BRANCO") && (Math.abs(yAxiliar - y) == 1) && !casa1.getDama())) {
                posicoesPossiveis.add("p" + (xAxiliar) + "" + (yAxiliar));
            }
        }
        return posicoesPossiveis;
    }

    public String getFimDeJogo(){
        Boolean blnFimDeJogoBrancas = false;
        Boolean blnFimDeJogoPretas = false;
        for (Casa casa:tabuleiro) {
            if (casa.getStrCor().equals("BRANCO")){

                blnFimDeJogoBrancas = true;
            }
            if (casa.getStrCor().equals("PRETO")){

                blnFimDeJogoPretas = true;
            }
            if (blnFimDeJogoBrancas && blnFimDeJogoPretas){
                break;
            }
        }
        if(blnFimDeJogoBrancas && !blnFimDeJogoPretas){
            jogador1.setNumeroVitorias(1);
            jogador2.setNumeroDerrotas(1);
            return "BRANCO";
        }
        else if(blnFimDeJogoPretas && !blnFimDeJogoBrancas){
            jogador2.setNumeroVitorias(1);
            jogador1.setNumeroDerrotas(1);
            return "PRETO";
        }
        else{
            return "";
        }

    }
    private String getLance(String x){
        String strX = "" + x.charAt(1);
        String strY = "" + x.charAt(2);

        int intX = Integer.parseInt(strX);
        int intY = Integer.parseInt(strY);

        switch (intX) {
            case 1:
                strX = "A" + intY;
                break;
            case 2:
                strX = "B" + intY;
                break;
            case 3:
                strX = "C" + intY;
                break;
            case 4:
                strX = "D" + intY;
                break;
            case 5:
                strX = "E" + intY;
                break;
            case 6:
                strX = "F" + intY;
                break;
            case 7:
                strX = "G" + intY;
                break;
            case 8:
                strX = "H" + intY;
                break;
        }
        return strX;

    }

    public Jogador getJogador1() {
        return jogador1;
    }

    public Jogador getJogador2() {
        return jogador2;
    }
}



