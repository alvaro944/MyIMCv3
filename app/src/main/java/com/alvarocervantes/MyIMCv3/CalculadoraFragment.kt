package com.alvarocervantes.MyIMCv3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.Fragment
import com.alvarocervantes.MyIMCv3.databinding.FragmentCalculadoraBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CalculadoraFragment : Fragment() {
    private var _binding: FragmentCalculadoraBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculadoraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCalcular.setOnClickListener { calcularIMC(view) }
    }

    private fun calcularIMC(view: View) {
        val peso = binding.etPeso.text.toString().toDoubleOrNull()
        val altura = binding.etAltura.text.toString().toDoubleOrNull()
        val generoSeleccionado = when (binding.rgGenero.checkedRadioButtonId) {
            R.id.rbHombre -> "Hombre"
            R.id.rbMujer -> "Mujer"
            else -> null
        }

        // Validar entradas
        if (peso == null || altura == null || generoSeleccionado == null) {
            Snackbar.make(view, "Por favor, completa todos los campos", Snackbar.LENGTH_SHORT).show()
            return
        }

        // Calcular el IMC
        val alturaMetros = altura / 100
        val imc = peso / (alturaMetros * alturaMetros)
        val estado = calcularEstadoIMC(imc, generoSeleccionado)

        // Mostrar el resultado en el fragmento
        binding.tvResultadoIMC.text = "IMC: ${String.format("%.2f", imc)} ($estado)"
        binding.tvResultadoIMC.visibility = View.VISIBLE

        // Mostrar diálogo de confirmación
        SaveDialogFragment { guardar ->
            if (guardar) {
                val fechaActual = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
                guardarResultado(fechaActual, generoSeleccionado, imc, estado, peso, altura)
                Snackbar.make(view, "Resultado guardado en el histórico", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(view, "Resultado no guardado", Snackbar.LENGTH_SHORT).show()
            }
        }.show(parentFragmentManager, "SaveDialog")
    }

    private fun calcularEstadoIMC(imc: Double, genero: String): String {
        return if (genero == "Hombre") {
            when {
                imc < 18.5 -> "Peso inferior al normal"
                imc in 18.5..24.9 -> "Normal"
                imc in 25.0..29.9 -> "Sobrepeso"
                else -> "Obesidad"
            }
        } else {
            when {
                imc < 18.5 -> "Peso inferior al normal"
                imc in 18.5..23.9 -> "Normal"
                imc in 24.0..28.9 -> "Sobrepeso"
                else -> "Obesidad"
            }
        }
    }

    private fun guardarResultado(
        fecha: String,
        genero: String,
        imc: Double,
        estado: String,
        peso: Double,
        altura: Double
    ) {
        try {
            val file = File(requireContext().filesDir, "resultados_imc.txt")
            val resultado = "$fecha;$genero;${"%.2f".format(imc)};$estado;${"%.2f".format(peso)};${"%.2f".format(altura)}\n"
            file.appendText(resultado)
        } catch (e: Exception) {
            Snackbar.make(binding.root, "Error al guardar el resultado: ${e.message}", Snackbar.LENGTH_LONG).show()
        }
    }

}
