package br.com.notifycar.repository.api;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.notifycar.R;
import br.com.notifycar.model.Usuario;
import br.com.notifycar.model.Veiculo;
import br.com.notifycar.util.UtilJson;

/**
 * Created by Desenvolvimento on 27/09/2016.
 */
public class ListaVeiculoStatusGeralTask extends AsyncTask<String, Void, String>{

    private String json = "";
    private String paramUrl= "";

    public ListaVeiculoStatusGeralTask(String paramUrl){
        this.paramUrl = paramUrl;
    }

    @Override
    protected String doInBackground(String... params) {
        URL url = null;
        try{
            url = new URL("http://notifycar-api.mybluemix.net/localizacao/veiculo/"+paramUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if(conn.getResponseCode() == 200){
                InputStream conteudo = conn.getInputStream();
                json = UtilJson.toString(conteudo);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return json;
    }

    @Override
    protected void onPostExecute(String json) {
        ArrayList<Veiculo> listVeiculo = new ArrayList<>();
        Veiculo veiculo = null;
        try {
            JSONArray lista = new JSONArray(json);
            for (int i = 0; i < lista.length() ; i++) {
                veiculo = new Veiculo();
                veiculo.setLatitude(lista.getJSONObject(i).getString("latitude").toString());
                veiculo.setLongitude(lista.getJSONObject(i).getString("longitude").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        listVeiculo.add(veiculo);
    }
}
