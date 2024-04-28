package com.example.rockpaperscissor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import pl.droidsonroids.gif.GifImageView

class GameOver : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        val btnHome = findViewById<Button>(R.id.btnHome)

        val winner = intent.getStringExtra("winner")
        setView(winner.toString())

        btnHome.setOnClickListener{
            finish()
        }
    }

    private fun setView(winner:String){

        val imageView:GifImageView = findViewById(R.id.statusDisplay)
        val bgView:GifImageView = findViewById(R.id.WonBgDisplay)
        val tvstatus:TextView = findViewById(R.id.tv_status)

        if(winner=="Computer"){
            imageView.setImageResource(R.drawable.lose_gif)
            tvstatus.text = "Better Luck Next Time\nYou lose"
            bgView.visibility = View.GONE
        }
        else{
            imageView.setImageResource(R.drawable.win_gif)
            tvstatus.text = "Congratulations\nYou won!"
            bgView.visibility = View.VISIBLE
        }
    }
}