package br.com.notifycar.repository.api;

import android.app.Activity;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import br.com.notifycar.helper.CamposHelper;
import br.com.notifycar.interfacetask.AsyncResponseLoc;
import br.com.notifycar.util.UtilJson;

/**
 * Created by Desenvolvimento on 01/10/2016.
 */
public class ListaUltimaLocalizacaoTask extends AsyncTask<String, Void, String> {

    public AsyncResponseLoc delegate  = null;
    private String json = "";
    private String veiculoId = "";

    private Activity activity;

    public ListaUltimaLocalizacaoTask(Activity activity, String veiculoId){
        this.activity = activity;
        this.veiculoId = veiculoId;
    }

    @Override
    protected String doInBackground(String... strings) {
        URL url = null;
        try {
            url = new URL("http://notifycar-api.mybluemix.net/localizacao/veiculo/57e719f9da1d4e003ade3918/1");
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

        try {
//            JSONObject listaLoc = new JSONObject(json);
//            listaLoc.getString("latitude");
//            listaLoc.getString("longitude");

            delegate.processResult(json);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
