package com.example.retrophotos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cargarphotos()//Cargamos las fotos al iniciar la app y de forma aleatoria
        img_photo.setOnClickListener { cargarphotos() }

    }
    fun cargarphotos(){
        Picasso.get().load(R.drawable.descargar).into(img_photo)
        Picasso.get().load(R.drawable.descargar).into(img_thumb)
        progressBar.visibility= View.VISIBLE
        spin_kit.visibility=View.VISIBLE

        val api = Retrofit.Builder()
            .baseUrl("https://picsum.photos/")//https://jsonplaceholder.typicode.com/
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Interfaz::class.java)
        GlobalScope.launch(Dispatchers.IO) {
        try {
        val response = api.getAnime().awaitResponse()
            if (response.isSuccessful){
                withContext(Dispatchers.Main){
                    val data = response.body()!!
                    val rand:Int = (0..data.size).random()
                    //Cargamos las imagenes
                    Picasso.get().load(data[rand].download_url).into(img_photo)
                    txt_autor.text =data[rand].author
                    progressBar.visibility=View.INVISIBLE
                    spin_kit.visibility=View.INVISIBLE
                    //Toast.makeText(this@MainActivity,"Número : ${rand} de un Total : ${data.size}",Toast.LENGTH_LONG).show()
                }
            }
        }catch (e:Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(this@MainActivity,e.message,Toast.LENGTH_LONG).show()
            }
        }
        }//Fin de Coroutina
    }
}