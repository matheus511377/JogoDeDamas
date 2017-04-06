package com.matheus.jogodedamas;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.matheus.jogodedamas.Classes.Casa;
import com.matheus.jogodedamas.Classes.Peca;
import com.matheus.jogodedamas.Classes.Tabuleiro;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    Tabuleiro tabuleiro;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart(){
        super.onStart();
        montarTabuleiro();
    }


    private void montarTabuleiro(){
        tabuleiro = new Tabuleiro();

        // monta o tabuleiro
        for (Casa item:tabuleiro.getTabuleiro()) {

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

        tabuleiro.mover(getNameCampo(this,view.getId()),img,this);

    }
}
