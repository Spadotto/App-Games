package com.example.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fragments.dataBase.Data
import com.example.fragments.dataBase.Games
import com.example.fragments.dataBase.Players
import com.example.fragments.databinding.FragmentRegisterBinding

class RegisterFragment: Fragment(R.layout.fragment_register) {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmButton.setOnClickListener {
            val games = ArrayList<Games>()
            games.addAll(listOf(Games("game1", 0, 0, 0), Games("game2", 0, 0, 0), Games("game3", 0, 0, 0)))
            val email = binding.emailET.text.toString()
            val username = binding.usernameET.text.toString()
            val password = binding.passwordET.text.toString()
            val user = Players(email, username, password)
            user.games.addAll(games)

            when {
                Data.getPlayer(user) != null -> {
                    Toast.makeText(activity, "Email alredy in use!", Toast.LENGTH_SHORT).show()
                }
                email.isEmpty() -> {
                    Toast.makeText(activity, "Email can't be blank!", Toast.LENGTH_SHORT).show()
                }
                username.isEmpty() -> {
                    Toast.makeText(activity, "Username can't be blank!", Toast.LENGTH_SHORT).show()
                }
                password.isEmpty() -> {
                    Toast.makeText(activity, "Password can't be blank!", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Data.addPlayer(user)
                    val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                    findNavController().navigate(action)
                }
            }

        }
    }
}
