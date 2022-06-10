package br.com.listadenotas.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Nota implements Serializable {

    //Qualquer entidade anotada como entidade, deve ter uma PrimaryKey, que vai ser uma chave que vai identificar os alunos, que vai deixar eles únicos
    //dentro do projeto. Para deixar o room gerenciar essa informação e não ser preciso gerenciar essa informação, delegamos essa função de gerar o id ao
    //prórpio room, colocando entre parênteses o autogenerate = true
    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private String titulo;
    private String descricao;

    public Nota(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
