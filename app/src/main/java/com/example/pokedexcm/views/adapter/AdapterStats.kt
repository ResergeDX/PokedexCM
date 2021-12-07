package com.example.pokedexcm.views.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexcm.databinding.StatsElementBinding

import com.example.pokedexcm.model.Statistics


class AdapterStats(context: Context, stats_list:List<Statistics>): RecyclerView.Adapter<AdapterStats.ViewHolder>()  {
    private val estadisticas=stats_list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterStats.ViewHolder {
        val layoutInflater= LayoutInflater.from(parent?.context)
        val binding = StatsElementBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)  {
        holder.bindData(estadisticas[position])
    }

    override fun getItemCount(): Int {
        return estadisticas.size
    }
    class ViewHolder(binding:StatsElementBinding):RecyclerView.ViewHolder(binding.root){
        private val tvNameStat=binding.tvNameStat
        private val tvInfoStat=binding.tvInfoStat
        fun bindData(stat:Statistics){

            tvNameStat.text=stat.stat?.name_stat
            tvInfoStat.text=stat.base_stat

        }
    }
}