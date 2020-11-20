package com.example.sp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ClickAgregarPersonaDialog implements DialogInterface.OnClickListener {

    MainActivity mainActivity;
    View view;

    public ClickAgregarPersonaDialog(MainActivity mainActivity, View view) {
        this.mainActivity = mainActivity;
        this.view = view;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        EditText etNombre;
        EditText etTelefono;
        JSONObject contacto;
        String contactos;
        JSONArray jsonArray;
        TextView tvArray;

        if(which == Dialog.BUTTON_POSITIVE){
            etNombre = view.findViewById(R.id.etNombre);
            etTelefono = view.findViewById(R.id.etTelefono);

            //Comprobar que no esté vacío
            if(!etNombre.getText().toString().equals("") &&  !etTelefono.getText().toString().equals("")){
                contacto = new JSONObject();
                try {
                    contacto.put("nombre", etNombre.getText().toString());
                    contacto.put("telefono", etTelefono.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("contacto", contacto.toString());

                //Recuperar el array de contactos
                SharedPreferences preferences = this.mainActivity.getSharedPreferences("arrayContactos", Context.MODE_PRIVATE);
                contactos = preferences.getString("arrayContactos", null);

                if(contactos != null) {
                    try {
                        jsonArray = new JSONArray(contactos);
                        jsonArray.put(contacto);
                        //Guardar en sharedpreferences
                        SharedPreferences.Editor editor = preferences.edit();

                        editor.putString("arrayContactos", jsonArray.toString());
                        editor.commit();

                        //actualizar textView
                        tvArray = this.mainActivity.findViewById(R.id.tvArray);
                        tvArray.setText(jsonArray.toString());
                        Log.d("arrayContactos", jsonArray.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    jsonArray = new JSONArray();
                    jsonArray.put(contacto);
                    //Crear nuevo sharedPreferences
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("arrayContactos", jsonArray.toString());
                    editor.commit();

                    //actualizar textView
                    tvArray = this.mainActivity.findViewById(R.id.tvArray);
                    tvArray.setText(jsonArray.toString());
                    Log.d("arrayContactos", jsonArray.toString());
                }
            } else {

                DialogGenerico dialogGenerico = new DialogGenerico("Completar datos","Tiene que completar los datos de contacto");
                dialogGenerico.show(this.mainActivity.getSupportFragmentManager(), "noCompletoDatos");
                Log.d("nombre", "Texto vacío");


            }
        }
    }
}
