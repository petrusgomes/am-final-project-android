package br.com.notifycar.helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.EGLDisplay;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.notifycar.R;
import br.com.notifycar.controller.mapa.MapsViewActivity;
import br.com.notifycar.menu.MenuTabActivity;
import br.com.notifycar.repository.api.BloqueioDesbloqueioVeiculoTask;
import br.com.notifycar.controller.mapa.MapsViewActivity;
import br.com.notifycar.model.Usuario;

/**
 * Created by Desenvolvimento on 23/09/2016.
 */
public class CamposHelper {

    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtSenha;
    private EditText edtTelefoneUsuario;

    private EditText edtLoginEmail;
    private EditText edtLoginSenha;

    private EditText edtAno;
    private EditText edtPlaca;
    private Spinner modelos;
    private Spinner cores;

    private EditText deviceEdt;

    private ListView listView;
    private BloqueioDesbloqueioVeiculoTask task;

    private TextView txtNomeModelo;
    private TextView txtNomeVeiculo;




    ProgressDialog progressDialog;
    private ImageView imgStatusBlockVeiculo;


    public JSONObject recuperaCamposUsuario(Activity activity) throws JSONException {
        edtNome = (EditText) activity.findViewById(R.id.edtNameCadastro);
        edtEmail = (EditText) activity.findViewById(R.id.edtEmailCadastro);
        edtTelefoneUsuario = (EditText) activity.findViewById(R.id.edtTelefoneCadastro);
        edtSenha = (EditText) activity.findViewById(R.id.edtSenhaCadastro);

        JSONObject usuario = new JSONObject();
        usuario.put("nome", edtNome.getText().toString());
        usuario.put("email", edtEmail.getText().toString());
        usuario.put("senha", edtSenha.getText().toString());
        usuario.put("telefone", edtTelefoneUsuario.getText().toString());


        return usuario;
    }


    public JSONObject recuperaCamposVeiculo(Activity activity, String usuarioId)throws JSONException {
        edtAno = (EditText) activity.findViewById(R.id.edtAno);
        edtPlaca = (EditText) activity.findViewById(R.id.placaEdt);
        modelos = (Spinner) activity.findViewById(R.id.spinnerModelos);
        cores = (Spinner) activity.findViewById(R.id.spinnerCores);

//        ArrayList<String> coresList = new ArrayList<>();
//        coresList.add("branco");
//        coresList.add("prata");
//        coresList.add("preto");
//        coresList.add("verde");
//        coresList.add("azul");
//        coresList.add("vinho");
//
//        ArrayAdapter<String> adp = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_dropdown_item, coresList);
//        cores.setAdapter(adp);

        JSONObject veiculo = new JSONObject();
        veiculo.put("cor", "verde");
        veiculo.put("ano", edtAno.getText().toString());
        veiculo.put("placa", edtPlaca.getText().toString());
        veiculo.put("modeloId", "57ef06025fe420003acc1bc7");
        veiculo.put("usuarioId", usuarioId);

        return  veiculo;
    }

    public JSONObject recuperaCamporDevice(Activity activity, String veiculoId) throws JSONException {
        deviceEdt = (EditText) activity.findViewById(R.id.deviceEdt);

        JSONObject device = new JSONObject();
        device.put("deviceId", deviceEdt.getText().toString());
        device.put("veiculoId",veiculoId);

        return device;
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
            }

            ArrayAdapter<Usuario> adp = new ArrayAdapter<Usuario>(activity, android.R.layout.simple_list_item_1, listaUsuario);
            listView.setAdapter(adp);


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


    public void recuperaListaSafe(Activity activity, String json, String idVeiculo, String placa){
        String urlRemote = "";
        try {
            JSONArray lista = new JSONArray(json);
            for (int i = 0; i < lista.length() ; i++) {
                urlRemote = lista.getJSONObject(i).getString("remoteControl");
            }

            Intent it = new Intent(activity, MapsViewActivity.class);
            it.putExtra("urlRemoteControl", urlRemote);
            it.putExtra("idVeiculo", idVeiculo);
            it.putExtra("placaVeiculo", placa);
            activity.startActivity(it);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void registraSafeVeiculo(final Activity activity, final String urlSafe){
        final CharSequence[] items = {"Ativar","Desativar"};
        // arraylist to keep the selected items
        final ArrayList seletedItems = new ArrayList();
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setTitle("Estacionamento Seguro").setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                        if (isChecked) {
                            int i = indexSelected;
                            if(i == 0){
                                task = new BloqueioDesbloqueioVeiculoTask(urlSafe,"lock",activity);
                                task.execute();
                                dialog.dismiss();
                            }  else if(i == 1){
                                task = new BloqueioDesbloqueioVeiculoTask(urlSafe,"reset",activity);
                                task.execute();
                                dialog.dismiss();
                            }
                            seletedItems.add(indexSelected);
                        } else if (seletedItems.contains(indexSelected)) {
                            // Else, if the item is already in the array, remove it
                            seletedItems.remove(Integer.valueOf(indexSelected));
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                }).create();
        dialog.show();
    }


    public void recuperaInformaçõesUsuario(Activity activity, String json){

        try {
          JSONObject veiculoObject = new JSONObject(json).getJSONObject("veiculo");

          String idVeiculo = veiculoObject.getString("_id");
          String placa = veiculoObject.getString("placa");

          JSONObject modeloObject = new JSONObject(json).getJSONObject("modelo");

          String modeloVeiculo = modeloObject.getString("nome");

          JSONObject fabricanteObject = new JSONObject(json).getJSONObject("fabricante");

          String fabricanteVeiculo = fabricanteObject.getString("nome");

          JSONObject  usuarioObject = new JSONObject(json).getJSONObject("usuario");

          String emailUsuario = usuarioObject.getString("email");

          JSONObject dispositivoObject = new JSONObject(json).getJSONObject("dispositivo");

          String dispositivoId = dispositivoObject.getString("deviceId");

//          hideDialog(activity);

          Intent it = new Intent(activity, MenuTabActivity.class);
          it.putExtra("idVeiculo", idVeiculo);
          it.putExtra("emailUsuario", emailUsuario);
          it.putExtra("modeloVeiculo", modeloVeiculo);
          it.putExtra("fabricanteVeiculo", fabricanteVeiculo);
          it.putExtra("placaVeiculo", placa);
          it.putExtra("dispositivoId", dispositivoId);
            activity.startActivity(it);


        }catch(Exception e){
            hideDialog(activity);
            e.printStackTrace();
        }


    }

    public void listaStatusAlarme(Activity activity, String json){
        Boolean statusAlarmeVeiculo;
        Boolean statusBloqueioVeiculo;

        try {
            JSONObject statusAlarme = new JSONObject(json);
            statusAlarmeVeiculo = statusAlarme.getBoolean("alarmeDisparado");
            statusBloqueioVeiculo = statusAlarme.getBoolean("bloqueado");

            imgStatusBlockVeiculo = (ImageView) activity.findViewById(R.id.imgStatusBlock);

            if(statusAlarmeVeiculo == true){

            } else {

            }

            if(statusBloqueioVeiculo == true){
                 imgStatusBlockVeiculo.setImageResource(R.drawable.status_block_green);
            } else {
                imgStatusBlockVeiculo.setImageResource(R.drawable.status_block_red);
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showDialog(Activity activity){
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Carregando...");
        progressDialog.setMessage("Aguarde");
        progressDialog.show();
    }

    public void hideDialog(Activity activity){
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.dismiss();
    }

}
