package com.novlesh.exp4

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var soundPool: SoundPool
    private var soundId: Int = 0
    private lateinit var rollAnimation: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val diceImage: ImageView = findViewById(R.id.diceImage)
        val rollButton: Button = findViewById(R.id.rollButton)

        // Initialize the SoundPool
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_GAME)
            .build()

        soundPool = SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            .build()

        // Load the sound file from the raw folder
        soundId = soundPool.load(this, R.raw.dice_roll, 1)

        // Load the roll animation
        rollAnimation = AnimationUtils.loadAnimation(this, R.anim.roll_animation)

        rollButton.setOnClickListener(View.OnClickListener {
            // Play the rolling sound effect
            soundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f)

            // Start the roll animation
            diceImage.startAnimation(rollAnimation)

            // Delay changing the dice face image to allow time for the animation
            diceImage.postDelayed({
                // Generate a random number between 1 and 6 (inclusive)
                val randomDiceNumber = Random.nextInt(1, 7)

                // Get the resource ID of the corresponding dice face image
                val drawableResource = when (randomDiceNumber) {
                    1 -> R.drawable.dice1
                    2 -> R.drawable.dice2
                    3 -> R.drawable.dice3
                    4 -> R.drawable.dice4
                    5 -> R.drawable.dice5
                    else -> R.drawable.dice6
                }

                // Set the dice face image after the animation completes
                diceImage.setImageResource(drawableResource)
            }, rollAnimation.duration)
        })
    }
}
