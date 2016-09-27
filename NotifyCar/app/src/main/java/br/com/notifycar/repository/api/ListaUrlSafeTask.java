package br.com.notifycar.repository.api;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.notifycar.helper.CamposHelper;
import br.com.notifycar.util.UtilJson;

/**
 * Created by Desenvolvimento on 25/09/2016.
 */
public class ListaUrlSafeTask extends AsyncTask<String, Void, String> {

    private String json;
    CamposHelper helper = new CamposHelper();
    private Activity activity;
    private ProgressDialog progressDialog;

    public ListaUrlSafeTask(Activity activity){
        this.activity = activity;
    }


    @Override
    protected String doInBackground(String... params) {

        URL url = null;
        try {
            url = new URL("http://notifycar-api.mybluemix.net/gateway/register/494101363422774");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");


            int codigo = conn.getResponseCode();
            if (codigo == 200) {
                InputStream conteudo = conn.getInputStream();
                json = UtilJson.toString(conteudo);
            }
        }catch (Exception e){
            e.getMessage();
        }
        return json;

    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Carregando...");
        progressDialog.setMessage("Aguarde");
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {

        helper.recuperaListaSafe(activity, json);

        progressDialog.dismiss();

    }
}
