package com.android.alejandra.tutoria2app6;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.alejandra.tutoria2app6.modelo.Equipo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ArrayList<Equipo> arrayEquipos = new ArrayList<>();
    private final static int COD_PETICION_INSERCION_EQUIPO=1;
    private EquipoAdaptador adaptador;
    private ListView lvEquipos;
    private int posBorrar=-1; //indicará posición del equipo a borrar. Se anotará cuando se elija un equipo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicializamos el array de equipos y cargamos los valores en el arrayList
        if(arrayEquipos.isEmpty())
            rellenarArrayList();

        //obtengo referencia a la Listview
        lvEquipos = (ListView) findViewById(R.id.listaEquipos);

        //declaro en instancio una variable de tipo adaptador
        adaptador = new EquipoAdaptador(this, arrayEquipos);

        //asigno adaptador
        lvEquipos.setAdapter(adaptador);

        //asigno listener que estará pendiente de si se pulsa un item
        lvEquipos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View view, int position, long id) {
                //anoto la posición que ocupa este equipo por si luego pulsa el botón borrar
                posBorrar=position;

                //muestro información del equipo elegido
                Intent i = new Intent(MainActivity.this, Activity_PantallaResultado.class);
                i.putExtra("NOMBRE_EQUIPO", ((Equipo) lista.getAdapter().getItem(position)).getNombreEquipo());
                i.putExtra("PUESTO", position + 1);
                startActivity(i);
            }
        });
    }


    /**
     * Método que carga los datos en el arrayList de equipos
     */
    private void rellenarArrayList() {
        //recupero el array de los escudos de los recursos
        TypedArray arrayTypedEscudos = getResources().obtainTypedArray(R.array.escudo_equipo);

        //recupero el array de los nombres de equipos
        String[] arrayNombreEquipos = getResources().getStringArray(R.array.nombre_equipo);

        //recupero el array de los puntos de equipos
        int[] arrayPuntosEquipos = getResources().getIntArray(R.array.puntos_equipo);

        //relleno el arraylist
        for (int i = 0; i < arrayNombreEquipos.length; i++) {
            arrayEquipos.add(new Equipo(arrayNombreEquipos[i], arrayTypedEscudos.getDrawable(i), arrayPuntosEquipos[i]));
        }
        arrayTypedEscudos.recycle();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if
        // it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_aniadir:
                lanzarActivityAniadir();
                return true;
            case R.id.action_borrar:
                borrarEquipoSeleccionado();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    /**Método que borra el equipo que esté seleccionado en la lista
     * Es llamado desde una opción de un menú de opción (BORRAR)
     */
    private void borrarEquipoSeleccionado() {
        if(posBorrar!=-1) {
            arrayEquipos.remove(posBorrar);
            adaptador.notifyDataSetChanged();
            posBorrar = -1;
            Toast.makeText(this,"EQUIPO BORRADO CORRECTAMENTE!!!",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"NINGÚN EQUIPO SELECCIONADO!!!",Toast.LENGTH_SHORT).show();
         
        }

    }

    /**Método que lanza la activity AniadirActivity
     * Es llamado desde una opción de un menu de opción (AÑADIR)
     */
    private void lanzarActivityAniadir() {
        Intent aniadirEquipoIntent=new Intent(this,AniadirActivity.class);
        startActivityForResult(aniadirEquipoIntent,COD_PETICION_INSERCION_EQUIPO);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==COD_PETICION_INSERCION_EQUIPO){
            if (resultCode==RESULT_OK){
                String resultadoInsercion=data.getStringExtra(AniadirActivity.RESULTADO_INSERCION);
                if(resultadoInsercion.equals("OK")){
                    //notifico al adaptador para que recargue lista
                   adaptador.notifyDataSetChanged();
                }
            }
            else{
                Toast.makeText(this,"INSERCIÓN CANCELADA: Equipo nuevo no insertado",Toast.LENGTH_SHORT).show();
            }

        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //guardo posBorrar por si me destruye la mainActiviy al abrir Activity_pantallaResultado
        outState.putInt("posBorrar",posBorrar);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        posBorrar=(int)savedInstanceState.get("posBorrar");
    }
}