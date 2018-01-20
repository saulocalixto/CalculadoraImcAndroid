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

public class relatorio extends AppCompatActivity {

    private imc imcObj;
    private pesoIdeal pesoIdeal;
    private quantidadeIdealDeAgua qtdIdealAgua;
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
        imcText.setText(getImcObj().getValorImc());
        perfilPesoUsuario.setText(pesoDoUsuario + " KG - " + getPesoIdeal().definirPesoIdeal());
        qtdDiariaDeAgua.setText(getqtdIdealAgua().getQuantidadeIdealDeAgua());
    }

    private imc getImcObj() {
        return imcObj == null ?
                new imc(transformeStringEmDouble(pesoDoUsuario), getAlturaDouble()) :
                imcObj;
    }

    private pesoIdeal getPesoIdeal() {
        return pesoIdeal == null ?
                new pesoIdeal(getAlturaDouble(),
                        transformeStringEmDouble(pesoDoUsuario),
                        sexoDoUsuario) :
                pesoIdeal;
    }

    private quantidadeIdealDeAgua getqtdIdealAgua() {
        return qtdIdealAgua == null ?
                new quantidadeIdealDeAgua(transformeStringEmDouble(pesoDoUsuario)) :
                qtdIdealAgua;
    }

    private double transformeStringEmDouble(String valor) {
        return valor == null ? 0.0 : Double.parseDouble(valor.replaceAll(",", "."));
    }

    /**
     * Transforma a altura passada pelo usuário em double.
     * Caso o usuário tenha digitado a altura em centímeros é feita a conversão
     * de centímeros para metros.
     * @return A altura do usuário convertida para metros e double.
     */
    private double getAlturaDouble() {
        double altura = transformeStringEmDouble(alturaDoUsuario);
        altura = altura > 100 ? altura / 100 : altura;
        return altura;
    }
}