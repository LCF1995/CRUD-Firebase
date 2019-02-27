package com.example.luciana.crudfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.luciana.crudfirebase.autores.ItemAgendament;
import com.example.luciana.crudfirebase.autores.RecyclerAdapterAgendament;
import com.example.luciana.crudfirebase.config.ConfiguracaoFirebase;
import com.example.luciana.crudfirebase.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button sair;
    private Button cadastrar;

    private FirebaseAuth usuarioFirebase;

    private List<Usuario> feedItemListAgendament = new ArrayList<Usuario>();
    private RecyclerView mRecyclerPromotion;
    private RecyclerAdapterAgendament adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuarioFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();
        cadastrar = findViewById(R.id.btnCadastrarNovo);

        mRecyclerPromotion = (RecyclerView) findViewById(R.id.recyclerViewAgendament);
        mRecyclerPromotion.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();

        referenciaFirebase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                feedItemListAgendament.clear();

                for(DataSnapshot obj:dataSnapshot.getChildren()){
                    Usuario a = obj.getValue(Usuario.class);
                    feedItemListAgendament.add(a);
                }

                Collections.reverse(feedItemListAgendament);



                adapter = new RecyclerAdapterAgendament(MainActivity.this, feedItemListAgendament);
                mRecyclerPromotion.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void deslogarUsuario(View v){

        usuarioFirebase.signOut();

        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        finish();

    }

}
