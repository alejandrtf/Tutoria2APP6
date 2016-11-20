package com.android.alejandra.tutoria2app6;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Activity_PantallaResultado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__pantalla_resultado);

        //poner titulo a la activity
        setTitle(getLocalClassName());

        TextView nombreEquipo=(TextView)findViewById(R.id.tvNombreEquipo);
        TextView puesto=(TextView)findViewById(R.id.tvPuestoEquipo);

        //obtengo datos del intent
        nombreEquipo.setText(getIntent().getStringExtra("NOMBRE_EQUIPO"));
        puesto.setText(String.valueOf(getIntent().getIntExtra("PUESTO",0)));
    }
}
