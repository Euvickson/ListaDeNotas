package br.com.listadenotas.ui.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import br.com.listadenotas.R;
import br.com.listadenotas.model.Nota;

public class AdapterRecyclerview extends RecyclerView.Adapter<AdapterRecyclerview.NotaViewHolder> {

    private final List<Nota> notas;
    private final Context context;
    private br.com.listadenotas.ui.recyclerview.adapter.listener.onItemClickListener onItemClickListener;

    public AdapterRecyclerview(List<Nota> notas, Context context) {
        this.notas = notas;
        this.context = context;
    }

    //Precisamos fazer o set para agir conforme já é costume ao implementar algum tipo de ação do clique, nesse caso, um setOnItemClickListener.
    public void setOnItemClickListener(br.com.listadenotas.ui.recyclerview.adapter.listener.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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

    public void insereNotaNova(Nota nota) {
        notas.add(nota);
        notifyItemInserted(notas.size());
    }

    public void altera(int posicao, Nota nota) {
        notas.set(posicao, nota);
        notifyItemChanged(posicao);
    }

    public void remove(int posicao) {
        notas.remove(posicao);
        notifyItemRemoved(posicao);
    }

    public void troca(int posicaoInicial, int posicaoFinal) {
        Collections.swap(notas, posicaoInicial, posicaoFinal);
        notifyItemMoved(posicaoInicial, posicaoFinal);
    }

    class NotaViewHolder extends RecyclerView.ViewHolder {

        private final TextView titulo;
        private final TextView descricao;
        //Como vamos precisar enviar a nota como parâmetro do onItemClickListener, tornamos a nota um atributo da classe viewHolder para conseguir trabalhar com ela.
        private Nota nota;

        public NotaViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_recyclerview_titulo);
            descricao = itemView.findViewById(R.id.item_recyclerview_descricao);
            //Aqui setamos o click do item, com a setOnClickListener, que é uma função que todos possuem, mas dentro dele nós chamamos o onItemClickListener.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Possibilitamos a ação de implementação para qualquer um que chamar o método. Ao invés de fazer uma implementação específica, delegamos a implementação para quem
                    //Chama.

                    //Para a posição, poderíamos trabalhar da mesma maneira que a nota, transformando em um atributo de classe e enviando, através do método "vincula" a posição vinda
                    //do onBindViewHolder, mas o próprio viewHolder conhece a sua posição. Então é só chamar o método getAdapterPosition que estamos enviando essa informação.
                    onItemClickListener.onItemClick(nota, getAdapterPosition());
                }
            });
        }

        public void vincula(Nota nota){
            this.nota = nota;
            preencheCampos(nota);
        }

        private void preencheCampos(Nota nota) {
            titulo.setText(nota.getTitulo());
            descricao.setText(nota.getDescricao());
        }
    }
}
