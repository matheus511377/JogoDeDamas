package com.matheus.jogodedamas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.matheus.jogodedamas.Classes.Contabilista;
import com.matheus.jogodedamas.Classes.Jogador;
import com.matheus.jogodedamas.Classes.Lance;
import com.matheus.jogodedamas.Classes.Partida;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PlacarActivity extends AppCompatActivity {
    private ListView listaPecas;
    private List<Lance> listaLances = new ArrayList<>();
    private adapterJogadores adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placar);
        Intent it = getIntent();
        String cpu =it.getStringExtra("CPU");

        TextView t =  (TextView) findViewById(R.id.txtInformacao);
        t.setText(cpu);
        listaPecas = (ListView) findViewById(R.id.lista);

        List<Jogador> jogadorList = new ArrayList<>();

        List<Partida> partidas =  Contabilista.getPartidaList();
        for (Partida p:partidas) {

            if(jogadorList.size() == 0){
                jogadorList.add( p.getJogo().getJogador1());
                jogadorList.add( p.getJogo().getJogador2());
            }
            else{
                boolean achou1 = false;
                boolean achou2 = false;
                for (int i=0; i<jogadorList.size();i++) {
                    Jogador j = jogadorList.get(i);
                    if(j.getNome().equals(p.getJogo().getJogador1().getNome())){
                        j.setNumeroVitorias(p.getJogo().getJogador1().getNumeroVitorias() == null?0:p.getJogo().getJogador1().getNumeroVitorias() + j.getNumeroVitorias());
                        j.setNumeroDerrotas(p.getJogo().getJogador1().getNumeroDerrotas() == null?0:p.getJogo().getJogador1().getNumeroDerrotas() + j.getNumeroDerrotas());
                        achou1 = true;

                    }

                    //Jogador 2
                    if(j.getNome().equals(p.getJogo().getJogador2().getNome())){
                        j.setNumeroVitorias(p.getJogo().getJogador2().getNumeroVitorias() == null?0:p.getJogo().getJogador2().getNumeroVitorias() + j.getNumeroVitorias());
                        j.setNumeroDerrotas(p.getJogo().getJogador2().getNumeroDerrotas() == null?0:p.getJogo().getJogador2().getNumeroDerrotas() + j.getNumeroDerrotas());
                        achou2 = true;
                    }
                    if (achou1 && achou2){
                        break;
                    }


                }
                if (!achou1){
                    jogadorList.add(p.getJogo().getJogador1());
                }
                if(!achou2){
                    jogadorList.add(p.getJogo().getJogador2());
                }
            }

        }

        adapter = new adapterJogadores(jogadorList,this);
        listaPecas.setAdapter(adapter);
    }
}
