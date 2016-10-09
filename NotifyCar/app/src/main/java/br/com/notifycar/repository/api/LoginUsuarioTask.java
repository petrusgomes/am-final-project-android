package br.com.notifycar.repository.api;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.CheckBox;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.notifycar.R;
import br.com.notifycar.helper.CamposHelper;
import br.com.notifycar.menu.MenuTabActivity;
import br.com.notifycar.repository.api.sharedpreferences.SalvarInformacoes;
import br.com.notifycar.util.UtilJson;

/**
 * Created by Desenvolvimento on 24/09/2016.
 */
public class LoginUsuarioTask extends AsyncTask<String, Void, String> {

    private Activity activity;
    private HttpURLConnection conn;
    private String json = "";
    private ProgressDialog progressDialog;
    private String tokenParaRegistro;
    private String cpEmail;
    private CheckBox chkSalvarLogin;
    CamposHelper helper = new CamposHelper();

    public LoginUsuarioTask(Activity activity, String cpEmail){
        this.activity = activity;
        this.cpEmail = cpEmail;
    }


    @Override
    protected void onPreExecute() {
        helper.showDialog(activity);
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL("http://notifycar-api.mybluemix.net/login");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());

            out.writeBytes(helper.recuperaCamposLogin(activity).toString());
            out.flush();
            out.close();

            InputStream conteudo = conn.getInputStream();
            json = UtilJson.toString(conteudo);

        }catch(Exception e){
            e.printStackTrace();
        }

        return json;

    }

    @Override
    protected void onPostExecute(String json) {

        try {
            JSONObject obj = new JSONObject(json);

            Boolean validaLogin = obj.getBoolean("auth");

            if (validaLogin == true) {
                chkSalvarLogin = (CheckBox) activity.findViewById(R.id.chkSalvarLogin);
                if(chkSalvarLogin.isChecked()) {
                    SalvarInformacoes salvarInformacoesCp = new SalvarInformacoes(activity);
                    salvarInformacoesCp.salvarInfoLogin(cpEmail);
                }
                helper.hideDialog(activity);
                ListaInformacoesUsuarioTask taskUsuario = new ListaInformacoesUsuarioTask(activity,cpEmail);
                taskUsuario.execute();
            } else if(validaLogin == false || json.isEmpty()) {
                    helper.hideDialog(activity);

                    AlertDialog alerta;

                    final AlertDialog.Builder builderAlert = new AlertDialog.Builder(activity);

                    builderAlert.setTitle("ERRO!");
                    builderAlert.setMessage("USUARIO OU SENHA INCORRETO");

                    builderAlert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alerta = builderAlert.create();
                    alerta.show();
                }


        } catch (Exception e) {
            helper.hideDialog(activity);
            e.printStackTrace();
        }

        helper.hideDialog(activity);
    }



}
