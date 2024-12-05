package com.alvarocervantes.MyIMCv3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class HistoricoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historico)

        // Referencia al RecyclerView en el layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // Configuración del LayoutManager para el RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Leer los datos del archivo y configurar el Adapter
        val historico = leerResultados()
        recyclerView.adapter = HistoricoAdapter(historico)
    }

    // Método para leer los resultados del archivo de texto
    private fun leerResultados(): List<String> {
        val file = File(filesDir, "resultados_imc.txt")
        return if (file.exists()) file.readLines() else emptyList()
    }
}
