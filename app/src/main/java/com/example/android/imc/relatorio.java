package com.example.android.imc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    private TextView imcText;
    private TextView pesoIdealUsuario;
    private TextView perfilUsuario;
    private TextView qtdDiariaDeAgua;
    private String pesoDoUsuario;
    private String alturaDoUsuario;
    private String sexoDoUsuario;
    private String nomeDoUsuario;
    private String idadeDoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);

        mapeiaCamposDaView();

        if(viewJaFoiCarregada()) {
            setaValores();
            PreecherRelatorio();
        }

    }

    private void setaValores() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        pesoDoUsuario = bundle.getString("peso");
        alturaDoUsuario = bundle.getString("altura");
        sexoDoUsuario = bundle.getString("sexo");
        nomeDoUsuario = bundle.getString("nome");
        idadeDoUsuario = bundle.getString("idade");
    }

    private boolean viewJaFoiCarregada() {
        return imcText.getText().toString().equals("IMC");
    }

    private void mapeiaCamposDaView() {
        imcText = (TextView) findViewById(imc);
        pesoIdealUsuario = (TextView) findViewById(pesoIdeal);
        perfilUsuario = (TextView) findViewById(perfil);
        qtdDiariaDeAgua = (TextView) findViewById(agua);
    }

    public double calcularImc() {

        double imc;

        double peso = valorSemCaracteresNaoNumericos(pesoDoUsuario);
        double altura = getAlturaDouble();
        imc = peso / (altura * altura);

        return imc;
    }

    private double getAlturaDouble() {
        double altura = valorSemCaracteresNaoNumericos(alturaDoUsuario);
        altura = altura > 100 ? altura / 100 : altura;
        return altura;
    }

    private double valorSemCaracteresNaoNumericos(String valor) {
        return Double.parseDouble(valor.replaceAll(",", "."));
    }

    public double calcularQuantidadedeAgua() {

        double quantidadeIdealDeAgua;

        double peso = valorSemCaracteresNaoNumericos(pesoDoUsuario);
        final double CONSTANTEAGUA = 35;

        quantidadeIdealDeAgua = peso * CONSTANTEAGUA;

        return quantidadeIdealDeAgua;
    }

    public double calcularPesoIdeal() {
        double pesoIdeal = 0.0;
        if(sexoDoUsuario.equals("Homem")) {
            pesoIdeal = (72.7 * getAlturaDouble()) - 58;
        } else if(sexoDoUsuario.equals("Mulher")) {
            pesoIdeal = (62.1 * getAlturaDouble()) - 44.7;
        }

        return pesoIdeal;
    }

    public String resultadoImc(double resultado) {

        return defineMensagemDeResultadoDoImc(resultado);
    }

    private String defineMensagemDeResultadoDoImc(double resultado) {

        String mensagem = "\nInsira os dados corretamente.";

        if(resultado < 18.5) {
            mensagem = "Abaixo do peso ideal.";
        } else if(resultado < 24.9) {
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

        perfil = "Nome: " + nomeDoUsuario.toString();
        perfil += "\nIdade: " + idadeDoUsuario.toString();
        perfil += "\nSexo: " + sexoDoUsuario;
        perfil += "\nAltura: " + alturaDoUsuario.toString();
        perfil += "\nPeso: " + pesoDoUsuario.toString();

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

        if(Double.parseDouble(pesoDoUsuario) - calcularPesoIdeal() > 0) {
            peso += "\nVocê deve perder " + formatter.format(Double.parseDouble(pesoDoUsuario) -
                    calcularPesoIdeal()) + " KG";
        } else {
            peso += "\nVocê deve ganhar " + formatter.format(calcularPesoIdeal() -
                    Double.parseDouble(pesoDoUsuario)) + " KG";
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

    public void PreecherRelatorio() {

        perfilUsuario.setText(definirPerfil());
        imcText.setText(definirIMC());
        pesoIdealUsuario.setText(definirPesoIdeal());
        qtdDiariaDeAgua.setText(definirAgua());
    }
}
