package com.example.fragments.dataBase

import java.util.ArrayList

object Data {

    val players = ArrayList<Players>()
    var onPlayer: Players? = null

    fun addPlayer(player: Players) {
        players.add(player)
    }

    fun getPlayer(player: Players): Players? {
        var index = 0
        for (i in players) {
            if (player.email == players[index].email) {

                return onPlayer
            }
            index += 1
        }
        return null
    }

    fun findPlayer(email: String): String {
        var index = 0
        for (i in players) {
            if (email == players[index].email) {
                onPlayer = players[index]
                return players[index].username
            }
            index += 1
        }
        return null.toString()
    }

}
