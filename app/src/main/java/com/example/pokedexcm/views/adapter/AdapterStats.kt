package com.example.pokedexcm.views.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexcm.R
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
        private var pbStatistics=binding.pbInfoStat
        fun bindData(stat:Statistics){

            tvNameStat.text=stat.stat?.name_stat
            tvInfoStat.text=stat.base_stat
            pbStatistics.progress= Integer.parseInt(stat.base_stat)
            /*
            when(stat.stat?.name_stat){
                "hp"->{
                    pbStatistics.setProgressTintList(ColorStateList.valueOf(Color.parseColor(R.color.hp.toString())))
                }
                "attack"->{
                    pbStatistics.setProgressTintList(ColorStateList.valueOf(Color.parseColor(R.color.attack.toString())))
                }
                "defense"->{
                    pbStatistics.setProgressTintList(ColorStateList.valueOf(Color.parseColor(R.color.defense.toString())))
                }
                "special-attack"->{
                    pbStatistics.setProgressTintList(ColorStateList.valueOf(Color.parseColor(R.color.special_attack.toString())))
                }
                "special-defense"->{
                    pbStatistics.setProgressTintList(ColorStateList.valueOf(Color.parseColor(R.color.special_defense.toString())))
                }
                "speed"->{
                    pbStatistics.setProgressTintList(ColorStateList.valueOf(Color.parseColor(R.color.speed.toString())))
                }


            }*/

        }
    }
}