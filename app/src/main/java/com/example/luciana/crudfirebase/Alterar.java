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
import com.example.luciana.crudfirebase.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;

public class Alterar extends AppCompatActivity {

    private EditText trabalho;
    private EditText autor;
    private EditText linguagem;
    private EditText git;
    private EditText usuario;
    private EditText email;
    private EditText senha;

    private Button alterar;
    private Button excluir;

    private Usuario u;

    DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar);

        trabalho = findViewById(R.id.editNomeTrabalhoAlterar);
        autor = findViewById(R.id.editNomeAutorAlterar);
        linguagem = findViewById(R.id.editLinguagemAlterar);
        git = findViewById(R.id.editGitAlterar);
        usuario = findViewById(R.id.editNomeUsuarioCadastroAlterar);
        email = findViewById(R.id.editEmailCadastroAlterar);
        senha = findViewById(R.id.editSenhaCadastroAlterar);

        alterar = findViewById(R.id.btnAlterar);
        excluir = findViewById(R.id.btnExcluir);

        referenciaFirebase = ConfiguracaoFirebase.getFirebase();

        Bundle extras;
        extras = getIntent().getExtras();

        u = new Gson().fromJson(extras.getString("users"), Usuario.class);

        trabalho.setText(u.getNomeTrabalho());
        autor.setText(u.getNome());
        linguagem.setText(u.getLinguagem());
        git.setText(u.getGit());
        usuario.setText(u.getUsuario());
        email.setText(u.getEmail());
        senha.setText(u.getSenha());

        excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                referenciaFirebase.child("users").child(u.getId()).removeValue();
                Toast.makeText(Alterar.this, "Removido com sucesso!", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = u.getId();

                u = new Usuario();
                u.setId(id);
                u.setNomeTrabalho(trabalho.getText().toString());
                u.setNome(autor.getText().toString());
                u.setLinguagem(linguagem.getText().toString());
                u.setGit(git.getText().toString());
                u.setUsuario(usuario.getText().toString());
                u.setEmail(email.getText().toString());
                u.setSenha(senha.getText().toString());

                referenciaFirebase.child("users").child(id)
                        .setValue(u)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Alterar.this, "Atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {

                                }
                            }
                        });



            }
        });


    }
}
