package br.com.listadenotas.ui;

import static br.com.listadenotas.ui.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.listadenotas.ui.NotaActivityConstantes.CODIGO_REQUISICAO_ENVIA_NOTA;
import static br.com.listadenotas.ui.NotaActivityConstantes.CODIGO_RESULTADO_NOTA_CRIADA;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.listadenotas.DAO.NotaDao;
import br.com.listadenotas.R;
import br.com.listadenotas.model.Nota;
import br.com.listadenotas.recyclerview.adapter.AdapterRecyclerview;

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
        adapter = new AdapterRecyclerview(todasNotas, this);
        RecyclerView listaDeNotas = findViewById(R.id.lista_notas_recyclerView);
        listaDeNotas.setAdapter(adapter);
        configuraLayoutManager(listaDeNotas);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (verificaRetornoComNota(requestCode, resultCode, data)) {
            Nota notaRecebida = (Nota) data.getSerializableExtra("nota");
            new NotaDao().insere(notaRecebida);
            adapter.insereNotaNova(notaRecebida);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean verificaRetornoComNota(int requestCode, int resultCode, @Nullable Intent data) {
        return verificaCodigoDeRequisicao(requestCode) && verificaCodigoResultadoNotaCriada(resultCode) && verificaIntent(data);
    }

    private boolean verificaCodigoDeRequisicao(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_ENVIA_NOTA;
    }

    private boolean verificaCodigoResultadoNotaCriada(int resultCode) {
        return resultCode == CODIGO_RESULTADO_NOTA_CRIADA;
    }

    private boolean verificaIntent(@Nullable Intent data) {
        return data.hasExtra(CHAVE_NOTA);
    }
}