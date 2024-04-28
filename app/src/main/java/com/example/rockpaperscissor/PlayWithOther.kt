package com.example.rockpaperscissor

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.rockpaperscissor.databinding.ActivityPlayWithOtherBinding
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

class PlayWithOther : AppCompatActivity() {

    private lateinit var sharedPrefs: SharedPreferences

    private var binding:ActivityPlayWithOtherBinding? = null

    private var animation1:AnimationDrawable? = null
    private var animation2:AnimationDrawable? = null
    private var setTime:CountDownTimer? = null

    private var playerNameone ="Player 1"
    private var playerNametwo ="Player 2"

    private var player1Ready = false
    private var player2Ready = false
    private var allowPlaying = true

    private lateinit var selectionP1:String
    private lateinit var selectionP2:String

    private var scoreP1 = 0
    private var scoreP2 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayWithOtherBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        sharedPrefs = getSharedPreferences("PlayerNames", Context.MODE_PRIVATE)

        getPlayerName()

        binding?.btnP1rock?.setOnClickListener{
            onPlayP1("rock")
        }

        binding?.btnP1paper?.setOnClickListener{
            onPlayP1("paper")
        }

        binding?.btnP1scissor?.setOnClickListener{
            onPlayP1("scissor")
        }

        binding?.btnP2rock?.setOnClickListener{
            onPlayP2("rock")
        }

        binding?.btnP2paper?.setOnClickListener{
            onPlayP2("paper")
        }

        binding?.btnP2scissor?.setOnClickListener{
            onPlayP2("scissor")
        }

        val btnQuit = findViewById<Button>(R.id.btnQuit)
        btnQuit.setOnClickListener {
            quitGame()
        }
    }

    private fun playAnimation(){
        binding?.ivIconP1?.setImageResource(0)
        binding?.ivIconP2?.setImageResource(0)
        binding?.tvP1Status?.text=""
        binding?.tvP2Status?.text=""
        binding?.ivIconP1?.setBackgroundResource(R.drawable.animation_rps)
        animation1 = binding?.ivIconP1?.background as AnimationDrawable
        binding?.ivIconP2?.setBackgroundResource(R.drawable.animation_rps)
        animation2 = binding?.ivIconP2?.background as AnimationDrawable

        setTime = object :CountDownTimer(3000,1000){
            override fun onTick(p0: Long) {
                animation1?.start()
                animation2?.start()
            }

            override fun onFinish() {
                animation1?.stop()
                animation2?.stop()
                allowPlaying=true
                player1Ready=false
                player2Ready=false
                binding?.ivIconP1?.setBackgroundResource(0)
                binding?.ivIconP2?.setBackgroundResource(0)
                setSelectedIcon()
                setScore()
                endGame()
            }
        }.start()
    }

    private fun onPlayP1(selection: String){
        if (allowPlaying){
            binding?.ivIconP1?.setImageResource(R.drawable.check)
            binding?.tvP1Status?.text="Ready"
            selectionP1 = selection
            player1Ready=true
            if (player2Ready){
                allowPlaying=false
                playAnimation()
            }
        }
    }

    private fun onPlayP2(selection: String){
        if (allowPlaying){
            binding?.ivIconP2?.setImageResource(R.drawable.check)
            binding?.tvP2Status?.text="Ready"
            selectionP2 = selection
            player2Ready=true
            if (player1Ready){
                allowPlaying=false
                playAnimation()
            }
        }
    }

    private fun setSelectedIcon(){
        when(selectionP1){
            "rock" -> binding?.ivIconP1?.setImageResource(R.drawable.rock)
            "paper" -> binding?.ivIconP1?.setImageResource(R.drawable.paper)
            "scissor" -> binding?.ivIconP1?.setImageResource(R.drawable.scissor)
        }
        when(selectionP2){
            "rock" -> binding?.ivIconP2?.setImageResource(R.drawable.rock)
            "paper" -> binding?.ivIconP2?.setImageResource(R.drawable.paper)
            "scissor" -> binding?.ivIconP2?.setImageResource(R.drawable.scissor)
        }
    }

    private fun getResult():String{
        return if (selectionP1 == selectionP2)
            "tie"
        else if(selectionP1=="rock" && selectionP2=="scissor" || selectionP1=="paper" && selectionP2=="rock" || selectionP1=="scissor" && selectionP2=="paper")
            "P1"
        else
            "P2"
    }

    private fun setScore(){
        if(getResult() == "tie"){
            binding?.tvP1Status?.text="tie"
            binding?.tvP2Status?.text="tie"
        }
        else if(getResult()=="P1"){
            binding?.tvP1Status?.text="win"
            binding?.tvP2Status?.text="lose"
            scoreP1++
            when(scoreP1){
                1->binding?.ivP1Star1?.setImageResource(R.drawable.star)
                2->binding?.ivP1Star2?.setImageResource(R.drawable.star)
                3->binding?.ivP1Star3?.setImageResource(R.drawable.star)
            }
        }
        else{
            binding?.tvP1Status?.text="lose"
            binding?.tvP2Status?.text="win"
            scoreP2++
            when(scoreP2){
                1->binding?.ivP2Star1?.setImageResource(R.drawable.star)
                2->binding?.ivP2Star2?.setImageResource(R.drawable.star)
                3->binding?.ivP2Star3?.setImageResource(R.drawable.star)
            }
        }
    }

    private fun getPlayerName(){
        val nameDialog = Dialog(this)
        nameDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        nameDialog.setContentView(R.layout.player_name_dialog)
        nameDialog.findViewById<Button>(R.id.btnOk).setOnClickListener{
            val name1 = nameDialog.findViewById<EditText>(R.id.et_Name1).text
            val name2 = nameDialog.findViewById<EditText>(R.id.et_Name2).text

            if (name1.isNotEmpty() && name2.isNotEmpty()){
                playerNameone = name1.toString()
                playerNametwo = name2.toString()
                binding?.playerName1?.text= playerNameone
                binding?.playerName2?.text = playerNametwo
                nameDialog.cancel()
            }
            else {
                Toast.makeText(this,"Enter both players name", Toast.LENGTH_SHORT).show()
            }
        }
        nameDialog.show()
        savePlayerNames(playerNameone, playerNametwo)
    }

    private fun savePlayerNames(player1Name: String, player2Name: String) {
        // Get SharedPreferences.Editor
        val editor = sharedPrefs.edit()

        // Write player names to SharedPreferences
        editor.putString("player1Name", player1Name)
        editor.putString("player2Name", player2Name)

        // Commit or apply the changes
        editor.apply()
    }

    private fun endGame(){
        if(scoreP1 == 3 || scoreP2 == 3){
            var winner = if (scoreP1 == 3)
                playerNameone
            else
                playerNametwo
            val intent =Intent(this,FinishActivity::class.java)
            intent.putExtra("name",winner)
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        setTime = null
    }

    private fun quitGame() {
        val instDialog = Dialog(this)
        instDialog.setContentView(R.layout.quit_alert)
        instDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        instDialog.findViewById<Button>(R.id.btnNo).setOnClickListener{
            instDialog.cancel()
        }
        instDialog.findViewById<Button>(R.id.btnYes).setOnClickListener{
            finish()
        }
        instDialog.show()

    }

}