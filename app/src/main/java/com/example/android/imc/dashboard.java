package com.example.android.imc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by saulocalixto on 29/12/16.
 */

public class dashboard extends AppCompatActivity {

    String pesoUsr;
    String alturaUsr;
    String sexoUsr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void gerarRelatorio(View view) {

        mensagem(view);

        Intent i = getIntent();

        Bundle bundle = i.getExtras();
        Intent intent = new Intent(this, relatorio.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void mensagem(View view) {

        TextView textView = (TextView) view;
        String opcao = "Opção: " + textView.getText().toString();
        Toast.makeText(this, opcao, Toast.LENGTH_LONG).show();
    }

    public void mostrarDietas(View view) {

        mensagem(view);
        Intent intent = new Intent(this, dieta.class);
        startActivity(intent);
    }
}
