package com.example.android.imc;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText pesoText;
    EditText alturaText;
    RadioButton sexoM;
    RadioButton sexoH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

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

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        String sexoUsr = descobrirSexo();

        if(getalturaText().length() == 0 || getpesoText().length() == 0) {

            String mensagemErro = getString(R.string.erro_entrada_dados);
            Toast toast = Toast.makeText(this, mensagemErro, Toast.LENGTH_SHORT);

            toast.show();
        } else {

            Bundle bundle = new Bundle();
            bundle.putString("peso", getpesoText());
            bundle.putString("altura", getalturaText());
            bundle.putString("sexo", sexoUsr);
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


}
