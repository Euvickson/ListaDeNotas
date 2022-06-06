package br.com.listadenotas.ui.activity;

import static br.com.listadenotas.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.listadenotas.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_EDITA_NOTA;
import static br.com.listadenotas.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_ENVIA_NOTA;
import static br.com.listadenotas.ui.activity.NotaActivityConstantes.CODIGO_RESULTADO_NOTA_CRIADA;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

import br.com.listadenotas.DAO.NotaDao;
import br.com.listadenotas.R;
import br.com.listadenotas.model.Nota;
import br.com.listadenotas.ui.adapter.AdapterRecyclerview;
import br.com.listadenotas.ui.adapter.listener.onItemClickListener;

public class ListaDeNotasActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Notas";
    private AdapterRecyclerview adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_notas);

        setTitle(TITULO_APPBAR);

        NotaDao dao = new NotaDao();
        AdicionaNotasDeExemplo(dao);
        List<Nota> todasNotas = dao.todos();
        configuraRecyclerView(todasNotas);
        configuraBotaoInsereNovaNota();
    }

    private void AdicionaNotasDeExemplo(NotaDao dao) {
        for (int i = 0; i <= 10; i++) {
            Nota nota = new Nota("Título " + i, "Descrição " + i);
            dao.insere(nota);
        }
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        RecyclerView listaDeNotas = findViewById(R.id.lista_notas_recyclerView);
        configuraAdapter(todasNotas, listaDeNotas);
        //Se tentarmos fazer a listaDeNotas.setOnItemClickListener, iremos ver que essa função não existe, apenas há a implementação padrão que toda view tem, com o setOnClickListener
        //Logo teremos que implementar por nós mesmos como a lista se comportará ao receber o clique em um de seus itens. Se pensarmos no RecyclerView, temos os viewHolders que
        //Representam as views dentro da lista, mas a melhor maneira seria uma interface.
        configuraLayoutManager(listaDeNotas);
    }

    private void configuraAdapter(List<Nota> todasNotas, RecyclerView listaDeNotas) {
        adapter = new AdapterRecyclerview(todasNotas, this);
        listaDeNotas.setAdapter(adapter);
        adapter.setOnItemClickListener(new onItemClickListener() {
            @Override
            public void onItemClick(Nota nota) {
                Intent inicializaEdicaoDeNota = new Intent(ListaDeNotasActivity.this, InsereNotaActivity.class);
                inicializaEdicaoDeNota.putExtra(CHAVE_NOTA, nota);
                startActivityForResult(inicializaEdicaoDeNota, CODIGO_REQUISICAO_EDITA_NOTA);
            }
        });
    }

    private void configuraLayoutManager(RecyclerView listaDeNotas) {
        //Com o LinearLayoutManager podemos setar a orientação da lista, se quisermos que ela seja horizontal só deveremos mandar, além do contexto, a constante e a direção da lista
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listaDeNotas.setLayoutManager(linearLayoutManager);
    }

    private void configuraBotaoInsereNovaNota() {
        TextView botaoInsereNota = findViewById(R.id.lista_notas_nova_nota);
        botaoInsereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vaiParaInsereNotaActivity = new Intent(ListaDeNotasActivity.this, InsereNotaActivity.class);
                //Essa é uma maneira de enviar um intent esperando um resultado. Esse método foi descontinuado, é necessário atualizar
                startActivityForResult(vaiParaInsereNotaActivity, CODIGO_REQUISICAO_ENVIA_NOTA);
            }
        });
    }

    //A onActivityResult é onde trabalhamos os objetos recebidos a partir do startActivityForResult. Nele temos a capacidade de checar vários objetos, a partir do código de requisição,
    //do código de resultado e da chave string que recebemos.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (verificaRetornoComNotaNova(requestCode, resultCode, data)) {
            Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
            new NotaDao().insere(notaRecebida);
            adapter.insereNotaNova(notaRecebida);
        } else if (verificaRetornoDeNotaEditada(requestCode, resultCode, data)){
            Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
            Toast.makeText(this, notaRecebida.getTitulo(), Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean verificaRetornoDeNotaEditada(int requestCode, int resultCode, @Nullable Intent data) {
        return vertificaCodigoRequisicaoNotaEditada(requestCode) && verificaCodigoResultado(resultCode) && verificaIntent(data);
    }

    private boolean vertificaCodigoRequisicaoNotaEditada(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_EDITA_NOTA;
    }

    private boolean verificaRetornoComNotaNova(int requestCode, int resultCode, @Nullable Intent data) {
        return verificaCodigoDeRequisicao(requestCode) && verificaCodigoResultado(resultCode) && verificaIntent(data);
    }

    private boolean verificaCodigoDeRequisicao(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_ENVIA_NOTA;
    }

    private boolean verificaCodigoResultado(int resultCode) {
        return resultCode == CODIGO_RESULTADO_NOTA_CRIADA;
    }

    private boolean verificaIntent(@Nullable Intent data) {
        return data.hasExtra(CHAVE_NOTA);
    }
}