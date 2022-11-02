package com.mjolner.lookfor

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mjolner.lookfor.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private var listOfImages: MutableList<ImageView>? = mutableListOf()
    var searchIndex = 0
    var score = 0
    private var timer: CountDownTimer? =null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        for ( i in 0..47){
            val viewImage = "imageView${i+1}"
            val temp = resources.getIdentifier(viewImage, "id", packageName)
            var imageView = findViewById<ImageView>(temp)
            listOfImages?.add(imageView)
        }

    }


    private fun timerStart(){
        timer?.cancel()
        timer = object: CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                binding.timerText.text = "${millisUntilFinished / 1000}s"
            }

            override fun onFinish() {

                binding.timerText.text = "Done!"
                val intent = Intent(this@GameActivity, EndGameActivity::class.java)
                intent.putExtra("score",score)
                startActivity(intent)
                finish()
            }
        }.start()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@GameActivity, MainActivity::class.java)
        timer?.cancel()
        startActivity(intent)
    }


    fun clickToCheckIndex(view: View){
        val imageTag = view.tag.toString().toInt()
        if (imageTag== searchIndex){
            score += 1
            binding.scoreText.text = score.toString()
        }
        genNewTask()
    }

    fun genNewTask(){
        setBackDefaultImage(searchIndex)
        getRndNumber()
        showTableView(searchIndex)
    }


    private fun getRndNumber(){
        searchIndex = (0..47).random()
    }

    private fun showTableView(number:Int){
        listOfImages!![number].setImageResource(R.drawable.pumpkin_1)
    }

    private fun setBackDefaultImage(number:Int){
        listOfImages!![number].setImageResource(R.drawable.pumpkin)
    }
}