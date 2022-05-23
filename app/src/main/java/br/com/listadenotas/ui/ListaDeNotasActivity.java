package br.com.listadenotas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
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

        TextView botaoInsereNota = findViewById(R.id.lista_notas_insere_nota);
        botaoInsereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vaiParaInsereNotaActivity = new Intent(ListaDeNotasActivity.this, InsereNotaActivity.class);
                startActivity(vaiParaInsereNotaActivity);
            }
        });

        NotaDao dao = new NotaDao();
        List<Nota> todasNotas = dao.todos();

        adapter = new AdapterRecyclerview(todasNotas, this);

        RecyclerView listaDeNotas = findViewById(R.id.lista_notas_recyclerView);
        listaDeNotas.setAdapter(adapter);
    }
}