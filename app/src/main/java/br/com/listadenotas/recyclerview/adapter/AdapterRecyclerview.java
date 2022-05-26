package br.com.listadenotas.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.listadenotas.R;
import br.com.listadenotas.model.Nota;

public class AdapterRecyclerview extends RecyclerView.Adapter {

    private final List<Nota> notas;
    private final Context context;

    public AdapterRecyclerview(List<Nota> notas, Context context) {
        this.notas = notas;
        this.context = context;
    }

    //é chamado apenas uma vez, e APENAS cria todas as views necessárias para aparecerem na tela do usuário. As informações de cada item são colocados pelo onBindViewHolder.
    //Como queremos criar os containers(views) nesse método, precisamos fazer o inflate de um layout que queremos que apareça na tela que chamar esse adapter e que possuir a lista.
    //
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_recycler_view, parent, false);
        return new notaViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    private class notaViewHolder extends RecyclerView.ViewHolder {
        public notaViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
