package com.example.android.imc;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import static com.example.android.imc.R.id.altura;
import static com.example.android.imc.R.id.calcular;
import static com.example.android.imc.R.id.peso;
import static com.example.android.imc.R.id.resultado;

public class MainActivity extends AppCompatActivity {

    TextView p;
    EditText a;
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        p = (EditText) findViewById(R.id.peso);
        a = (EditText) findViewById(altura);
        t = (TextView) findViewById(resultado);
    }

    public double calcularImc() {

        double resultado;

        double peso = Double.parseDouble(p.getText().toString().replaceAll(",", "."));
        double altura = Double.parseDouble(a.getText().toString().replaceAll(",", "."));
        if(altura != 0) {
            resultado = peso / (altura * altura);
        } else {
            resultado = 0.0;
        }
        return resultado;
    }

    public void calcularImc(View view) {

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        NumberFormat formatter = new DecimalFormat("#0.00");

        double resultado = calcularImc();

        t.setText("Seu IMC é: " + formatter.format(resultado));
    }
}
