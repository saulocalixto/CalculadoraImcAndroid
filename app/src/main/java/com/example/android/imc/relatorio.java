package com.example.android.imc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.view.MenuItem;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import static com.example.android.imc.R.id.agua;
import static com.example.android.imc.R.id.idade;
import static com.example.android.imc.R.id.imc;
import static com.example.android.imc.R.id.nome;
import static com.example.android.imc.R.id.sexo;
import static com.example.android.imc.R.id.peso;
import static com.example.android.imc.R.id.altura;

/**
 * Created by saulocalixto on 27/12/16.
 */

public class relatorio extends AppCompatActivity {

    final double QUANTIDADE_DE_AGUA_POR_KG = 35;
    final double LITRO = 1000;

    private TextView imcText;
    private TextView perfilPesoUsuario;
    private TextView perfilNome;
    private TextView perfilIdade;
    private TextView perfilSexo;
    private TextView perfilAltura;
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mapeiaCamposDaView();

        if(viewJaFoiCarregada()) {
            setaValores();
            PreecherRelatorio();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, dashboard.class);
        i.putExtras(getIntent());
        startActivity(i);
        finish();
    }

    private void mapeiaCamposDaView() {
        imcText = (TextView) findViewById(imc);
        perfilPesoUsuario = (TextView) findViewById(peso);
        perfilNome = (TextView) findViewById(nome);
        perfilAltura = (TextView) findViewById(altura);
        perfilIdade = (TextView) findViewById(idade);
        perfilSexo = (TextView) findViewById(sexo);
        qtdDiariaDeAgua = (TextView) findViewById(agua);
    }

    private boolean viewJaFoiCarregada() {
        return imcText.getText().toString().equals("IMC");
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

    private void PreecherRelatorio() {
        perfilNome.setText(nomeDoUsuario);
        perfilIdade.setText(idadeDoUsuario + " anos");
        perfilSexo.setText(sexoDoUsuario);
        perfilAltura.setText(getAlturaDouble() + " m");
        imcText.setText(definirIMC());
        perfilPesoUsuario.setText(pesoDoUsuario + " KG - " + definirPesoIdeal());
        qtdDiariaDeAgua.setText(definirAgua());
    }

    private String definirIMC() {
        String IMC;

        NumberFormat formatter = new DecimalFormat("#0.00");

        IMC = "IMC: " + formatter.format(calcularImc());
        IMC += " - " + defineMensagemDeResultadoDoImc(calcularImc());

        return IMC;
    }

    private double calcularImc() {

        double imc;

        double peso = transformeStringEmDouble(pesoDoUsuario);
        double altura = getAlturaDouble();
        imc = peso / (altura * altura);

        return imc;
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

    private double transformeStringEmDouble(String valor) {
        return valor == null ? 0.0 : Double.parseDouble(valor.replaceAll(",", "."));
    }

    /**
     * Transforma a altura passada pelo usuário em double.
     * Caso o usuário tenha digitado a altura em centímeros, não em metros é feita a conversão
     * de centímeros para metros.
     * @return A altura do usuário convertida para metros e double.
     */
    private double getAlturaDouble() {
        double altura = transformeStringEmDouble(alturaDoUsuario);
        altura = altura > 100 ? altura / 100 : altura;
        return altura;
    }

    public double calcularQuantidadedeAgua() {

        double quantidadeIdealDeAgua;

        double peso = transformeStringEmDouble(pesoDoUsuario);

        quantidadeIdealDeAgua = peso * QUANTIDADE_DE_AGUA_POR_KG;

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

    public String definirPesoIdeal() {

        NumberFormat formatter = new DecimalFormat("#0.00");

        String peso = "";

        boolean pesoDoUsuarioEhMaiorQuePesoIdeal = Double.parseDouble(pesoDoUsuario) - calcularPesoIdeal() > 0;

        if(pesoDoUsuarioEhMaiorQuePesoIdeal) {
            peso += formatter.format(Double.parseDouble(pesoDoUsuario) -
                    calcularPesoIdeal()) + " KG acima do peso.";
        } else {
            peso += formatter.format(calcularPesoIdeal() -
                    Double.parseDouble(pesoDoUsuario)) + " KG abaixo do peso.";
        }

        return peso;
    }

    public String definirAgua() {

        NumberFormat formatter = new DecimalFormat("#0.00");

        return formatter.format(calcularQuantidadedeAgua() / LITRO).toString() + " LT";
    }
}
