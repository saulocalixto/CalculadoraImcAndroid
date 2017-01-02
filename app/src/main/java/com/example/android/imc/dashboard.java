package com.example.android.imc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.android.imc.R.drawable.coracao;

/**
 * Created by saulocalixto on 29/12/16.
 */

public class dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

    }


    public void selecionarOpcao(View view) {

        switch (view.getId()) {
            case R.id.saude:
                mensagem(view);
                Intent i = getIntent();
                Bundle bundle = i.getExtras();
                Intent intent = new Intent(this, relatorio.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.dieta:

                mensagem(view);
                intent = new Intent(this, dieta.class);
                startActivity(intent);
        }

    }

    public void mensagem(View view) {

        TextView textView = (TextView) view;
        String opcao = "Opção: " + textView.getText().toString();
        Toast.makeText(this, opcao, Toast.LENGTH_LONG).show();
    }
}
