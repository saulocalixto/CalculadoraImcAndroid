package com.github.saulocalixto.healthme.Model;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by saulocalixto on 20/01/18.
 */

public class pesoIdeal {
    private double pesoIdeal;
    private double altura;
    private double peso;
    private String sexo;

    public pesoIdeal(double altura, double peso, String sexo) {
        this.altura = altura;
        this.peso = peso;
        this.sexo = sexo;
    }

    public String definirPesoIdeal() {

        NumberFormat formatter = new DecimalFormat("#0.00");

        String pesoIdeal = "";

        boolean pesoDoUsuarioEhMaiorQuePesoIdeal = peso - calcularPesoIdeal() > 0;

        if(pesoDoUsuarioEhMaiorQuePesoIdeal) {
            pesoIdeal += formatter.format(peso -
                    calcularPesoIdeal()) + " KG acima do peso.";
        } else {
            pesoIdeal += formatter.format(calcularPesoIdeal() -
                    peso) + " KG abaixo do peso.";
        }

        return pesoIdeal;
    }

    private double calcularPesoIdeal() {
        switch(sexo) {
            case "Homem":
                pesoIdeal = (72.7 * altura) - 58;
                break;
            case "Mulher":
                pesoIdeal = (62.1 * altura) - 44.7;
                break;
            default:
                pesoIdeal = 0.0;
        }

        return pesoIdeal;
    }
}
