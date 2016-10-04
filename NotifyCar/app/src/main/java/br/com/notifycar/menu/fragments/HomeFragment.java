package br.com.notifycar.menu.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import org.w3c.dom.Text;

import br.com.notifycar.R;
import br.com.notifycar.repository.api.ListaInformacoesUsuarioTask;
import br.com.notifycar.repository.api.ListaUrlSafeTask;

/**
 * Created by Ratan on 7/29/2015.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {


    private Button btnMapa;
    private ListaUrlSafeTask task;
    private String idVeiculo;
    private String nomeDoVeiculo;
    private String nomeDoModelo;
    private TextView txtNomeVeiculo;
    private TextView txtModeloVeiculo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_home, null, false);

        btnMapa = (Button) v.findViewById(R.id.btnMapa);
        btnMapa.setOnClickListener(this);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        Intent it = getActivity().getIntent();
        Bundle dadosUsuario = it.getExtras();

        nomeDoVeiculo = dadosUsuario.getString("modeloVeiculo");
        idVeiculo = dadosUsuario.getString("idVeiculo");
        nomeDoModelo = dadosUsuario.getString("fabricanteVeiculo");

        txtNomeVeiculo = (TextView) getActivity().findViewById(R.id.txtNomeVeiculo);
        txtNomeVeiculo.setText(nomeDoVeiculo);

        txtModeloVeiculo = (TextView) getActivity().findViewById(R.id.txtNomeModelo);
        txtModeloVeiculo.setText(nomeDoModelo);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMapa:
                task = new ListaUrlSafeTask(getActivity(), idVeiculo);
                task.execute();
                break;
            default:
                break;
        }
    }
}
