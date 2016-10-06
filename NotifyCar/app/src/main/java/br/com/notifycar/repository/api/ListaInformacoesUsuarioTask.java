package br.com.notifycar.repository.api;

import android.app.Activity;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.notifycar.helper.CamposHelper;
import br.com.notifycar.util.UtilJson;

/**
 * Created by Desenvolvimento on 28/09/2016.
 */
public class ListaInformacoesUsuarioTask extends AsyncTask<String, Void, String> {

    private String json = "";
    private String emailUsuario = "";
    CamposHelper helper = new CamposHelper();

    private Activity activity;

    public ListaInformacoesUsuarioTask(Activity activity, String emailUsuario){
        this.activity = activity;
        this.emailUsuario = emailUsuario;
    }

    @Override
    protected String doInBackground(String... strings) {
        URL url = null;
        try {
            url = new URL("http://notifycar-api.mybluemix.net/usuario/all/"+emailUsuario);
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
    protected void onPostExecute(String json) {

        helper.recuperaInformaçõesUsuario(activity, json);
    }
}
