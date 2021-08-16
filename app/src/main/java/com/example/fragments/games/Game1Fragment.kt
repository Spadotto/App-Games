package com.example.fragments.games

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.fragments.R
import com.example.fragments.dataBase.Data
import com.example.fragments.databinding.FragmentGame1Binding
import kotlin.random.Random

class Game1Fragment: Fragment(R.layout.fragment_game1) {

    private var _binding: FragmentGame1Binding? = null
    private val binding get() = _binding!!
    private var btnList = ArrayList<Button>()
    var fimJogo: Boolean = false
    var jogador: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGame1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnList = arrayListOf<Button>(binding.btn1, binding.btn2, binding.btn3, binding.btn4, binding.btn5, binding.btn6, binding.btn7, binding.btn8, binding.btn9)
        binding.btnRestart.visibility = View.GONE
        for (x in 0..btnList.size-1) {
            btnList[x].setOnClickListener {
                joga(it as Button)
            }
        }
        binding.btnRestart.setOnClickListener {
            restart()
        }
    }

    private fun joga(btn: Button) {
        marca(btn)
        if (jogador<9 && !checkGame()) jogaComp()
    }

    private fun marca(btn: Button) {
        if(jogador % 2 != 0) {
            btn.text = "X"
        } else {
            btn.text = "O"
        }
        btn.isClickable = false
        checkGame()
        jogador++
    }

    private fun jogaComp() {
        if(checkPos() != -1) {
            Log.d("Posicao: ",checkPos().toString())
            marca(btnList[checkPos()])
        } else {
            var bol: Boolean = true
            do {
                var rand = Random.nextInt(9)
                if (btnList[rand].text == "") {
                    marca(btnList[rand])
                    bol = false
                } else {
                    bol = true
                }
            } while (bol)
        }
    }

    private fun checkPos(): Int {
        for (x in 0..2) {
            if ( btnList[x].text != "" || btnList[x+3].text != "" || btnList[x+6].text != "") {
                if (btnList[x].text == btnList[x + 3].text && btnList[x + 6].text == "") {
                    return x + 6
                }
                if (btnList[x + 6].text == btnList[x + 3].text && btnList[x].text == "") {
                    return x
                }
                if (btnList[x + 6].text == btnList[x].text && btnList[x + 3].text == "") {
                    return x + 3
                }
            }
        }
        var numb = listOf<Int>(0,3,6)

        for (x in numb) {
            if (btnList[x].text != "" || btnList[x+2].text != "" || btnList[x+2].text != "") {
                if (btnList[x].text == btnList[x + 1].text && btnList[x + 2].text == "") {
                    return x + 2
                }
                if (btnList[x + 2].text == btnList[x + 1].text && btnList[x].text == "") {
                    return x
                }
                if (btnList[x].text == btnList[x + 2].text && btnList[x + 1].text == "") {
                    return x + 1
                }
            }
        }
        if (btnList[0].text != "" && btnList[0].text == btnList[4].text && btnList[8].text == "") return 8
        if (btnList[4].text != "" && btnList[4].text == btnList[8].text && btnList[0].text == "") return 0
        if (btnList[0].text != "" && btnList[0].text == btnList[4].text && btnList[4].text == "") return 4
        if (btnList[2].text != "" && btnList[2].text == btnList[4].text && btnList[6].text == "") return 6
        if (btnList[2].text != "" && btnList[2].text == btnList[6].text && btnList[4].text == "") return 4
        if (btnList[4].text != "" && btnList[4].text == btnList[6].text && btnList[2].text == "") return 2
        return -1
    }

    private fun restart() {
        for (x in 0..btnList.size-1) {
            btnList[x].text = ""
            btnList[x].isClickable = true
        }
        binding.tvResult.text = ""
        jogador = 1
        fimJogo = false
        binding.btnRestart.visibility = View.GONE
    }

    private fun checkGame(): Boolean {
        val numb = listOf<Int>(0,3,6)
        for (x in numb) {
            if (btnList[x].text != "" && btnList[x].text == btnList[x+1].text && btnList[x+1].text == btnList[x + 2].text) {
                winner()
                return true
            }
        }
        for (x in 0..2) {
            if (btnList[x].text != "" && btnList[x].text == btnList[x+3].text && btnList[x+3].text == btnList[x+6].text) {
                winner()
                return true
            }
        }
        if ((btnList[0].text != "" && btnList[0].text == btnList[4].text && btnList[4].text == btnList[8].text)
            || (btnList[2].text != "" && btnList[2].text == btnList[4].text && btnList[4].text == btnList[6].text)) {
            winner()
            return true
        } else if (jogador == 9) {
            binding.tvResult.text = "A Tie!!"
            Data.onPlayer!!.games[0].draws += 1
            binding.btnRestart.visibility = View.VISIBLE
            return true
        }
        return false
    }

    private fun winner() {
        if (!fimJogo) {
            if (jogador % 2 != 0) {
                Data.onPlayer!!.games[0].wins += 1
                binding.tvResult.text = "You Won :)"
            } else {
                Data.onPlayer!!.games[0].deaths += 1
                binding.tvResult.text = "You Lost :("
            }
            endGame()
            binding.btnRestart.visibility = View.VISIBLE
            fimJogo = true
        }
    }

    private fun endGame() {
        for (x in 0..btnList.size-1) {
            btnList[x].isClickable = false
        }
    }

}