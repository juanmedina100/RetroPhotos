package com.example.retrophotos

import retrofit2.Call
import retrofit2.http.GET

interface Interfaz {

    //@GET("photos")
    //fun getPhotos():Call<List<PhotosModel>>
    @GET("v2/list")
    fun getAnime():Call<List<Anime>>
}