package com.android.alejandra.tutoria2app6;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.alejandra.tutoria2app6.modelo.Equipo;

import java.util.ArrayList;

/**
 * Created by Sandra on 16/11/2016.
 */

public class EquipoAdaptador extends ArrayAdapter<Equipo> {
    private Context context;
    private ArrayList<Equipo> datos;

    public EquipoAdaptador(Context context, ArrayList<Equipo> datos) {
        //al super pasamos el contexto, el layout de cada item de la lista y
        // el array con los datos
        super(context, R.layout.item_lista_equipos, datos);

        //guardamos los parámetros en las variables de la clase
        this.context = context;
        this.datos = datos;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // declaro variable ViewHolder
        EquipoHolder holder = null;
        // recojo el valor de convertView
        View item = convertView;

        if (item == null) {
            // Nunca se infló la vista aún.
            // En primer lugar "inflamos" una nueva vista
            LayoutInflater inflater = LayoutInflater.from(context);
            item = inflater.inflate(R.layout.item_lista_equipos, null);
            // Instanciamos el ViewHolder y almacenamos las referencias a sus 3
            // hijos
            holder = new EquipoHolder();
            holder.escudo = (ImageView) item.findViewById(R.id.ivEscudoItem);
            holder.nombreEquipo = (TextView) item.findViewById(R.id.tvNombreEquipoItem);
            holder.puntosEquipo = (TextView) item.findViewById(R.id.tvPuntosEquipoItem);
            // guardamos el holder en la vista item
            item.setTag(holder);
        } else {
            // obtenemos el ViewHolder guardado previamente, para tener acceso
            // rápido a los TextView
            holder = (EquipoHolder) item.getTag();
        }

        //Asignamos los datos correspondientes la equipo de la posición position

        // asignamos el escudo.
        holder.escudo.setImageDrawable(getItem(position).getImagenEscudo());
        // asignamos el nombre
        holder.nombreEquipo.setText(datos.get(position).getNombreEquipo());
        // asignamos los puntos
        holder.puntosEquipo.setText(String.valueOf(datos.get(position).getPuntos()));

        // Devolvemos la vista para que se muestre en el ListView.
        return item;
    }

    static class EquipoHolder {
        ImageView escudo;
        TextView nombreEquipo;
        TextView puntosEquipo;
    }
}
