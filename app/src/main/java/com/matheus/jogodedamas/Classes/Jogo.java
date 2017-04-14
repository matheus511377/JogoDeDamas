package com.matheus.jogodedamas.Classes;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.matheus.jogodedamas.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matheus on 05/04/2017.
 */

public class Jogo {
    private static List<Casa> tabuleiro = new ArrayList<Casa>();
    private List<String> listInicialBranca;
    private List<String> listInicialPreta;
    private List<String> listInicialDemaisCasas;
    private List<String> listLetras;
    private List<Integer> listNumeros;

    private Context contexto;
    private String x = "";
    private String y = "";
    private Casa casa1;
    private Casa casa2;
    private boolean blnBrancasJogam = true;

    public Jogo(Context contexto) {
        this.contexto = contexto;
        inicializaTabuleiro();
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
        for (Casa item : tabuleiro) {
            if (item.getPosicao().equals(posicao)) {
                item.setCasaSelecionada(!item.getCasaSelecionada());
                return !item.getCasaSelecionada();
            }

        }
        return false;
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
            if (impar) {
                impar = false;
                if ((casa.getStrCor().equals("")) || (casa.getStrCor().equals(c2.getStrCor()))) {
                    podeComerPeca = false;
                    break;
                }
            }
            else {
                impar = true;
                if (!(casa.getStrCor().equals(""))) {
                    podeComerPeca = false;
                    break;
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

            tabuleiro.remove(casa1);
            tabuleiro.remove(casa2);

            img = casa1.getImageView();
            img.setImageResource(casa2.getlngIdImagemPeca());
            img.setBackgroundColor(contexto.getResources().getColor(R.color.marron));
            casa1.setImageView(img);
            casa1.setStrCor(casa2.getStrCor());
            casa1.setLngIdImagemPeca(casa2.getlngIdImagemPeca());
            casa1.setCasaSelecionada(false);
            tabuleiro.add(casa1);


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
            if (!(casa1.getStrCor().equals("PRETO") && (Math.abs(yAxiliar - y) == 1))) {
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
            if (!(casa1.getStrCor().equals("PRETO") && (Math.abs(yAxiliar - y) == 1))) {
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
            if (!(casa1.getStrCor().equals("BRANCO") && (Math.abs(yAxiliar - y) == 1))) {
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
            if (!(casa1.getStrCor().equals("BRANCO") && (Math.abs(yAxiliar - y) == 1))) {
                posicoesPossiveis.add("p" + (xAxiliar) + "" + (yAxiliar));
            }
        }
        return posicoesPossiveis;
    }

}

