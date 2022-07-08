package br.com.listadenotas.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity
public class Nota implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String titulo;

    private String descricao;
    private Calendar dataCriacao = Calendar.getInstance();
    private Calendar dataEdicao = Calendar.getInstance();

    /*Qualquer entidade anotada como entidade, deve ter uma PrimaryKey, que vai ser uma chave que vai identificar os alunos, que vai deixar eles únicos
      dentro do projeto. Para deixar o room gerenciar essa informação e não ser preciso gerenciar essa informação, delegamos essa função de gerar o id ao
      prórpio room, colocando entre parênteses o autogenerate = true */

    public Nota(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Calendar getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Calendar dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Calendar getDataEdicao() {
        return dataEdicao;
    }

    public void setDataEdicao(Calendar dataEdicao) {
        this.dataEdicao = dataEdicao;
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

    public void editaInformacoes(String titulo, String descricao){
        verificaSeHouveMudanca(titulo, descricao);
        this.titulo = titulo;
        this.descricao = descricao;
    }

    private void verificaSeHouveMudanca(String titulo, String descricao) {
        if(!titulo.equals(this.titulo) || !descricao.equals(this.descricao)){
            setDataEdicao(Calendar.getInstance());
        }
    }

    public String datasFormatadas(){
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        String dataCriacaoFormatada = formatador.format(dataCriacao.getTime());
        String dataEdicaoFormatada = formatador.format(dataEdicao.getTime());
        return "Criada em " + dataCriacaoFormatada + " - Última edição em " + dataEdicaoFormatada;
    }

    public void trocaId(Nota notaFinal) {
        int auxiliar = this.id;
        this.id = notaFinal.getId();
        notaFinal.setId(auxiliar);
    }
}
