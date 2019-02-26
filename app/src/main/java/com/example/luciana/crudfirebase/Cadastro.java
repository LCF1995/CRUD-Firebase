package com.example.luciana.crudfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Cadastro extends AppCompatActivity {

    private EditText nome;
    private EditText email;
    private EditText senha;
    private Button cadastrar;

    private Usuario usuario;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nome = findViewById(R.id.editNome);
        email = findViewById(R.id.editEmail);
        senha = findViewById(R.id.editSenha);
        cadastrar = findViewById(R.id.btCadastrar);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nome.getText().toString().equals("") || email.getText().toString().equals("") || senha.getText().toString().equals("")){
                    Toast.makeText(Cadastro.this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show();
                } else{

                    usuario = new Usuario();
                    usuario.setNome(nome.getText().toString());
                    usuario.setEmail(email.getText().toString());
                    usuario.setSenha(senha.getText().toString());

                    cadastrarUsuario();

                }

            }
        });


    }


    private void cadastrarUsuario(){

        auth = ConfiguracaoFirebase.getFirebaseAutenticacao();

        auth.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(Cadastro.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    Toast.makeText(Cadastro.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                    usuario.setId(auth.getUid());
                    usuario.salvar();

                    Preferencias preferencias = new Preferencias(Cadastro.this);
                    preferencias.salvarDados(auth.getUid(), usuario.getNome());

                    abrirLoginUsuario();


                } else{
                    String erro = "";
                    try{
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erro = "Escolha uma senha que contenha, letras e números.";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "Email indicado não é válido.";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erro = "Já existe uma conta com esse e-mail.";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(Cadastro.this, "Erro ao cadastrar usuário: " + erro, Toast.LENGTH_LONG ).show();

                }

            }
        });

    }

    public void abrirLoginUsuario(){
        Intent intent = new Intent(Cadastro.this, Login.class);
        startActivity(intent);
        finish();
    }

}
