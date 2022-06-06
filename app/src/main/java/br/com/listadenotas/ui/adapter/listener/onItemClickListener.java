package br.com.listadenotas.ui.adapter.listener;

import br.com.listadenotas.model.Nota;

public interface onItemClickListener {
    //O comportamento que espera-se dentro da interface é o clique de item. Essa ação deve ser executada para cada clique dentro do viewHolder. O esperado é que quando o item seja
    //Clicado, a nota seja enviada
    void onItemClick(Nota nota);
}
