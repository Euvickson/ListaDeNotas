package br.com.listadenotas.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.listadenotas.model.Nota;

//Da mesma maneira que no ListaDeNotasDatabase, a documentação sugere que utilizemos essa classe como interface, ou como classe abstrata. Para melhor
//compreensão, abrir a documentação oficial. Só um detalhe, interfaces são para casos mais simples, como o desse projeto. E classes abstratas teria um membro
//ou um método que vai ter mais comportamentos do que são gerados pelo room.
@Dao
public interface NotaDAO {
    //Ao implementar o método "insere", o Room não sabe o que fazer come esse tipo de dado. E dado que é uma interface, os métodos não podem ter corpo. Logo, devemos anotar com o
    //@Insert que é um comportamento padrão do room, onde ele sabe que tem que inserir um dado dentro do banco de dados, assim como esse, existem outras anotações simples. Lembrando
    //que o objeto enviado por argumento deve ser uma entidade para que tudo possa funcionar corretamente
    @Insert
    void insere(Nota nota);

    //Como não existe uma anotação para devolver todos os itens do banco de dados, então vamos escrever uma query manualmente, para instruir o room sobre o que deve ser feito ao chamar
    //esse comportamento. Nesse caso, selecione tudo de nota.
    @Query("SELECT * FROM nota")
    List<Nota> todos();

    @Delete
    void remove(Nota nota);

    @Update
    void altera(Nota nota);

    @Query("DELETE FROM nota")
    void deletaTodos();

    @Insert
    void adicionaTodos(List<Nota> todasNotas);
}
