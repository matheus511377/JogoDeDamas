package com.matheus.jogodedamas;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.matheus.jogodedamas.Classes.Casa;
import com.matheus.jogodedamas.Classes.Contabilista;
import com.matheus.jogodedamas.Classes.Jogador;
import com.matheus.jogodedamas.Classes.Jogo;
import com.matheus.jogodedamas.Classes.Lance;
import com.matheus.jogodedamas.Classes.Partida;
import com.matheus.jogodedamas.Classes.Peca;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Jogo jogo;
    private ImageView img;
    private Jogador jogador1;
    private Jogador jogador2;
    private int sala;
    private ListView listaPecas;
    private List<Lance> listaLances = new ArrayList<>();
    private adapterLances adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent intent = getIntent();
        this.jogador1 = (Jogador) intent.getSerializableExtra("Jogador1");
        this.jogador2 = (Jogador) intent.getSerializableExtra("Jogador2");
        this.sala = intent.getIntExtra("sala",0);
        TextView t = (TextView) findViewById(R.id.txtJogadores);
        t.setText(jogador1.getNome() + " VS " + jogador2.getNome());
        montarTabuleiro();
        listaPecas = (ListView) findViewById(R.id.lista);

        adapter = new adapterLances(listaLances,this);
        listaPecas.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onStart(){
        super.onStart();
    }
    private void montarTabuleiro(){

        jogo = new Jogo(this,jogador1,jogador2,adapter,listaLances, sala);
        Partida partida = new Partida();
        partida.setJogo(jogo);
        Contabilista.setPartida(partida);

        // monta o jogo
        for (Casa item: jogo.getTabuleiro()) {

            img = (ImageView) findViewById(getIdCampo(this,item.getPosicao()));
            if (!(img == null)){
                img.setImageResource(item.getlngIdImagemPeca());
                img.setOnClickListener(this);
                item.setImageView(img);

            }
        }

    }
    public static int getIdCampo(Context pContext, String strNomeCampo){
        return pContext.getResources().getIdentifier(strNomeCampo, "id", pContext.getPackageName());
    }

    public static String getNameCampo(Context pContext, int idCampo){
        return pContext.getResources().getResourceName(idCampo).replace("com.matheus.jogodedamas:id/","");
    }

    @Override
    public void onClick(View view) {
        img = (ImageView) findViewById(view.getId());

        jogo.mover(getNameCampo(this,view.getId()),img);
        adapter.notifyDataSetChanged();

        if(!jogo.getFimDeJogo().equals("")){
            Toast.makeText(this, "FIM DE JOGO " + jogo.getFimDeJogo() + " GANHARAM", Toast.LENGTH_SHORT).show();
            Intent it = new Intent(this,PlacarActivity.class);
            it.putExtra("CPU",jogo.getFimDeJogo() + " GANHARAM");
            startActivity(it);
            finish();
        }

    }
}
