package com.matheus.jogodedamas;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.matheus.jogodedamas.Classes.Jogador;

public class InicialActivity extends AppCompatActivity {
    private String m_Text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);
    }

    public void jogardedois(View v){
        Intent it = new Intent(this, JogadoresActivity.class);
        startActivity(it);
        finish();
    }
    public void jogadorVsCpu(View v){
        Intent it = new Intent(this, JogadoresActivity.class);
        it.putExtra("CPU",true);
        finish();
        startActivity(it);
    }

}
