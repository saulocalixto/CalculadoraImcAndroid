package com.example.android.imc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;


public class dadosUsuarioAdapter extends RecyclerView.Adapter<dadosUsuariosHolder> {

    private final List<dadosUsuario> dadosUsuarios;

    public dadosUsuarioAdapter(List<dadosUsuario> dados) {
        this.dadosUsuarios = dados;
    }

    @Override
    public dadosUsuariosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new dadosUsuariosHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_listar_usuarios, parent, false));
    }

    @Override
    public void onBindViewHolder(dadosUsuariosHolder holder, final int position) {
        holder.nomeUsuario.setText(dadosUsuarios.get(position).getNome());
        final dadosUsuario dados = dadosUsuarios.get(position);

        editarPerfil(holder, dados);

        selecionarPerfil(holder, dados);

        excluirPerfil(holder, dados);
    }

    private void editarPerfil(dadosUsuariosHolder holder, final dadosUsuario dados) {
        holder.btnEditar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity(v);
                Intent intent = activity.getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("dadosUsuario", dados);
                activity.finish();
                activity.startActivity(intent);
            }
        });
    }

    private void selecionarPerfil(dadosUsuariosHolder holder, final dadosUsuario dados) {
        holder.nomeUsuario.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity(v);
                Intent intent = activity.getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("usuarioSelecionado", dados);
                activity.finish();
                activity.startActivity(intent);
            }
        });
    }

    private void excluirPerfil(dadosUsuariosHolder holder, final dadosUsuario dados) {
        holder.btnExcluir.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirmação")
                        .setMessage("Tem certeza que deseja excluir este perfil de usuário?")
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dadosUsuarioDAO dao = new dadosUsuarioDAO(view.getContext());
                                boolean sucesso = dao.excluir(dados.getId());
                                if(sucesso) {
                                    removerCliente(dados);
                                    Snackbar.make(view, "Perfil de usuário excluído!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }else{
                                    Snackbar.make(view, "Erro ao excluir o perfil!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dadosUsuarios != null ? dadosUsuarios.size() : 0;
    }

    public void adicionarDadosUsuario(dadosUsuario dados){
        dadosUsuarios.add(dados);
        notifyItemInserted(getItemCount());
    }

    private Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    public void atualizarDadosUsuario(dadosUsuario dados){
        dadosUsuarios.set(dadosUsuarios.indexOf(dados), dados);
        notifyItemChanged(dadosUsuarios.indexOf(dados));
    }

    public void removerCliente(dadosUsuario dados){
        int position = dadosUsuarios.indexOf(dados);
        dadosUsuarios.remove(position);
        notifyItemRemoved(position);
    }
}