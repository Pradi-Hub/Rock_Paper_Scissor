package com.example.rockpaperscissor

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPlayWithOther = findViewById<Button>(R.id.btnPlayWithOther)
        btnPlayWithOther.setOnClickListener{
            val intent = Intent(this,PlayWithOther::class.java)
            startActivity(intent)
        }

        val btnPlayWithComputer = findViewById<Button>(R.id.btnPlayWithComputer)
        btnPlayWithComputer.setOnClickListener{
            val intent = Intent(this,PlayWithComputer::class.java)
            startActivity(intent)
        }

        val tvInstruction = findViewById<TextView>(R.id.tvInstruction)
        tvInstruction.setOnClickListener{
            showInstructions()
        }
    }

    private fun showInstructions(){
        val instDialog = Dialog(this)
        instDialog.setContentView(R.layout.instruction_dialog)
        instDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        instDialog.findViewById<Button>(R.id.btnOk).setOnClickListener{
            instDialog.cancel()
        }
        instDialog.show()
    }
}