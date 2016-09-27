package br.com.notifycar.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.notifycar.R;
import br.com.notifycar.helper.CamposValidate;
import br.com.notifycar.repository.api.LoginUsuarioTask;
import br.com.notifycar.util.CustomImgLogo;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    private Button btnLogin;
    private Button btnCadastrarActivity;
    private LoginUsuarioTask task;
    private CamposValidate validate;
    private CustomImgLogo customImgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        customImgLogo = (CustomImgLogo) findViewById(R.id.imgLogo);
        customImgLogo.setImageResource(R.drawable.logonotify);

        btnCadastrarActivity = (Button) findViewById(R.id.btnCadastrar);
        btnCadastrarActivity.setOnClickListener(this);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        validate = new CamposValidate();
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.btnLogin:
              boolean vldExecute = validate.validaCamposLogin(this);
               if(vldExecute == true) {
                   task = new LoginUsuarioTask(this);
                   task.execute();
               }
               break;
           case R.id.btnCadastrar:
               Intent it = new Intent(this, CadastroActivity.class);
               startActivity(it);
           default:
               break;
       }
    }
}
