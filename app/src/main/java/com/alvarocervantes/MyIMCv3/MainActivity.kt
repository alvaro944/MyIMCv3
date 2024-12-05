package com.alvarocervantes.MyIMCv3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias de la UI
        val etPeso = findViewById<EditText>(R.id.et_peso)
        val etAltura = findViewById<EditText>(R.id.et_altura)
        val rgGenero = findViewById<RadioGroup>(R.id.rg_genero)
        val btnCalcular = findViewById<Button>(R.id.btn_calcular)
        val btnHistorico = findViewById<Button>(R.id.btn_historico) // Nuevo botón para mostrar el histórico
        val tvIMC = findViewById<TextView>(R.id.tv_imc)       // Nuevo TextView para mostrar el número del IMC
        val tvEstado = findViewById<TextView>(R.id.tv_estado) // Nuevo TextView para mostrar el estado (Normal, Obesidad, etc.)

        // Evento del botón calcular
        btnCalcular.setOnClickListener {
            val peso = etPeso.text.toString().toDoubleOrNull()
            val altura = etAltura.text.toString().toDoubleOrNull()

            // Validar entradas
            if (peso == null || altura == null || rgGenero.checkedRadioButtonId == -1) {
                Toast.makeText(this, getString(R.string.toast_error), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Cálculo del IMC
            val alturaMetros = altura / 100
            val imc = peso / (alturaMetros * alturaMetros)
            val generoSeleccionado = when (rgGenero.checkedRadioButtonId) {
                R.id.rb_hombre -> "Hombre"
                R.id.rb_mujer -> "Mujer"
                else -> null
            }

            // Determinar estado del IMC
            val estado = calcularEstadoIMC(imc, generoSeleccionado!!)

            // Actualizar la UI
            tvIMC.text = String.format("%.2f", imc) // Mostrar el IMC en grande
            tvEstado.text = estado                  // Mostrar el estado en el rectángulo

            // Guardar el resultado en el archivo
            val fechaActual = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date())
            guardarResultado(fechaActual, generoSeleccionado, imc, estado)
        }

        // Evento del botón Histórico
        btnHistorico.setOnClickListener {
            // Cambiamos la acción para abrir la nueva actividad
            val intent = Intent(this, HistoricoActivity::class.java)
            startActivity(intent)
        }
    }

    // Método para calcular el estado del IMC basado en el género
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

    // Método para guardar el resultado en un archivo de texto
    private fun guardarResultado(fecha: String, genero: String, imc: Double, estado: String) {
        // Ruta del archivo
        val file = File(filesDir, "resultados_imc.txt")

        // Formato del resultado
        val resultado = "$fecha;$genero;${"%.2f".format(imc)};$estado\n"

        // Escribir en el archivo
        file.appendText(resultado)
    }

    // Método para leer el contenido del archivo de resultados
    private fun leerResultados(): String {
        val file = File(filesDir, "resultados_imc.txt")
        return if (file.exists()) {
            file.readText()
        } else {
            "No hay resultados registrados."
        }
    }
}
