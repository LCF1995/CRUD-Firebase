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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastrarAutor extends AppCompatActivity {

    private EditText trabalho;
    private EditText autor;
    private EditText linguagem;
    private EditText git;
    private EditText usuario;
    private EditText email;
    private EditText senha;

    private Button cadastrar;
    private Button cancelar;

    private FirebaseAuth auth;

    private Usuario u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_autor);

        trabalho = findViewById(R.id.editNomeTrabalho);
        autor = findViewById(R.id.editNomeAutor);
        linguagem = findViewById(R.id.editLinguagem);
        git = findViewById(R.id.editGit);
        usuario = findViewById(R.id.editNomeUsuarioCadastro);
        email = findViewById(R.id.editEmailCadastro);
        senha = findViewById(R.id.editSenhaCadastro);
        cadastrar = findViewById(R.id.btnCadastrarAutor);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(trabalho.getText().toString().equals("") ||
                        autor.getText().toString().equals("") ||
                        linguagem.getText().toString().equals("") ||
                        git.getText().toString().equals("") ||
                        usuario.getText().toString().equals("") ||
                        senha.getText().toString().equals("")){
                    Toast.makeText(CadastrarAutor.this, "Preencha todos os dados!", Toast.LENGTH_SHORT).show();
                } else{

                    u = new Usuario();

                    u.setNomeTrabalho(trabalho.getText().toString());
                    u.setNome(autor.getText().toString());
                    u.setLinguagem(linguagem.getText().toString());
                    u.setGit(git.getText().toString());
                    u.setUsuario(usuario.getText().toString());
                    u.setEmail(email.getText().toString());
                    u.setSenha(senha.getText().toString());

                    cadastrar();


                }

            }
        });

    }

    public void cadastrar(){

        auth = ConfiguracaoFirebase.getFirebaseAutenticacao();

        auth.createUserWithEmailAndPassword(
                u.getEmail(),
                u.getSenha()
        ).addOnCompleteListener(CadastrarAutor.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    Toast.makeText(CadastrarAutor.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                    u.setId(auth.getUid());
                    u.salvar();

                    Preferencias preferencias = new Preferencias(CadastrarAutor.this);
                    preferencias.salvarDados(auth.getUid(), u.getNome());

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

                    Toast.makeText(CadastrarAutor.this, "Erro ao cadastrar usuário: " + erro, Toast.LENGTH_LONG ).show();

                }

            }
        });

    }

    public void abrirLoginUsuario(){
        Intent intent = new Intent(CadastrarAutor.this, Login.class);
        startActivity(intent);
        finish();
    }

}
