package br.com.listadenotas.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    //Quando chegamos nesse método, recebemos um ViewHolder nos parâmetros. Esse viewHolder é a view "Atual" que precisa receber as informações. Então usamos a referência "holder"
    //Para mandar as informações que queremos. Então pegamos o espaço que queremos preencher, a partir do layout da lista definido no onCreateViewHolder, e setamos o texto a partir
    //Da nota dentro de sua posição
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Nota nota = notas.get(position);
        TextView tituloDoItem = holder.itemView.findViewById(R.id.item_recyclerview_titulo);
        tituloDoItem.setText(nota.getTitulo());
        TextView descricaoDoItem = holder.itemView.findViewById(R.id.item_recyclerview_descricao);
        descricaoDoItem.setText(nota.getDescricao());
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
