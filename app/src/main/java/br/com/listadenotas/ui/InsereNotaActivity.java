package br.com.listadenotas.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.com.listadenotas.DAO.NotaDao;
import br.com.listadenotas.R;
import br.com.listadenotas.model.Nota;

public class InsereNotaActivity extends AppCompatActivity {

    private EditText titulo;
    private EditText descricao;
    private NotaDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insere_nota);

        setTitle("Insere nota");
        dao = new NotaDao();

        inicializaCampos();
    }

    private void inicializaCampos() {
        titulo = findViewById(R.id.insere_nota_titulo);
        descricao = findViewById(R.id.insere_nota_descricao);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_salvar_insere_nota, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Esse método é o reponsável por identificar se algum menu foi tocado. Só é preciso identificar qual o menu foi clicado.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_salvar_insere_nota){
            Nota notaCriada = new Nota(titulo.getText().toString(), descricao.getText().toString());
            dao.insere(notaCriada);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}