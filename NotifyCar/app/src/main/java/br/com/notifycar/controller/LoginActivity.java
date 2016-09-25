package br.com.notifycar.controller;

import android.content.Intent;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import br.com.notifycar.R;
import br.com.notifycar.api.CadastroUsuarioTask;
import br.com.notifycar.api.ListaUsuarioTask;
import br.com.notifycar.api.LoginUsuarioTask;
import br.com.notifycar.util.CustomImgLogo;
import br.com.notifycar.util.DeviceID;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    private Button btnLogin;
    private Button btnCadastrarActivity;
    private DeviceID deviceId;
    private LoginUsuarioTask task;
    private CustomImgLogo customImgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


//        deviceId = new DeviceID(this);
//        System.out.println("**********************:   " + deviceId.retornaIdDevice());

        customImgLogo = (CustomImgLogo) findViewById(R.id.imgLogo);
        customImgLogo.setImageResource(R.drawable.logonotify);

        btnCadastrarActivity = (Button) findViewById(R.id.btnCadastrar);
        btnCadastrarActivity.setOnClickListener(this);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.btnLogin:
               task = new LoginUsuarioTask(this);
               task.execute();
               break;
           case R.id.btnCadastrar:
               Intent it = new Intent(this, CadastroActivity.class);
               startActivity(it);
           default:
               break;
       }
    }
}
