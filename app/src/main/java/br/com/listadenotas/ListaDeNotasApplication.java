package br.com.listadenotas;

import android.app.Application;

import androidx.room.Room;

import br.com.listadenotas.dao.NotaDao;
import br.com.listadenotas.database.ListaDeNotasDatabase;
import br.com.listadenotas.database.dao.RoomNotaDAO;
import br.com.listadenotas.model.Nota;

public class ListaDeNotasApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        criaNotasDeTeste();
    }

    private void criaNotasDeTeste() {
        //A maneira de funcionar o aplicativo com o room é: 1: Buscar o database, 2: fazer a instância do DAO a partir do database, 3: Daí terá acesso aos comportamentos do db.

        //Para fazer a inserção de uma lista de notas, é necessário uma instância do DAO, mas como ele é uma classe abstrata, não é possível fazer isso. O room por si mesmo já possui
        //uma maneira de fazer isso, através do Room.databaseBuilder, que vai devolver a instância necessária de database.

        //Por padrão o Room não permite que sejam realizadas operações com o banco de dados na thread principal. A intenção é criar uma thread separada para conseguir seguir a
        //atitude recomendada. Mas para objetivo de teste, como ele é um builder, podemos usar a chamada encadeada .allowMainThreadQueries() para forçar essa atitude

        //Esse nome é o nome do arquivo que vai ser gerado e que vai manter os dados do banco de dados.
        ListaDeNotasDatabase database = Room.databaseBuilder(this, ListaDeNotasDatabase.class, "ListaDeNotas.db").allowMainThreadQueries().build();

        RoomNotaDAO dao = database.getNotaDao();

        dao.insere(new Nota("Teste Titulo 1", "Teste descrição 1"));
        dao.insere(new Nota("Teste Titulo 2", "Teste descrição 2"));
    }
}
