package br.com.notifycar.helper;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.notifycar.R;
import br.com.notifycar.model.Usuario;

/**
 * Created by Desenvolvimento on 23/09/2016.
 */
public class CamposHelper {

    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtSenha;

    private EditText edtLoginEmail;
    private EditText edtLoginSenha;

    private ListView listView;

    public JSONObject recuperaCamposUsuario(Activity activity) throws JSONException {
        edtNome = (EditText) activity.findViewById(R.id.nome);
        edtEmail = (EditText) activity.findViewById(R.id.email);
        edtSenha = (EditText) activity.findViewById(R.id.senha);

        JSONObject usuario = new JSONObject();
        usuario.put("nome", edtNome.getText().toString());
        usuario.put("email", edtEmail.getText().toString());
        usuario.put("senha", edtSenha.getText().toString());

        return usuario;
    }


    public void recuperaListaUsuario(Activity activity, String json){
        listView = (ListView) activity.findViewById(R.id.listaUsr);
        List<Usuario> listaUsuario = new ArrayList<>();
        Usuario usuario = null;
        try {
            JSONArray lista = new JSONArray(json);
            for (int i = 0; i < lista.length() ; i++) {
                usuario = new Usuario();
                usuario.setNome(lista.getJSONObject(i).getString("nome"));
                usuario.setEmail(lista.getJSONObject(i).getString("email"));
                usuario.setSenha(lista.getJSONObject(i).getString("senha"));
                usuario.setId(lista.getJSONObject(i).getString("_id"));
                usuario.setDataAtualizado(lista.getJSONObject(i).getString("atualizadoEm"));



                listaUsuario.add(usuario);
//                lista.getJSONObject(i).getString("nome");
//                Log.i("******", lista.getJSONObject(i).getString("email"));
            }

            ArrayAdapter<Usuario> adp = new ArrayAdapter<Usuario>(activity, android.R.layout.simple_list_item_1, listaUsuario);
            listView.setAdapter(adp);


//            Log.i("tee", lista.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject recuperaCamposLogin(Activity activity) throws JSONException {
        edtLoginEmail = (EditText) activity.findViewById(R.id.edtEmailLogin);
        edtLoginSenha = (EditText) activity.findViewById(R.id.edtSenhaLogin);

        JSONObject login = new JSONObject();
        login.put("email", edtLoginEmail.getText().toString());
        login.put("senha", edtLoginSenha.getText().toString());

        return login;
    }

}
