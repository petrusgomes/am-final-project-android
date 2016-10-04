package com.example.renan.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Cadastro_veiculo_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_veiculo_activity);

        Spinner spinner =(Spinner) findViewById(R.id.colors_spinner);

        ArrayAdapter<CharSequence>   adapter = ArrayAdapter.createFromResource(this,R.array.colors_spinner,
                R.layout.spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        Spinner spinner1 = (Spinner) findViewById(R.id.marcas_spinners);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.marcas_spinners,R.layout.spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter1);
    }
}
