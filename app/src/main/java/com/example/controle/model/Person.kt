package com.example.controle.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Person(

    val nome: String,
    val cpf: String,
    val dataNascimento: String,
    val telefone: String,
    val email: String

) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}