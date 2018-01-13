package com.example.android.imc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import static android.view.View.*;


public class MainActivity extends AppCompatActivity {

    private EditText pesoText;
    private EditText alturaText;
    private EditText nomeText;
    private EditText idadeText;
    private RadioButton sexoM;
    private RadioButton sexoH;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private String pegarSexoDoUsuario() {
        String sexo = "";
        if (sexoH.isChecked()) {
            sexo = "Homem";
        } else if (sexoM.isChecked()) {
            sexo = "Mulher";
        }

        return sexo;
    }

    public void calcularImc(View view) {

        boolean dadosInformados = dadoFoiInformado(getalturaText()) && dadoFoiInformado(getpesoText()) &&
                dadoFoiInformado(getidadeText()) && dadoFoiInformado(getnomeText());

        if (dadosInformados) {
            chamaViewDashBoard();
        } else {

            mostraMensagemDeDadosNaoInformados();
        }
    }

    private void mostraMensagemDeDadosNaoInformados() {
        String mensagemErro = getString(R.string.erro_entrada_dados);
        Toast toast = Toast.makeText(this, mensagemErro, Toast.LENGTH_SHORT);

        toast.show();
    }

    private void chamaViewDashBoard() {

        String sexoUsr = pegarSexoDoUsuario();

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

    private boolean dadoFoiInformado(String dado) {
        return dado != null && !dado.trim().isEmpty();
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


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.android.imc/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.android.imc/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
