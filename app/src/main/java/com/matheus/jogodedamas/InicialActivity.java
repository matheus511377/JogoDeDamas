package com.matheus.jogodedamas;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.matheus.jogodedamas.Classes.Contabilista;
import com.matheus.jogodedamas.Classes.Jogador;
import com.matheus.jogodedamas.Classes.Partida;

import java.util.ArrayList;
import java.util.List;

public class InicialActivity extends AppCompatActivity {
    private String m_Text = "";
    private List<Jogador> listJogadores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);



    }
    @Override
    public void onStart() {
        super.onStart();
    }


    public void jogardedois(View v){
        Intent it = new Intent(this, JogadoresActivity.class);

        startActivity(it);

    }
    public void online(View v){

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(! (currentUser == null)){
            Intent it = new Intent(this, Online.class);
            startActivity(it);
        }
        else{
            Intent it = new Intent(this, Login.class);
            startActivity(it);
        }



    }
    public void jogadorVsCpu(View v){
        Intent it = new Intent(this, JogadoresActivity.class);
        it.putExtra("CPU",true);
        startActivity(it);
    }
    public void placar(View v){
        Intent it = new Intent(this,PlacarActivity.class);
        it.putExtra("CPU","");
        startActivity(it);
    }

}
