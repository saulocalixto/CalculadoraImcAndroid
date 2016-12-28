package com.example.android.imc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import static com.example.android.imc.R.id.imc;
import static com.example.android.imc.R.id.pesoIdeal;

/**
 * Created by saulocalixto on 27/12/16.
 */

public class relatorio extends Activity {

    TextView imcText;
    TextView pesoIdealUsuario;
    TextView mensagemUsr;
    String pesoUsr;
    String alturaUsr;
    String sexoUsr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.relatorio);


        imcText = (TextView) findViewById(imc);
        pesoIdealUsuario = (TextView) findViewById(pesoIdeal);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        pesoUsr = bundle.getString("peso");
        alturaUsr = bundle.getString("altura");
        sexoUsr = bundle.getString("sexo");

        DefinirIMC();

    }

    public double calcularImc() {

        double resultado;

        double peso = Double.parseDouble(pesoUsr.replaceAll(",", "."));
        double altura = Double.parseDouble(alturaUsr.replaceAll(",", "."));
        if(altura != 0 || peso != 0) {
            resultado = peso / (altura * altura);
        } else {
            resultado = 0.0;
        }
        return resultado;
    }

    public double calcularPesoIdeal() {
        double pesoIdeal = 0.0;
        if(sexoUsr.equals("Homem")) {
            pesoIdeal = (72.7 * Double.parseDouble(alturaUsr)) - 58;
        } else if(sexoUsr.equals("Mulher")) {
            pesoIdeal = (62.1 * Double.parseDouble(alturaUsr)) - 44.7;
        }

        return pesoIdeal;
    }

    public String resultadoImc(double resultado) {

        String mensagem = "\nInsira os dados corretamente.";

        if(resultado < 18.5) {
            mensagem = "\nAbaixo do peso ideal.";
        } else if(resultado <24.9) {
            mensagem = "\nSeu IMC está na média ideal, parabéns!";
        } else if (resultado < 29.9) {
            mensagem = "\nLevemente acima do peso";
        } else if(resultado < 34.9) {
            mensagem = "\nObesidade grau 1";
        } else if(resultado < 39.9) {
            mensagem = "\nObesidade grau 2, severa!";
        } else if(resultado > 40) {
            mensagem = "\nObesidade grau 3, mórbida!";
        }

        return mensagem;
    }

    public void DefinirIMC() {

        NumberFormat formatter = new DecimalFormat("#0.00");

        double resultado = calcularImc();
        String mensagem = resultadoImc(resultado);

        imcText.setText("Seu IMC é: " + formatter.format(resultado) +
                "\nVocê está: " + mensagem);
        if(Double.parseDouble(pesoUsr) - calcularPesoIdeal() > 0) {
            pesoIdealUsuario.setText("Seu peso ideal é: " + formatter.format(calcularPesoIdeal())
                    + " Kg" + "\nVocê deve perder: "
                    + formatter.format(Double.parseDouble(pesoUsr) - calcularPesoIdeal()) + " Kg");
        } else {
            pesoIdealUsuario.setText("Seu peso ideal é: " + formatter.format(calcularPesoIdeal())
                    + " Kg" + "\nVocê deve ganhar: "
                    + formatter.format(calcularPesoIdeal() - Double.parseDouble(pesoUsr)) + " Kg");
        }
    }

    public void voltar (View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
