package com.example.pokedexcm.views.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedexcm.databinding.ActivityPokemonListBinding
import com.example.pokedexcm.model.PokeApi
import com.example.pokedexcm.model.PokeDetail
import com.example.pokedexcm.model.Pokemon
import com.example.pokedexcm.model.PokemonInfoApi
import com.example.pokedexcm.views.adapter.Adapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonList : AppCompatActivity(), Adapter.OnItemListener{
    private val URL="https://pokeapi.co/"
    private val LOGTAG="LOGS"
    private lateinit var binding: ActivityPokemonListBinding
    private var pokemon_list= ArrayList<Pokemon>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityPokemonListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit: Retrofit =Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val PokeApi: PokeApi = retrofit.create(PokeApi::class.java)

        val call = PokeApi.getPokemon("api/v2/pokemon","151")

        call.enqueue(object: Callback<PokemonInfoApi> {
            override fun onResponse(
                call: Call<PokemonInfoApi>,
                response: Response<PokemonInfoApi>
            ) {
                //Conexion Exitosa
                Log.d(LOGTAG, "Respuesta: ${response.toString()}")
                pokemon_list = response.body()?.lista!!
                for (poke in pokemon_list){
                    getSprite(poke.url,pokemon_list.indexOf(poke),pokemon_list)
                }
                binding.rvPokemonList.layoutManager = LinearLayoutManager(this@PokemonList)
                binding.rvPokemonList.adapter = Adapter(this@PokemonList, pokemon_list, this@PokemonList)
            }

            override fun onFailure(call: Call<PokemonInfoApi>, t: Throwable) {
                Log.d(LOGTAG, "Conexion Fallida")
            }

        })


    }

    override fun onItemClick(pokemon: Pokemon) {
        val params=Bundle()
        params.putString("name",pokemon.name)
        params.putString("url",pokemon.url)
        val intent= Intent(this,PokemonDetail::class.java)
        intent.putExtras(params)
        startActivity(intent)
    }
    private fun getSprite(url: String?, spaceList:Int, lista:ArrayList<Pokemon>){
        val id= url?.split("/")?.get(6)
        Log.d(LOGTAG,"${id}")

        lista.get(spaceList).sprite="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"
        lista.get(spaceList).num_pokedex=Integer.parseInt(id)

    }
}
