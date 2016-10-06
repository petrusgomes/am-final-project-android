package br.com.notifycar.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

import br.com.notifycar.R;
import br.com.notifycar.helper.CamposValidate;
import br.com.notifycar.repository.api.AlteraFcmIdUsuarioTask;
import br.com.notifycar.repository.api.CadastraFcmIdUsuarioTask;
import br.com.notifycar.repository.api.ListaInformacoesUsuarioTask;
import br.com.notifycar.repository.api.LoginUsuarioTask;
import br.com.notifycar.repository.api.sharedpreferences.SalvarInformacoes;
import br.com.notifycar.util.CustomImgLogo;
import br.com.notifycar.util.DeviceID;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnLogin;
    private Button btnCadastrarActivity;
    private LoginUsuarioTask task;
    private CamposValidate validate;
    private CustomImgLogo customImgLogo;
    private EditText edtEmail;
    private String deviceId;
    private DeviceID dvId;
    private AlteraFcmIdUsuarioTask taskFcm;
    private SalvarInformacoes salvarInformacoesCp;

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

        edtEmail = (EditText) findViewById(R.id.edtEmailLogin);

        salvarInformacoesCp = new SalvarInformacoes(this);


        edtEmail.setText(salvarInformacoesCp.getEmailLogin());

    }

    @Override
    protected void onResume() {
        super.onResume();
        dvId = new DeviceID(this);
        deviceId = dvId.retornaIdDevice();
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.btnLogin:
              validate = new CamposValidate();
              boolean vldExecute = validate.validaCamposLogin(this);
               if(vldExecute == true) {
                   taskFcm = new AlteraFcmIdUsuarioTask(edtEmail.getText().toString());
                   taskFcm.execute();

                   task = new LoginUsuarioTask(this, edtEmail.getText().toString());
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
