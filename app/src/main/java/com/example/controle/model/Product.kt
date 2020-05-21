package com.example.controle.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Product(

    val nome: String,
    val data: String,
    val consumer: String,
    val valor: String

) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}