package com.example.pokedexcm.model

import com.google.gson.annotations.SerializedName

class PokemonInfoApi{
    @SerializedName("results")
    var lista: ArrayList<Pokemon>?=null
}
class Pokemon {
    @SerializedName("name")
    var name: String?=null

    @SerializedName("url")
    var url: String?=null

    var sprite: String?=null
    var num_pokedex:Int?=null

}
