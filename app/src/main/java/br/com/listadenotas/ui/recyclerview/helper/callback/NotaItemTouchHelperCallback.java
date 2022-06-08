package br.com.listadenotas.ui.recyclerview.helper.callback;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import br.com.listadenotas.DAO.NotaDao;
import br.com.listadenotas.ui.recyclerview.adapter.AdapterRecyclerview;

public class NotaItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private AdapterRecyclerview adapter;

    public NotaItemTouchHelperCallback(AdapterRecyclerview adapter) {
        this.adapter = adapter;
    }

    //Esse método é resposável por definir o que vamos permitir de animação. Deslizar pra direita ou esquerda
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

        //Pra fazer marcações para definir o deslize pra direita ou esquerda, utilizamos um valor inteiro, definido por constantes no ItemTouchHelper.
        //Para definir deslizes tanto pra direita quanto pra esquerda, usamos o padrão abaixo.
        int marcacoesDeDeslize = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;

        //Criar as marcações não é suficiente, temos que fazer também com que os movimentos que esperamos, sejam criados. por isso usamos o makeMovementFlags
        //o DragFlags é o usado pra arrastar os elementos. Pra não definir nada, é só usar o valor 0. Essa função devolve a resposta que enviamos no return
        return makeMovementFlags(0, marcacoesDeDeslize);
    }

    //A ideia desse é uma chamada pra quando um elemento for arrastado dentro do recyclerView
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    //Nesse daqui é quando o moviemnto for de deslize. É como se fosse uma chamada de listeners.
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        //A princípio, ao tentar remover a nota fazendo uma instância do DAO, devemos mandar a posição, mas não temos essa posição, porém temos o viewHolder
        //e ele mesmo sabe qual a posição do elemento deslizado na lista.
        int posicaoDaNotaDeslizada = viewHolder.getAdapterPosition();
        new NotaDao().remove(posicaoDaNotaDeslizada);

        //Da mesma maneira que removemos o item no DAO, devemos remover no adapter pra ele mesmo atualizar a lista de itens, mas não possuímos o adapter,
        //logo, vamos receber o adapter que queremos mexer, via construtor e torná-la um atributo de classe.
        adapter.remove(posicaoDaNotaDeslizada);
    }
}
