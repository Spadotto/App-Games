package com.example.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fragments.dataBase.Data
import com.example.fragments.databinding.FragmentHomeBinding

class HomeFragment: Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.welcomeTV.text = "Hello " + Data.onPlayer!!.username
        binding.game1winsTV.text = "Wins: " + Data.onPlayer!!.games[0].wins
        binding.game1deathsTV.text = "Deaths: " + Data.onPlayer!!.games[0].deaths
        binding.game1drawsTV.text = "Draws: " + Data.onPlayer!!.games[0].draws

        binding.game2winsTV.text = "Best Score: " + Data.onPlayer!!.games[1].wins + " clicks"
        binding.game2deathsTV.text = "Last Score: " + Data.onPlayer!!.games[1].deaths + " clicks"

        binding.game3winsTV.text = "Best Score: " + Data.onPlayer!!.games[2].wins
        binding.game3deathsTV.text = "Last Score: " + Data.onPlayer!!.games[2].deaths

    }

}