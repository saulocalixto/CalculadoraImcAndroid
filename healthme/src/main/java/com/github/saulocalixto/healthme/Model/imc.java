package com.github.saulocalixto.healthme.Model;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by saulocalixto on 20/01/18.
 */

public class imc {
    private double imc;
    private double altura;
    private double peso;

    public imc(double peso, double altura) {
        this.altura = altura;
        this.peso = peso;
    }

    public String getValorImc() {
        String imc;

        NumberFormat formatter = new DecimalFormat("#0.00");

        imc = formatter.format(calcularImc());
        imc += " - " + defineMensagemDeResultadoDoImc(calcularImc());

        return imc;
    }

    private double calcularImc() {

        imc = peso / (altura * altura);

        return imc;
    }

    private String defineMensagemDeResultadoDoImc(double resultado) {

        String mensagem = "\nInsira os dados corretamente.";

        if(resultado < 18.5) {
            mensagem = "Abaixo do peso ideal";
        } else if(resultado < 24.9) {
            mensagem = "Seu IMC está na média ideal!";
        } else if (resultado < 29.9) {
            mensagem = "Levemente acima do peso";
        } else if(resultado < 34.9) {
            mensagem = "Obesidade grau 1!";
        } else if(resultado < 39.9) {
            mensagem = "Obesidade grau 2, severa!";
        } else if(resultado > 40) {
            mensagem = "Obesidade grau 3, mórbida!";
        }

        return mensagem;
    }
}
