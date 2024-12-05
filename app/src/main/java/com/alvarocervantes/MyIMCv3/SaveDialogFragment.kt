package com.alvarocervantes.MyIMCv3

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SaveDialogFragment(private val onDecision: (Boolean) -> Unit) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle("Guardar resultado")
            .setMessage("¿Deseas guardar este resultado en el histórico?")
            .setPositiveButton("Sí") { _, _ -> onDecision(true) }
            .setNegativeButton("No") { _, _ -> onDecision(false) }
            .create()
    }
}
