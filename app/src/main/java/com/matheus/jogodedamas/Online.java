package com.matheus.jogodedamas;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.api.model.StringList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.matheus.jogodedamas.Classes.Jogador;
import com.matheus.jogodedamas.Classes.Jogadores;
import com.matheus.jogodedamas.Classes.Sala;
import com.matheus.jogodedamas.Classes.Teste;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Online extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView txtEmail;
    final ArrayList<Sala> cursos = new ArrayList<>();
    private AlertDialog alerta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);
        final FirebaseUser currentUser;

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("jogo");
        DatabaseReference myRef1 = database.getReference("jogo");
        final ArrayAdapter<Sala> adapter = new ArrayAdapter<Sala>(this,android.R.layout.simple_list_item_1,cursos);
        ListView listaDeCursos = (ListView) findViewById(R.id.lista);
        listaDeCursos.setAdapter(adapter);
        HashMap<String,Jogadores> stringTesteHashMap = new HashMap<>();
        for (int i = 1;i<=10;i++){
            Sala sala = new Sala();
            sala.numeroDaSala = i;
            cursos.add(sala);
            Jogador j1 = new Jogador();
            Jogadores jogadores = new Jogadores();
            jogadores.j1 = "";
            jogadores.j2 = "";
            stringTesteHashMap.put(String.valueOf(i), jogadores);
        }
        //myRef.setValue(stringTesteHashMap);

        listaDeCursos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                if(cursos.get(position).ocupado == true){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Online.this);
                    builder.setTitle("Sala lotada");
                    builder.setMessage("Favor utilizar outra sala");
                    alerta = builder.create();
                    alerta.show();
                    return;
                }
                DatabaseReference referencia = database.getReference("jogo/"+ (position + 1));
                FirebaseUser user = mAuth.getCurrentUser();
                Map<String, Object> childUpdates = new HashMap<>();
                Bundle b = new Bundle();
                Jogador jogador1 = new Jogador();
                Jogador jogador2 = new Jogador();
                if(cursos.get(position).nomeJogador1 == null){
                    childUpdates.put("j1", user.getEmail().toString());
                    jogador1.setNome(user.getEmail().toString());
                    b.putSerializable("Jogador1",jogador1);

                    jogador2.setNome("Aguardando jogador");
                    b.putSerializable("Jogador2",jogador2);
                }

                else{
                    childUpdates.put("j2", user.getEmail().toString());
                    jogador2.setNome(user.getEmail().toString());
                    b.putSerializable("Jogador2",jogador2);

                    jogador1.setNome("Aguardando jogador");
                    b.putSerializable("Jogador1",jogador1);
                }
                b.putInt("sala",position + 1);
                referencia.updateChildren(childUpdates);
                Intent it = new Intent(Online.this, MainActivity.class);
                it.putExtras(b);
                startActivity(it);
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        if(! (currentUser == null)){
            txtEmail.setText(currentUser.getEmail().toString());
        }

        Jogador jogador = new Jogador();
        jogador.setNome("bile");
        jogador.setNumeroVitorias(2);
        jogador.setNumeroDerrotas(1);


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue(String.class);
                //Log.d("BANCO", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("BANCO", "Failed to read value.", error.toException());
            }
        });


        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                ArrayList<HashMap<String,String>> post = ( ArrayList<HashMap<String,String>>)dataSnapshot.getValue();
                // ...
                int modificou = 0;
                if(post == null){
                    return;
                }
                for(int i = 0; i<=10;i++){
                    if (post.get(i) != null) {
                        HashMap<String, String> t = post.get(i);
                        if(t.get("j1") != null){
                            if (! t.get("j1").equals("")){
                                cursos.get(i-1).nomeJogador1 = t.get("j1");
                                modificou = 1;
                            }
                        }
                        if(t.get("j2") != null){
                            if (! t.get("j2").equals("")){
                                cursos.get(i-1).nomeJogador2 = t.get("j2");
                                modificou = 1;
                            }
                        }

                        if(t.get("j1") != null && t.get("j2") != null){
                            if (! t.get("j1").equals("") && ! t.get("j2").equals("")){
                                cursos.get(i-1).ocupado = true;
                            }
                        }
                        if(modificou ==1) {
                            adapter.notifyDataSetChanged();
                        }
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
        myRef.addValueEventListener(postListener);
    }
    public void desconectar(View v){
        FirebaseAuth.getInstance().signOut();
        finish();
    }
}
