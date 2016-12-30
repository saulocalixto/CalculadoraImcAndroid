package com.example.android.imc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import static com.example.android.imc.R.id.imc;
import static com.example.android.imc.R.id.pesoIdeal;

/**
 * Created by saulocalixto on 27/12/16.
 */

public class relatorio extends AppCompatActivity {

    TextView imcText;
    TextView pesoIdealUsuario;
    String pesoUsr;
    String alturaUsr;
    String sexoUsr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);


        imcText = (TextView) findViewById(imc);
        pesoIdealUsuario = (TextView) findViewById(pesoIdeal);

        if(imcText.getText().toString().equals("IMC")) {

            Intent intent = getIntent();

            Bundle bundle = intent.getExtras();

            pesoUsr = bundle.getString("peso");
            alturaUsr = bundle.getString("altura");
            sexoUsr = bundle.getString("sexo");

            getSupportActionBar().setHomeButtonEnabled(true);

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

    public void DefinirIMC() {

        if(imcText.getText().toString().equals("IMC")) {

            NumberFormat formatter = new DecimalFormat("#0.00");

            double quantAgua = calcularQuantidadedeAgua();

            double resultado = calcularImc();
            String mensagem = resultadoImc(resultado);

            imcText.setText("Seu IMC é: " + formatter.format(resultado) +
                    "\nSignificado: " + mensagem);
            if (Double.parseDouble(pesoUsr) - calcularPesoIdeal() > 0) {
                pesoIdealUsuario.setText("Seu peso ideal é: " + formatter.format(calcularPesoIdeal())
                        + " Kg" + "\nVocê deve perder: "
                        + formatter.format(Double.parseDouble(pesoUsr) - calcularPesoIdeal()) + " Kg "  +
                        "\nQuantidade de Água ideal para ser ingerida por dia: "
                        + formatter.format(quantAgua/1000)  + " L.");
            } else {
                pesoIdealUsuario.setText("Seu peso ideal é: " + formatter.format(calcularPesoIdeal())
                        + " Kg" + "\nVocê deve ganhar: "
                        + formatter.format(calcularPesoIdeal() - Double.parseDouble(pesoUsr)) + " Kg" +
                "\nQuantidade de Água ideal para ser ingerida por dia: "
                        + formatter.format(quantAgua/1000)  + " L.");
            }
        }
    }
}
