package com.example.android.imc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private EditText pesoText;
    private EditText alturaText;
    private EditText nomeText;
    private EditText idadeText;
    private RadioButton sexoM;
    private RadioButton sexoH;
    private dadosUsuario dadosEditado = null;
    private RecyclerView recyclerView;
    private dadosUsuarioAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //verifica se começou agora ou se veio de uma edição
        Intent intent = getIntent();
        mapeiaCamposFormulario(intent);
        if (intent.hasExtra("dadosUsuario")) {
            dadosEditado = (dadosUsuario) intent.getSerializableExtra("dadosUsuario");
            mostraTelaCadastro();
            setaValoresEmFormulario();
        }
        if(intent.hasExtra("usuarioSelecionado")) {
            chamaViewDashBoard(intent);
        }

        configurarRecycler();
    }

    @Override
    public void onBackPressed() {
        mostraLista();
        dadosEditado = null;
        setaValoresEmFormulario();
    }

    private void mostraTelaCadastro() {
        findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
        findViewById(R.id.includecadastro).setVisibility(View.VISIBLE);
        findViewById(R.id.btnAdd).setVisibility(View.INVISIBLE);
    }

    private void mostraLista() {
        findViewById(R.id.includemain).setVisibility(View.VISIBLE);
        findViewById(R.id.btnAdd).setVisibility(View.VISIBLE);
        findViewById(R.id.includecadastro).setVisibility(View.INVISIBLE);
    }

    private void mapeiaCamposFormulario(Intent intent) {
        nomeText = (EditText) findViewById(R.id.nome);
        idadeText = (EditText) findViewById(R.id.idadeUsr);
        alturaText = (EditText) findViewById(R.id.altura);
        pesoText = (EditText) findViewById(R.id.peso);
        sexoM = (RadioButton) findViewById(R.id.mulherRadio);
        sexoH = (RadioButton) findViewById(R.id.homemRadio);
    }

    private void setaValoresEmFormulario() {
        nomeText.setText(dadosEditado != null ? dadosEditado.getNome() : "");
        idadeText.setText(dadosEditado != null ? dadosEditado.getIdade() : "");
        alturaText.setText(dadosEditado != null ? String.valueOf(dadosEditado.getAltura()) : "");
        pesoText.setText(dadosEditado != null ? String.valueOf(dadosEditado.getPeso()) : "");
        sexoH.setChecked(dadosEditado != null ? dadosEditado.getSexo().equals("Homem") : false);
        sexoM.setChecked(dadosEditado != null ? dadosEditado.getSexo().equals("Mulher") : false);
    }

    private void configurarRecycler() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        anexaObjetosNaLista();
    }

    private void anexaObjetosNaLista() {
        dadosUsuarioDAO dao = new dadosUsuarioDAO(this);
        adapter = new dadosUsuarioAdapter(dao.retornarTodos());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    public void novoUsuario(View view) {
        mostraTelaCadastro();
    }

    public void addDadosUsuario(View view) {
        boolean dadosInformados = dadoFoiInformado(getalturaText()) && dadoFoiInformado(getpesoText()) &&
                dadoFoiInformado(getidadeText()) && dadoFoiInformado(getnomeText());

        if (dadosInformados) {
            gravaDados(view);
            hideKeyboard(getBaseContext(), view);
        } else {
            mostraMensagemDeDadosNaoInformados(view, getString(R.string.erro_entrada_dados));
        }
    }

    private void gravaDados(View view) {
        dadosUsuarioDAO dao = new dadosUsuarioDAO(getBaseContext());
        boolean sucesso = false;
        if (dadosEditado != null) {
            sucesso = adicionaDadosNoBanco(dao, dadosEditado.getId());
        } else {
            sucesso = adicionaDadosNoBanco(dao, 0);
        }

        if (sucesso) {
            dadosUsuario dados = dao.retornarUltimo();
            if (dadosEditado != null) {
                adapter.atualizarDadosUsuario(dados);
                dadosEditado = null;
            } else {
                adapter.adicionarDadosUsuario(dados);
            }
            setaValoresEmFormulario();
            mostraLista();
        } else {
            mostraMensagemDeDadosNaoInformados(view, "Houve um erro ao salvar os dados.");
        }
    }

    private void mostraMensagemDeDadosNaoInformados(View view, String mensagem) {
        Snackbar.make(view, mensagem, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private boolean adicionaDadosNoBanco(dadosUsuarioDAO dao, int id) {
        if (id <= 0) {
            return dao.salvar(
                    getnomeText(),
                    pegarSexoDoUsuario(),
                    getidadeText(),
                    Double.parseDouble(getalturaText()),
                    Double.parseDouble(getpesoText()));
        } else {
            return dao.salvar(
                    id,
                    getnomeText(),
                    pegarSexoDoUsuario(),
                    getidadeText(),
                    Double.parseDouble(getalturaText()),
                    Double.parseDouble(getpesoText()));
        }

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

    public void chamaViewDashBoard(Intent intent) {

        dadosEditado = (dadosUsuario) intent.getSerializableExtra("usuarioSelecionado");

        Bundle bundle = new Bundle();
        bundle.putString("peso", String.valueOf(dadosEditado.getPeso()));
        bundle.putString("altura", String.valueOf(dadosEditado.getAltura()));
        bundle.putString("sexo", dadosEditado.getSexo());
        bundle.putString("nome", dadosEditado.getNome());
        bundle.putString("idade", dadosEditado.getIdade());
        Intent i = new Intent(this, dashboard.class);
        i.putExtras(bundle);
        startActivity(i);
    }

    public static void hideKeyboard(Context context, View editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
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
