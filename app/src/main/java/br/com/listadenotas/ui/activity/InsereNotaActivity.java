package br.com.listadenotas.ui.activity;

import static br.com.listadenotas.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.listadenotas.ui.activity.NotaActivityConstantes.CHAVE_POSICAO;
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
    private int posicaoRecebida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insere_nota);

        Intent intentRecebida = getIntent();
        if(intentRecebida.hasExtra(CHAVE_NOTA) && intentRecebida.hasExtra(CHAVE_POSICAO)){
            setTitle(TITULO_APPBAR_EDITA_NOTA);
            //Quando pegamos um int a partir desse método específico, precisamos mandar um valor padrão, caso o valor que esperamos não venha. Como "-1" não é um valor de posição
            //válido dentro de uma lista, não temos o perigo de alterar alguma nota que não seja a que queremos.

            //Se deixarmos a posição recebida apenas como variável local, não conseguiríamos, a priori, retorná-la junto a nota no método retornaNotaParaOnActivityResult. Por isso
            //Iremos tornar a posição um atributo de classe.
            posicaoRecebida = intentRecebida.getIntExtra(CHAVE_POSICAO, -1);

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
            retornaNotaParaOnActivityResult(notaCriada);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void retornaNotaParaOnActivityResult(Nota notaCriada) {
        Intent resultadoDaInsercao = new Intent();
        resultadoDaInsercao.putExtra(CHAVE_NOTA, notaCriada);
        resultadoDaInsercao.putExtra(CHAVE_POSICAO, posicaoRecebida);

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