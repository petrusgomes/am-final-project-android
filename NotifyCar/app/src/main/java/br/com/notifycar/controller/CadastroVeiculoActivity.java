package br.com.notifycar.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.com.notifycar.R;
import br.com.notifycar.repository.api.CadastroVeiculoTask;

public class CadastroVeiculoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnCadastrarVeiculo;
    private String idUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_veiculo);

        Intent it = getIntent();
        Bundle bundle = it.getExtras();
        idUsuario = bundle.getString("idUsuario");

        btnCadastrarVeiculo  = (Button) findViewById(R.id.btnCadastroVeiculo);
        btnCadastrarVeiculo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnCadastroVeiculo:
                CadastroVeiculoTask task = new CadastroVeiculoTask(this,idUsuario);
                task.execute();
                break;
            default:
                break;
        }
    }
}
