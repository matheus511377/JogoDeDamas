package com.matheus.jogodedamas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.matheus.jogodedamas.Classes.Jogador;

import org.w3c.dom.Text;

public class JogadoresActivity extends AppCompatActivity {

    Jogador jogador1;
    Jogador jogador2;
    TextView txtJogador1;
    TextView txtJogador2;

    boolean cpu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogadores);
        Intent i = getIntent();
        cpu =i.getBooleanExtra("CPU",false);
        txtJogador1 = (TextView) findViewById(R.id.txtJogador1);
        txtJogador2 = (TextView) findViewById(R.id.txtJogador2);

        if(cpu){
            txtJogador2.setText("CPU");
            txtJogador2.setEnabled(false);
        }
    }
    public void jogar(View v){





        if(txtJogador1.getText().toString().equals("")){
            Toast.makeText(this, "Nome do jogador 1 invalido", Toast.LENGTH_SHORT).show();
            txtJogador1.requestFocus();

            return;
        }
        if(txtJogador2.getText().toString().equals("")){
            Toast.makeText(this, "Nome do jogador 2 invalido", Toast.LENGTH_SHORT).show();
            txtJogador2.requestFocus();
            return;
        }

        jogador1 = new Jogador();
        jogador1.setNome(txtJogador1.getText().toString());

        jogador2 = new Jogador();
        jogador2.setNome(txtJogador2.getText().toString());

        Intent it = new Intent(this, MainActivity.class);
        Bundle b = new Bundle();

        b.putSerializable("Jogador1",jogador1);
        b.putSerializable("Jogador2",jogador2);
        it.putExtras(b);
        startActivity(it);
        finish();
    }
}
