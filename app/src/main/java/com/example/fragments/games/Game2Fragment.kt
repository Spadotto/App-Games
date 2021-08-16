package com.example.fragments.games

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fragments.R
import com.example.fragments.dataBase.Data
import com.example.fragments.databinding.FragmentGame2Binding

class Game2Fragment: Fragment(R.layout.fragment_game2) {

    private var _binding: FragmentGame2Binding? = null
    private val binding get() = _binding!!
    private lateinit var buttons: List<ImageButton>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null
    private var clicks: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGame2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val images = mutableListOf(
            R.drawable.butterfly,
            R.drawable.bat,
            R.drawable.parrot,
            R.drawable.panda,
            R.drawable.dove,
            R.drawable.spider
        )

        images.addAll(images)
        images.shuffle()

        buttons = listOf(binding.imageButton1, binding.imageButton2, binding.imageButton3, binding.imageButton4, binding.imageButton5, binding.imageButton6,
            binding.imageButton7, binding.imageButton8, binding.imageButton9, binding.imageButton10, binding.imageButton11, binding.imageButton12)

        cards = buttons.indices.map {
                index ->
            MemoryCard(images[index])
        }

        buttons.forEachIndexed {
                index, button ->
            button.setOnClickListener {
                Log.i(TAG, "button clicked!!")
                clicks += 1
                updateModels(index)
                updateViews()
                checkCards()
            }
        }

        binding.restartButton.setOnClickListener {
            var idx = 0
            images.shuffle()
            clicks = 0

            for (i in cards) {
                cards[idx].isMatched = false
                idx += 1
            }
            restoreCards()
            updateViews()

            buttons = listOf(binding.imageButton1, binding.imageButton2, binding.imageButton3, binding.imageButton4, binding.imageButton5, binding.imageButton6,
                binding.imageButton7, binding.imageButton8, binding.imageButton9, binding.imageButton10, binding.imageButton11, binding.imageButton12)

            cards = buttons.indices.map {
                    index ->
                MemoryCard(images[index])
            }

            buttons.forEachIndexed {
                    index, button ->
                button.setOnClickListener {
                    Log.i(TAG, "button clicked!!")
                    clicks += 1
                    updateModels(index)
                    updateViews()
                    checkCards()
                }
            }
        }
    }

    private fun checkCards() {
        var idx = 0
        var aux = 0
        for (i in cards) {
            if (cards[idx].isMatched) {
                aux += 1
            }
            if (aux == 12) {
                Data.onPlayer!!.games[1].deaths = clicks
                if (Data.onPlayer!!.games[1].wins > clicks || Data.onPlayer!!.games[1].wins == 0) {
                    Data.onPlayer!!.games[1].wins = clicks
                }
            }
            idx += 1
        }
    }

    private fun updateViews() {
        cards.forEachIndexed { index, card ->
            val button = buttons[index]
            button.setImageResource(if (card.isFaceUp) card.identifier else R.drawable.gamecontroller)
        }
    }

    private fun updateModels(position: Int) {
        val card = cards[position]

        if (card.isFaceUp) {
            Toast.makeText(activity, "Invalid move!", Toast.LENGTH_SHORT).show()
            return
        }

        if (indexOfSingleSelectedCard == null) {
            restoreCards()
            indexOfSingleSelectedCard = position
        } else {
            checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }
        card.isFaceUp = !card.isFaceUp
    }

    private fun restoreCards() {
        for (card in cards) {
            if (!card.isMatched) {
                card.isFaceUp = false
            }
        }
    }

    private fun checkForMatch(position1: Int, position2: Int) {
        if (cards[position1].identifier == cards[position2].identifier) {
            cards[position1].isMatched = true
            cards[position2].isMatched = true
        }
    }
}
