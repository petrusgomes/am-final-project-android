package br.com.notifycar.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.notifycar.R;
import br.com.notifycar.repository.api.CadastroUsuarioTask;

/**
 * Created by Desenvolvimento on 23/09/2016.
 */
public class CadastroActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnCadastrar;
    private Button btnListaUsuario;
    private TextView txtBackLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrousr);

        btnCadastrar = (Button) findViewById(R.id.btnCadastrarUsuario);
        btnCadastrar.setOnClickListener(this);

        btnListaUsuario = (Button) findViewById(R.id.btnListaUsuario);
        btnListaUsuario.setOnClickListener(this);

        txtBackLogin = (TextView) findViewById(R.id.txtBackLogin);
        txtBackLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCadastrarUsuario:
                CadastroUsuarioTask task = new CadastroUsuarioTask(this);
                task.execute();
                break;
            case R.id.btnListaUsuario:
                Intent it = new Intent(this, ListaActivity.class);
                startActivity(it);
                break;
            case R.id.txtBackLogin:
                finish();
            default:
                break;
        }
    }

}
