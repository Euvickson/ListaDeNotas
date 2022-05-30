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

public class AdapterRecyclerview extends RecyclerView.Adapter<AdapterRecyclerview.NotaViewHolder> {

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
    public AdapterRecyclerview.NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_recycler_view, parent, false);
        return new NotaViewHolder(viewCriada);
    }

    //Quando chegamos nesse método, recebemos um ViewHolder nos parâmetros. Esse viewHolder é a view "Atual" que precisa receber as informações. Então usamos a referência "holder"
    //Para mandar as informações que queremos. Então pegamos o espaço que queremos preencher, a partir do layout da lista definido no onCreateViewHolder, e setamos o texto a partir
    //Da nota dentro de sua posição
    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerview.NotaViewHolder holder, int position) {
        Nota nota = notas.get(position);

        //Trabalhar buscando as view sempre que o onBindViewHolder for chhamado, pode ser um pouco custoso a depender dos tipos de view que a gente estiver trabalhando, logo, é bom
        //delegar essa responsabilidade ao NotaViewHolder que já terá as views buscadas e prontas para receberem essas informações, evitando custo na performance

        //Podemos mandar a nossa classe "NotaViewHolder" dentro do extends, para indicar qual que é o viewHolder com o qual iremos trabalhar. E para evitar de fazer o cast
        holder.vincula(nota);
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    class NotaViewHolder extends RecyclerView.ViewHolder {

        private final TextView titulo;
        private final TextView descricao;

        public NotaViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_recyclerview_titulo);
            descricao = itemView.findViewById(R.id.item_recyclerview_descricao);
        }

        public void vincula(Nota nota){
            titulo.setText(nota.getTitulo());
            descricao.setText(nota.getDescricao());
        }
    }
}
