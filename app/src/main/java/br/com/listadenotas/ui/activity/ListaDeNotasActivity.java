package br.com.listadenotas.ui.activity;

import static br.com.listadenotas.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.listadenotas.ui.activity.NotaActivityConstantes.CHAVE_POSICAO;
import static br.com.listadenotas.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_EDITA_NOTA;
import static br.com.listadenotas.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_ENVIA_NOTA;
import static br.com.listadenotas.ui.activity.NotaActivityConstantes.POSICAO_INVALIDA;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

import br.com.listadenotas.dao.NotaDao;
import br.com.listadenotas.R;
import br.com.listadenotas.database.ListaDeNotasDatabase;
import br.com.listadenotas.database.dao.RoomNotaDAO;
import br.com.listadenotas.model.Nota;
import br.com.listadenotas.ui.recyclerview.adapter.AdapterRecyclerview;
import br.com.listadenotas.ui.recyclerview.helper.callback.NotaItemTouchHelperCallback;

public class ListaDeNotasActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Notas";
    private AdapterRecyclerview adapter;
    private RoomNotaDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_notas);

        setTitle(TITULO_APPBAR);

        /*A maneira de funcionar o aplicativo com o room é: 1: Buscar o database, 2: fazer a instância do DAO a partir do database, 3: Daí terá acesso aos comportamentos do db.

        Para fazer a inserção de uma lista de notas, é necessário uma instância do DAO, mas como ele é uma classe abstrata, não é possível fazer isso. O room por si mesmo já possui
        uma maneira de fazer isso, através do Room.databaseBuilder, que vai devolver a instância necessária de database.

        Por padrão o Room não permite que sejam realizadas operações com o banco de dados na thread principal. A intenção é criar uma thread separada para conseguir seguir a
        atitude recomendada. Mas para objetivo de teste, como ele é um builder, podemos usar a chamada encadeada .allowMainThreadQueries() para forçar essa atitude

        Esse nome é o nome do arquivo que vai ser gerado e que vai manter os dados do banco de dados.*/
        dao = Room.databaseBuilder(this, ListaDeNotasDatabase.class, "ListaDeNotas.db").allowMainThreadQueries().build().getNotaDao();

        List<Nota> todasNotas = dao.todos();
        configuraRecyclerView(todasNotas);
        configuraBotaoInsereNovaNota();
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        RecyclerView listaDeNotas = findViewById(R.id.lista_notas_recyclerView);
        configuraAdapter(todasNotas, listaDeNotas);
        configuraLayoutManager(listaDeNotas);

        configuraItemTouchHelper(listaDeNotas);
    }

    private void configuraItemTouchHelper(RecyclerView listaDeNotas) {
        //Essa classe é específica do RecyclerView pra fazer essas configurações de animações. Ela dá um erro de compilação no construtor porque ela exige
        //que exista uma implementação de uma entidade chamada de Callback, que é responsável de fazer a configuração de deslize ou movimento.
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new NotaItemTouchHelperCallback(adapter, dao));

        //Para anexar os comportamentos animados no recyclerView, usamos a referência do objeto itemTouchHelper e o método estático attachToRecyclerView
        itemTouchHelper.attachToRecyclerView(listaDeNotas);
    }

    private void configuraLayoutManager(RecyclerView listaDeNotas) {
        //Com o LinearLayoutManager podemos setar a orientação da lista, se quisermos que ela seja horizontal só deveremos mandar, além do contexto, a constante e a direção da lista
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listaDeNotas.setLayoutManager(linearLayoutManager);
    }

    private void configuraBotaoInsereNovaNota() {
        TextView botaoInsereNota = findViewById(R.id.lista_notas_nova_nota);
        botaoInsereNota.setOnClickListener(view -> inicializaInsereNotaActivityInsereNovaNota());
    }

    private void inicializaInsereNotaActivityInsereNovaNota() {
        Intent vaiParaInsereNotaActivity = new Intent(ListaDeNotasActivity.this, InsereNotaActivity.class);
        //Essa é uma maneira de enviar um intent esperando um resultado. Esse método foi descontinuado, é necessário atualizar
        startActivityForResult(vaiParaInsereNotaActivity, CODIGO_REQUISICAO_ENVIA_NOTA);
    }

    private void configuraAdapter(List<Nota> todasNotas, RecyclerView listaDeNotas) {
        adapter = new AdapterRecyclerview(todasNotas, this);
        //Se tentarmos fazer a listaDeNotas.setOnItemClickListener, iremos ver que essa função não existe, apenas há a implementação padrão que toda view tem, com o setOnClickListener
        //Logo teremos que implementar por nós mesmos como a lista se comportará ao receber o clique em um de seus itens. Se pensarmos no RecyclerView, temos os viewHolders que
        //Representam as views dentro da lista, mas a melhor maneira seria uma interface.
        listaDeNotas.setAdapter(adapter);
        configuraToqueEmView();
    }

    private void configuraToqueEmView() {
        adapter.setOnItemClickListener(this::inicializaInsereNotaActivityComEdicao);
    }

    private void inicializaInsereNotaActivityComEdicao(Nota nota, int posicao) {
        Intent inicializaEdicaoDeNota = new Intent(ListaDeNotasActivity.this, InsereNotaActivity.class);
        inicializaEdicaoDeNota.putExtra(CHAVE_NOTA, nota);
        inicializaEdicaoDeNota.putExtra(CHAVE_POSICAO, posicao);
        startActivityForResult(inicializaEdicaoDeNota, CODIGO_REQUISICAO_EDITA_NOTA);
    }

    //A onActivityResult é onde trabalhamos os objetos recebidos a partir do startActivityForResult. Nele temos a capacidade de checar vários objetos, a partir do código de requisição,
    //do código de resultado e da chave string que recebemos.

    //Anteriormente estávamos utilizando uma constante criada "int CODIGO_RESULTADO_NOTA_CRIADA = 2;", mas o que é necessário saber é se o item foi criado
    //portanto, vamos utilizar ocódigo de resultado padrão do próprio android, assim outro desenvolvedor não precisa saber qual o nosso código criado.
    //Quando se utiliza a constante Activity.RESULT_OK o recomendado, pela própria android, é que seja verificado por último se o código deu certo depois de
    //Verificar o outros itens. Isso se dá porque quando verificamos se há o objeto e se o código de requisição deu certo, estamos prontos pra tomar uma
    //ação, mas as vezes essa ação não vai ser sempre um RESULT_OK, pode ser que seja um RESULT_CANCELED e dessa maneira estaríamos prontos pra mandar uma
    //mensagem ou tomar alguma atitude.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (verificaRetornoComNotaNova(requestCode, data)) {

            if (verificaCodigoResultado(resultCode)) {
                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                insereNotaNova(notaRecebida);
            }

        } else if (verificaRetornoDeNotaEditada(requestCode, data)) {
            if (verificaCodigoResultado(resultCode)) {
                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                int posicaoRecebida = data.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
                //Caso haja um bug e a posição recebida seja inválida dentro dessa classe, ele manda uma mensagem através de um toast
                if (verificaPosicaoValida(posicaoRecebida)) {
                    editaNota(notaRecebida, posicaoRecebida);
                } else {
                    Toast.makeText(this, "Ocorreu um problema na alteração da nota", Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void insereNotaNova(Nota nota) {
        dao.insere(nota);
        //aqui vamos notificar o adapter da alteração, para isso é necessário criar o método para inserir a nota na lista interna do adapter e ao mesmo tempo notificar a mudança
        adapter.insereNotaNova(nota);
    }

    private void editaNota(Nota nota, int posicao) {
        dao.altera(nota);
        adapter.altera(posicao, nota);
    }

    private boolean verificaPosicaoValida(int posicaoRecebida) {
        return posicaoRecebida > POSICAO_INVALIDA;
    }

    private boolean verificaRetornoDeNotaEditada(int requestCode, @Nullable Intent data) {
        return vertificaCodigoRequisicaoNotaEditada(requestCode) && verificaIntent(data);
    }

    private boolean vertificaCodigoRequisicaoNotaEditada(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_EDITA_NOTA;
    }

    private boolean verificaRetornoComNotaNova(int requestCode, @Nullable Intent data) {
        return verificaCodigoDeRequisicao(requestCode) && verificaIntent(data);
    }

    private boolean verificaCodigoDeRequisicao(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_ENVIA_NOTA;
    }

    private boolean verificaCodigoResultado(int resultCode) {
        return resultCode == Activity.RESULT_OK;
    }

    //Ao trabalhar com intents, devemos ter o cuidado para não tomar nullPointerException, porque quando buscamos a data.hasExtra, podemos receber null
    private boolean verificaIntent(@Nullable Intent data) {
        return data != null && data.hasExtra(CHAVE_NOTA);
    }
}