package com.example.luciana.crudfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.luciana.crudfirebase.config.ConfiguracaoFirebase;
import com.example.luciana.crudfirebase.helper.Preferencias;
import com.example.luciana.crudfirebase.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private EditText email;
    private EditText senha;
    private Button btLogin;

    private Usuario usuario;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verificarUsuarioLogado();

        email = findViewById(R.id.editEmail);
        senha = findViewById(R.id.editSenha);
        btLogin = findViewById(R.id.btLogin);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(email.getText().toString().equals("") || senha.getText().toString().equals("")){
                Toast.makeText(Login.this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show();
            } else{

                usuario = new Usuario();
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                validarLogin();

            }

            }
        });


    }

    private void validarLogin(){

        auth = ConfiguracaoFirebase.getFirebaseAutenticacao();
        auth.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){//se deu certo o processo de autenticação

                    DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();

                    referenciaFirebase.child("users").child(auth.getUid()).limitToLast(100).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Usuario user = dataSnapshot.getValue(Usuario.class);

                            Preferencias preferencias = new Preferencias(Login.this);
                            preferencias.salvarDados(auth.getUid(), user.getNome());

                            verificarUsuarioLogado();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.err.println("The read failed: " + databaseError.getCode());
                        }
                    });
                } else{
                    Toast.makeText(Login.this, "Falha ao fazer login!", Toast.LENGTH_SHORT).show();
                    email.setText("");
                    senha.setText("");
                }

            }
        });

    }

    private void verificarUsuarioLogado(){
        auth = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if( auth.getCurrentUser() != null ){


            Preferencias preferencias = new Preferencias(Login.this);

            startActivity(new Intent(Login.this, MainActivity.class));
            this.finish();

        }
    }

    public void abrirCadastro(View v){

        Intent intent = new Intent(Login.this, Cadastro.class);
        startActivity(intent);

    }

}
