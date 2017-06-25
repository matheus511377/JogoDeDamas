package com.matheus.jogodedamas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CadastrarLogin extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText txtEmail;
    private EditText txtSenha;
    private EditText txtConfirmarSenha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_login);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
        txtConfirmarSenha = (EditText) findViewById(R.id.txtConfirmaSenha);
    }



    public void cadastrar(View v){


        mAuth = FirebaseAuth.getInstance();

        if (txtEmail.getText().toString().equals("")){
            Toast.makeText(this, "Senhas não conferem", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!txtSenha.getText().toString().equals(txtConfirmarSenha.getText().toString())){
            Toast.makeText(this, "Senhas não conferem", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(),txtSenha.getText().toString() )
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("A", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(CadastrarLogin.this, "Cadastrado", Toast.LENGTH_SHORT).show();
                            Intent it = new Intent(CadastrarLogin.this, Online.class);
                            startActivity(it);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("A", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CadastrarLogin.this, task.getException().getMessage().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
