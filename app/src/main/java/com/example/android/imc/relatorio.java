package com.example.android.imc;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import static com.example.android.imc.R.id.agua;
import static com.example.android.imc.R.id.imc;
import static com.example.android.imc.R.id.perfil;
import static com.example.android.imc.R.id.pesoIdeal;

/**
 * Created by saulocalixto on 27/12/16.
 */

public class relatorio extends AppCompatActivity {

    TextView imcText;
    TextView pesoIdealUsuario;
    TextView perfilUsuario;
    TextView aguaUsr;
    String pesoUsr;
    String alturaUsr;
    String sexoUsr;
    String nomeUsr;
    String idadeUsr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);


        imcText = (TextView) findViewById(imc);
        pesoIdealUsuario = (TextView) findViewById(pesoIdeal);
        perfilUsuario = (TextView) findViewById(perfil);
        aguaUsr = (TextView) findViewById(agua);


        if(imcText.getText().toString().equals("IMC")) {

            Intent intent = getIntent();

            Bundle bundle = intent.getExtras();

            pesoUsr = bundle.getString("peso");
            alturaUsr = bundle.getString("altura");
            sexoUsr = bundle.getString("sexo");
            nomeUsr = bundle.getString("nome");
            idadeUsr = bundle.getString("idade");

            DefinirIMC();
        }

    }

    public double calcularImc() {

        double resultado;

        double peso = Double.parseDouble(pesoUsr.replaceAll(",", "."));
        double altura = Double.parseDouble(alturaUsr.replaceAll(",", "."));

        resultado = peso / (altura * altura);

        return resultado;
    }
    public double calcularQuantidadedeAgua() {

        double resultado;

        double peso = Double.parseDouble(pesoUsr.replaceAll(",", "."));
        final double constanteAgua = 35;

        resultado = peso * constanteAgua;

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
            mensagem = "Abaixo do peso ideal.";
        } else if(resultado <24.9) {
            mensagem = "Seu IMC está na média ideal, parabéns!";
        } else if (resultado < 29.9) {
            mensagem = "Levemente acima do peso";
        } else if(resultado < 34.9) {
            mensagem = "Obesidade grau 1";
        } else if(resultado < 39.9) {
            mensagem = "Obesidade grau 2, severa!";
        } else if(resultado > 40) {
            mensagem = "Obesidade grau 3, mórbida!";
        }

        return mensagem;
    }

    public String definirPerfil() {
        String perfil;

        perfil = "Nome: " + nomeUsr.toString();
        perfil += "\nIdade: " + idadeUsr.toString();
        perfil += "\nSexo: " + sexoUsr;
        perfil += "\nAltura: " + alturaUsr.toString();
        perfil += "\nPeso: " + pesoUsr.toString();

        return perfil;
    }

    public String definirIMC() {
        String IMC;

        NumberFormat formatter = new DecimalFormat("#0.00");

        IMC = "IMC: " + formatter.format(calcularImc());
        IMC += "\n" + resultadoImc(calcularImc());

        return IMC;
    }

    public String definirPesoIdeal() {

        NumberFormat formatter = new DecimalFormat("#0.00");

        String peso;

        peso = "Peso ideal: " + formatter.format(calcularPesoIdeal()) + " KG";

        if(Double.parseDouble(pesoUsr) - calcularPesoIdeal() > 0) {
            peso += "\nVocê deve perder " + formatter.format(Double.parseDouble(pesoUsr) -
                    calcularPesoIdeal()) + " KG";
        } else {
            peso += "\nVocê deve ganhar " + formatter.format(calcularPesoIdeal() -
                    Double.parseDouble(pesoUsr)) + " KG";
        }

        return peso;

    }

    public String definirAgua() {

        NumberFormat formatter = new DecimalFormat("#0.00");

        String agua;

        agua = "Levando em conta seu peso e sua altura, você deve ingerir:";
        agua += "\n" + formatter.format(calcularQuantidadedeAgua() / 1000).toString()
                + " Litros de Água diária.";

        return agua;

    }


    public void DefinirIMC() {

        if(imcText.getText().toString().equals("IMC")) {

            perfilUsuario.setText(definirPerfil());
            imcText.setText(definirIMC());
            pesoIdealUsuario.setText(definirPesoIdeal());
            aguaUsr.setText(definirAgua());
        }
    }
}
