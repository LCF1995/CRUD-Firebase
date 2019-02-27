package com.example.luciana.crudfirebase.autores;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.luciana.crudfirebase.R;

public class RowHolderAgendament extends RecyclerView.ViewHolder {

    protected TextView title, hours, status, week, dayweek;

    public RowHolderAgendament(View itemView) {
        super(itemView);

        this.title = (TextView) itemView.findViewById(R.id.textViewLinguagem);
        this.hours = (TextView) itemView.findViewById(R.id.textViewGit);
        this.status = (TextView) itemView.findViewById(R.id.textViewUsuario);
        this.week = (TextView) itemView.findViewById(R.id.textViewAutor);
        this.dayweek = (TextView) itemView.findViewById(R.id.textViewTrabalho);

    }
}
