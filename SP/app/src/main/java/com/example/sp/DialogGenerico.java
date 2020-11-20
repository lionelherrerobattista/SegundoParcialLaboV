package com.example.sp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogGenerico extends DialogFragment {

    String textoMensaje;
    String titulo;

    public DialogGenerico(String titulo, String textoMensaje) {
        this.textoMensaje = textoMensaje;
        this.titulo = titulo;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(titulo);

        builder.setMessage(this.textoMensaje);
        builder.setPositiveButton("Ok", null);

        return builder.create();
    }
}
