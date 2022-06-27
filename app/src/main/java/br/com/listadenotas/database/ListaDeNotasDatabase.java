package br.com.listadenotas.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.com.listadenotas.database.dao.RoomNotaDAO;
import br.com.listadenotas.model.Nota;

//Antes de iniciar qualquer configuração do Room dentro de um projeto, é necessário ler a documentação oficial, para ter noções dos conceitos básicos.

//Quando colocamos a anotação Database ele alarma de cara obrigando a definir as entidades e as versões. Sempre ao tentar implementar algum código referente
//ao room, a boa prática é fazer o build do projeto, ao invés de tentar rodar o aplicativo de cara, para ver quais problemas estão tendo na aplicação.

//Ao implementar as entidades, deveremos mandar as entidades que desejamos trabalhar no banco de dados, no nosso caso, é a nota. Para isso, a classe Nota
//deve receber a notação @Entity, e representam tabelas no banco de dados do app, a versão do nosso projeto, toda versão começa com a primeira. O terceiro
//argumento é o exportSchema, ao colocarmos falso ele não criará um arquivo JSON mostrando como o banco de dados foi gerado.

//A documentação oficial indica usarmos classes abstratas para não termos que implementar os métodos nem lidar com linguagem de baixo nível do SQL. Então
//vamos utilizar classes DAO, que possuem os métodos necessários para trabalhar com os bancos de dados, assim como o DAO do projeto hoje
@Database(entities = {Nota.class}, version = 1, exportSchema = false)
public abstract class ListaDeNotasDatabase extends RoomDatabase {

    private static final String NOME_BANCO_DE_DADOS = "ListaDeNotas.db";

    public abstract RoomNotaDAO getNotaDao();

    public static ListaDeNotasDatabase getInstance(Context context){
        return Room.databaseBuilder(context, ListaDeNotasDatabase.class, NOME_BANCO_DE_DADOS).allowMainThreadQueries().build();
    }
}
