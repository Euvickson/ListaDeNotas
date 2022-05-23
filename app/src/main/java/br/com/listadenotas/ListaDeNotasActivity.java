package br.com.listadenotas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import br.com.listadenotas.DAO.NotaDao;
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
        dao.todos()

        adapter = new AdapterRecyclerview();

        RecyclerView recyclerView = findViewById(R.id.lista_notas_recyclerView);
        recyclerView.setAdapter(adapter);
    }
}