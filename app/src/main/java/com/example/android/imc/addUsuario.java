package com.example.android.imc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;


public class addUsuario extends AppCompatActivity {

    private EditText pesoText;
    private EditText alturaText;
    private EditText nomeText;
    private EditText idadeText;
    private RadioButton sexoM;
    private RadioButton sexoH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_usuario);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nomeText = (EditText) findViewById(R.id.nome);
        idadeText = (EditText) findViewById(R.id.idadeUsr);
        pesoText = (EditText) findViewById(R.id.peso);
        alturaText = (EditText) findViewById(R.id.altura);
        sexoH = (RadioButton) findViewById(R.id.homemRadio);
        sexoM = (RadioButton) findViewById(R.id.mulherRadio);
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

    public void addDadosUsuario(View view) {

        boolean dadosInformados = dadoFoiInformado(getalturaText()) && dadoFoiInformado(getpesoText()) &&
                dadoFoiInformado(getidadeText()) && dadoFoiInformado(getnomeText());

        if(dadosInformados) {
            gravaDados(view);
        } else {
            mostraMensagemDeDadosNaoInformados(view, getString(R.string.erro_entrada_dados));
        }
    }

    private void gravaDados(View view){
        dadosUsuarioDAO dao = new dadosUsuarioDAO(getBaseContext());
        dadosUsuarioAdapter adapter = new dadosUsuarioAdapter(new ArrayList<dadosUsuario>());

        boolean sucesso = adicionaDadosNoBanco(dao);

        if(sucesso) {
            dadosUsuario dados = dao.retornarUltimo();
            adapter.adicionarDadosUsuario(dados);
            retornaMainActivity();
        } else{
            mostraMensagemDeDadosNaoInformados(view, "Houve um erro ao salvar os dados.");
        }
    }

    private boolean adicionaDadosNoBanco(dadosUsuarioDAO dao) {
        return dao.salvar(getnomeText(),
                pegarSexoDoUsuario(),
                getidadeText(),
                Double.parseDouble(getalturaText()),
                Double.parseDouble(getpesoText()));
    }

    private void mostraMensagemDeDadosNaoInformados(View view, String mensagem) {
        Snackbar.make(view, mensagem, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
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

    private void retornaMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
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
}
