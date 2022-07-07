package br.com.listadenotas.database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

class ListaDeNotasMigrations {
    static final Migration[] TODAS_MIGRATIONS = {new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Nota ADD COLUMN dataCriacao INTEGER");
            database.execSQL("ALTER TABLE Nota ADD COLUMN dataEdicao INTEGER");
          }
        }
    };
}
