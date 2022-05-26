package br.com.listadenotas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

        for(int i = 0; i<= 1000; i++){
            Nota nota = new Nota("Título " + i, "Descrição " + i);
            dao.insere(nota);
        }

        List<Nota> todasNotas = dao.todos();

        configuraRecyclerView(todasNotas);

        configuraBotaoInsereNovaNota();
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        adapter = new AdapterRecyclerview(todasNotas, this);
        RecyclerView listaDeNotas = findViewById(R.id.lista_notas_recyclerView);
        listaDeNotas.setAdapter(adapter);

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
                startActivity(vaiParaInsereNotaActivity);
            }
        });
    }
}