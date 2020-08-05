package com.example.controle.model

import androidx.room.Entity
import androidx.room.PrimaryKey

import java.io.Serializable

@Entity
class FixedExpenses(

    val expenseName : String,
    val expenseRegistration : String,
    val expenseValue : String,
    val expenseUrl : String

): Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}