package com.example.android.imc;

import java.io.Serializable;

/**
 * Created by saulocalixto on 20/01/18.
 */

public class dadosUsuario implements Serializable {

    private int id;
    private String nome;
    private String sexo;
    private String idade;
    private double peso;
    private double altura;

    public dadosUsuario(int id, String nome, String sexo, String idade, double altura, double peso){
        this.id = id;
        this.nome = nome;
        this.sexo = sexo;
        this.idade = idade;
        this.altura = altura;
        this.peso = peso;
    }

    public int getId(){ return this.id; }
    public String getNome(){ return this.nome; }
    public String getSexo(){ return this.sexo; }
    public String getIdade(){ return this.idade; }
    public double getPeso(){ return this.peso; }
    public double getAltura(){ return this.altura; }

    @Override
    public boolean equals(Object o){
        return this.id == ((dadosUsuario)o).id;
    }

    @Override
    public int hashCode(){
        return this.id;
    }
}
