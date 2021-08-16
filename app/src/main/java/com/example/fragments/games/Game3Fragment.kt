package com.example.fragments.games

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.fragments.R
import com.example.fragments.dataBase.Data
import com.example.fragments.databinding.FragmentGame3Binding
import kotlin.random.Random as Random

class Game3Fragment: Fragment(R.layout.fragment_game3) {

    private var _binding: FragmentGame3Binding? = null
    private val binding get() = _binding!!
    var score: Int = 0
    var imageArray = ArrayList<ImageView>()
    var handler: Handler = Handler()
    var runnable: Runnable = Runnable { }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGame3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.bestTV.text = "Best Score: ${Data.onPlayer!!.games[2].wins}"
        binding.ivApple.setOnClickListener { increaseScore(view) }
        binding.ivBanana.setOnClickListener { increaseScore(view) }
        binding.ivCherry.setOnClickListener { increaseScore(view) }
        binding.ivGrapes.setOnClickListener { increaseScore(view) }
        binding.ivKiwi.setOnClickListener { increaseScore(view) }
        binding.ivOrange.setOnClickListener { increaseScore(view) }
        binding.ivPear.setOnClickListener { increaseScore(view) }
        binding.ivStrawberry.setOnClickListener { increaseScore(view) }
        binding.ivWatermelon.setOnClickListener { increaseScore(view) }

        score = 0

        imageArray = arrayListOf(binding.ivApple, binding.ivBanana, binding.ivCherry, binding.ivGrapes, binding.ivKiwi, binding.ivOrange, binding.ivPear, binding.ivStrawberry, binding.ivWatermelon)

        hideImages()

        object : CountDownTimer(10000, 1000) {

            override fun onFinish() {

                binding.tvTime.text = "Game Over."
                handler.removeCallbacks(runnable)

                for (image in imageArray) {
                    image.visibility = View.INVISIBLE
                }

                if (Data.onPlayer!!.games[2].wins > score) {
                    Data.onPlayer!!.games[2].deaths = score
                } else {
                    Data.onPlayer!!.games[2].deaths = score
                    Data.onPlayer!!.games[2].wins = score
                }

                binding.tvScore.text = "Score: $score"

            }

            override fun onTick(p0: Long) {
                binding.tvTime.text = "Time: " + p0 / 1000
            }

        }.start()

        binding.restartButton.setOnClickListener {

            binding.bestTV.text = "Best Score: ${Data.onPlayer!!.games[2].wins}"
            score = 0
            binding.tvScore.text = "Score: $score"
            hideImages()
            binding.tvTime.text = "Time: " +10000/1000

            for (image in imageArray) {
                image.visibility = View.INVISIBLE
            }

            object : CountDownTimer(10000, 1000) {

                override fun onFinish() {

                    binding.tvTime.text = "Game Over."
                    handler.removeCallbacks(runnable)

                    for (image in imageArray) {
                        image.visibility = View.INVISIBLE
                    }

                    if (Data.onPlayer!!.games[2].wins > score) {
                        Data.onPlayer!!.games[2].deaths = score
                    } else {
                        Data.onPlayer!!.games[2].deaths = score
                        Data.onPlayer!!.games[2].wins = score
                    }

                    binding.tvScore.text = "Score: $score"

                }

                override fun onTick(p0: Long) {
                    binding.tvTime.text = "Time: " + p0 / 1000
                }

            }.start()
        }

    }

    fun hideImages() {

        runnable = object : Runnable {
            override fun run() {
                for (image in imageArray) {
                    image.visibility = View.INVISIBLE
                }

                val index = Random.nextInt(8 - 0)
                imageArray[index].visibility = View.VISIBLE

                handler.postDelayed(runnable, 500)
            }

        }
        handler.post(runnable)
    }

    fun increaseScore(view: View) {
        score++
        binding.tvScore.text = "Score: " + score
    }

}
