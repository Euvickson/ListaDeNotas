package br.com.listadenotas.database.converter;

import androidx.room.TypeConverter;

import java.util.Calendar;

/*Na classe, um método será responsável por pegar o valor do objeto e mandar para um valor compatível para o banco de dados, enquanto outro método fará o caminho inverso. Um cuidado
* a se tomar na hora de pegar os valores do banco de dados é tomar cuidado com notas já criadas antes da atualização do app, que não vão possuir valores de datas no banco de dados,
*  então o ideal é evitar tomar um nullPointerException fazendo uma verificação dos valores.*/
public class ConversorCalendar {

    @TypeConverter
    public Long paraLong(Calendar valor){
        return valor.getTimeInMillis();
    }

    @TypeConverter
    public Calendar paraCalendar(Long valor){
        Calendar momentoAtual = Calendar.getInstance();
        if (valor != null){
            momentoAtual.setTimeInMillis(valor);
        }
        return momentoAtual;
    }

}
