package com.example.pokedexcm.views.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.pokedexcm.R
import com.example.pokedexcm.databinding.ActivityPokeDetailBinding
import com.example.pokedexcm.model.*
import com.example.pokedexcm.views.adapter.AdapterAbility
import com.example.pokedexcm.views.adapter.AdapterStats
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonDetail : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPokeDetailBinding

    private val URL="https://pokeapi.co/"
    private val LOGTAG="LOGS"
    private var abilities_list= ArrayList<Ability>()
    private var estadisticas=ArrayList<Statistics>()
    private var lista_tipos=ArrayList<Types>()
    private var boolShiny=true
    private var imageDefault=""
    private var imageShiny=""
    private lateinit var imagenPokemon:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =ActivityPokeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val bundle = intent.extras
        val url=bundle?.getSerializable("url") as String
        Log.d(LOGTAG,"${url}")
        val id= url?.split("/")?.get(6)
        Log.d(LOGTAG,"${id}")
        val PokeAPI:PokeApi= retrofit.create(PokeApi::class.java)

        val call: Call<PokeDetail> = PokeAPI.getPokeInfo(id)

        call.enqueue(object: Callback<PokeDetail> {
            override fun onResponse(call: Call<PokeDetail>, response: Response<PokeDetail>) {
                Log.d(LOGTAG, "Conexion exitosa ${response.body()?.Nombre}")
                with(binding) {

                    tvPokemonName.text=response.body()?.Nombre
                    tvBaseExp.text=response.body()?.exp_base
                    tvHeight.text=response.body()?.altura
                    tvWeight.text=response.body()?.peso
                    btShiny.setOnClickListener(this@PokemonDetail)

                    abilities_list=response.body()?.abilities!!
                    estadisticas= response.body()?.estadisticas!!
                    lista_tipos = response.body()?.types!!

                    imagenPokemon=ivPokemon
                    imageDefault= response.body()?.sprites?.front_def.toString()
                    imageShiny= response.body()?.sprites?.front_shiny.toString()

                    Glide.with(this@PokemonDetail)
                        .load(response.body()?.sprites?.front_def)
                        .into(ivPokemon)
                    val adapter_Estadisticas=AdapterStats(this@PokemonDetail,estadisticas)
                    rvStats.layoutManager= LinearLayoutManager(this@PokemonDetail)
                    rvStats.adapter=adapter_Estadisticas

                    val adapter_Habilidad= AdapterAbility(this@PokemonDetail,abilities_list)
                    rvAbilities.layoutManager=LinearLayoutManager(this@PokemonDetail)
                    rvAbilities.adapter=adapter_Habilidad
                    TypeImage(lista_tipos.get(0),ivType1)
                    if(lista_tipos.size==2) {
                        TypeImage(lista_tipos.get(1), ivType2)
                    }
                    /*
                    abilities_list=response.body()?.abilities!!
                    estadisticas= response.body()?.estadisticas!!
                    lista_tipos = response.body()?.types!!
                    Log.d(LOGTAG,"${abilities_list.get(0).ability?.ab_nombre}")
                    Log.d(LOGTAG,"${estadisticas.get(0).stat?.name_stat}")
                    Log.d(LOGTAG,"${lista_tipos.get(0).tipo?.tipo_nombre}")
                    */
                }
            }
            override fun onFailure(call: Call<PokeDetail>, t: Throwable) {
                Log.d(LOGTAG, "Conexion Fallida")
            }

        })
    }
    private fun TypeImage(tipo: Types,image:ImageView){
        when(tipo.tipo?.tipo_nombre){

            "bug"->{return image.setImageResource(R.drawable.bug)}
            "dark"->{return image.setImageResource(R.drawable.dark)}
            "dragon"->{return image.setImageResource(R.drawable.dragon)}
            "electric"->{return image.setImageResource(R.drawable.electric)}
            "fairy"->{return image.setImageResource(R.drawable.fairy)}
            "fighting"->{return image.setImageResource(R.drawable.fighting)}
            "fire"->{return image.setImageResource(R.drawable.fire)}
            "flying"->{return image.setImageResource(R.drawable.flying)}
            "ghost"->{return image.setImageResource(R.drawable.ghost)}
            "grass"->{return image.setImageResource(R.drawable.plant)}
            "ground"->{return image.setImageResource(R.drawable.ground)}
            "ice"->{return image.setImageResource(R.drawable.ice)}
            "normal"->{return image.setImageResource(R.drawable.normal)}
            "poison"->{return image.setImageResource(R.drawable.poison)}
            "psychic"->{return image.setImageResource(R.drawable.psychic)}
            "rock"->{return image.setImageResource(R.drawable.rock)}
            "steel"->{return image.setImageResource(R.drawable.steel)}
            "water"->{return image.setImageResource(R.drawable.water)}
            else-> return image.setImageResource(android.R.color.transparent)

        }
    }

    override fun onClick(p0: View?) {
        if (boolShiny==false){
            Glide.with(this@PokemonDetail)
                .load(imageDefault)
                .into(imagenPokemon)
            boolShiny=true
        }else{
            Glide.with(this@PokemonDetail)
                .load(imageShiny)
                .into(imagenPokemon)
            boolShiny=false

        }
    }

}