package br.com.listadenotas.recyclerview.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.listadenotas.model.Nota;

public class AdapterRecyclerview extends RecyclerView.Adapter {

    private final List<Nota> notas;
    private final Context context;

    public AdapterRecyclerview(List<Nota> notas, Context context) {
        this.notas = notas;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return notas.size();
    }
}
