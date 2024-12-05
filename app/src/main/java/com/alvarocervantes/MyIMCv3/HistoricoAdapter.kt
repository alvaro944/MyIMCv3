package com.alvarocervantes.MyIMCv3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoricoAdapter(private val historico: List<String>) :
    RecyclerView.Adapter<HistoricoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMes: TextView = view.findViewById(R.id.tvMes)
        val tvDia: TextView = view.findViewById(R.id.tvDia)
        val tvAnio: TextView = view.findViewById(R.id.tvAnio)
        val tvEstado: TextView = view.findViewById(R.id.tvEstado)
        val tvGenero: TextView = view.findViewById(R.id.tvGenero)
        val tvIMC: TextView = view.findViewById(R.id.tvIMC)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_historico, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val registro = historico[position].split(";")

        // Formatear la fecha
        val fechaParts = registro[0].split("/")
        val dia = fechaParts.getOrNull(0) ?: "N/A"
        val mes = obtenerMes(fechaParts.getOrNull(1)?.toIntOrNull())
        val anio = fechaParts.getOrNull(2) ?: "N/A"

        // Asignar valores a las vistas
        holder.tvMes.text = mes
        holder.tvDia.text = dia
        holder.tvAnio.text = anio
        holder.tvEstado.text = registro.getOrNull(3) ?: "N/A"
        holder.tvGenero.text = registro.getOrNull(1) ?: "N/A"
        holder.tvIMC.text = registro.getOrNull(2) ?: "N/A"
    }

    override fun getItemCount() = historico.size

    // Método para obtener el nombre del mes
    private fun obtenerMes(mes: Int?): String {
        return when (mes) {
            1 -> "Enero"
            2 -> "Febrero"
            3 -> "Marzo"
            4 -> "Abril"
            5 -> "Mayo"
            6 -> "Junio"
            7 -> "Julio"
            8 -> "Agosto"
            9 -> "Septiembre"
            10 -> "Octubre"
            11 -> "Noviembre"
            12 -> "Diciembre"
            else -> "N/A"
        }
    }
}
