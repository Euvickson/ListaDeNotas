package br.com.listadenotas.ui.recyclerview.adapter.listener;

import br.com.listadenotas.model.Nota;

public interface onItemClickListener {
    //O comportamento que espera-se dentro da interface é o clique de item. Essa ação deve ser executada para cada clique dentro do viewHolder. O esperado é que quando o item seja
    //Clicado, a nota seja enviada. Para trabalhar com a edição de notas a posição da nota clicada também deve ser enviada.
    void onItemClick(Nota nota, int posicao);
}
