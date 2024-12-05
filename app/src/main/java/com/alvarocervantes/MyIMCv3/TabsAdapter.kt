package com.alvarocervantes.MyIMCv3

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabsAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private val fragments = listOf(
        CalculadoraFragment(),  // Fragmento para la Calculadora
        HistoricoFragment()     // Fragmento para el Histórico
    )
    private val titles = listOf(
        "Calculadora",
        "Histórico"
    )

    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment = fragments[position]

    fun getTitle(position: Int): String = titles[position]
}
