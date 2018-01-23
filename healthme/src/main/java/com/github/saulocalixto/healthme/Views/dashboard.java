package com.github.saulocalixto.healthme.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.saulocalixto.healthme.R;

/**
 * Created by saulocalixto on 29/12/16.
 */

public class dashboard extends AppCompatActivity {

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void selecionarOpcao(View view) {
        switch (view.getId()) {
            case R.id.saude:
                montaViewRelatorio(view);
                break;
            case R.id.dieta:
                montaViewDieta(view);
                break;
        }
    }

    private void montaViewRelatorio(View view) {
        mensagem(view);
        Intent intent = new Intent(this, relatorio.class);
        intent.putExtras(getIntent());
        startActivity(intent);
        finish();
    }

    private void montaViewDieta(View view) {
        mensagem(view);
        Intent intent = new Intent(this, dieta.class);
        intent.putExtras(getIntent());
        startActivity(intent);
        finish();
    }

    private void mensagem(View view) {
        TextView textView = (TextView) view;
        String opcao = "Opção: " + textView.getText().toString();
        Toast.makeText(this, opcao, Toast.LENGTH_LONG).show();
    }
}
