package com.example.android.imc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import static android.view.View.Z;


public class MainActivity extends AppCompatActivity {

    EditText pesoText;
    EditText alturaText;
    EditText nomeText;
    EditText idadeText;
    RadioButton sexoM;
    RadioButton sexoH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        nomeText = (EditText) findViewById(R.id.nome);
        idadeText = (EditText) findViewById(R.id.idadeUsr);
        pesoText = (EditText) findViewById(R.id.peso);
        alturaText = (EditText) findViewById(R.id.altura);
        sexoH = (RadioButton) findViewById(R.id.homemRadio);
        sexoM = (RadioButton) findViewById(R.id.mulherRadio);
    }

    private String descobrirSexo() {
        String sexo = "";
        if(sexoH.isChecked()) {
            sexo = "Homem";
        } else if(sexoM.isChecked()) {
            sexo = "Mulher";
        }

        return sexo;
    }

    public void calcularImc(View view) {

        String sexoUsr = descobrirSexo();

        if(getalturaText().length() == 0 || getpesoText().length() == 0 ||
                getidadeText().length() == 0 || getnomeText().length() == 0) {

            String mensagemErro = getString(R.string.erro_entrada_dados);
            Toast toast = Toast.makeText(this, mensagemErro, Toast.LENGTH_SHORT);

            toast.show();
        } else {

            Bundle bundle = new Bundle();
            bundle.putString("peso", getpesoText());
            bundle.putString("altura", getalturaText());
            bundle.putString("sexo", sexoUsr);
            bundle.putString("nome", getnomeText());
            bundle.putString("idade", getidadeText());
            Intent intent = new Intent(this, dashboard.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    public String getpesoText() {
        return this.pesoText.getText().toString();
    }

    public String getalturaText() {
        return this.alturaText.getText().toString();
    }

    public String getnomeText() {
        return this.nomeText.getText().toString();
    }

    public String getidadeText() {
        return this.idadeText.getText().toString();
    }


}
