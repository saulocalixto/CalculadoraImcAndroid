package com.example.android.imc;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by saulocalixto on 20/01/18.
 */

public class quantidadeIdealDeAgua {

    final double QUANTIDADE_DE_AGUA_POR_KG = 35;
    final double LITRO = 1000;
    private double peso;

    public quantidadeIdealDeAgua(double peso) {
        this.peso = peso;
    }

    public String getQuantidadeIdealDeAgua() {
        NumberFormat formatter = new DecimalFormat("#0.00");

        return formatter.format(calcularQuantidadeIdealDeAgua() / LITRO).toString() + " LT";
    }

    private double calcularQuantidadeIdealDeAgua() {

        double quantidadeIdealDeAgua;

        quantidadeIdealDeAgua = peso * QUANTIDADE_DE_AGUA_POR_KG;

        return quantidadeIdealDeAgua;
    }
}
