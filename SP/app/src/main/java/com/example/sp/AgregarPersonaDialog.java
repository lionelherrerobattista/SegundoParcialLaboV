package com.example.sp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AgregarPersonaDialog extends DialogFragment {

    MainActivity mainActivity;


    public AgregarPersonaDialog(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Nuevo contacto");

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.agregar_contacto, null);
        builder.setView(view);

        //Pasar view para recuperar botones
        ClickAgregarPersonaDialog listener = new ClickAgregarPersonaDialog(mainActivity, view);

        builder.setPositiveButton("Guardar", listener);
        builder.setNegativeButton("Cancelar", null);

        return builder.create();
    }

}
