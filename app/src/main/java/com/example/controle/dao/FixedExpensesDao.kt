package com.example.controle.dao

import androidx.room.*
import com.example.controle.model.FixedExpenses


@Dao
interface FixedExpensesDao {

    @Insert
    suspend fun addFixedExpenses(fixedExpenses: FixedExpenses)

    @Query("SELECT * FROM FixedExpenses ORDER BY id DESC")
    suspend fun getAllFixedExpenses(): List<FixedExpenses>

    @Insert
    suspend fun addMultpleFixedExpenses(vararg fixedExpenses: FixedExpenses)

    @Update
    suspend fun updateFixedExpenses(fixedExpenses: FixedExpenses)

    @Delete
    suspend fun deleteFixedExpenses(fixedExpenses: FixedExpenses)


}