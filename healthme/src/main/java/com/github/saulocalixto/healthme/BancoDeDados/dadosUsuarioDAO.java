package com.github.saulocalixto.healthme.BancoDeDados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.github.saulocalixto.healthme.Model.dadosUsuario;

import java.util.ArrayList;
import java.util.List;

public class dadosUsuarioDAO {
    private final String TABLE_DADOS_USUARIO = "DadosUsuario";
    private DbGateway gw;

    public dadosUsuarioDAO(Context ctx){
        gw = DbGateway.getInstance(ctx);
    }

    public boolean salvar(String nome, String sexo, String idade, double altura, double peso){
        return salvar(0, nome, sexo, idade, altura, peso);
    }

    public boolean salvar(int id, String nome, String sexo, String idade, double altura, double peso){
        ContentValues cv = new ContentValues();
        cv.put("Nome", nome);
        cv.put("Sexo", sexo);
        cv.put("Idade", idade);
        cv.put("Altura", altura);
        cv.put("Peso", peso);
        boolean ehUpdate = id > 0;
        if(ehUpdate) {
            return gw.getDatabase().update(TABLE_DADOS_USUARIO, cv, "ID=?", new String[]{ id + "" }) > 0;
        } else {
            return gw.getDatabase().insert(TABLE_DADOS_USUARIO, null, cv) > 0;
        }
    }

    public List<dadosUsuario> retornarTodos(){
        List<dadosUsuario> dadosUsuarios = new ArrayList<>();
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM DadosUsuario", null);
        while(cursor.moveToNext()){
            dadosUsuarios.add(montaDadosUsuario(cursor));
        }
        cursor.close();
        return dadosUsuarios;
    }

    public dadosUsuario retornarUltimo(){
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM DadosUsuario ORDER BY ID DESC", null);
        if(cursor.moveToFirst()){
            dadosUsuario dados = montaDadosUsuario(cursor);
            cursor.close();
            return dados;
        }

        return null;
    }

    private dadosUsuario montaDadosUsuario(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex("ID"));
        String nome = cursor.getString(cursor.getColumnIndex("Nome"));
        String sexo = cursor.getString(cursor.getColumnIndex("Sexo"));
        String idade = cursor.getString(cursor.getColumnIndex("Idade"));
        Double altura = cursor.getDouble(cursor.getColumnIndex("Altura"));
        Double peso = cursor.getDouble(cursor.getColumnIndex("Peso"));
        return new dadosUsuario(id, nome, sexo, idade, altura, peso);
    }

    public boolean excluir(int id){
        return gw.getDatabase().delete(TABLE_DADOS_USUARIO, "ID=?", new String[]{ id + "" }) > 0;
    }
}
