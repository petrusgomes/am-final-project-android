package br.com.notifycar.repository.api;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.notifycar.R;
import br.com.notifycar.controller.CadastroDeviceActivity;
import br.com.notifycar.controller.LoginActivity;
import br.com.notifycar.helper.CamposHelper;
import br.com.notifycar.util.UtilJson;

/**
 * Created by Desenvolvimento on 06/10/2016.
 */
public class CadastroDeviceTask extends AsyncTask<String, Void, String> {

    private Activity activity;
    private ProgressDialog progressDialog;
    private String json = "";
    private String veiculoId;
    CamposHelper helper = new CamposHelper();

    public CadastroDeviceTask(Activity activity, String veiculoId){
        this.activity = activity;
        this.veiculoId = veiculoId;
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
        try{
            URL url = new URL("http://notifycar-api.mybluemix.net/dispositivo");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());


            out.writeBytes(helper.recuperaCamporDevice(activity,veiculoId).toString());

            out.flush();
            out.close();


            InputStream conteudo = conn.getInputStream();
            json = UtilJson.toString(conteudo);

        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return json;
    }

    @Override
    protected void onPostExecute(String json) {

        try {
            JSONObject device = new JSONObject(json);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        Toast.makeText(activity, "Cadastro Concluido com sucesso! ", Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
        Intent it = new Intent(activity, LoginActivity.class);
        activity.startActivity(it);
    }
}
