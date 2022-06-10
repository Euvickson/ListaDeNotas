package br.com.listadenotas.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.listadenotas.model.Nota;

public class NotaDao {

    private final static ArrayList<Nota> notas = new ArrayList<>();

    public List<Nota> todos() {
        return (List<Nota>) notas.clone();
    }

    public void insere(Nota nota) {
        notas.add(nota);
    }

    public void altera(int posicao, Nota nota){
        notas.set(posicao, nota);
    }

    public void remove(int posicao) {
        notas.remove(posicao);
    }

    public void troca(int posicaoInicial, int posicaoFinal) {
        Collections.swap(notas, posicaoInicial, posicaoFinal);
    }
}
