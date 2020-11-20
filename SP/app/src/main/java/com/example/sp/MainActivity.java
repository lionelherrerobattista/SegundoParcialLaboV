package com.example.sp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Cargar array contactos
        this.tv = findViewById(R.id.tvArray);

        //Buscar sharedPreferences
        SharedPreferences preferences = getSharedPreferences("arrayContactos", Context.MODE_PRIVATE);
        String contactos = preferences.getString("arrayContactos", null);

        if(contactos != null) {
            this.tv.setText(contactos);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Recupero el xml como objeto java
        getMenuInflater().inflate(R.menu.menu, menu);

        //Guardo el texto a buscar
        MenuItem menuItem = menu.findItem(R.id.opSearch);//recuperamos el menu
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView(); //Le sacamos el action view
        searchView.setOnQueryTextListener(this); //le seteamos el listener

        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.agregarPersona) {

            //Mostrar dialog para agregar persona
            AgregarPersonaDialog dialogGenerico = new AgregarPersonaDialog(this);
            dialogGenerico.show(getSupportFragmentManager(), "agregarContacto");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        boolean encontroPersona = false;
        JSONObject persona = null;
        this.tv = findViewById(R.id.tvArray);

        try {
            JSONArray arrayContactos = new JSONArray(tv.getText().toString());

            //Recorrer el json
            for(int i=0; i < arrayContactos.length(); i++) {
                JSONObject object = arrayContactos.getJSONObject(i);

                if(object.get("nombre").toString().toLowerCase().contains(query.toLowerCase())) {
                    encontroPersona = true;
                    persona = object;
                    break;
                }
            }

            if(encontroPersona) {
                //Mostrar mensaje encontro
                DialogGenerico dialogGenerico = new DialogGenerico("Persona Encontrada","El telefono es " + persona.get("telefono"));
                dialogGenerico.show(getSupportFragmentManager(), "encontroPersona");
            } else {
                //Mostrar mensaje no encontró
                DialogGenerico dialogGenerico = new DialogGenerico("No Encontrada","La persona que buscó no está dentro de la lista");
                dialogGenerico.show(getSupportFragmentManager(), "noEncontroPersona");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}