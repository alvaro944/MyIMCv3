package com.alvarocervantes.MyIMCv3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvarocervantes.MyIMCv3.databinding.FragmentHistoricoBinding
import java.io.File

class HistoricoFragment : Fragment() {
    private var _binding: FragmentHistoricoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoricoBinding.inflate(inflater, container, false)

        val historico = leerResultados()
        val adapter = HistoricoAdapter(historico)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        return binding.root
    }

    private fun leerResultados(): List<String> {
        val file = File(requireContext().filesDir, "resultados_imc.txt")
        return if (file.exists()) file.readLines() else emptyList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
