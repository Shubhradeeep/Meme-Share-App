package com.example.memeshare

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity() {
    var url: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMeme()
    }
    @SuppressLint("SuspiciousIndentation")
    private fun loadMeme(){
   // val image= findViewById<ImageView>(R.id.meme)
        bar.visibility= View.VISIBLE
            // Instantiate the RequestQueue.

               // val queue = Volley.newRequestQueue(this)
               url = "https://meme-api.com/gimme"

        // Request a string response from the provided URL.
                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.GET, url,null,
                    { response ->
                     url=response.getString("url")
                        Glide.with(this).load(url).listener(object:RequestListener<Drawable>
                        {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {

                                bar.visibility=View.GONE
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {

                                bar.visibility=View.GONE
                                return false
                            }
                        }
                        ).into(meme)
                    },
                    {
                    })

        // Add the request to the RequestQueue.
               // queue.add(jsonObjectRequest)
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    fun nextMeme(view: View) {
        loadMeme()
    }
    fun shareMeme(view: View) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/*"
        //val photoFile = File(filesDir, "foo.jpg")
        shareIntent.putExtra(Intent.EXTRA_TEXT,"$url" )
       val chooser=Intent.createChooser(shareIntent,"Share the Meme..")
        startActivity(chooser)
    }
}