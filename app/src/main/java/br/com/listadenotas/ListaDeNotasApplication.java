package br.com.listadenotas;

import android.app.Application;

import br.com.listadenotas.dao.NotaDao;
import br.com.listadenotas.model.Nota;

public class ListaDeNotasApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        criaNotasDeTeste();
    }

    private void criaNotasDeTeste() {
        NotaDao dao = new NotaDao();
        dao.insere(new Nota("Teste Titulo 1", "Teste descrição 1"));
        dao.insere(new Nota("Teste Titulo 2", "Teste descrição 2"));
    }
}
