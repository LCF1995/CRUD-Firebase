package com.example.luciana.crudfirebase.autores;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.luciana.crudfirebase.Alterar;
import com.example.luciana.crudfirebase.R;
import com.example.luciana.crudfirebase.config.ConfiguracaoFirebase;
import com.example.luciana.crudfirebase.helper.Preferencias;
import com.example.luciana.crudfirebase.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;

import java.util.List;

public class RecyclerAdapterAgendament  extends RecyclerView.Adapter<RowHolderAgendament>{

    private List<Usuario> feedItemList;
    private Context mContext;

    private int statusColor[] = {R.color.status0, R.color.status1, R.color.status2, R.color.status3, R.color.status4};

    Preferencias preferencias;

    DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();

    public RecyclerAdapterAgendament(Context context, List<Usuario> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;

        this.preferencias = new Preferencias(mContext);
    }

    @Override
    public RowHolderAgendament onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_agendament, null);
        RowHolderAgendament mh = new RowHolderAgendament(v);

        return mh;
    }

    @Override
    public void onBindViewHolder(final RowHolderAgendament feedListRowHolder, int i) {
        final Usuario feedItem = feedItemList.get(i);

        feedListRowHolder.title.setText(Html.fromHtml(feedItem.getLinguagem()));
        feedListRowHolder.status.setText(Html.fromHtml(feedItem.getUsuario()));
        feedListRowHolder.hours.setText(Html.fromHtml(feedItem.getGit()));
        feedListRowHolder.dayweek.setText(Html.fromHtml(feedItem.getNomeTrabalho()));
        feedListRowHolder.week.setText(Html.fromHtml(feedItem.getNome()));

        feedListRowHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, Alterar.class);
                i.putExtra("users", new Gson().toJson(feedItem));
                mContext.startActivity(i);


                    }



        });

        feedListRowHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

}
