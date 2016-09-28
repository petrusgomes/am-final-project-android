package br.com.notifycar.repository.api;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.notifycar.helper.CamposHelper;
import br.com.notifycar.menu.MenuTabActivity;
import br.com.notifycar.util.UtilJson;

/**
 * Created by Desenvolvimento on 24/09/2016.
 */
public class LoginUsuarioTask extends AsyncTask<String, Void, String> {

    private Activity activity;
    private HttpURLConnection conn;
    private String json = "";
    private ProgressDialog progressDialog;
    private String cpEmail;
    private String tokenParaRegistro;
    CamposHelper helper = new CamposHelper();

    public LoginUsuarioTask(Activity activity, String cpEmail){
        this.activity = activity;
        this.cpEmail = cpEmail;
    }


    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Carregando...");
        progressDialog.setMessage("Aguarde");
        progressDialog.show();
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


            if(conn.getResponseCode() == 200) {
                InputStream conteudo = conn.getInputStream();
                json = UtilJson.toString(conteudo);
            }
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

            if(validaLogin == true){
                Intent it = new Intent(activity, MenuTabActivity.class);
                it.putExtra("cpEmail", cpEmail);
                activity.startActivity(it);
            } else {
                AlertDialog alerta;

                final AlertDialog.Builder builderAlert = new AlertDialog.Builder(activity);

                builderAlert.setTitle("ERRO!");
                builderAlert.setMessage("USUARIO OU SENHA INCORRETO");

                builderAlert.setNeutralButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alerta = builderAlert.create();
                alerta.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        progressDialog.dismiss();
    }
}
