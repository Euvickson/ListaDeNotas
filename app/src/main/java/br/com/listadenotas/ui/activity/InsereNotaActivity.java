package br.com.listadenotas.ui.activity;

import static br.com.listadenotas.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.listadenotas.ui.activity.NotaActivityConstantes.CODIGO_RESULTADO_NOTA_CRIADA;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.com.listadenotas.R;
import br.com.listadenotas.model.Nota;

public class InsereNotaActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR_NOVA_NOTA = "Insere nota";
    public static final String TITULO_APPBAR_EDITA_NOTA = "Editando nota";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insere_nota);

        Intent intentRecebida = getIntent();
        if(intentRecebida.hasExtra(CHAVE_NOTA)){
            setTitle(TITULO_APPBAR_EDITA_NOTA);
            Nota notaRecebida = (Nota) intentRecebida.getSerializableExtra(CHAVE_NOTA);
            EditText titulo = findViewById(R.id.insere_nota_titulo);
            EditText descricao = findViewById(R.id.insere_nota_descricao);
            titulo.setText(notaRecebida.getTitulo());
            descricao.setText(notaRecebida.getDescricao());
        } else {
            setTitle(TITULO_APPBAR_NOVA_NOTA);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_salvar_insere_nota, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Esse método é o reponsável por identificar se algum menu foi tocado. Só é preciso identificar qual o menu foi clicado.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (verificaMenuTocado(item)) {
            Nota notaCriada = criaNota();
            retornaNotaParaResponderStartActivityForResult(notaCriada);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void retornaNotaParaResponderStartActivityForResult(Nota notaCriada) {
        Intent resultadoDaInsercao = new Intent();
        resultadoDaInsercao.putExtra(CHAVE_NOTA, notaCriada);

        //Esse é o método utilizado para responder ao startActivityForResult, enviando uma intent e o código de resultado
        setResult(CODIGO_RESULTADO_NOTA_CRIADA, resultadoDaInsercao);
    }

    private boolean verificaMenuTocado(@NonNull MenuItem item) {
        return item.getItemId() == R.id.menu_salvar_insere_nota;
    }

    @NonNull
    private Nota criaNota() {
        EditText titulo = findViewById(R.id.insere_nota_titulo);
        EditText descricao = findViewById(R.id.insere_nota_descricao);
        return new Nota(titulo.getText().toString(), descricao.getText().toString());
    }
}