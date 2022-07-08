package br.com.listadenotas.database;

import static br.com.listadenotas.database.ListaDeNotasMigrations.TODAS_MIGRATIONS;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import br.com.listadenotas.database.converter.ConversorCalendar;
import br.com.listadenotas.database.dao.NotaDAO;
import br.com.listadenotas.model.Nota;
/*Antes de iniciar qualquer configuração do Room dentro de um projeto, é necessário ler a documentação oficial, para ter noções dos conceitos básicos.

Quando colocamos a anotação Database ele alarma de cara obrigando a definir as entidades e as versões. Sempre ao tentar implementar algum código referente ao room, a boa prática
é fazer o build do projeto, ao invés de tentar rodar o aplicativo de cara, para ver quais problemas estão tendo na aplicação.

Ao implementar as entidades, deveremos mandar as entidades que desejamos trabalhar no banco de dados, no nosso caso, é a nota. Para isso, a classe Nota deve receber a notação
@Entity, e representam tabelas no banco de dados do app, a versão do nosso projeto, toda versão começa com a primeira. O terceiro argumento é o exportSchema, ao colocarmos falso
ele não criará um arquivo JSON mostrando como o banco de dados foi gerado.

A documentação oficial indica usarmos classes abstratas para não termos que implementar os métodos nem lidar com linguagem de baixo nível do SQL. Então vamos utilizar classes DAO,
que possuem os métodos necessários para trabalhar com os bancos de dados, assim como o DAO do projeto hoje

Para adicionar colunas que não utilizam tipos primitivos, devemos adicionar conversores a partir da notação a seguir, e criar uma classe para que o room possa lidar com os tipos
não primitivos*/

@Database(entities = {Nota.class}, version = 2, exportSchema = false)
@TypeConverters({ConversorCalendar.class})
public abstract class ListaDeNotasDatabase extends RoomDatabase {

    private static final String NOME_BANCO_DE_DADOS = "ListaDeNotas.db";

    public abstract NotaDAO getNotaDao();


    /*Ao modificar qualquer entidade utilizada no banco de dados, nesse caso para adicionar colunas, devemos mostrar ao banco de dados que estamos fazendo modificações de versão
     * do bd. Para isso, nós adicionamos o ".addMigrations", indicando de qual versão para qual versão estamos mudando e as instuções necessárias para a mudança do banco de dados.
     * Para adicionar colunas o processo é simples, apenas alterar a tabela para receber uma nova coluna. Caso seja desejável retirar uma coluna, o processo é complexo*/
    public static ListaDeNotasDatabase getInstance(Context context) {
        return Room
                .databaseBuilder(context, ListaDeNotasDatabase.class, NOME_BANCO_DE_DADOS)
                .addMigrations(TODAS_MIGRATIONS)
                .build();
    }
}
