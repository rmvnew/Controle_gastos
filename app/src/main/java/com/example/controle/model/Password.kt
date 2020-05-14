package com.example.controle.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Password(
    val password: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}